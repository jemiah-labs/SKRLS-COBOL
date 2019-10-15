/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.call.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.CallByValueContext;
import io.proleap.cobol.Cobol85Parser.CallByValuePhraseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.call.ByValue;
import io.proleap.cobol.asg.metamodel.procedure.call.ByValuePhrase;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class ByValuePhraseImpl extends CobolDivisionElementImpl implements ByValuePhrase {

	protected List<ByValue> byValues = new ArrayList<ByValue>();

	protected final CallByValuePhraseContext ctx;
	
	private Producer producer;
	
	public ByValuePhraseImpl(final ProgramUnit programUnit, final CallByValuePhraseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public ByValue addByValue(final CallByValueContext ctx) {
		ByValue result = (ByValue) getASGElement(ctx);

		if (result == null) {
			result = new ByValueImpl(programUnit, ctx, producer);

			// call
			final ValueStmt valueStmt = createValueStmt(ctx.identifier(), ctx.literal());
			result.setValueStmt(valueStmt);

			// type
			final ByValue.ByValueType type;

			if (ctx.ADDRESS() != null) {
				type = ByValue.ByValueType.ADDRESS_OF;
			} else if (ctx.LENGTH() != null) {
				type = ByValue.ByValueType.LENGTH_OF;
			} else {
				type = null;
			}

			result.setByValueType(type);

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
