/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.screen.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.asg.metamodel.data.screen.ScreenDescriptionEntriesSymbolTableEntry;
import io.proleap.cobol.asg.metamodel.data.screen.ScreenDescriptionEntry;

public class ScreenDescriptionEntriesSymbolTableEntryImpl implements ScreenDescriptionEntriesSymbolTableEntry {
	
	private final Producer producer;
	
	public ScreenDescriptionEntriesSymbolTableEntryImpl(final Producer producer) {
		this.producer = producer;
	}
	
	protected List<ScreenDescriptionEntry> screenDescriptionEntries = new ArrayList<ScreenDescriptionEntry>();

	@Override
	public void addScreenDescriptionEntry(final ScreenDescriptionEntry screenDescriptionEntry) {
		if (!screenDescriptionEntries.isEmpty()) {
			producer.emitWarningMessage("multiple declarations of screen description entry " + screenDescriptionEntry);
		}

		screenDescriptionEntries.add(screenDescriptionEntry);
	}

	@Override
	public List<ScreenDescriptionEntry> getScreenDescriptionEntries() {
		return screenDescriptionEntries;
	}

	@Override
	public ScreenDescriptionEntry getScreenDescriptionEntry() {
		return screenDescriptionEntries.isEmpty() ? null : screenDescriptionEntries.get(0);
	}
}
