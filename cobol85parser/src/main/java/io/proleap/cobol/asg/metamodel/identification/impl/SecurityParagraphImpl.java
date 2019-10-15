/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.identification.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.SecurityParagraphContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.identification.SecurityParagraph;
import io.proleap.cobol.asg.metamodel.impl.CobolDivisionElementImpl;

public class SecurityParagraphImpl extends CobolDivisionElementImpl implements SecurityParagraph {

	protected final SecurityParagraphContext ctx;

	protected String security;

	public SecurityParagraphImpl(final ProgramUnit programUnit, final SecurityParagraphContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);

		this.ctx = ctx;
	}

	@Override
	public String getSecurity() {
		return security;
	}

	@Override
	public void setSecurity(final String security) {
		this.security = security;
	}
}
