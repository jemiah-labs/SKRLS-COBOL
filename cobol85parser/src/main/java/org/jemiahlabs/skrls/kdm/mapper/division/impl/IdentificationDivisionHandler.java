package org.jemiahlabs.skrls.kdm.mapper.division.impl;

import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.kdm.mapper.division.DivisionHandler;
import org.jemiahlabs.skrls.kdm.models.CommentUnit;
import org.jemiahlabs.skrls.kdm.models.Comments;
import org.jemiahlabs.skrls.kdm.models.KDMSegment;
import org.jemiahlabs.skrls.kdm.models.code.CodeElement;
import org.jemiahlabs.skrls.kdm.models.code.CodeModel;

import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.identification.IdentificationDivision;

public class IdentificationDivisionHandler extends DivisionHandler {

	public IdentificationDivisionHandler(Producer producer) {
		super(producer);
	}
	
	public IdentificationDivisionHandler(Producer producer, DivisionHandler nextHandler) {
		super(producer, nextHandler);
	}

	@Override
	public void process(CompilationUnit compilationUnit, KDMSegment model) {
		getProducerMessage().emitInfoMessage("Identification Division Extracting");
		model.setName(compilationUnit.getName());
		
		CodeModel codeModel = createCodeModel(compilationUnit.getProgramUnit().getIdentificationDivision());
		
		if(codeModel != null) model.addCodeModel(codeModel);
		
		if(isNextHandler())
			getNextHandler().process(compilationUnit, model);
	}
	
	private CodeModel createCodeModel(IdentificationDivision identificationDivision) {
		try {
			CodeModel codeModel = new CodeModel();
			codeModel.setType("code:CodeModel");
			
			Comments comments = new Comments();
			comments.setAuthor(identificationDivision.getAuthorParagraph().getAuthor());
			comments.addCommentUnit(new CommentUnit(identificationDivision.getRemarksParagraph().getRemarks()));
			
			CodeElement codeElement = new CodeElement();
			codeElement.setType("code:CompilationUnit");
			codeElement.setName(identificationDivision.getProgramIdParagraph().getName());
			codeElement.setComments(comments);
			
			codeModel.addCodeElement(codeElement);
			return codeModel;
		} catch(NullPointerException ex) {
			getProducerMessage().emitInfoMessage("Incomplete IdentificationDivision");
		} catch (Exception ex) {
			getProducerMessage().emitInfoMessage(ex.getMessage());
		}
		
		return null;
	}
}
