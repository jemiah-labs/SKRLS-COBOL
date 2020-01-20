package org.jemiahlabs.skrls.kdm.models.code;

import java.util.ArrayList;
import java.util.List;

import org.jemiahlabs.skrls.kdm.models.XmiEntity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("storableUnit") 
public class StorableUnit extends XmiEntity {
	@XStreamAsAttribute
	protected String name;
	@XStreamAsAttribute
	private StorableKind kind;
	@XStreamImplicit
	private List<StorableUnit> storableUnits;
	@XStreamImplicit
	private List<ItemUnit> itemUnits;
	
	public StorableUnit(String name) {
		this.name = name;
		storableUnits = new ArrayList<StorableUnit>();
		itemUnits = new ArrayList<ItemUnit>();
	}
	
	public StorableUnit() {
		storableUnits = new ArrayList<StorableUnit>();
		itemUnits = new ArrayList<ItemUnit>();
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
	
	public StorableKind getKind() {
		return kind;
	}

	public void setKind(StorableKind kind) {
		this.kind = kind;
	}
}
