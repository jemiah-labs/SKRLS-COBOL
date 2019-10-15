/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.valuestmt.relation.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.AbbreviationContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.valuestmt.ArithmeticValueStmt;
import io.proleap.cobol.asg.metamodel.valuestmt.relation.Abbreviation;

public class AbbreviationImpl extends AbstractComparisonValueStmtImpl implements Abbreviation {

	protected Abbreviation abbreviation;

	protected ArithmeticValueStmt arithmeticExpression;

	protected AbbreviationContext ctx;
	
	private final Producer producer;

	public AbbreviationImpl(final ProgramUnit programUnit, final AbbreviationContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
	}

	@Override
	public Abbreviation addAbbreviation(final AbbreviationContext ctx) {
		Abbreviation result = (Abbreviation) getASGElement(ctx);

		if (result == null) {
			result = new AbbreviationImpl(programUnit, ctx, producer);

			// operator
			if (ctx.relationalOperator() != null) {
				result.addOperator(ctx.relationalOperator());
			}

			// arithmetic expression
			if (ctx.arithmeticExpression() != null) {
				final ArithmeticValueStmt arithmeticExpression = createArithmeticValueStmt(ctx.arithmeticExpression());
				result.setArithmeticExpression(arithmeticExpression);
			}

			// abbreviation
			if (ctx.abbreviation() != null) {
				result.addAbbreviation(ctx.abbreviation());
			}

			abbreviation = result;
			subValueStmts.add(result);
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public Abbreviation getAbbreviation() {
		return abbreviation;
	}

	@Override
	public ArithmeticValueStmt getArithmeticExpression() {
		return arithmeticExpression;
	}

	@Override
	public void setArithmeticExpression(final ArithmeticValueStmt arithmeticExpression) {
		this.arithmeticExpression = arithmeticExpression;
	}
}
