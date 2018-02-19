package com.almundo;

import com.almundo.controller.CallCenter;
import com.almundo.controller.Dispatcher;
import com.almundo.controller.UserController;
import com.almundo.model.CallEvent;
import com.almundo.model.Role;
import com.almundo.model.User;
import com.almundo.util.Status;

public class Microservice {

	private static UserController UserController;
	private static Dispatcher dispatcher;
	private static CallCenter<User> callCenter;

	public static void main(String[] args) throws Exception{
		callCenter = new CallCenter<User>();
		User s1 = new User("supervisor", Status.Avl, Role.SUPERVISOR);
		
		User d1 = new User("director", Status.Avl, Role.DIRECTOR);
		
		User o1 = new User("agent1", Status.Bus, Role.EMPLOYEE);
		User o2 = new User("agent2", Status.Bus, Role.EMPLOYEE);
		User o3 = new User("agent3", Status.Avl, Role.EMPLOYEE);
		callCenter.addUserToShift(s1);
		callCenter.addUserToShift(d1);
		callCenter.addUserToShift(o1);
		callCenter.addUserToShift(o2);
		callCenter.addUserToShift(o3);
		
		UserController = new UserController(callCenter);
		dispatcher = new Dispatcher(UserController);

		CallEvent c1 = new CallEvent("573112345675");
		CallEvent c2 = new CallEvent("573124578906");
		CallEvent c3 = new CallEvent("573134575406");
		CallEvent c4 = new CallEvent("573144574506");
		callCenter.addCallEvent(c1);
		callCenter.addCallEvent(c2);
		callCenter.addCallEvent(c3);
		callCenter.addCallEvent(c4);

		// run calls
		int callId = 0;
		CallEvent tempCustomer = UserController.getNextCallEvents();
		while (tempCustomer != null) {
			dispatcher.dispatchCall(callId, tempCustomer);
			callId++;
			tempCustomer = UserController.getNextCallEvents();
		}
		
		dispatcher.terminateDispatch();

	}
}
