package org.apache.solr.handler.dataimport.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationListener.class);

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("����");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		//�Ƿ񼤻�
		if(!SolrDataImportProperties.SYNC_ENABLED()){
			logger.info("��ʱ���񲻼���");
			return;
		}
		if(SolrDataImportProperties.SYNC_CORCES() == null){
			logger.info("û�о����core");
			return;
		}
		
		logger.info("��ʱ����������ݿ�ʼ");
		ServletContext servletContext = servletContextEvent.getServletContext();

		try {
			// ������������ƻ�
			Timer deltaTimer = new Timer();
			DeltaImportHttpGetScheduler deltaTask =  new DeltaImportHttpGetScheduler();
			
			// get a calendar to set the start time (first run)
			Calendar calendar = Calendar.getInstance();

			// set the first run to now + interval (to avoid fireing while the
			// app/server is starting)
			calendar.add(Calendar.MINUTE, SolrDataImportProperties.DELTA_INTERVAL());
			
			Date startTime = calendar.getTime();

			// schedule the task
			deltaTimer.scheduleAtFixedRate(deltaTask, startTime, 1000 * 60 * SolrDataImportProperties.DELTA_INTERVAL());

			// save the timer in context
			servletContext.setAttribute("deltaTimer", deltaTimer);

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			if(SolrDataImportProperties.FULL_INTERVAL() == 0){
				logger.info("����ȫ�����¶�ʱ����");
				return;
			}
			// ������������ƻ�
			Timer fullImportTimer = new Timer();
			FullImportHttpGetScheduler  fullImportTask = new FullImportHttpGetScheduler();
			Calendar fullImportCalendar = Calendar.getInstance();
			
			fullImportCalendar.add(Calendar.MINUTE, SolrDataImportProperties.FULL_INTERVAL());
			Date fullImportStartTime = fullImportCalendar.getTime();

			// schedule the task
			fullImportTimer.scheduleAtFixedRate(fullImportTask, fullImportStartTime, 1000 * 60 * SolrDataImportProperties.FULL_INTERVAL());

			// save the timer in context
			servletContext.setAttribute("fullImportTimer", fullImportTimer);
	
		} catch (Exception e) {
			if (e.getMessage().endsWith("disabled")) {
				logger.warn("Schedule disabled");
			} else {
				logger.error("Problem initializing the scheduled task: ", e);
			}
		}
		
		
	}

}
