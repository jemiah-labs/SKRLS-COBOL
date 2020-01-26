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
import io.proleap.cobol.asg.metamodel.environment.inputoutput.InputOutputSection;
import io.proleap.cobol.asg.metamodel.environment.inputoutput.filecontrol.AccessModeClause;
import io.proleap.cobol.asg.metamodel.environment.inputoutput.filecontrol.AssignClause;
import io.proleap.cobol.asg.metamodel.environment.inputoutput.filecontrol.FileControlEntry;
import io.proleap.cobol.asg.metamodel.environment.inputoutput.filecontrol.FileControlParagraph;
import io.proleap.cobol.asg.metamodel.environment.inputoutput.filecontrol.OrganizationClause;
import io.proleap.cobol.asg.metamodel.environment.inputoutput.filecontrol.SelectClause;
import io.proleap.cobol.asg.metamodel.valuestmt.IntegerLiteralValueStmt;
import io.proleap.cobol.asg.metamodel.valuestmt.ValueStmt;
import org.jemiahlabs.skrls.kdm.models.platform.Machine;
import org.jemiahlabs.skrls.kdm.models.platform.PlatformModel;
import org.jemiahlabs.skrls.kdm.models.platform.resources.DeployedResource;
import org.jemiahlabs.skrls.kdm.models.platform.resources.ResourceType;
import org.jemiahlabs.skrls.kdm.models.util.Counter;

import java.util.ArrayList;
import java.util.List;
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
		if(Objects.nonNull(environmentDivision)) {
			PlatformModel platformModel = createPlatformModel(environmentDivision);
			model.setPlatformModel(platformModel);
		}
		if(hasNextHandler())
			getNextHandler().process(compilationUnit, model);
	}

	private PlatformModel createPlatformModel(EnvironmentDivision environmentDivision) {
	    getMessageProducer().emitInfoMessage("Processing platform model");
		PlatformModel platformModel = new PlatformModel();
		platformModel.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
		platformModel.setType("platform:PlatformModel");

		ConfigurationSection configSection = environmentDivision.getConfigurationSection();
        if(Objects.nonNull(configSection)){
            platformModel.setMachine(processConfigurationSection(configSection));
        }

        InputOutputSection ioSection = environmentDivision.getInputOutputSection();
        if(Objects.nonNull(ioSection)){
            platformModel.setDeployedResource(processInputOutputSection(ioSection));
        }

		return platformModel;
	}

	private Machine processConfigurationSection(ConfigurationSection configSection){
	    getMessageProducer().emitInfoMessage("Processing configuration section");
		Machine machine = new Machine();
		machine.setType("platform:Machine");
		machine.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));

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

    private DeployedResource processInputOutputSection(InputOutputSection ioSection){
        getMessageProducer().emitInfoMessage("Processing input output section");
	    DeployedResource deployedResource = new DeployedResource();
	    deployedResource.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
        List<ResourceType> resources = new ArrayList<>();
        FileControlParagraph fileControlParagraph = ioSection.getFileControlParagraph();
        if(Objects.nonNull(fileControlParagraph)){
            List<FileControlEntry> fileControlEntries = fileControlParagraph.getFileControlEntries();
            fileControlEntries.forEach(fileControlEntry -> {

                ResourceType resource = new ResourceType();
                SelectClause selectClause = fileControlEntry.getSelectClause();
                if(Objects.nonNull(selectClause)){
                    resource.setName(selectClause.getName());
                }

                AssignClause assignClause = fileControlEntry.getAssignClause();
                if(Objects.nonNull(assignClause)){
                    resource.setSource(getResourceSource(assignClause));
                    AssignClause.AssignClauseType clauseType = assignClause.getAssignClauseType();
                    if(Objects.nonNull(clauseType)){
                        resource.setKind(clauseType.name());
                    }
                }

                AccessModeClause accessModeClause = fileControlEntry.getAccessModeClause();
                if(Objects.nonNull(accessModeClause)){
                    AccessModeClause.Mode mode = accessModeClause.getMode();
                    if(Objects.nonNull(mode)){
                        resource.setAccessMethod(mode.name());
                    }
                }

                OrganizationClause organizationClause = fileControlEntry.getOrganizationClause();
                if(Objects.nonNull(organizationClause)){
                    OrganizationClause.Mode mode = organizationClause.getMode();
                    if(Objects.nonNull(mode)){
                        resource.setOrganization(mode.name());
                    }
                }

                resource.setId(String.format("id.%s", Counter.getGlobalCounter().increment()));
                resources.add(resource);
            });
        }
        deployedResource.setResources(resources);
        return deployedResource;
    }

    private String getResourceSource(AssignClause assignClause){
	    ValueStmt stmtImpl = assignClause.getToValueStmt();
	    if(Objects.nonNull(stmtImpl)){
	        return stmtImpl.toString();
        }
	    return null;
    }
}