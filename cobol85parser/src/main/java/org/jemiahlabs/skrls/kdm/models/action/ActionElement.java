package org.jemiahlabs.skrls.kdm.models.action;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.kdm.models.code.CodeElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("codeElement")
public class ActionElement extends CodeElement {
	@XStreamAsAttribute
	private String kind;
	@XStreamImplicit
	private List<CodeElement> codeElements;
	@XStreamImplicit
	private List<ControlElement> controlElements;
	@XStreamImplicit
	private List<CallableUnit> callableUnits;
	
	public ActionElement(String name) {
		setName(name);
		codeElements = new ArrayList<CodeElement>();
	}
	
	public void addCodeElement(CodeElement codeElement) {
		codeElements.add(codeElement);
	}
	
	public List<CodeElement> getCodeElements() {
		return codeElements;
	}
	
	public void addControlElement(ControlElement controlElement) {
		controlElements.add(controlElement);
	}
	
	public List<ControlElement> getControlElements() {
		return controlElements;
	}
	
	public void addCallableUnit(CallableUnit callableUnit) {
		callableUnits.add(callableUnit);
	}
	
	public List<CallableUnit> getCallableUnits() {
		return callableUnits;
	}
	
	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
}
