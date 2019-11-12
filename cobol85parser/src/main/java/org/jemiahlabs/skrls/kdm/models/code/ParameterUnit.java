package org.jemiahlabs.skrls.kdm.models.code;

import org.jemiahlabs.skrls.kdm.models.XmiEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("parameterUnit")
public class ParameterUnit extends XmiEntity {
	@XStreamAsAttribute
	private String name;
	@XStreamAsAttribute
	private int pos;
	@XStreamAsAttribute
	private ParameterKind kind;
	
	public ParameterUnit(String name, int pos) {
		this.name = name;
		this.pos = pos;
	}
	
	public ParameterUnit(String name) {
		this(name, 0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPos() {
		return pos;
	}
	
	public void setPos(int pos) {
		this.pos = pos;
	}
	
	public ParameterKind getKind() {
		return kind;
	}
	
	public void setKind(ParameterKind kind) {
		this.kind = kind;
	}
}
