/*
 * Copyright (C) 2017, Ulrich Wolffgang <ulrich.wolffgang@proleap.io>
 * All rights reserved.
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */

package io.proleap.cobol.asg.metamodel.data.programlibrary;

import java.util.List;

import io.proleap.cobol.Cobol85Parser.LibraryAttributeClauseFormat2Context;
import io.proleap.cobol.Cobol85Parser.LibraryEntryProcedureClauseFormat2Context;
import io.proleap.cobol.Cobol85Parser.LibraryIsCommonClauseContext;
import io.proleap.cobol.Cobol85Parser.LibraryIsGlobalClauseContext;

public interface LibraryDescriptionEntryImport extends LibraryDescriptionEntry {

	CommonClause addCommonClause(LibraryIsCommonClauseContext ctx);

	GlobalClause addGlobalClause(LibraryIsGlobalClauseContext ctx);

	ImportAttribute addImportAttribute(LibraryAttributeClauseFormat2Context ctx);

	ImportEntryProcedure addImportEntryProcedure(LibraryEntryProcedureClauseFormat2Context ctx);

	CommonClause getCommonClause();

	GlobalClause getGlobalClause();

	List<ImportAttribute> getImportAttributes();

	List<ImportEntryProcedure> getImportEntryProcedures();

}
