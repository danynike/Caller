package com.almundo.controller;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import com.almundo.model.CallEvent;
import com.almundo.model.Role;
import com.almundo.model.User;

/**
 * clase para monitorear los empleados en cola por role y colas de las llamadas
 * @author ADMIN
 *
 * @param <E>
 */
public class CallCenter<E> {
	private Map<String, Deque<User>> employeesInShift;
	private Deque<CallEvent> queuedCallers;
	
	public CallCenter() {
		employeesInShift = new HashMap<String, Deque<User>>();
		queuedCallers = new LinkedList<CallEvent>();
		
		employeesInShift.put(Role.EMPLOYEE.name(), new LinkedList<User>());
		employeesInShift.put(Role.SUPERVISOR.name(), new LinkedList<User>());
		employeesInShift.put(Role.DIRECTOR.name(), new LinkedList<User>());
	}

	public void addUserToShift(User e) {
		employeesInShift.get(e.getRole().name()).push(e);
	}

	public Map<String, Deque<User>> getAllUserInTurn() {
		return employeesInShift;
	}

	public Deque<CallEvent> getAllCallersQueued() {
		return queuedCallers;
	}
	
	public void addCallEvent(CallEvent c) {
		queuedCallers.add(c);
	}

}
