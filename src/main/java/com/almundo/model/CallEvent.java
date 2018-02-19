package com.almundo.model;

/**
 * clave de los eventos de llamadas
 * @author ADMIN
 *
 */
public class CallEvent {

	private String callerId;
	
	public CallEvent(String callerId){
		setCallerId(callerId);
	}
	
	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}
	
	public String getCallerId() {
		return callerId;
	}
	
}
