package org.jemiahlabs.skrls.kdm.models;

import java.util.ArrayList;
import java.util.List;

public class Comments {
	public List<CommentUnit> commentUnits;
	
	public Comments() {
		commentUnits = new ArrayList<CommentUnit>();
	}
	
	public void addCommentUnit(CommentUnit commentUnit) {
		commentUnits.add(commentUnit);
	}

	public List<CommentUnit> getCommentUnits() {
		return commentUnits;
	}
	
}
