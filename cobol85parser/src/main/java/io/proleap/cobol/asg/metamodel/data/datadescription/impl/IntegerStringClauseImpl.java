/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.datadescription.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.DataIntegerStringClauseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.datadescription.IntegerStringClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class IntegerStringClauseImpl extends CobolDivisionElementImpl implements IntegerStringClause {

	protected DataIntegerStringClauseContext ctx;

	protected PrimitiveType primitiveType;

	public IntegerStringClauseImpl(final ProgramUnit programUnit, final DataIntegerStringClauseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public PrimitiveType getPrimitiveType() {
		return primitiveType;
	}

	@Override
	public void setPrimitiveType(final PrimitiveType primitiveType) {
		this.primitiveType = primitiveType;
	}

}
