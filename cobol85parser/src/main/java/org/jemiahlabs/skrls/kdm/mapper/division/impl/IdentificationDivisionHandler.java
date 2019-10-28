package org.jemiahlabs.skrls.kdm.mapper.division.impl;

import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.kdm.mapper.division.DivisionHandler;
import org.jemiahlabs.skrls.kdm.models.KDMSegment;

import io.proleap.cobol.asg.metamodel.CompilationUnit;

public class IdentificationDivisionHandler extends DivisionHandler {

	public IdentificationDivisionHandler(Producer producer) {
		super(producer);
	}
	
	public IdentificationDivisionHandler(Producer producer, DivisionHandler nextHandler) {
		super(producer, nextHandler);
	}

	@Override
	public void process(CompilationUnit compilationUnit, KDMSegment model) {
		getProducerMessage().emitInfoMessage("Identification Division Extracting");
		
		if(isNextHandler())
			getNextHandler().process(compilationUnit, model);
	}
}
