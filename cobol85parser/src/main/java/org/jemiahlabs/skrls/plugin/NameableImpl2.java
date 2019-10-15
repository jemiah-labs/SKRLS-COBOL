package org.jemiahlabs.skrls.plugin;

import org.jemiahlabs.skrls.core.Nameable;

public class NameableImpl2 implements Nameable {

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
		return "COBOL";
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
