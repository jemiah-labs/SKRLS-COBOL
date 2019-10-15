/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.impl;

import org.antlr.v4.runtime.ParserRuleContext;
import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.DataDivisionContext;
import io.proleap.cobol.Cobol85Parser.EnvironmentDivisionBodyContext;
import io.proleap.cobol.Cobol85Parser.EnvironmentDivisionContext;
import io.proleap.cobol.Cobol85Parser.IdentificationDivisionBodyContext;
import io.proleap.cobol.Cobol85Parser.IdentificationDivisionContext;
import io.proleap.cobol.Cobol85Parser.ProcedureDivisionContext;
import io.proleap.cobol.Cobol85Parser.ProgramUnitContext;
import io.proleap.cobol.asg.metamodel.ASGElement;
import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.DataDivision;
import io.proleap.cobol.asg.metamodel.data.impl.DataDivisionImpl;
import io.proleap.cobol.asg.metamodel.environment.EnvironmentDivision;
import io.proleap.cobol.asg.metamodel.environment.impl.EnvironmentDivisionImpl;
import io.proleap.cobol.asg.metamodel.identification.IdentificationDivision;
import io.proleap.cobol.asg.metamodel.identification.impl.IdentificationDivisionImpl;
import io.proleap.cobol.asg.metamodel.procedure.ProcedureDivision;
import io.proleap.cobol.asg.metamodel.procedure.impl.ProcedureDivisionImpl;

public class ProgramUnitImpl extends CompilationUnitElementImpl implements ProgramUnit {
	
	protected final ProgramUnitContext ctx;

	protected DataDivision dataDivision;

	protected EnvironmentDivision environmentDivision;

	protected IdentificationDivision identificationDivision;

	protected ProcedureDivision procedureDivision;
	
	private final Producer producer;

	public ProgramUnitImpl(final CompilationUnit compilationUnit, final ProgramUnitContext ctx, final Producer producer) {
		super(compilationUnit, ctx);
		
		this.producer = producer;
		this.ctx = ctx;
	}

	@Override
	public DataDivision addDataDivision(final DataDivisionContext ctx) {
		DataDivision result = (DataDivision) getASGElement(ctx);

		if (result == null) {
			result = new DataDivisionImpl(this, ctx, producer);
			dataDivision = result;

			registerASGElement(result);
		}

		return result;
	}

	@Override
	public EnvironmentDivision addEnvironmentDivision(final EnvironmentDivisionContext ctx) {
		EnvironmentDivision result = (EnvironmentDivision) getASGElement(ctx);

		if (result == null) {
			result = new EnvironmentDivisionImpl(this, ctx, producer);
			environmentDivision = result;

			for (final EnvironmentDivisionBodyContext environmentDivisionBodyContext : ctx.environmentDivisionBody()) {
				if (environmentDivisionBodyContext.configurationSection() != null) {
					result.addConfigurationSection(environmentDivisionBodyContext.configurationSection());
				} else if (environmentDivisionBodyContext.inputOutputSection() != null) {
					result.addInputOutputSection(environmentDivisionBodyContext.inputOutputSection());
				} else if (environmentDivisionBodyContext.specialNamesParagraph() != null) {
					result.addSpecialNamesParagraph(environmentDivisionBodyContext.specialNamesParagraph());
				} else {
					producer.emitWarningMessage("unknown environment division body " + environmentDivisionBodyContext);
				}
			}

			registerASGElement(result);
		}

		return result;
	}

	@Override
	public IdentificationDivision addIdentificationDivision(final IdentificationDivisionContext ctx) {
		IdentificationDivision result = (IdentificationDivision) getASGElement(ctx);

		if (result == null) {
			result = new IdentificationDivisionImpl(this, ctx, producer);
			identificationDivision = result;

			if (ctx.programIdParagraph() != null) {
				result.addProgramIdParagraph(ctx.programIdParagraph());
			}

			for (final IdentificationDivisionBodyContext identificationDivisionBodyContext : ctx
					.identificationDivisionBody()) {
				if (identificationDivisionBodyContext.authorParagraph() != null) {
					result.addAuthorParagraph(identificationDivisionBodyContext.authorParagraph());
				} else if (identificationDivisionBodyContext.dateWrittenParagraph() != null) {
					result.addDateWrittenParagraph(identificationDivisionBodyContext.dateWrittenParagraph());
				} else if (identificationDivisionBodyContext.dateCompiledParagraph() != null) {
					result.addDateCompiledParagraph(identificationDivisionBodyContext.dateCompiledParagraph());
				} else if (identificationDivisionBodyContext.installationParagraph() != null) {
					result.addInstallationParagraph(identificationDivisionBodyContext.installationParagraph());
				} else if (identificationDivisionBodyContext.remarksParagraph() != null) {
					result.addRemarksParagraph(identificationDivisionBodyContext.remarksParagraph());
				} else if (identificationDivisionBodyContext.securityParagraph() != null) {
					result.addSecurityParagraph(identificationDivisionBodyContext.securityParagraph());
				} else {
					producer.emitWarningMessage("unknown identification division body " + identificationDivisionBodyContext);
				}
			}

			registerASGElement(result);
		}

		return result;
	}

	@Override
	public ProcedureDivision addProcedureDivision(final ProcedureDivisionContext ctx) {
		ProcedureDivision result = (ProcedureDivision) getASGElement(ctx);

		if (result == null) {
			result = new ProcedureDivisionImpl(this, ctx, producer);
			procedureDivision = result;

			registerASGElement(result);
		}

		return result;
	}

	protected ASGElement getASGElement(final ParserRuleContext ctx) {
		final ASGElement result = compilationUnit.getProgram().getASGElementRegistry().getASGElement(ctx);
		return result;
	}

	@Override
	public DataDivision getDataDivision() {
		return dataDivision;
	}

	@Override
	public EnvironmentDivision getEnvironmentDivision() {
		return environmentDivision;
	}

	@Override
	public IdentificationDivision getIdentificationDivision() {
		return identificationDivision;
	}

	@Override
	public ProcedureDivision getProcedureDivision() {
		return procedureDivision;
	}

	protected void registerASGElement(final ASGElement asgElement) {
		compilationUnit.getProgram().getASGElementRegistry().addASGElement(asgElement);
	}
}
