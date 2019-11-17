package org.jemiahlabs.skrls.kdm.mapper.division.impl;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;
import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.kdm.mapper.division.DivisionHandler;
import org.jemiahlabs.skrls.kdm.models.KDMSegment;
import org.jemiahlabs.skrls.kdm.models.action.ActionElement;
import org.jemiahlabs.skrls.kdm.models.action.CallableKind;
import org.jemiahlabs.skrls.kdm.models.action.CallableUnit;
import org.jemiahlabs.skrls.kdm.models.code.CodeElement;
import org.jemiahlabs.skrls.kdm.models.code.CodeModel;
import org.jemiahlabs.skrls.kdm.models.code.ParameterKind;
import org.jemiahlabs.skrls.kdm.models.code.ParameterUnit;
import org.jemiahlabs.skrls.kdm.models.code.Source;
import org.jemiahlabs.skrls.kdm.models.util.Counter;

import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.procedure.Paragraph;
import io.proleap.cobol.asg.metamodel.procedure.ProcedureDivision;
import io.proleap.cobol.asg.metamodel.procedure.Section;
import io.proleap.cobol.asg.metamodel.procedure.Statement;
import io.proleap.cobol.asg.metamodel.procedure.call.CallStatement;
import io.proleap.cobol.asg.metamodel.procedure.ifstmt.Else;
import io.proleap.cobol.asg.metamodel.procedure.ifstmt.IfStatement;
import io.proleap.cobol.asg.metamodel.procedure.perform.PerformInlineStatement;
import io.proleap.cobol.asg.metamodel.procedure.perform.PerformStatement;
import io.proleap.cobol.asg.metamodel.procedure.perform.PerformStatement.PerformStatementType;
import io.proleap.cobol.asg.metamodel.valuestmt.ConditionValueStmt;

public class ProcedureDivisionHandler extends DivisionHandler {

	public ProcedureDivisionHandler(Producer producer, DivisionHandler nextHandler) {
		super(producer, nextHandler);
	}
	
	public ProcedureDivisionHandler(Producer producer) {
		super(producer);
	}
	
	@Override
	public void process(CompilationUnit compilationUnit, KDMSegment model) {
		getMessageProducer().emitInfoMessage("Procedure Division Extracting");
		
		ProcedureDivision procedureDivision = compilationUnit.getProgramUnit().getProcedureDivision();
		
		if(procedureDivision != null) {
			CodeModel codeModel = createCodeModel(procedureDivision);
			model.addCodeModel(codeModel);
		}
		
		if(hasNextHandler())
			getNextHandler().process(compilationUnit, model);
	}
	
	private CodeModel createCodeModel(ProcedureDivision procedureDivision) {
		CodeModel codeModel = new CodeModel();
		codeModel.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		codeModel.setType("code:CodeModel");
		
		ActionElement rootActionElement = new ActionElement();
		rootActionElement.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		rootActionElement.setType("code:CodeAssembly");
		codeModel.addCodeElement(rootActionElement);
		
		procedureDivision.getSections().forEach(
				section -> rootActionElement.addCodeElement(processSection(section)) );
		procedureDivision.getRootParagraphs().forEach(
				paragraph -> rootActionElement.addCodeElement(processParagraph(paragraph)) );
		procedureDivision.getStatements().forEach( 
				statement -> rootActionElement.addCodeElement(processStatement(statement)) );
		
		return codeModel;
	}
	
	private ActionElement processSection(Section section) {
		ActionElement actionElement = new ActionElement();
		actionElement.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		actionElement.setName(section.getName());
		actionElement.setType("action:BlockUnit");
		
		section.getParagraphs().forEach(
				paragraph -> actionElement.addCodeElement(processParagraph(paragraph)) );
		section.getStatements().forEach(
				statement -> actionElement.addCodeElement(processStatement(statement)) );
		
		return actionElement;
	}
	
	private ActionElement processParagraph(Paragraph paragraph) {
		ActionElement actionElement = new ActionElement();
		actionElement.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		actionElement.setName(paragraph.getName());
		actionElement.setType("action:BlockUnit");
		
		paragraph.getStatements().forEach(
				statement -> actionElement.addCodeElement(processStatement(statement)) );
		
		return actionElement;
	}
	
	private CodeElement processStatement(Statement statement) {
		return buildCodeElement(statement);
	}
	
	private CodeElement buildCodeElement(Statement statement) {
		if (statement instanceof IfStatement) {
			return createControlFlowElement((IfStatement) statement);
		} else if(statement instanceof PerformStatement) {
			return createIterableFlowElement((PerformStatement) statement);
		} else if(statement instanceof CallStatement) {
			return createSubroutineElement((CallStatement) statement);
		} else {
			return createGenericElement(statement);
		}
	}
	
	private CodeElement createControlFlowElement(IfStatement statement) {
		ActionElement rootActionElement = new ActionElement();
		rootActionElement.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		rootActionElement.setType("action:ControlFlow");
		
		ConditionValueStmt conditionStatement = statement.getCondition();
		Source source = new Source("Cobol");
		source.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		source.setSnippet(getStringStatement(conditionStatement.getCtx().children));
		rootActionElement.setSource(source);
		
		createFlowTrueElement(statement, rootActionElement);
		createFlowFalseElement(statement, rootActionElement);
		
		return rootActionElement;
	}
	
	private void createFlowTrueElement(IfStatement statement, ActionElement rootActionElement) {
		final ActionElement flowTrue = new ActionElement();
		flowTrue.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		flowTrue.setType("action:TrueFlow");
		
		statement.getThen().getStatements().forEach(s -> {
			CodeElement elementInner = processStatement(s);
			flowTrue.addCodeElement(elementInner);
		});
		
		rootActionElement.addCodeElement(flowTrue);
	}
	
	private void createFlowFalseElement(IfStatement statement, ActionElement rootActionElement) {
		Else elseStatements = statement.getElse();
		
		if(elseStatements != null) {
			final ActionElement flowFalse = new ActionElement();
			flowFalse.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
			flowFalse.setType("action:FalseFlow");
			
			elseStatements.getStatements().forEach(s -> {
				CodeElement elementInner = processStatement(s);
				flowFalse.addCodeElement(elementInner);
			});
			
			rootActionElement.addCodeElement(flowFalse);
		}
	}
	
	private CodeElement createIterableFlowElement(PerformStatement statement) {
		ActionElement rootActionElement = new ActionElement();
		rootActionElement.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		rootActionElement.setType("action:ControlFlow");
		
		if(statement.getPerformStatementType() == PerformStatementType.PROCEDURE) {
			createProcedureFlowElement(statement, rootActionElement);
		} else {
			createInlineFlowElement(statement, rootActionElement);
		}
		
		return rootActionElement;
	}
	
	private void createProcedureFlowElement(PerformStatement statement, ActionElement rootActionElement) {
		String snippet =  getStringStatement(statement.getCtx().children);
		Source source = new Source("Cobol");
		source.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		source.setSnippet(snippet);
		
		rootActionElement.setSource(source);
		rootActionElement.setKind("call");
	}
	
	private void createInlineFlowElement(PerformStatement statement, ActionElement rootActionElement) {
		StringBuilder snippet = new StringBuilder();
		snippet.append(statement.getCtx().getChild(0).getText() + " ");
		
		PerformInlineStatement performInlineStatement = statement.getPerformInlineStatement();
		getStringStatement(performInlineStatement.getCtx().getChild(0), snippet);
		Source source = new Source("Cobol");
		source.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		source.setSnippet(snippet.toString().trim());
		
		rootActionElement.setSource(source);
		
		final ActionElement entryFlow = new ActionElement();
		entryFlow.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		entryFlow.setType("action:EntryFlow");
		
		statement.getPerformInlineStatement().getStatements().forEach(s -> {
			CodeElement elementInner = processStatement(s);
			entryFlow.addCodeElement(elementInner);
		});
		
		rootActionElement.addCodeElement(entryFlow);
	}
	
	private CodeElement createSubroutineElement(CallStatement statement) {
		CallableUnit callableUnit = new CallableUnit();
		callableUnit.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		callableUnit.setKind(CallableKind.EXTERNAL);
		callableUnit.setName(statement.getProgramValueStmt().getCtx().getText());
		
		Source source = new Source("Cobol");
		source.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		source.setSnippet(getStringStatement(statement.getCtx().children));
		callableUnit.setSource(source);
		
	    final Counter countParameters = Counter.getLocalCounter(1);
		
		statement.getUsingPhrasePhrase().getUsingParameters().forEach( p -> {
			if(p.getByContentPhrase() != null) {
				p.getByContentPhrase().getByContents().forEach(parameter -> {
					ParameterUnit parameterUnit = 
							createParameterElement(parameter.getCtx().getText(), ParameterKind.BYVALUE, countParameters.increment());
					callableUnit.addParameterUnit(parameterUnit);
				});
			} else if (p.getByReferencePhrase() != null) {
				p.getByReferencePhrase().getByReferences().forEach(parameter -> {
					ParameterUnit parameterUnit = 
							createParameterElement(parameter.getCtx().getText(), ParameterKind.BYREFERENCE, countParameters.increment());
					callableUnit.addParameterUnit(parameterUnit);
				});
			}
		});
		
		return callableUnit;
	}
	
	private ParameterUnit createParameterElement(String name, ParameterKind kind, int position) {
		ParameterUnit parameterUnit = new ParameterUnit(name, position);
		parameterUnit.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		parameterUnit.setKind(kind);
		return parameterUnit;
	}
	
	private CodeElement createGenericElement(Statement statement) {
		ActionElement rootActionElement = new ActionElement();
		rootActionElement.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		rootActionElement.setType("action:ActionElement");
		
		Source source = new Source("Cobol");
		source.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		source.setSnippet(getStringStatement(statement.getCtx().children));
		rootActionElement.setSource(source);
		
		return rootActionElement;
	}
	
	private String getStringStatement(List<ParseTree> parseTrees) {
		StringBuilder builder = new StringBuilder();
		parseTrees.forEach(p -> getStringStatement(p, builder));
		
		return builder.toString().trim();
	}
	
	private void getStringStatement(ParseTree parseTree, StringBuilder accomulator) {
		if (parseTree.getChildCount() > 0)
			getTokensFromStatement(parseTree, accomulator);
		else
			accomulator.append(parseTree.getText() + " ");
	}
	
	private void getTokensFromStatement(ParseTree parseTree, StringBuilder accomulator) {
		for(int i = 0; i < parseTree.getChildCount(); i++) {
			ParseTree childCurrent = parseTree.getChild(i);
			
			if (childCurrent.getChildCount() > 0)
				getTokensFromStatement(childCurrent, accomulator);
			else
				accomulator.append(childCurrent.getText() + " ");
		}
	}

}
