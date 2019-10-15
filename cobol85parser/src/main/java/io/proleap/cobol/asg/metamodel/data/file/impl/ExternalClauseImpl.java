/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.file.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.ExternalClauseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.file.ExternalClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class ExternalClauseImpl extends CobolDivisionElementImpl implements ExternalClause {

	protected ExternalClauseContext ctx;

	protected boolean external;

	public ExternalClauseImpl(final ProgramUnit programUnit, final ExternalClauseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public boolean isExternal() {
		return external;
	}

	@Override
	public void setExternal(final boolean external) {
		this.external = external;
	}
}
