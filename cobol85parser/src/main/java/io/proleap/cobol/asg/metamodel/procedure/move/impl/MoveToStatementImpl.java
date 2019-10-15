/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.move.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.MoveToSendingAreaContext;
import io.proleap.cobol.Cobol85Parser.MoveToStatementContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.move.MoveToStatement;
import io.proleap.cobol.asg.metamodel.procedure.move.MoveToSendingArea;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class MoveToStatementImpl extends CobolDivisionElementImpl implements MoveToStatement {

	protected final MoveToStatementContext ctx;

	protected List<Call> receivingAreaCalls = new ArrayList<Call>();

	protected MoveToSendingArea moveToSendingArea;
	
	private final Producer producer;

	public MoveToStatementImpl(final ProgramUnit programUnit, final MoveToStatementContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public MoveToSendingArea addMoveToSendingArea(final MoveToSendingAreaContext ctx) {
		MoveToSendingArea result = (MoveToSendingArea) getASGElement(ctx);

		if (result == null) {
			result = new MoveToSendingAreaImpl(programUnit, ctx, producer);

			final ValueStmt sendingAreaValueStmt = createValueStmt(ctx.identifier(), ctx.literal());
			result.setSendingAreaValueStmt(sendingAreaValueStmt);

			moveToSendingArea = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public void addReceivingAreaCall(final Call receivingAreaCall) {
		receivingAreaCalls.add(receivingAreaCall);
	}

	@Override
	public List<Call> getReceivingAreaCalls() {
		return receivingAreaCalls;
	}

	@Override
	public MoveToSendingArea getSendingArea() {
		return moveToSendingArea;
	}
}
