/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.datadescription.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.DataWithLowerBoundsClauseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.datadescription.WithLowerBoundsClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class WithLowerBoundsClauseImpl extends CobolDivisionElementImpl implements WithLowerBoundsClause {

	protected DataWithLowerBoundsClauseContext ctx;

	protected boolean withLowerBounds;

	public WithLowerBoundsClauseImpl(final ProgramUnit programUnit, final DataWithLowerBoundsClauseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public boolean isWithLowerBounds() {
		return withLowerBounds;
	}

	@Override
	public void setWithLowerBounds(final boolean withLowerBounds) {
		this.withLowerBounds = withLowerBounds;
	}

}
