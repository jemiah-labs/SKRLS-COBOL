package org.jemiahlabs.skrls.kdm.models.platform.resources;

import java.util.List;
import org.jemiahlabs.skrls.kdm.models.XmiEntity;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class DeployedResource extends XmiEntity {

    @XStreamImplicit
    private List<ResourceType> resources;

    public List<ResourceType> getResources() {
        return resources;
    }

    public void setResources(List<ResourceType> resources) {
        this.resources = resources;
    }
}
