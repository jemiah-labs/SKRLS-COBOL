/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.report.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.ReportGroupIndicateClauseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.report.GroupIndicateClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class GroupIndicateClauseImpl extends CobolDivisionElementImpl implements GroupIndicateClause {

	protected ReportGroupIndicateClauseContext ctx;

	protected boolean indicate;

	public GroupIndicateClauseImpl(final ProgramUnit programUnit, final ReportGroupIndicateClauseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public boolean isIndicate() {
		return indicate;
	}

	@Override
	public void setIndicate(final boolean indicate) {
		this.indicate = indicate;
	}

}
