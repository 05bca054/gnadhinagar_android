package com.nplussolutions.easygandhinagar;

public class RootMapPOJO {
	String des;
	String distance;
	String duration;
	
	public RootMapPOJO(String des, String distance, String duration) {
		super();
		this.des = des;
		this.distance = distance;
		this.duration = duration;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
