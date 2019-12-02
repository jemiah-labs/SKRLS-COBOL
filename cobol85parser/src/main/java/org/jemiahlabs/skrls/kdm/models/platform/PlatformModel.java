package org.jemiahlabs.skrls.kdm.models.platform;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import org.jemiahlabs.skrls.kdm.models.XmiEntity;

public class PlatformModel extends XmiEntity {

    @XStreamAsAttribute
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
