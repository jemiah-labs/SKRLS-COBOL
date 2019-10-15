/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.inspect.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.InspectBeforeAfterContext;
import io.proleap.cobol.Cobol85Parser.InspectConvertingPhraseContext;
import io.proleap.cobol.Cobol85Parser.InspectToContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.procedure.inspect.BeforeAfterPhrase;
import io.proleap.cobol.asg.metamodel.procedure.inspect.Converting;
import io.proleap.cobol.asg.metamodel.procedure.inspect.To;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class ConvertingImpl extends InspectPhraseImpl implements Converting {

	protected List<BeforeAfterPhrase> beforeAfterPhrases = new ArrayList<BeforeAfterPhrase>();

	protected final InspectConvertingPhraseContext ctx;

	protected ValueStmt fromValueStmt;

	protected To to;

	private final Producer producer;
	
	public ConvertingImpl(final ProgramUnit programUnit, final InspectConvertingPhraseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public BeforeAfterPhrase addBeforeAfterPhrase(final InspectBeforeAfterContext ctx) {
		BeforeAfterPhrase result = (BeforeAfterPhrase) getASGElement(ctx);

		if (result == null) {
			result = createBeforeAfterPhrase(ctx);
			beforeAfterPhrases.add(result);
		}

		return result;
	}

	@Override
	public To addTo(final InspectToContext ctx) {
		To result = (To) getASGElement(ctx);

		if (result == null) {
			result = new ToImpl(programUnit, ctx, producer);

			final ValueStmt toValueStmt = createValueStmt(ctx.identifier(), ctx.literal());
			result.setToValueStmt(toValueStmt);

			to = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public List<BeforeAfterPhrase> getBeforeAfterPhrases() {
		return beforeAfterPhrases;
	}

	@Override
	public ValueStmt getFromValueStmt() {
		return fromValueStmt;
	}

	@Override
	public To getTo() {
		return to;
	}

	@Override
	public void setFromValueStmt(final ValueStmt fromValueStmt) {
		this.fromValueStmt = fromValueStmt;
	}

}
