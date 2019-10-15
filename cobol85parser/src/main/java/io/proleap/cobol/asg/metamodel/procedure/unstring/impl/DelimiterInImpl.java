/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.unstring.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.UnstringDelimiterInContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.unstring.DelimiterIn;

public class DelimiterInImpl extends CobolDivisionElementImpl implements DelimiterIn {

	protected final UnstringDelimiterInContext ctx;

	protected Call delimiterInCall;

	public DelimiterInImpl(final ProgramUnit programUnit, final UnstringDelimiterInContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public Call getDelimiterInCall() {
		return delimiterInCall;
	}

	@Override
	public void setDelimiterInCall(final Call delimiterInCall) {
		this.delimiterInCall = delimiterInCall;
	}

}
