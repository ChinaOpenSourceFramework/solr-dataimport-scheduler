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
		logger.info("发送请求{}",coreUrl);
		String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(coreUrl);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                logger.debug(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.warn("发送GET请求出现异常！{}" , e);
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        logger.info("请求返回结果:{}",result);
	}
}
