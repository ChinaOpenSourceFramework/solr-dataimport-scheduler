package org.apache.solr.handler.dataimport.scheduler;

import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FullImportHttpGetScheduler  extends TimerTask{
	private static final Logger logger = LoggerFactory.getLogger(FullImportHttpGetScheduler.class);
	private String[] syncCores = SolrDataImportProperties.SYNC_CORCES();
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if (syncCores.length == 1 && syncCores[0].isEmpty()) {
			logger.warn(" , 前面必须是core");		
		} else {
			for (String coreName : syncCores) {
				HttpUtil.prepUrlSend(coreName,SolrDataImportProperties.FULL_PARAMS());	
			}
		}
	}


}
