package org.jemiahlabs.skrls.kdm.mapper.division.impl;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.kdm.mapper.division.DivisionHandler;
import org.jemiahlabs.skrls.kdm.models.KDMSegment;
import org.jemiahlabs.skrls.kdm.models.code.DataElement;
import org.jemiahlabs.skrls.kdm.models.code.DataModel;
import org.jemiahlabs.skrls.kdm.models.code.ItemUnit;
import org.jemiahlabs.skrls.kdm.models.code.StorableKind;
import org.jemiahlabs.skrls.kdm.models.code.StorableUnit;
import org.jemiahlabs.skrls.kdm.models.code.ValueElement;
import org.jemiahlabs.skrls.kdm.models.util.Counter;
import org.jemiahlabs.skrls.kdm.models.util.LinkedIterator;

import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.NamedElement;
import io.proleap.cobol.asg.metamodel.data.DataDivision;
import io.proleap.cobol.asg.metamodel.data.database.DataBaseSection;
import io.proleap.cobol.asg.metamodel.data.database.DataBaseSectionEntry;
import io.proleap.cobol.asg.metamodel.data.datadescription.DataDescriptionEntry;
import io.proleap.cobol.asg.metamodel.data.datadescription.DataDescriptionEntryGroup;
import io.proleap.cobol.asg.metamodel.data.datadescription.PictureClause;
import io.proleap.cobol.asg.metamodel.data.datadescription.ValueClause;
import io.proleap.cobol.asg.metamodel.data.file.FileDescriptionEntry;
import io.proleap.cobol.asg.metamodel.data.file.FileSection;
import io.proleap.cobol.asg.metamodel.data.linkage.LinkageSection;
import io.proleap.cobol.asg.metamodel.data.report.FirstDetailClause;
import io.proleap.cobol.asg.metamodel.data.report.FootingClause;
import io.proleap.cobol.asg.metamodel.data.report.HeadingClause;
import io.proleap.cobol.asg.metamodel.data.report.LastDetailClause;
import io.proleap.cobol.asg.metamodel.data.report.PageLimitClause;
import io.proleap.cobol.asg.metamodel.data.report.ReportDescription;
import io.proleap.cobol.asg.metamodel.data.report.ReportGroupDescriptionEntry;
import io.proleap.cobol.asg.metamodel.data.report.ReportSection;
import io.proleap.cobol.asg.metamodel.data.workingstorage.WorkingStorageSection;

public class DataDivisionHandler extends DivisionHandler {
	private StorableKind kindCurrent;
	
	public DataDivisionHandler(Producer producer, DivisionHandler nextHandler) {
		super(producer, nextHandler);
	}
	
	public DataDivisionHandler(Producer producer) {
		super(producer);
	}

	@Override
	public void process(CompilationUnit compilationUnit, KDMSegment model) {
		getMessageProducer().emitInfoMessage("Data Division Extracting.");
		DataDivision dataDivision = compilationUnit.getProgramUnit().getDataDivision();
		if(dataDivision != null) {
			DataModel dataModel = createDataModel(dataDivision);
			model.setDataModel(dataModel);
		}
		if(hasNextHandler())
			getNextHandler().process(compilationUnit, model);
	}
	
	private DataModel createDataModel(DataDivision dataDivision) {
		DataModel dataModel = new DataModel();
		dataModel.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		List<DataElement> sections = new ArrayList<>();
		sections.add(processFileSection(dataDivision.getFileSection()));
		sections.add(processWorkingStorageSection(dataDivision.getWorkingStorageSection()));
		sections.add(processLinkageSection(dataDivision.getLinkageSection()));
		sections.add(processDatabaseSection(dataDivision.getDataBaseSection()));
		sections.add(processReportSection(dataDivision.getReportSection()));
		sections.forEach(section -> {
			if(section != null)
				dataModel.addDataElement(section);
		});
		return dataModel;
	}
	
	
	private DataElement processReportSection(ReportSection reportSection) {
		try {
			List<ReportDescription> entries = reportSection.getReportDescriptions();
			DataElement dataElement = new DataElement();
			dataElement.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
			dataElement.setType("data:DataAction");			
			entries.forEach((reportDescription) -> {
				dataElement.addDataElement(processReportDescription(reportDescription));
			});			
			return dataElement;
		} catch(NullPointerException ex) {
			getMessageProducer().emitInfoMessage("Not Found ReportSection");
		} catch (Exception ex) {
			getMessageProducer().emitInfoMessage(ex.getMessage());
		}
		return null;
	}
	
	private DataElement processDatabaseSection(DataBaseSection databaseSection) {
		try {
			List<DataBaseSectionEntry> entries = databaseSection.getDataBaseSectionEntries();			
			DataElement dataElement = new DataElement();
			dataElement.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
			dataElement.setType("data:DataEvent");			
			entries.forEach((entry) -> {
				String name = entry.getValueStmt1().getCtx().getText();
				String ext = entry.getValueStmt2().getCtx().getText();
				ItemUnit itemUnit = new ItemUnit(name, ext);
				itemUnit.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
				dataElement.addItemUnit(itemUnit);
			});			
			return dataElement;
		} catch(NullPointerException ex) {
			getMessageProducer().emitInfoMessage("Not Found DataBaseSection");
		} catch (Exception ex) {
			getMessageProducer().emitInfoMessage(ex.getMessage());
		}
		return null;
	}
	
	private DataElement processFileSection(FileSection fileSection) {
		try {
			List<FileDescriptionEntry> entries = fileSection.getFileDescriptionEntries();
			
			DataElement dataElement = new DataElement();
			dataElement.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
			dataElement.setType("data:DataAction");
			entries.forEach((fileEntry) -> {
				DataElement dataElementInner = new DataElement(fileEntry.getName());
				dataElementInner.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
				dataElementInner.setType("data:RecordFile");
				dataElement.addDataElement(dataElementInner);
				List<DataDescriptionEntry> entriesInner  = fileEntry.getDataDescriptionEntries();
				LinkedIterator<DataDescriptionEntry> sequence = new LinkedIterator<>(entriesInner);
				kindCurrent = StorableKind.REGISTER;
				highLevelExtractData(sequence, sequence.hasNext() ? sequence.next() : null, dataElementInner);
			});
			return dataElement;
		} catch(NullPointerException ex) {
			getMessageProducer().emitInfoMessage("Not Found FileSection");
		} catch (Exception ex) {
			getMessageProducer().emitInfoMessage(ex.getMessage());
		}
		return null;
	}
	
	private DataElement processWorkingStorageSection(WorkingStorageSection workingStorageSection) {
		try {
			List<DataDescriptionEntry> entries = workingStorageSection
				.getDataDescriptionEntries()
				.get(0)
				.getDataDescriptionEntryContainer()
				.getDataDescriptionEntries();
			
			DataElement dataElement = new DataElement();
			dataElement.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
			dataElement.setType("data:DataAction");
			LinkedIterator<DataDescriptionEntry> sequence = new LinkedIterator<DataDescriptionEntry>(entries);
			kindCurrent = StorableKind.LOCAL;
			highLevelExtractData(sequence, sequence.hasNext() ? sequence.next() : null, dataElement);
			return dataElement;
		} catch(NullPointerException ex) {
			getMessageProducer().emitInfoMessage("Not Found WorkingStorageSection");
		} catch (Exception ex) {
			getMessageProducer().emitInfoMessage(ex.getMessage());
		}		
		return null;
	}
	
	private DataElement processLinkageSection(LinkageSection linkageSection) {
		try {
			List<DataDescriptionEntry> entries = linkageSection.getDataDescriptionEntries();
			
			DataElement dataElement = new DataElement();
			dataElement.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
			dataElement.setType("data:DataAction");
			LinkedIterator<DataDescriptionEntry> current = new LinkedIterator<DataDescriptionEntry>(entries);
			kindCurrent = StorableKind.EXTERNAL;
			highLevelExtractData(current, current.hasNext() ? current.next() : null, dataElement);
			return dataElement;
		} catch(NullPointerException ex) {
			getMessageProducer().emitInfoMessage("Not Found LinkageSection");
		} catch (Exception ex) {
			getMessageProducer().emitInfoMessage(ex.getMessage());
		}
		return null;
	}
	
	private DataElement processReportDescription(ReportDescription reportDescription) {
		DataElement dataElement = new DataElement();
		dataElement.setName(reportDescription.getName());
		dataElement.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		dataElement.setType("data:DataAction");
		
		addReportHeadingIfExist(reportDescription.getReportDescriptionEntry().getHeadingClause(), dataElement);
		addReportFootingIfExist(reportDescription.getReportDescriptionEntry().getFootingClause(), dataElement);
		addReportFirstDetailIfExist(reportDescription.getReportDescriptionEntry().getFirstDetailClause(), dataElement);
		addReportLastDetailIfExist(reportDescription.getReportDescriptionEntry().getLastDetailClause(), dataElement);
		addReportPageLimitIfExist(reportDescription.getReportDescriptionEntry().getPageLimitClause(), dataElement);
		
		if(reportDescription.getRootReportGroupDescriptionEntries().size() > 0) {
			StorableUnit storableUnit = new StorableUnit();
			storableUnit.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
			reportDescription.getRootReportGroupDescriptionEntries().forEach(entry -> {
				List<ReportGroupDescriptionEntry> entries = entry.getReportGroupDescriptionEntries();
				if(entries != null && entries.size() > 0) {
					extractReportGroupDescriptionEntries(entry, storableUnit);
				} else {
					extractReportGroupDescriptionEntry(entry, storableUnit);
				}
			});
			dataElement.addStorableUnit(storableUnit);
		}
		
		return dataElement;
	}
	
	private void addReportHeadingIfExist(HeadingClause clause, DataElement root) {
		if(clause == null) return;
		
		ItemUnit itemUnit = new ItemUnit("HEADING", extractTokens(clause.getCtx()));
		itemUnit.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		itemUnit.setValueElement(new ValueElement(""+clause.getHeadingIntegerLiteral().getValue()));
		root.addItemUnit(itemUnit);
	}
	
	private void addReportFootingIfExist(FootingClause clause, DataElement root) {
		if(clause == null) return;
		
		ItemUnit itemUnit = new ItemUnit("FOOTING", extractTokens(clause.getCtx()));
		itemUnit.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		itemUnit.setValueElement(new ValueElement(""+clause.getFootingIntegerLiteral().getValue()));
		root.addItemUnit(itemUnit);
	}
	
	private void addReportFirstDetailIfExist(FirstDetailClause clause, DataElement root) {
		if(clause == null) return;
		
		ItemUnit itemUnit = new ItemUnit("FIRST_DETAIL", extractTokens(clause.getCtx()));
		itemUnit.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		itemUnit.setValueElement(new ValueElement(""+clause.getFirstDetailIntegerLiteral().getValue()));
		root.addItemUnit(itemUnit);
	}
	
	private void addReportLastDetailIfExist(LastDetailClause clause, DataElement root) {
		if(clause == null) return;
		
		ItemUnit itemUnit = new ItemUnit("LAST_DETAIL", extractTokens(clause.getCtx()));
		itemUnit.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		itemUnit.setValueElement(new ValueElement(""+clause.getLastDetailIntegerLiteral().getValue()));
		root.addItemUnit(itemUnit);
	}
	
	private void addReportPageLimitIfExist(PageLimitClause clause, DataElement root) {
		if(clause == null) return;
		
		ItemUnit itemUnit = new ItemUnit("PAGE_LIMITS", extractTokens(clause.getCtx()));
		itemUnit.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		root.addItemUnit(itemUnit);
	}
	
	private void extractReportGroupDescriptionEntries(ReportGroupDescriptionEntry reportGroupDescriptionEntry, StorableUnit root) {
		StorableUnit storableUnit = new StorableUnit(reportGroupDescriptionEntry.getName());
		storableUnit.setKind(StorableKind.LOCAL);
		storableUnit.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		root.addStorableUnit(storableUnit);
		
		reportGroupDescriptionEntry.getReportGroupDescriptionEntries().forEach(entry -> {
			List<ReportGroupDescriptionEntry> reportGroupDescriptionEntryInner = entry.getReportGroupDescriptionEntries();
			if(reportGroupDescriptionEntryInner != null && reportGroupDescriptionEntryInner.size() > 0) {
				extractReportGroupDescriptionEntries(entry, storableUnit);
			} else {
				extractReportGroupDescriptionEntry(entry, storableUnit);
			}
		});		
	}
	
	private void extractReportGroupDescriptionEntry(ReportGroupDescriptionEntry reportGroupDescriptionEntry, StorableUnit root) {
		ItemUnit itemUnit = createItemUnit(reportGroupDescriptionEntry);		
		root.addItemUnit(itemUnit);
	}
	
	private void highLevelExtractData(LinkedIterator<DataDescriptionEntry> sequence, DataDescriptionEntry after, DataElement store) {
		DataDescriptionEntry currentValue = sequence.CurrentValue();
		if(currentValue == null) {
			return;
		} else if(after == null || currentValue.getLevelNumber() == after.getLevelNumber()) {
			store.addItemUnit(createItemUnit(currentValue));
			highLevelExtractData(sequence, sequence.hasNext() ? sequence.next() : null, store);
		} else if(currentValue.getLevelNumber() < after.getLevelNumber()) {
			StorableUnit newStore = createStorableUnit(currentValue);
			store.addStorableUnit(newStore);
			lowLevelExtractData(sequence, sequence.hasNext() ? sequence.next() : null, newStore);
			highLevelExtractData(sequence, sequence.hasNext() ? sequence.next() : null, store);
		}
	}
	
	private void lowLevelExtractData(LinkedIterator<DataDescriptionEntry> sequence, DataDescriptionEntry after, StorableUnit store) {
		DataDescriptionEntry currentValue = sequence.CurrentValue();
		if (currentValue == null) {
			return;
		} else if (after == null || currentValue.getLevelNumber() == after.getLevelNumber()) {
			store.addItemUnit(createItemUnit(currentValue));
			lowLevelExtractData(sequence, sequence.hasNext() ? sequence.next() : null, store);
		} else if (currentValue.getLevelNumber() < after.getLevelNumber()) {
			StorableUnit newStore = createStorableUnit(currentValue);
			store.addStorableUnit(newStore);
			lowLevelExtractData(sequence, sequence.hasNext() ? sequence.next() : null, newStore);
		} else if (currentValue.getLevelNumber() > after.getLevelNumber()) {
			store.addItemUnit(createItemUnit(currentValue));
			return;
		}
	}
	
	private ItemUnit createItemUnit(DataDescriptionEntry dataDescriptionEntry) {
		String ext = "";
		PictureClause picture = ((DataDescriptionEntryGroup)dataDescriptionEntry).getPictureClause();
		if(picture != null) {
			ext = extractTokens(picture.getCtx());
		}
		
		ItemUnit itemUnit = new ItemUnit(dataDescriptionEntry.getName(), ext);
		itemUnit.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		ValueClause valueClause = ((DataDescriptionEntryGroup)dataDescriptionEntry).getValueClause();
		if(valueClause != null) {
			String value = valueClause.getValueIntervals().get(0).getCtx().getText();
			ValueElement valueElement = new ValueElement(value);
			valueElement.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
			itemUnit.setValueElement(valueElement);
		}
		return itemUnit;
	}	
	
	private ItemUnit createItemUnit(ReportGroupDescriptionEntry reportGroupDescriptionEntry) {
		String ext = extractTokens(reportGroupDescriptionEntry.getCtx()); 
		ItemUnit itemUnit = new ItemUnit(reportGroupDescriptionEntry.getName(), ext);
		itemUnit.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		if (reportGroupDescriptionEntry.getUsageClause() != null)
			itemUnit.setLabel(reportGroupDescriptionEntry.getUsageClause().getUsageClauseType().name());
		
		
		return itemUnit;
	}
	
	private StorableUnit createStorableUnit(NamedElement namedElement) {
		return createStorableUnit(namedElement, kindCurrent);
	}
	
	private StorableUnit createStorableUnit(NamedElement namedElement, StorableKind storableKind) {
		StorableUnit storableUnit = new StorableUnit(namedElement.getName());
		storableUnit.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		storableUnit.setKind(storableKind);
		return storableUnit;
	}
	
	private String extractTokens(ParserRuleContext parserRuleContext) {
		String[] tokens = (String[]) parserRuleContext.children.stream()
				.map( p -> p.getText())
				.toArray(String[]::new);
		return String.join(" ", tokens).trim();
	}
}
