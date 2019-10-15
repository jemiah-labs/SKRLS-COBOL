/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.datadescription.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.asg.metamodel.data.datadescription.DataDescriptionEntriesSymbolTableEntry;
import io.proleap.cobol.asg.metamodel.data.datadescription.DataDescriptionEntry;

public class DataDescriptionEntriesSymbolTableEntryImpl implements DataDescriptionEntriesSymbolTableEntry {
	
	protected List<DataDescriptionEntry> dataDescriptionEntries = new ArrayList<DataDescriptionEntry>();
	
	private final Producer producer;
	
	public DataDescriptionEntriesSymbolTableEntryImpl(Producer producer) {
		this.producer = producer;
	}

	@Override
	public void addDataDescriptionEntry(final DataDescriptionEntry dataDescriptionEntry) {
		if (!dataDescriptionEntries.isEmpty()) {
			producer.emitInfoMessage("multiple declarations of data description entry" + dataDescriptionEntry);
		}

		dataDescriptionEntries.add(dataDescriptionEntry);
	}

	@Override
	public List<DataDescriptionEntry> getDataDescriptionEntries() {
		return dataDescriptionEntries;
	}

	@Override
	public DataDescriptionEntry getDataDescriptionEntry() {
		return dataDescriptionEntries.isEmpty() ? null : dataDescriptionEntries.get(0);
	}
}
