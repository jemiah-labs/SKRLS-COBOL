package org.jemiahlabs.skrls.kdm.mapper.division.impl;

import io.proleap.cobol.asg.metamodel.IntegerLiteral;
import io.proleap.cobol.asg.metamodel.environment.EnvironmentDivision;
import io.proleap.cobol.asg.metamodel.environment.configuration.ConfigurationSection;
import io.proleap.cobol.asg.metamodel.environment.configuration.object.DiskSizeClause;
import io.proleap.cobol.asg.metamodel.environment.configuration.object.MemorySizeClause;
import io.proleap.cobol.asg.metamodel.environment.configuration.object.ObjectComputerParagraph;
import io.proleap.cobol.asg.metamodel.environment.configuration.object.SegmentLimitClause;
import io.proleap.cobol.asg.metamodel.environment.configuration.source.SourceComputerParagraph;
import org.jemiahlabs.skrls.core.Producer;
import org.jemiahlabs.skrls.kdm.mapper.division.DivisionHandler;
import org.jemiahlabs.skrls.kdm.models.KDMSegment;

import io.proleap.cobol.asg.metamodel.CompilationUnit;
import io.proleap.cobol.asg.metamodel.valuestmt.IntegerLiteralValueStmt;
import org.jemiahlabs.skrls.kdm.models.platform.Machine;
import org.jemiahlabs.skrls.kdm.models.platform.PlatformModel;
import org.jemiahlabs.skrls.kdm.models.util.Counter;

import java.util.Objects;

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
		ConfigurationSection configSection = environmentDivision.getConfigurationSection();
		platformModel.setMachine(processConfigurationSection(configSection));

		return platformModel;
	}

	private Machine processConfigurationSection(ConfigurationSection configSection){
		Machine machine = new Machine();

		ObjectComputerParagraph objectComputerParagraph = configSection.getObjectComputerParagraph();
		if(Objects.nonNull(objectComputerParagraph)){
            machine.setName(objectComputerParagraph.getName());

            MemorySizeClause memoryInfo = objectComputerParagraph.getMemorySizeClause();
            if(Objects.nonNull(memoryInfo)){
                machine.setMemorySize(getMemorySize(objectComputerParagraph.getMemorySizeClause()));
                machine.setMemoryUnits(getMemoryUnits(memoryInfo));
            }

            DiskSizeClause diskSizeInfo = objectComputerParagraph.getDiskSizeClause();
            if(Objects.nonNull(diskSizeInfo)){
                machine.setDiskSize(getDiskSize(diskSizeInfo));
                machine.setDiskUnits(getDiskUnits(diskSizeInfo));
            }

            SegmentLimitClause segmentLimitsInfo = objectComputerParagraph.getSegmentLimitClause();
            if(Objects.nonNull(segmentLimitsInfo)){
                machine.setSegmentLimit(getSegmentLimit(segmentLimitsInfo));
            }

            SourceComputerParagraph sourceComputerParagraph = configSection.getSourceComputerParagraph();
            if(Objects.nonNull(sourceComputerParagraph)){
                machine.setDebugginMode(isDebugginModeOn(sourceComputerParagraph));
            }
        }
		return machine;
	}

    private int getSegmentLimit(SegmentLimitClause segmentLimitsInfo) {
        IntegerLiteral segmentLimitsIntegerLiteral = segmentLimitsInfo.getIntegerLiteral();
        int segmentLimits = segmentLimitsIntegerLiteral.getValue().intValue();
        return segmentLimits;
    }

    private int getDiskSize(DiskSizeClause diskSizeInfo) {
        IntegerLiteralValueStmt diskSizeIntegerLiteral = (IntegerLiteralValueStmt) diskSizeInfo.getValueStmt();
        int diskSize = diskSizeIntegerLiteral.getLiteral().getValue().intValue();
        return diskSize;
    }

    private String getDiskUnits(DiskSizeClause diskSizeInfo) {
        return diskSizeInfo.getUnit().name();
    }

    private int getMemorySize(MemorySizeClause memoryInfo){
        IntegerLiteralValueStmt memorySizeIntegerLiteral = (IntegerLiteralValueStmt) memoryInfo.getValueStmt();
        int memorySize = memorySizeIntegerLiteral.getLiteral().getValue().intValue();
        return memorySize;
    }

    private String getMemoryUnits(MemorySizeClause memoryInfo) {
        return memoryInfo.getUnit().name();
    }

    private boolean isDebugginModeOn(SourceComputerParagraph sourceComputerParagraph){
	    return sourceComputerParagraph.isDebuggingMode();
    }

}
