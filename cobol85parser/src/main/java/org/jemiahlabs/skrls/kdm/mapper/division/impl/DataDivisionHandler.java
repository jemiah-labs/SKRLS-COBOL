package org.jemiahlabs.skrls.kdm.mapper.division.impl;

import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.kdm.mapper.division.DivisionHandler;
import org.jemiahlabs.skrls.kdm.models.KDMSegment;
import org.jemiahlabs.skrls.kdm.models.code.DataElement;
import org.jemiahlabs.skrls.kdm.models.code.DataModel;

import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.data.DataDivision;
import io.proleap.cobol.asg.metamodel.data.file.FileSection;
import io.proleap.cobol.asg.metamodel.data.linkage.LinkageSection;
import io.proleap.cobol.asg.metamodel.data.workingstorage.WorkingStorageSection;

public class DataDivisionHandler extends DivisionHandler {

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
		
		dataModel.addDataElement(processFileSection(dataDivision.getFileSection()));
		dataModel.addDataElement(processWorkingStorageSection(dataDivision.getWorkingStorageSection()));
		dataModel.addDataElement(processLinkageSection(dataDivision.getLinkageSection()));
		
		if(dataModel.getDataElements().size() > 0) dataModel.setLabel("Data Division");
		
		return dataModel;
	}
	
	private DataElement processFileSection(FileSection fileSection) {
		return null;
	}
	
	private DataElement processWorkingStorageSection(WorkingStorageSection workingStorageSection) {
		return null;
	}
	
	private DataElement processLinkageSection(LinkageSection linkageSection) {
		return null;
	}
}
