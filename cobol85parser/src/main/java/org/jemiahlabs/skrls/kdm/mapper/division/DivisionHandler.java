package org.jemiahlabs.skrls.kdm.mapper.division;

import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.kdm.models.KDMSegment;

import io.proleap.cobol.asg.metamodel.CompilationUnit;

public abstract class DivisionHandler {
	private Producer producer;
	private DivisionHandler nextHandler;
	
	public DivisionHandler(Producer producer) {
		this.producer = producer;
	}
	
	public DivisionHandler(Producer producer, DivisionHandler nextHandler) {
		this.producer = producer;
		this.nextHandler = nextHandler;
	}
	
	public void setNextHandler(DivisionHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
	
	public boolean isNextHandler() {
		return nextHandler != null;
	}
	
	public DivisionHandler getNextHandler() {
		return nextHandler;
	}
	
	public Producer getProducerMessage() {
		return producer;
	}
	
	public abstract void process(CompilationUnit compilationUnit, KDMSegment model);
}
