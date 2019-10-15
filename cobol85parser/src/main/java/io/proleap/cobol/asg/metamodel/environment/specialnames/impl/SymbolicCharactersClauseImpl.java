/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.environment.specialnames.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.SymbolicCharactersClauseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.environment.specialnames.SymbolicCharactersClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class SymbolicCharactersClauseImpl extends CobolDivisionElementImpl implements SymbolicCharactersClause {

	protected final SymbolicCharactersClauseContext ctx;

	protected SymbolicCharactersClauseType symbolicCharactersClauseType;

	public SymbolicCharactersClauseImpl(final ProgramUnit programUnit, final SymbolicCharactersClauseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public SymbolicCharactersClauseType getSymbolicCharactersClauseType() {
		return symbolicCharactersClauseType;
	}

	@Override
	public void setSymbolicCharactersClauseType(final SymbolicCharactersClauseType symbolicCharactersClauseType) {
		this.symbolicCharactersClauseType = symbolicCharactersClauseType;
	}

}
