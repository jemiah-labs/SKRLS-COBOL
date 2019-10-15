/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.receive.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.ReceiveStatusContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.receive.Status;

public class StatusImpl extends CobolDivisionElementImpl implements Status {

	protected final ReceiveStatusContext ctx;

	protected Call statusCall;

	public StatusImpl(final ProgramUnit programUnit, final ReceiveStatusContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public Call getStatusCall() {
		return statusCall;
	}

	@Override
	public void setStatusCall(final Call statusCall) {
		this.statusCall = statusCall;
	}

}
