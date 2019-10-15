/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.valuestmt.in.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.InLibraryContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.call.Call;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;
import io.proleap.cobol.asg.metamodel.valuestmt.in.InLibrary;

public class InLibraryImpl extends CobolDivisionElementImpl implements InLibrary {

	protected InLibraryContext ctx;

	protected Call libraryCall;

	public InLibraryImpl(final ProgramUnit programUnit, final InLibraryContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public Call getLibraryCall() {
		return libraryCall;
	}

	@Override
	public void setLibraryCall(final Call libraryCall) {
		this.libraryCall = libraryCall;
	}

}
