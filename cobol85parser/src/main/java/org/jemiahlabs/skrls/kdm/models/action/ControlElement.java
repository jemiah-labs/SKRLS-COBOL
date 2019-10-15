package org.jemiahlabs.skrls.kdm.models.action;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.kdm.models.code.CodeElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("controlElement")
public class ControlElement {
	@XStreamAsAttribute
	private String name;
	@XStreamAsAttribute
	private String kind;
	@XStreamImplicit
	private List<CodeElement> codeElements;
	
	public ControlElement(String name) {
		this.name = name;
		codeElements = new ArrayList<CodeElement>();
	}

	public void addCodeElement(CodeElement element) {
		codeElements.add(element);
	}

	public List<CodeElement> getCodeElements() {
		return codeElements;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}
}
