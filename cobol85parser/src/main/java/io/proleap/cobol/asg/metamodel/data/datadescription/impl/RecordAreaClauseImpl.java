/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.datadescription.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.DataRecordAreaClauseContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.datadescription.RecordAreaClause;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class RecordAreaClauseImpl extends CobolDivisionElementImpl implements RecordAreaClause {

	protected DataRecordAreaClauseContext ctx;

	protected boolean recordArea;

	public RecordAreaClauseImpl(final ProgramUnit programUnit, final DataRecordAreaClauseContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public boolean isRecordArea() {
		return recordArea;
	}

	@Override
	public void setRecordArea(final boolean recordArea) {
		this.recordArea = recordArea;
	}

}
