package com.ruchir.toDoList.bean;

/**
 * 
 * @author	Ruchir Gupta
 * @email	erruchirgupta@gmail.com
 * @date	11:41:34 PM
 * 
 **/



public class BaseResponseBean {

	private String messgae;

	public BaseResponseBean(String messgae) {
		this.messgae = messgae;
	}

	public String getMessgae() {
		return messgae;
	}

	public void setMessgae(String messgae) {
		this.messgae = messgae;
	}
	
}
