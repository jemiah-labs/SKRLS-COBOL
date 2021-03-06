/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.sort.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.SortCollatingNationalContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.sort.National;

public class NationalImpl extends CobolDivisionElementImpl implements National {

	protected Call alphabetCall;

	protected final SortCollatingNationalContext ctx;

	public NationalImpl(final ProgramUnit programUnit, final SortCollatingNationalContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public Call getAlphabetCall() {
		return alphabetCall;
	}

	@Override
	public void setAlphabetCall(final Call alphabetCall) {
		this.alphabetCall = alphabetCall;
	}

}
