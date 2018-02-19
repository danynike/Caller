package com.almundo.controller;

import java.time.LocalDateTime;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almundo.model.CallEvent;
import com.almundo.model.User;
import com.almundo.util.ServiceConf;
import com.almundo.util.Status;

/**
 * Clase para monitorear los agentes y las llamadas, empezando la llamada y finalizando la misma
 * @author ADMIN
 *
 */
public class ReportCall implements Runnable {
	private long id;
	private User user;
	private CallEvent callE;
	private static final Logger logger = LoggerFactory.getLogger(ReportCall.class);

	public ReportCall(long l, CallEvent c, User e) {
		id = l;
		user = e;
		callE = c;
	}

	@Override
	public void run() {
		Random r = new Random();
		int threadDuration = (r.nextInt(ServiceConf.getInt("max.duration", 11) - ServiceConf.getInt("min.duration", 5)) + ServiceConf.getInt("min.duration", 5)) * 1000;
		logger.info(LocalDateTime.now() + " -CallerId= " + callE.getCallerId() + " -" + user.toString());
		try {
			user.setStatus(Status.Avl);
			Thread.sleep(threadDuration);
			logger.info(LocalDateTime.now() + " Finaliza " + id);
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
	}

	public User geUser() {
		return user;
	}

}
