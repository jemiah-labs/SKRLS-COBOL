/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.linkage.impl;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.Cobol85Parser.LinkageSectionContext;
import io.proleap.cobol.asg.metamodel.ProgramUnit;
import io.proleap.cobol.asg.metamodel.data.datadescription.impl.DataDescriptionEntryContainerImpl;
import io.proleap.cobol.asg.metamodel.data.linkage.LinkageSection;

public class LinkageSectionImpl extends DataDescriptionEntryContainerImpl implements LinkageSection {

	protected final DataDescriptionEntryContainerType containerType = DataDescriptionEntryContainerType.LINKAGE_SECTION;

	protected final LinkageSectionContext ctx;

	public LinkageSectionImpl(final ProgramUnit programUnit, final LinkageSectionContext ctx, final Producer producer) {
		super(programUnit, ctx, producer);
		
		this.ctx = ctx;
	}

	@Override
	public DataDescriptionEntryContainerType getContainerType() {
		return containerType;
	}

}
