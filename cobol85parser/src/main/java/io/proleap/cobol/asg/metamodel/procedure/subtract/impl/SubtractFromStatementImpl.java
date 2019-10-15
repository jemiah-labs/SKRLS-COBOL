/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.subtract.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.SubtractFromStatementContext;
import io.proleap.cobol.Cobol85Parser.SubtractMinuendContext;
import io.proleap.cobol.Cobol85Parser.SubtractSubtrahendContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.subtract.Minuend;
import io.proleap.cobol.asg.metamodel.procedure.subtract.SubtractFromStatement;
import io.proleap.cobol.asg.metamodel.procedure.subtract.Subtrahend;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class SubtractFromStatementImpl extends CobolDivisionElementImpl implements SubtractFromStatement {

	protected final SubtractFromStatementContext ctx;

	protected List<Minuend> minuends = new ArrayList<Minuend>();

	protected List<Subtrahend> subtrahends = new ArrayList<Subtrahend>();
	
	private final Producer producer;

	public SubtractFromStatementImpl(final ProgramUnit programUnit, final SubtractFromStatementContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public Minuend addMinuend(final SubtractMinuendContext ctx) {
		Minuend result = (Minuend) getASGElement(ctx);

		if (result == null) {
			result = new MinuendImpl(programUnit, ctx, producer);

			// minuend
			if (ctx.identifier() != null) {
				final Call minuendCall = createCall(ctx.identifier());
				result.setMinuendCall(minuendCall);
			}

			// rounded
			if (ctx.ROUNDED() != null) {
				result.setRounded(true);
			}

			minuends.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public Subtrahend addSubtrahend(final SubtractSubtrahendContext ctx) {
		Subtrahend result = (Subtrahend) getASGElement(ctx);

		if (result == null) {
			result = new SubtrahendImpl(programUnit, ctx, producer);

			// subtrahend
			final ValueStmt subtrahendValueStmt = createValueStmt(ctx.identifier(), ctx.literal());
			result.setSubtrahendValueStmt(subtrahendValueStmt);

			subtrahends.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public List<Minuend> getMinuends() {
		return minuends;
	}

	@Override
	public List<Subtrahend> getSubtrahends() {
		return subtrahends;
	}
}
