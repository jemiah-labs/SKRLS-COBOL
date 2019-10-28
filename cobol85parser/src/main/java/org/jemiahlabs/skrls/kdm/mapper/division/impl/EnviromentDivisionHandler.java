package org.jemiahlabs.skrls.kdm.mapper.division.impl;

import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.kdm.mapper.division.DivisionHandler;
import org.jemiahlabs.skrls.kdm.models.KDMSegment;

import io.proleap.cobol.asg.metamodel.CompilationUnit;

public class EnviromentDivisionHandler extends DivisionHandler {

	public EnviromentDivisionHandler(Producer producer, DivisionHandler nextHandler) {
		super(producer, nextHandler);
	}
	
	public EnviromentDivisionHandler(Producer producer) {
		super(producer);
	}
	
	@Override
	public void process(CompilationUnit compilationUnit, KDMSegment model) {
		getProducerMessage().emitInfoMessage("Enviroment Division Extracting");
		
		if(isNextHandler())
			getNextHandler().process(compilationUnit, model);
	}

}
