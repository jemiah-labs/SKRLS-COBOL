/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.accept.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.AcceptFromMnemonicStatementContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.accept.AcceptFromMnemonicStatement;

public class AcceptFromMnemonicImpl extends CobolDivisionElementImpl implements AcceptFromMnemonicStatement {

	protected final AcceptFromMnemonicStatementContext ctx;

	protected Call mnemonicCall;

	public AcceptFromMnemonicImpl(final ProgramUnit programUnit, final AcceptFromMnemonicStatementContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public Call getMnemonicCall() {
		return mnemonicCall;
	}

	@Override
	public void setMnemonicCall(final Call mnemonicCall) {
		this.mnemonicCall = mnemonicCall;
	}

}
