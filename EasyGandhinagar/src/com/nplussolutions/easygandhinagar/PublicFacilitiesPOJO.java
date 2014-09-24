package com.nplussolutions.easygandhinagar;

public class PublicFacilitiesPOJO {
	String name;
	String id;
	boolean check;

	public PublicFacilitiesPOJO(String name,String id,boolean check) {
		super();
		this.name = name;
		this.id = id;
		this.check = check;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	};
	

}
