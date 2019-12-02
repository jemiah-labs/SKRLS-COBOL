package org.jemiahlabs.skrls.kdm.mapper.division.impl;

import io.proleap.cobol.asg.metamodel.environment.EnvironmentDivision;
import io.proleap.cobol.asg.metamodel.environment.configuration.ConfigurationSection;
import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.kdm.mapper.division.DivisionHandler;
import org.jemiahlabs.skrls.kdm.models.KDMSegment;

import io.proleap.cobol.asg.metamodel.CompilationUnit;
import org.jemiahlabs.skrls.kdm.models.platform.PlatformModel;
import org.jemiahlabs.skrls.kdm.models.util.Counter;

import java.util.List;

public class EnvironmentDivisionHandler extends DivisionHandler {

	public EnvironmentDivisionHandler(Producer producer, DivisionHandler nextHandler) {
		super(producer, nextHandler);
	}
	
	public EnvironmentDivisionHandler(Producer producer) {
		super(producer);
	}
	
	@Override
	public void process(CompilationUnit compilationUnit, KDMSegment model) {
		getMessageProducer().emitInfoMessage("Extracting Environment Division");
		EnvironmentDivision environmentDivision = compilationUnit.getProgramUnit().getEnvironmentDivision();
		if(environmentDivision != null) {
			PlatformModel platformModel = createPlatformModel(environmentDivision);
			model.setPlatformModel(platformModel);
		}
		if(hasNextHandler())
			getNextHandler().process(compilationUnit, model);
	}

	private PlatformModel createPlatformModel(EnvironmentDivision environmentDivision) {
		PlatformModel platformModel = new PlatformModel();
		platformModel.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));

		return platformModel;
	}

}
