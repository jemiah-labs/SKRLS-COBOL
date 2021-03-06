package org.jemiahlabs.skrls.kdm.models.code;

import org.jemiahlabs.skrls.kdm.models.XmiEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("source") 
public class Source extends XmiEntity {
	@XStreamAsAttribute
	private String language;
	@XStreamAsAttribute
	private String snippet;
	
	public Source(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
}
