# solr-dataimport-scheduler

之前的定时任务是很早之前的，现在已经不太适合，所有重新写的，现在是基于solr 7。
可以直接下载下来代码可以直接运行。
项目很简单就不做详细介绍：
一个入口类，一个配置类，一个工具类，两个定时任务类。

## 使用
用开发工具eclipse导出jar
把dataimport.properties放到solr启动项目中，和solr启动项目的lo4j.xml同一个目录
配置监听器，在solr项目的web.xml加入下面的。具体可以查看项目的中的配置

```
<!-- Creates the Spring Container shared by all Servlets and Filters -->
	<listener>
		<listener-class>org.apache.solr.handler.dataimport.scheduler.ApplicationListener</listener-class>
	</listener>
	
```
