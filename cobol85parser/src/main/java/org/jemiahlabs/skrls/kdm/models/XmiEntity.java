package org.jemiahlabs.skrls.kdm.models;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

public class XmiEntity {
	@XStreamAsAttribute 
    @XStreamAlias("xmi:id")
	private String id;
	@XStreamAsAttribute 
    @XStreamAlias("xmi:label")
	private String label;
	@XStreamAsAttribute 
    @XStreamAlias("xmi:uuid")
	private String uuid;
	@XStreamAsAttribute 
    @XStreamAlias("xmi:type")
	private String type;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
