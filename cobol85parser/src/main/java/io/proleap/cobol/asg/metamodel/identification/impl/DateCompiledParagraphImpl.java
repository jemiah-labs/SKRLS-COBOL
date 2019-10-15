/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.identification.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.DateCompiledParagraphContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.identification.DateCompiledParagraph;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class DateCompiledParagraphImpl extends CobolDivisionElementImpl implements DateCompiledParagraph {

	protected final DateCompiledParagraphContext ctx;

	protected String dateCompiled;

	public DateCompiledParagraphImpl(final ProgramUnit programUnit, final DateCompiledParagraphContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public String getDateCompiled() {
		return dateCompiled;
	}

	@Override
	public void setDateCompiled(final String dateCompiled) {
		this.dateCompiled = dateCompiled;
	}
}
