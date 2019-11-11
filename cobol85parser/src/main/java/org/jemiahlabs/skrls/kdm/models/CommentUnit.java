package org.jemiahlabs.skrls.kdm.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("commentUnit")
public class CommentUnit {
	@XStreamAsAttribute
	private String text;
	
	public CommentUnit(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
