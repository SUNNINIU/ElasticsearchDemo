# ElasticsearchDemo
DEMO使用技术点：Maven+ SpringBoot + JDK 1.8.0_151 + ElasticSearch-2.3.3 + ElasticSearch-analysis-ik-1.9.3 + typeahead.js输入框输入信息后,自动提示补全控件 + jqPaginator.js分页控件+ Thymeleaf 页面模板显示
 
ElasticSearch是一个基于Lucene的搜索服务器。它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。
我们建立一个网站或应用程序，并要添加搜索功能，但是想要完成搜索工作的创建是非常困难的。我们希望搜索解决方案要运行速度快，我们希望能有一个零配置和一个完全免费的搜索模式，我们希望能够简单地使用JSON通过HTTP来索引数据，我们希望我们的搜索服务器始终可用，我们希望能够从一台开始并扩展到数百台，
我们要实时搜索，我们要简单的多租户，我们希望建立一个云的解决方案。因此我们利用Elasticsearch来解决所有这些问题以及可能出现的更多其它问题。

ElasticSearch 从2.x 至5.x api改动较大，本文仅以elasticsearch-2.3.3作为Demo，其他version操作详细查看官网 https://www.elastic.co/products/elasticsearch


一. Demo 技术列表：

  01.SpringBoot Spring Boot是由Pivotal团队提供的全新框架，其设计目的是用来简化新Spring应用的初始搭建以及开发过程。该框架使用了特定的方式来进行配置，从而使开发人员不再需要定义样板化的配置,详细请看 http://projects.spring.io/spring-boot/

  02.ElasticSearch-2.3.3  ElasticSearch是一个基于Lucene的搜索服务器 详细看官网 https://www.elastic.co/products/elasticsearch
  
  03.ElasticSearch-analysis-ik-1.9.3 是ElasticSearch的一个分词插件，elasticsearch-analysis-ik 是一款中文的分词插件，支持自定义词库，详细请看 https://github.com/medcl/elasticsearch-analysis-ik
  
  04.Typeahead.js 输入框输入信息后,自动提示补全控件,详细请看 https://github.com/twitter/typeahead.js/
  
  05.jqPaginator.js分页控件，详细请看作者网址：http://jqpaginator.keenwon.com/
  
  06.Thymeleaf 页面模板显示，目前Spring官方已经不推荐使用JSP来开发WEB了，推荐使用Thymeleaf作为前端页面 详细请看官网：http://www.thymeleaf.org/；
  
	Pom.xml

		<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
		<modelVersion>4.0.0</modelVersion>
		<groupId>elasticsearch</groupId>
		<artifactId>ElasticsearchDemo</artifactId>
		<packaging>war</packaging>
		<version>0.0.1-SNAPSHOT</version>
		<name>ElasticsearchDemo Maven Webapp</name>
		<url>http://maven.apache.org</url>
		   <!-- spring boot 基本环境 -->
		<parent>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-parent</artifactId>
			<version>1.3.1.RELEASE</version>
		</parent>
		<dependencies>
			<!--es2.3.3  -->
			<dependency>
				<groupId>org.elasticsearch</groupId>
				<artifactId>elasticsearch</artifactId>
				<version>2.3.3</version>
			</dependency>

			<dependency>
				<groupId>com.fasterxml.jackson.core</groupId>
				<artifactId>jackson-databind</artifactId>
				<version>2.6.6</version>
			</dependency>
	     		<!--springboot  -->
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-web</artifactId>
			</dependency>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-test</artifactId>
				<scope>test</scope>
			</dependency>
			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-web</artifactId>
			</dependency>
			<dependency>
				<groupId>org.mybatis.spring.boot</groupId>
				<artifactId>mybatis-spring-boot-starter</artifactId>
				<version>1.1.1</version>
			</dependency>

			<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-devtools</artifactId>
				<optional>true</optional>
			</dependency>
			<!--MySql  -->
				
			 <dependency>
				<groupId>mysql</groupId>
				<artifactId>mysql-connector-java</artifactId>
			</dependency>
			<!--thymeleaf  -->
			<dependency>  
			 <groupId>org.springframework.boot</groupId>  
			 <artifactId>spring-boot-starter-thymeleaf</artifactId>  
			</dependency> 
			
		</dependencies>
		<build>
			<finalName>ElasticsearchDemo</finalName>
		</build>
	</project>

  
  
二、DEMO DOC

	1.本文以window elasticsearch-2.3.3 version为例， 下载地址 https://www.elastic.co/downloads/past-releases 选择相应version 下载.zip ;

	2.解压.zip , 目录elasticsearch-2.3.3\bin 双击 elasticsearch.bat 启动elasticsearch；

	3.安装 ik 分词 
	   01.ik下载地址：https://github.com/medcl/elasticsearch-analysis-ik
	   
	   02.在github 上有相应的ik--> elasticsearch 版本对应表，请按照对应表下载对应 version 的ik.zip; 本文使用elasticsearch-2.3.3 对应ik版本为 elasticsearch-analysis-ik-1.9.3。
	   
	   03.下载 elasticsearch-analysis-ik-1.9.3.zip ,解压后使用dos 命令进入到解压文件夹的根目录，使用 mvn clean package  命令对ik 进行打包。
		  命令执行完毕创建elasticsearch-analysis-ik-1.9.3-sources.jar，elasticsearch-analysis-ik-1.9.3.jar 以及一些文件在target文件夹下表示打包完毕。
		  
	   04.在elasticsearch-2.3.3中手动创建目录plugins\analysis-ik ，将 elasticsearch-analysis-ik-1.9.3\target\releases\elasticsearch-analysis-ik-1.9.3 目录下的plugin-descriptor.properties及整个文件夹config， 和elasticsearch-analysis-ik-1.9.3.jar 拷贝到该目录下。
		 
	   05.将 elasticsearch-analysis-ik-1.9.3\target\releases\elasticsearch-analysis-ik-1.9.3 所有.jar 拷贝到 elasticsearch-2.3.3 根目录lib下。
	   
	   06.在elasticsearch-2.3.3的配置文件config/elasticsearch.yml中最后增加ik的配置
		   index:  
		  analysis:                     
			analyzer:        
			  ik:  
				  alias: [ik_analyzer]  
				  type: org.elasticsearch.index.analysis.IkAnalyzerProvider  
			  ik_max_word:  
				  type: ik  
				  use_smart: false  
			  ik_smart:  
				  type: ik  
				  use_smart: true 
		   
		   或者
		   
		   index.analysis.analyzer.ik.type : “ik”  
		   
		07.重启elasticsearch-2.3.3，正常启动表示配置成功。
	
	4.使用maven 导入demo
	
	5.执行package com.net.Application.java 类启动SpringBoot, 启动会执行 package com.net.InitESData.java 初始化测试数据
	
	6.成功启动完毕后，在浏览器上输入 http://localhost:8080/index 便可进行预览测试。

项目效果图：

![Image text](https://github.com/SUNNINIU/ElasticsearchDemo/blob/master/src/main/resources/static/image/ElasticsearchDemo.gif)

备注：任何问题在github提交Issues,有时间会及时修复提交代码。

本demo 参考文章 http://blog.csdn.net/hunanlzg/article/details/51658370 感谢！

	
	   
