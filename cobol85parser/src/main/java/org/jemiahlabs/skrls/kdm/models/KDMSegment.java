package org.jemiahlabs.skrls.kdm.models;

import org.jemiahlabs.skrls.kdm.models.code.CodeModel;
import org.jemiahlabs.skrls.kdm.models.code.DataModel;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

@XStreamAlias("kdm:Segment")
public class KDMSegment {
	@XStreamAsAttribute 
    @XStreamAlias("xmi:version")
	private final String version = "2.1";
	@XStreamAsAttribute 
    @XStreamAlias("xmlns:xmi")
	private final String xmlns = "http://www.omg.org/XMI";
	@XStreamAsAttribute 
    @XStreamAlias("xmlns:kdm")
	private final String kdm = "http://kdm.omg.org/kdm";
	@XStreamAsAttribute 
    @XStreamAlias("xmlns:action")
	private final String action = "http://kdm.omg.org/action";
	@XStreamAsAttribute 
    @XStreamAlias("xmlns:code")
	private final String code = "http://kdm.omg.org/code";
	@XStreamAsAttribute 
    @XStreamAlias("xmlns:data")
	private final String data = "http://kdm.omg.org/data";
	@XStreamAsAttribute 
    @XStreamAlias("xmlns:platform")
	private final String platform = "http://kdm.omg.org/platform";
	@XStreamAsAttribute 
	private String name;
	
	private DataModel dataModel;
	private CodeModel codemodel;
	
	public KDMSegment() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DataModel getDataModel() {
		return dataModel;
	}

	public void setDataModel(DataModel dataModel) {
		this.dataModel = dataModel;
	}

	public CodeModel getCodemodel() {
		return codemodel;
	}

	public void setCodemodel(CodeModel codemodel) {
		this.codemodel = codemodel;
	}
}
