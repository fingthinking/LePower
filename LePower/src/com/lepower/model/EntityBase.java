package com.lepower.model;

import org.xutils.db.annotation.Column;

public abstract class EntityBase {
	
	@Column(autoGen=false,isId=true,name="id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
