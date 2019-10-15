/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.procedure.declaratives.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.ProcedureDeclarativeContext;
import io.proleap.cobol.Cobol85Parser.ProcedureSectionHeaderContext;
import io.proleap.cobol.Cobol85Parser.UseStatementContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.ProcedureDivision;
import io.proleap.cobol.asg.metamodel.procedure.declaratives.Declarative;
import io.proleap.cobol.asg.metamodel.procedure.declaratives.SectionHeader;
import io.proleap.cobol.asg.metamodel.procedure.use.UseStatement;
import io.proleap.cobol.asg.metamodel.procedure.use.impl.UseStatementImpl;

public class DeclarativeImpl extends CobolDivisionElementImpl implements Declarative {

	protected final ProcedureDeclarativeContext ctx;

	protected SectionHeader sectionHeader;

	protected UseStatement useStatement;
	
	private final Producer producer;

	public DeclarativeImpl(final ProgramUnit programUnit, final ProcedureDeclarativeContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public SectionHeader addSectionHeader(final ProcedureSectionHeaderContext ctx) {
		SectionHeader result = (SectionHeader) getASGElement(ctx);

		if (result == null) {
			result = new SectionHeaderImpl(programUnit, ctx, producer);

			sectionHeader = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public UseStatement addUseStatement(final UseStatementContext ctx) {
		UseStatement result = (UseStatement) getASGElement(ctx);

		if (result == null) {
			final ProcedureDivision procedureDivision = programUnit.getProcedureDivision();
			result = new UseStatementImpl(programUnit, procedureDivision, ctx, producer);

			// type
			final UseStatement.UseType type;

			if (ctx.useAfterClause() != null) {
				result.addUseAfterStatement(ctx.useAfterClause());
				type = UseStatement.UseType.AFTER;
			} else if (ctx.useDebugClause() != null) {
				result.addUseDebugStatement(ctx.useDebugClause());
				type = UseStatement.UseType.DEBUG;
			} else {
				type = null;
			}

			result.setUseType(type);

			useStatement = result;
			registerASGElement(result);
		}

		return result;
	}

	@Override
	public SectionHeader getSectionHeader() {
		return sectionHeader;
	}

	@Override
	public UseStatement getUseStament() {
		return useStatement;
	}

}
