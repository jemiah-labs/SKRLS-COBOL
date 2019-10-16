package org.jemiahlabs.skrls.cobol85;

import org.jemiahlabs.skrls.core.Nameable;

public class NameableImpl implements Nameable {

	@Override
	public String getNameProduct() {
		return "SKRLS-COBOL";
	}
	
	@Override
	public String getVersion() {
		return "V1.0.0";
	}

	@Override
	public String getTargetLanguage() {
		return "COBOL85";
	}

	@Override
	public String[] getAuthors() {
		String authors[] = {"Alan M.E <alanmarquez@outlook.com>", "Jose L.C <ludiancohen@gmail.com>"};
		return authors;
	}

	@Override
	public String getDescription() {
		return "";
	}
}
