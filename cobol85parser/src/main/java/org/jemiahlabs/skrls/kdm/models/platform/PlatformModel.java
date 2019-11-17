package org.jemiahlabs.skrls.kdm.models.platform;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import org.jemiahlabs.skrls.kdm.models.XmiEntity;
import org.jemiahlabs.skrls.kdm.models.platform.resources.DeployedResource;

public class PlatformModel extends XmiEntity {

    @XStreamAlias("platformElement")
    private Machine machine;

    @XStreamAlias("deployedResource")
    private DeployedResource deployedResource;

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public void setDeployedResource(DeployedResource deployedResource){
        this.deployedResource = deployedResource;
    }
}
