/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.evaluate.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.EvaluateAlsoSelectContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.evaluate.AlsoSelect;
import io.proleap.cobol.asg.metamodel.procedure.evaluate.Select;

public class AlsoSelectImpl extends CobolDivisionElementImpl implements AlsoSelect {

	protected final EvaluateAlsoSelectContext ctx;

	protected Select select;

	public AlsoSelectImpl(final ProgramUnit programUnit, final EvaluateAlsoSelectContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public Select getSelect() {
		return select;
	}

	@Override
	public void setSelect(final Select select) {
		this.select = select;
	}

}
