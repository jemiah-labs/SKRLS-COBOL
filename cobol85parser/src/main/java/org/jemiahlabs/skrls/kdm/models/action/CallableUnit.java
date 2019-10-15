package org.jemiahlabs.skrls.kdm.models.action;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.kdm.models.code.ParameterUnit;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("callableUnit")
public class CallableUnit {
	@XStreamAsAttribute
	private String name;
	@XStreamAsAttribute
	private CallableKind kind;
	@XStreamImplicit
	List<ParameterUnit> parameters;
	
	public CallableUnit(String name) {
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
