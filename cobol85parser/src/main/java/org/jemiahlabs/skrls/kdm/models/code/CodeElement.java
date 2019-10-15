package org.jemiahlabs.skrls.kdm.models.code;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("codeElement")
public class CodeElement {
	@XStreamAsAttribute
	private String name;
	@XStreamAsAttribute
	private Source source;
	
	public CodeElement(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Source getSource() {
		return source;
	}
	
	public void setSource(Source source) {
		this.source = source;
	}
}
