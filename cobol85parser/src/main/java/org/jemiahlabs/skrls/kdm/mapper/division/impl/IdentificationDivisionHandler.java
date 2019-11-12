package org.jemiahlabs.skrls.kdm.mapper.division.impl;

import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.kdm.mapper.division.DivisionHandler;
import org.jemiahlabs.skrls.kdm.models.CommentUnit;
import org.jemiahlabs.skrls.kdm.models.Comments;
import org.jemiahlabs.skrls.kdm.models.KDMSegment;
import org.jemiahlabs.skrls.kdm.models.code.CodeElement;
import org.jemiahlabs.skrls.kdm.models.code.CodeModel;
import org.jemiahlabs.skrls.kdm.models.util.Counter;

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
			codeModel.setId(String.format("id.%s", Counter.getCounterGlobal().increment()));
			codeModel.setType("code:CodeModel");
			
			CodeElement codeElement = new CodeElement();
			codeElement.setId(String.format("id.%s", Counter.getCounterGlobal().increment()));
			codeElement.setType("code:CompilationUnit");
			codeElement.setName(identificationDivision.getProgramIdParagraph().getName());
			
			Comments comments = new Comments();
			comments.setId(String.format("id.%s", Counter.getCounterGlobal().increment()));
			comments.setAuthor(identificationDivision.getAuthorParagraph().getAuthor());
			
			CommentUnit commentUnit = new CommentUnit(identificationDivision.getRemarksParagraph().getRemarks());
			commentUnit.setId(String.format("id.%s", Counter.getCounterGlobal().increment()));
			comments.addCommentUnit(commentUnit);
			
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
