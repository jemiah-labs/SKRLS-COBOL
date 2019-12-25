package org.jemiahlabs.skrls.kdm.models.platform.resources;

import org.jemiahlabs.skrls.kdm.models.XmiEntity;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("platformElement")
public class ResourceType extends XmiEntity {

    @XStreamAsAttribute
    private String name;

    @XStreamAsAttribute
    private String kind;

    @XStreamAsAttribute
    private String accessMethod;

    @XStreamAsAttribute
    private String organization;

    @XStreamAsAttribute
    private String source;

    public ResourceType(){
        setType("platform:ResourceType");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setAccessMethod(String accessMethod) {
        this.accessMethod = accessMethod;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public void setSource(String source) {
        this.source = source;
    }
}