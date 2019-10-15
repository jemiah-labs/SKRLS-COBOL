/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.perform.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.PerformAfterContext;
import io.proleap.cobol.Cobol85Parser.PerformVaryingPhraseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.perform.After;
import io.proleap.cobol.asg.metamodel.procedure.perform.VaryingPhrase;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class AfterImpl extends CobolDivisionElementImpl implements After {

	protected final PerformAfterContext ctx;

	protected VaryingPhrase varyingPhrase;
	
	private final Producer producer;

	public AfterImpl(final ProgramUnit programUnit, final PerformAfterContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public VaryingPhrase addVaryingPhrase(final PerformVaryingPhraseContext ctx) {
		VaryingPhrase result = (VaryingPhrase) getASGElement(ctx);

		if (result == null) {
			result = new VaryingPhraseImpl(programUnit, ctx, producer);

			// varying
			final ValueStmt varyingValueStmt = createValueStmt(ctx.identifier(), ctx.literal());
			result.setVaryingValueStmt(varyingValueStmt);

			// from
			result.addFromPhrase(ctx.performFrom());

			// by
			result.addByPhrase(ctx.performBy());

			// until
			result.addUntil(ctx.performUntil());

			varyingPhrase = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public VaryingPhrase getVaryingPhrase() {
		return varyingPhrase;
	}

}
