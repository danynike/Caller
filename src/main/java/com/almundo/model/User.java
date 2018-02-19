package com.almundo.model;

import com.almundo.util.Status;

/**
 * Clase para setear los empleados
 * @author ADMIN
 *
 */
public class User {

	private String name;
	private String status = Status.Avl;
	private Role role;
	
	public User(String name, String status, Role role){
		setName(name);
		setStatus(status);
		setRole(role);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return getName() + "-" + getStatus() + " ("+getRole().name()+")";
	}
}
