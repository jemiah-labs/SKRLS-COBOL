/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.screen.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.ScreenDescriptionLightClauseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.screen.LightClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class LightClauseImpl extends CobolDivisionElementImpl implements LightClause {

	protected ScreenDescriptionLightClauseContext ctx;

	protected LightClauseType lightClauseType;

	public LightClauseImpl(final ProgramUnit programUnit, final ScreenDescriptionLightClauseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public LightClauseType getLightClauseType() {
		return lightClauseType;
	}

	@Override
	public void setLightClauseType(final LightClauseType lightClauseType) {
		this.lightClauseType = lightClauseType;
	}

}
