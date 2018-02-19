package com.almundo.util;

import java.util.List;
import java.util.Observer;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.ReloadingFileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.event.EventListener;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.reloading.PeriodicReloadingTrigger;
import org.apache.commons.configuration2.reloading.ReloadingController;
import org.apache.commons.configuration2.reloading.ReloadingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * clase para cargar el properties de configuracion
 * @author ADMIN
 *
 */
public class ServiceConf {

	private static final Logger logger = LoggerFactory.getLogger(ServiceConf.class);
	private static final String PROPERTIES_FILE = "./config.properties";
	private static Observer observer;

	private static ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration> builder;
	protected static PropertiesConfiguration props;

	static {

		Parameters params = new Parameters();
		builder = new ReloadingFileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
				.configure(params.properties().setFileName(PROPERTIES_FILE).setListDelimiterHandler(new DefaultListDelimiterHandler(',')));
		ReloadingController controller = builder.getReloadingController();
		PeriodicReloadingTrigger trigger = new PeriodicReloadingTrigger(controller, null, 10, TimeUnit.SECONDS);
		controller.addEventListener(ReloadingEvent.ANY, new EventListener<ReloadingEvent>() {
			@Override
			public void onEvent(ReloadingEvent arg0) {
				logger.info("SE HA RECARGADO EL ARCHIVO DE CONFIGURACION {}", PROPERTIES_FILE);
				try {
					props = (PropertiesConfiguration) builder.getConfiguration();
					
					observer.update(null, null);
				} catch (ConfigurationException e) {
					logger.error(e.getMessage(), e);
				}
			}
		});

		trigger.start();

		try {
			props = (PropertiesConfiguration) builder.getConfiguration();
		} catch (ConfigurationException e) {
			logger.error(e.getMessage(), e);
		}
	}

	private ServiceConf() {

	}

	public static void setObserver(Observer observer) {
		ServiceConf.observer = observer;
	}

	public static String getCustomProperty(String property, String defaultValue) {
		return props.getString(property, defaultValue);
	}

	public static String getCustomProperty(String property) {
		return props.getString(property);
	}

	public static int getInt(String key, int valueDefault) {
		return props.getInt(key, valueDefault);
	}

	public static List<String> getStringList(String key) {
		return props.getList(String.class, key);
	}

}
