package org.jemiahlabs.skrls.kdm.mapper.division.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.kdm.mapper.division.DivisionHandler;
import org.jemiahlabs.skrls.kdm.models.KDMSegment;
import org.jemiahlabs.skrls.kdm.models.code.DataElement;
import org.jemiahlabs.skrls.kdm.models.code.DataModel;
import org.jemiahlabs.skrls.kdm.models.code.ItemUnit;
import org.jemiahlabs.skrls.kdm.models.code.StorableKind;
import org.jemiahlabs.skrls.kdm.models.code.StorableUnit;
import org.jemiahlabs.skrls.kdm.models.util.LinkedIterator;

import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.data.DataDivision;
import io.proleap.cobol.asg.metamodel.data.datadescription.DataDescriptionEntry;
import io.proleap.cobol.asg.metamodel.data.datadescription.DataDescriptionEntryGroup;
import io.proleap.cobol.asg.metamodel.data.file.FileDescriptionEntry;
import io.proleap.cobol.asg.metamodel.data.file.FileSection;
import io.proleap.cobol.asg.metamodel.data.linkage.LinkageSection;
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
		getProducerMessage().emitInfoMessage("Data Division Extracting.");
		
		DataModel dataModel = createDataModel(compilationUnit.getProgramUnit().getDataDivision());
		model.setDataModel(dataModel);
		
		if(isNextHandler())
			getNextHandler().process(compilationUnit, model);
	}
	
	private DataModel createDataModel(DataDivision dataDivision) {
		DataModel dataModel = new DataModel();
		
		List<DataElement> sections = new ArrayList<DataElement>();
		sections.add(processFileSection(dataDivision.getFileSection()));
		sections.add(processWorkingStorageSection(dataDivision.getWorkingStorageSection()));
		sections.add(processLinkageSection(dataDivision.getLinkageSection()));
		
		sections.forEach(section -> {
			if(section != null)
				dataModel.addDataElement(section);
		});
		
		if(dataModel.getDataElements().size() > 0) dataModel.setLabel("Data Division");
		
		return dataModel;
	}
	
	private DataElement processFileSection(FileSection fileSection) {
		try {
			DataElement dataElement = new DataElement("File Section");
			List<FileDescriptionEntry> entries = fileSection.getFileDescriptionEntries();
			
			entries.forEach((fileEntry) -> {
				DataElement dataElementInner = new DataElement(fileEntry.getName());
				dataElementInner.setType("data:RecordFile");
				dataElement.addDataElement(dataElementInner);
				List<DataDescriptionEntry> entriesInner  = fileEntry.getDataDescriptionEntries();
				
				LinkedIterator<DataDescriptionEntry> sequence = new LinkedIterator<DataDescriptionEntry>(entriesInner);
				kindCurrent = StorableKind.REGISTER;
				highLevelExtractData(sequence, sequence.hasNext() ? sequence.next() : null, dataElementInner);
			});
			
			return dataElement;
			
		} catch(NullPointerException ex) {
			getProducerMessage().emitInfoMessage("Not Found FileSection");
		} catch (Exception ex) {
			getProducerMessage().emitInfoMessage(ex.getMessage());
		}
		
		return null;
	}
	
	private DataElement processWorkingStorageSection(WorkingStorageSection workingStorageSection) {
		try {
			DataElement dataElement = new DataElement("Working Storage Section");
			List<DataDescriptionEntry> entries = workingStorageSection
					.getDataDescriptionEntries()
					.get(0)
					.getDataDescriptionEntryContainer()
					.getDataDescriptionEntries();
			
			LinkedIterator<DataDescriptionEntry> sequence = new LinkedIterator<DataDescriptionEntry>(entries);
			kindCurrent = StorableKind.LOCAL;
			highLevelExtractData(sequence, sequence.hasNext() ? sequence.next() : null, dataElement);
			return dataElement;
			
		} catch(NullPointerException ex) {
			getProducerMessage().emitInfoMessage("Not Found WorkingStorageSection");
		} catch (Exception ex) {
			getProducerMessage().emitInfoMessage(ex.getMessage());
		}
		
		return null;
	}
	
	private DataElement processLinkageSection(LinkageSection linkageSection) {
		try {
			DataElement dataElement = new DataElement("Linkage Section");
			List<DataDescriptionEntry> entries = linkageSection
					.getDataDescriptionEntries();
			
			LinkedIterator<DataDescriptionEntry> current = new LinkedIterator<DataDescriptionEntry>(entries);
			kindCurrent = StorableKind.EXTERNAL;
			highLevelExtractData(current, current.hasNext() ? current.next() : null, dataElement);
			return dataElement;
			
		} catch(NullPointerException ex) {
			getProducerMessage().emitInfoMessage("Not Found LinkageSection");
		} catch (Exception ex) {
			getProducerMessage().emitInfoMessage(ex.getMessage());
		}
		
		return null;
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
		var picture = ((DataDescriptionEntryGroup)dataDescriptionEntry).getPictureClause();
		if(picture != null) {
			String[] tokens = (String[]) picture.getCtx().children.stream()
					.map( p -> p.getText())
					.toArray(String[]::new);
			ext = String.join(" ", tokens);
		}
		
		return new ItemUnit(dataDescriptionEntry.getName(), ext);
	}
	
	private StorableUnit createStorableUnit(DataDescriptionEntry dataDescriptionEntry) {
		StorableUnit storableUnit = new StorableUnit(dataDescriptionEntry.getName());
		storableUnit.setKind(kindCurrent);
		
		return storableUnit;
	}
}
