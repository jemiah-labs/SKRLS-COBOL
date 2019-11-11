package org.jemiahlabs.skrls.kdm.models.action;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.kdm.models.code.CodeElement;
import org.jemiahlabs.skrls.kdm.models.code.ParameterUnit;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("codeElement")
public class CallableUnit extends CodeElement {
	@XStreamAsAttribute
	private CallableKind kind;
	@XStreamImplicit
	List<ParameterUnit> parameters;
	
	public CallableUnit() {
		setType("action:CallableUnit");
		parameters = new ArrayList<ParameterUnit>();
	}
	
	public void addParameterUnit(ParameterUnit parameterUnit) {
		parameters.add(parameterUnit);
	}
	
	public List<ParameterUnit> getParameterUnits() {
		return parameters;
	}

	public CallableKind getKind() {
		return kind;
	}

	public void setKind(CallableKind kind) {
		this.kind = kind;
	}
}
