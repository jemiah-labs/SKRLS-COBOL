package org.jemiahlabs.skrls.kdm.models.platform;

import org.jemiahlabs.skrls.kdm.models.XmiEntity;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class Machine extends XmiEntity {

    @XStreamAsAttribute
    private String name;

    @XStreamAsAttribute
    private int memorySize;

    @XStreamAsAttribute
    private String memoryUnits;

    @XStreamAsAttribute
    private int diskSize;

    @XStreamAsAttribute
    private String diskUnits;

    @XStreamAsAttribute
    private int segmentLimit;

    @XStreamAsAttribute
    private boolean debugginMode;

    public Machine(){

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMemorySize(int memorySize) {
        this.memorySize = memorySize;
    }

    public void setDiskSize(int diskSize) {
        this.diskSize = diskSize;
    }

    public void setSegmentLimit(int segmentLimit) {
        this.segmentLimit = segmentLimit;
    }

    public void setDebugginMode(boolean debugginMode) {
        this.debugginMode = debugginMode;
    }

    public void setMemoryUnits(String memoryUnits) {
        this.memoryUnits = memoryUnits;
    }

    public void setDiskUnits(String diskUnits) {
        this.diskUnits = diskUnits;
    }
}
