package io.proleap.cobol.asg.metamodel.procedure.stop.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.StopStatementGivingContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.procedure.stop.StopStatementGiving;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public class StopStatementGivingImpl extends CobolDivisionElementImpl implements StopStatementGiving {

	protected final StopStatementGivingContext ctx;

	protected ValueStmt givingValueStmt;

	public StopStatementGivingImpl(final ProgramUnit programUnit, final StopStatementGivingContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public ValueStmt getGivingValueStmt() {
		return givingValueStmt;
	}

	@Override
	public void setGivingValueStmt(final ValueStmt givingValueStmt) {
		this.givingValueStmt = givingValueStmt;
	}
}
