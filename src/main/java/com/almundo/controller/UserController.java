package com.almundo.controller;

import java.util.Arrays;
import java.util.Deque;
import java.util.Map;

import com.almundo.model.CallEvent;
import com.almundo.model.Role;
import com.almundo.model.User;
import com.almundo.util.Status;

public class UserController {

	private CallCenter<User> callCenter;
	private Deque<CallEvent> callEvent;
	private Map<String, Deque<User>> shiftUsers;

	public UserController(CallCenter<User> cc) {
		callCenter = cc;
		shiftUsers = cc.getAllUserInTurn();
		callEvent = callCenter.getAllCallersQueued();
	}
	
	public Deque<CallEvent> getCallEvents() {
		return callEvent;
	}

	public CallEvent getNextCallEvents() {
		return callEvent.poll();
	}

	public void addNextCallEvent(CallEvent c) {
		callEvent.offerFirst(c);
	}

	public User getNextAvailableUser() {
		User temp = null;
		boolean found = false;
		int typeOfUserIndex = 0;
		while (typeOfUserIndex < shiftUsers.size() && found == false) {
			int employeeIndex = 0;
			Deque<User> employeesOfType = shiftUsers.get(Arrays.asList(Role.values()).get(typeOfUserIndex).name());
			while (employeeIndex < employeesOfType.size()) {
				temp = employeesOfType.poll();
				employeesOfType.offer(temp);
				if (temp.getStatus().equals(Status.Avl)) {
					found = true;
					temp.setStatus(Status.Bus);
					break;
				} else {
					temp = null;
					employeeIndex++;
				}

			}
			typeOfUserIndex++;
		}
		return temp;
	}

}