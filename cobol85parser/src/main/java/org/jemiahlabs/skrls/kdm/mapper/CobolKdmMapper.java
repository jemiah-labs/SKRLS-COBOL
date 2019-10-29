package org.jemiahlabs.skrls.kdm.mapper;

import java.lang.reflect.InvocationTargetException;

import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.kdm.mapper.division.DivisionHandler;
import org.jemiahlabs.skrls.kdm.models.KDMSegment;

import io.proleap.cobol.asg.metamodel.CompilationUnit;

public class CobolKdmMapper {
	private Producer producer;
	private DivisionHandler handlerStart;
	private DivisionHandler handlerEnd;
	
	public CobolKdmMapper(Producer producer) {
		this.producer = producer;
	}
	
	public KDMSegment process(CompilationUnit compilationUnit) {
		KDMSegment model = new KDMSegment();
		
		if(handlerStart != null) {
			handlerStart.process(compilationUnit, model);
		} else {
			producer.emitErrorMessage("KDM failed mapping");
		}
		
		return model;
	}
	
	public void addHandlerDefinition(Class<? extends DivisionHandler> handlerDefinition) {
		if(handlerStart == null) {
			handlerStart = resolvehandler(handlerDefinition);
			handlerEnd = handlerStart;
		} else {
			DivisionHandler handlerCurrent = resolvehandler(handlerDefinition);
			handlerEnd.setNextHandler(handlerCurrent);
			handlerEnd = handlerCurrent;
		}
	}
	
	private DivisionHandler resolvehandler(Class<? extends DivisionHandler> handlerDefinition) {
		try {
			return createHandlerDivision(handlerDefinition);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			producer.emitWarningMessage("failed load Handler Definition: " + e.getMessage());
			return null;
		}
	}
	
	private DivisionHandler createHandlerDivision(Class<? extends DivisionHandler> handlerDefinition) throws InstantiationException, 
		IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return handlerDefinition.getConstructor(Producer.class).newInstance(producer);
	}
	
}
