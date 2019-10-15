/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.report.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.ReportDescriptionFirstDetailClauseContext;
import io.proleap.cobol.asg.metamodel.IntegerLiteral;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.report.FirstDetailClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class FirstDetailClauseImpl extends CobolDivisionElementImpl implements FirstDetailClause {

	protected final ReportDescriptionFirstDetailClauseContext ctx;

	protected IntegerLiteral firstDetailIntegerLiteral;

	public FirstDetailClauseImpl(final ProgramUnit programUnit, final ReportDescriptionFirstDetailClauseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public IntegerLiteral getFirstDetailIntegerLiteral() {
		return firstDetailIntegerLiteral;
	}

	@Override
	public void setFirstDetailIntegerLiteral(final IntegerLiteral firstDetailIntegerLiteral) {
		this.firstDetailIntegerLiteral = firstDetailIntegerLiteral;
	}

}
