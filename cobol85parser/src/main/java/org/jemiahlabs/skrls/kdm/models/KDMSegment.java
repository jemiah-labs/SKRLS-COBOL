package org.jemiahlabs.skrls.kdm.models;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.kdm.models.code.CodeModel;
import org.jemiahlabs.skrls.kdm.models.code.DataModel;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.converters.reflection.NativeFieldKeySorter;

@XStreamAlias("kdm:Segment")
public class KDMSegment extends NativeFieldKeySorter {
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
	
	@XStreamAlias("model")
	private DataModel dataModel;
	@XStreamImplicit
	private List<CodeModel> codeModels;
	
	public KDMSegment() {
		codeModels = new ArrayList<CodeModel>();
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
	
	public void addCodeModel(CodeModel codeModel) {
		codeModels.add(codeModel);
	}
	
	public List<CodeModel> getCodemodel() {
		return codeModels;
	}

}
