/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.communication.impl;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.core.Producer;

import io.proleap.cobol.asg.metamodel.data.communication.CommunicationDescriptionEntriesSymbolTableEntry;
import io.proleap.cobol.asg.metamodel.data.communication.CommunicationDescriptionEntry;

public class CommunicationDescriptionEntriesSymbolTableEntryImpl
		implements CommunicationDescriptionEntriesSymbolTableEntry {
	
	private final Producer producer;
	
	public CommunicationDescriptionEntriesSymbolTableEntryImpl(final Producer producer) {
		this.producer = producer;
	}

	protected List<CommunicationDescriptionEntry> communicationDescriptionEntries = new ArrayList<CommunicationDescriptionEntry>();

	@Override
	public void addCommunicationDescriptionEntry(final CommunicationDescriptionEntry communicationDescriptionEntry) {
		if (!communicationDescriptionEntries.isEmpty()) {
			producer.emitWarningMessage("multiple declarations of communication description entry " + communicationDescriptionEntry);
		}

		communicationDescriptionEntries.add(communicationDescriptionEntry);
	}

	@Override
	public List<CommunicationDescriptionEntry> getCommunicationDescriptionEntries() {
		return communicationDescriptionEntries;
	}

	@Override
	public CommunicationDescriptionEntry getCommunicationDescriptionEntry() {
		return communicationDescriptionEntries.isEmpty() ? null : communicationDescriptionEntries.get(0);
	}
}
