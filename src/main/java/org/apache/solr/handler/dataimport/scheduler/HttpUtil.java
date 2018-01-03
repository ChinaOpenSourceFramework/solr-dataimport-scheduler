package org.apache.solr.handler.dataimport.scheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	
	public static void prepUrlSend(String params) {
		String coreUrl = "http://" + SolrDataImportProperties.SERVER() + ":" + SolrDataImportProperties.PORT() + "/" + SolrDataImportProperties.WEBAPP() + params;
		sendHttpGet(coreUrl, null);
	}

	public static void prepUrlSend(String coreName, String params) {
		String coreUrl = "http://" + SolrDataImportProperties.SERVER() + ":" + SolrDataImportProperties.PORT() + "/" + SolrDataImportProperties.WEBAPP() + "/" + coreName + params;
		sendHttpGet(coreUrl, coreName);
	}

	private static void sendHttpGet(String coreUrl, String coreName) {
		logger.info("��������{}",coreUrl);
		String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(coreUrl);
            // �򿪺�URL֮�������
            URLConnection connection = realUrl.openConnection();
            // ����ͨ�õ���������
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // ����ʵ�ʵ�����
            connection.connect();
            // ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = connection.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
            for (String key : map.keySet()) {
                logger.debug(key + "--->" + map.get(key));
            }
            // ���� BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.warn("����GET��������쳣��{}" , e);
        }
        // ʹ��finally�����ر�������
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        logger.info("���󷵻ؽ��:{}",result);
	}
}
