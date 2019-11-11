package org.jemiahlabs.skrls.kdm.models.code;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.kdm.models.XmiEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("dataElement")
public class DataElement extends XmiEntity {
	@XStreamAsAttribute
	protected String name;
	@XStreamImplicit
	private List<DataElement> dataElements;
	@XStreamImplicit
	private List<StorableUnit> storableUnits;
	@XStreamImplicit
	private List<ItemUnit> itemUnits;
	
	public DataElement() {
		this(null);
	}
	
	public DataElement(String name) {
		this.name = name;
		dataElements = new ArrayList<DataElement>();
		storableUnits = new ArrayList<StorableUnit>();
		itemUnits = new ArrayList<ItemUnit>();
	}
	
	public void addDataElement(DataElement dataElement) {
		dataElements.add(dataElement);
	}

	public List<DataElement> getDataElement() {
		return dataElements;
	}
	
	public void addStorableUnit(StorableUnit storableUnit) {
		storableUnits.add(storableUnit);
	}

	public List<StorableUnit> getStorableUnits() {
		return storableUnits;
	}
	
	public void addItemUnit(ItemUnit itemUnit) {
		itemUnits.add(itemUnit);
	}
	
	public List<ItemUnit> getItemUnits() {
		return itemUnits;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
