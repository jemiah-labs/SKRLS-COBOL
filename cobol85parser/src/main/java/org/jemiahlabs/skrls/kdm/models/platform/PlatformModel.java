package org.jemiahlabs.skrls.kdm.models.platform;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import org.jemiahlabs.skrls.kdm.models.XmiEntity;

import java.util.List;

@XStreamAlias("platformModel")
public class PlatformModel extends XmiEntity {

    @XStreamAsAttribute
    private String name;

    private Machine machine;

    @XStreamImplicit
    private List<ResourceType> resources;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }
}
