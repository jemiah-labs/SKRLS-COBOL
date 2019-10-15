/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.MnemonicNameContext;
import io.proleap.cobol.asg.metamodel.MnemonicName;
import io.proleap.cobol.asg.metamodel.ProgramUnit;

public class MnemonicNameImpl extends CobolDivisionElementImpl implements MnemonicName {

	protected final MnemonicNameContext ctx;

	protected final String value;

	public MnemonicNameImpl(final String value, final ProgramUnit programUnit, final MnemonicNameContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
		this.value = value;
	}

	@Override
	public MnemonicNameContext getCtx() {
		return ctx;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return super.toString() + ", value=[" + value + "]";
	}
}
