package org.jemiahlabs.skrls.kdm.models.code;

import org.jemiahlabs.skrls.kdm.models.Comments;
import org.jemiahlabs.skrls.kdm.models.XmiEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("codeElement")
public class CodeElement extends XmiEntity {
	@XStreamAsAttribute
	private String name;
	
	private Source source;
	private Comments comments;
	
	public CodeElement() {
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

	public Comments getComments() {
		return comments;
	}

	public void setComments(Comments comments) {
		this.comments = comments;
	}
}
