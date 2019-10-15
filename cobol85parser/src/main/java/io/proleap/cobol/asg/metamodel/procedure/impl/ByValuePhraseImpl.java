/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.ProcedureDivisionByValueContext;
import io.proleap.cobol.Cobol85Parser.ProcedureDivisionByValuePhraseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.ByValue;
import io.proleap.cobol.asg.metamodel.procedure.ByValuePhrase;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class ByValuePhraseImpl extends CobolDivisionElementImpl implements ByValuePhrase {

	protected List<ByValue> byValues = new ArrayList<ByValue>();

	protected final ProcedureDivisionByValuePhraseContext ctx;
	
	private final Producer producer;

	public ByValuePhraseImpl(final ProgramUnit programUnit, final ProcedureDivisionByValuePhraseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public ByValue addByValue(final ProcedureDivisionByValueContext ctx) {
		ByValue result = (ByValue) getASGElement(ctx);

		if (result == null) {
			result = new ByValueImpl(programUnit, ctx, producer);

			// any
			if (ctx.ANY() != null) {
				result.setAny(true);
			}

			// value
			final ValueStmt valueValueStmt = createValueStmt(ctx.identifier(), ctx.literal());
			result.setValueValueStmt(valueValueStmt);

			byValues.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public List<ByValue> getByValues() {
		return byValues;
	}
}
