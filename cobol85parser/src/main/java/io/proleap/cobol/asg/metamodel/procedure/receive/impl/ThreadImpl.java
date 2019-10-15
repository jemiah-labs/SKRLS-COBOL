/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.receive.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.ReceiveThreadContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.receive.Thread;

public class ThreadImpl extends CobolDivisionElementImpl implements Thread {

	protected final ReceiveThreadContext ctx;

	protected Call threadInCall;

	public ThreadImpl(final ProgramUnit programUnit, final ReceiveThreadContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public Call getThreadInCall() {
		return threadInCall;
	}

	@Override
	public void setThreadInCall(final Call threadInCall) {
		this.threadInCall = threadInCall;
	}

}
