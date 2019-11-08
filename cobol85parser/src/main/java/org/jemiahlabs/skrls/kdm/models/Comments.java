package org.jemiahlabs.skrls.kdm.models;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class Comments {
	@XStreamAsAttribute
	private String Author;
	@XStreamImplicit
	private List<CommentUnit> commentUnits;
	
	public Comments() {
		commentUnits = new ArrayList<CommentUnit>();
	}
	
	public void addCommentUnit(CommentUnit commentUnit) {
		commentUnits.add(commentUnit);
	}

	public List<CommentUnit> getCommentUnits() {
		return commentUnits;
	}

	public String getAuthor() {
		return Author;
	}

	public void setAuthor(String author) {
		Author = author;
	}
}
