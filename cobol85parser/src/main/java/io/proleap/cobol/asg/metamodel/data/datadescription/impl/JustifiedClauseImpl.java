/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.datadescription.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.DataJustifiedClauseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.datadescription.JustifiedClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class JustifiedClauseImpl extends CobolDivisionElementImpl implements JustifiedClause {

	protected DataJustifiedClauseContext ctx;

	protected Justified justified;

	public JustifiedClauseImpl(final ProgramUnit programUnit, final DataJustifiedClauseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public Justified getJustified() {
		return justified;
	}

	@Override
	public void setJustified(final Justified justified) {
		this.justified = justified;
	}

}
