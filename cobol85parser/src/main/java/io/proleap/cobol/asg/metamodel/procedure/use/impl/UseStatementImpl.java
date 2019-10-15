/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.use.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.UseAfterClauseContext;
import io.proleap.cobol.Cobol85Parser.UseDebugClauseContext;
import io.proleap.cobol.Cobol85Parser.UseDebugOnContext;
import io.proleap.cobol.Cobol85Parser.UseStatementContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.Scope;
import io.proleap.cobol.asg.metamodel.procedure.StatementType;
import io.proleap.cobol.asg.metamodel.procedure.StatementTypeEnum;
import io.proleap.cobol.asg.metamodel.procedure.impl.StatementImpl;
import io.proleap.cobol.asg.metamodel.procedure.use.UseAfterStatement;
import io.proleap.cobol.asg.metamodel.procedure.use.UseDebugStatement;
import io.proleap.cobol.asg.metamodel.procedure.use.UseStatement;

public class UseStatementImpl extends StatementImpl implements UseStatement {

	protected final UseStatementContext ctx;

	protected final StatementType statementType = StatementTypeEnum.USE;

	protected UseAfterStatement useAfterStatement;

	protected UseDebugStatement useDebugStatement;

	protected UseType useType;
	
	private final Producer producer;

	public UseStatementImpl(final ProgramUnit programUnit, final Scope scope, final UseStatementContext ctx, final Producer producer) {
		super(programUnit, scope, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public UseAfterStatement addUseAfterStatement(final UseAfterClauseContext ctx) {
		UseAfterStatement result = (UseAfterStatement) getASGElement(ctx);

		if (result == null) {
			result = new UseAfterStatementImpl(programUnit, ctx, producer);

			// global
			if (ctx.GLOBAL() != null) {
				result.setGlobal(true);
			}

			// on
			result.addAfterOn(ctx.useAfterOn());

			useAfterStatement = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public UseDebugStatement addUseDebugStatement(final UseDebugClauseContext ctx) {
		UseDebugStatement result = (UseDebugStatement) getASGElement(ctx);

		if (result == null) {
			result = new UseDebugStatementImpl(programUnit, ctx, producer);

			// debug on
			for (final UseDebugOnContext useDebugOnContext : ctx.useDebugOn()) {
				result.addDebugOn(useDebugOnContext);
			}

			useDebugStatement = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public StatementType getStatementType() {
		return statementType;
	}

	@Override
	public UseAfterStatement getUseAfterStatement() {
		return useAfterStatement;
	}

	@Override
	public UseDebugStatement getUseDebugStatement() {
		return useDebugStatement;
	}

	@Override
	public UseType getUseType() {
		return useType;
	}

	@Override
	public void setUseType(final UseType useType) {
		this.useType = useType;
	}

}
