/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.screen.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.ScreenDescriptionBackgroundColorClauseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.screen.BackgroundColorClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class BackgroundColorClauseImpl extends CobolDivisionElementImpl implements BackgroundColorClause {

	protected ValueStmt colorValueStmt;

	protected ScreenDescriptionBackgroundColorClauseContext ctx;

	public BackgroundColorClauseImpl(final ProgramUnit programUnit,
			final ScreenDescriptionBackgroundColorClauseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public ValueStmt getColorValueStmt() {
		return colorValueStmt;
	}

	@Override
	public void setColorValueStmt(final ValueStmt colorValueStmt) {
		this.colorValueStmt = colorValueStmt;
	}

}
