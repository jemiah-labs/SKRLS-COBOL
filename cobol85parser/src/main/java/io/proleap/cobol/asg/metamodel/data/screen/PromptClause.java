/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.screen;

import io.proleap.cobol.Cobol85Parser.ScreenDescriptionPromptOccursClauseContext;
import io.proleap.cobol.asg.metamodel.CobolDivisionElement;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;

public interface PromptClause extends CobolDivisionElement {

	Occurs addOccurs(ScreenDescriptionPromptOccursClauseContext ctx);

	ValueStmt getCharacterValueStmt();

	Occurs getOccurs();

	void setCharacterValueStmt(ValueStmt characterValueStmt);

}
