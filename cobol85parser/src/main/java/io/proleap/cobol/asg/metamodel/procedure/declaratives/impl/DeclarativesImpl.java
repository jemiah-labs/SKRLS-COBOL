/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.declaratives.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.ProcedureDeclarativeContext;
import io.proleap.cobol.Cobol85Parser.ProcedureDeclarativesContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.declaratives.Declarative;
import io.proleap.cobol.asg.metamodel.procedure.declaratives.Declaratives;

public class DeclarativesImpl extends CobolDivisionElementImpl implements Declaratives {

	protected final ProcedureDeclarativesContext ctx;

	protected List<Declarative> declaratives = new ArrayList<Declarative>();
	
	private final Producer producer;

	public DeclarativesImpl(final ProgramUnit programUnit, final ProcedureDeclarativesContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public Declarative addDeclarative(final ProcedureDeclarativeContext ctx) {
		Declarative result = (Declarative) getASGElement(ctx);

		if (result == null) {
			result = new DeclarativeImpl(programUnit, ctx, producer);

			result.addSectionHeader(ctx.procedureSectionHeader());
			result.addUseStatement(ctx.useStatement());

			declaratives.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public List<Declarative> getDeclaratives() {
		return declaratives;
	}

}
