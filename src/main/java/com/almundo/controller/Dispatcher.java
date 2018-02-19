package com.almundo.controller;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almundo.model.CallEvent;
import com.almundo.model.User;
import com.almundo.util.ServiceConf;

public class Dispatcher {
	
	private ExecutorService executor;
	private UserController usersController;
	private static final Logger logger = LoggerFactory.getLogger(ReportCall.class);

	public Dispatcher(UserController ec) {
		usersController = ec;
		executor = Executors.newFixedThreadPool(ServiceConf.getInt("calls", 10));
	}

	public void dispatchCall(long customerCallId, CallEvent customer) {
		User nextAvailableEmployee = usersController.getNextAvailableUser();
		if (nextAvailableEmployee != null) {
			Runnable call = new ReportCall(customerCallId, customer, nextAvailableEmployee);
			executor.execute(call);
		}
		else {
			logger.info(LocalDateTime.now() + " No User available to take the call.");
			usersController.addNextCallEvent(customer);
		}
	}

	public void terminateDispatch() throws InterruptedException {
		executor.shutdown();
		executor.awaitTermination(11, TimeUnit.SECONDS);
		logger.info("All Calls finished.");
	}

}