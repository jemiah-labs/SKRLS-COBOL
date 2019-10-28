package org.jemiahlabs.skrls.kdm.mapper.division.impl;

import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.kdm.mapper.division.DivisionHandler;
import org.jemiahlabs.skrls.kdm.models.KDMSegment;

import io.proleap.cobol.asg.metamodel.CompilationUnit;

public class ProcedureDivisionHandler extends DivisionHandler {

	public ProcedureDivisionHandler(Producer producer, DivisionHandler nextHandler) {
		super(producer, nextHandler);
	}
	
	public ProcedureDivisionHandler(Producer producer) {
		super(producer);
	}
	
	@Override
	public void process(CompilationUnit compilationUnit, KDMSegment model) {
		getProducerMessage().emitInfoMessage("Procedure Division Extracting");
		
		if(isNextHandler())
			getNextHandler().process(compilationUnit, model);
	}

}
