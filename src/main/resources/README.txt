1.本文以window elasticsearch-2.3.3 version为例， 下载地址 https://www.elastic.co/downloads/past-releases 选择相应version 下载.zip ;

2.解压.zip , 目录elasticsearch-2.3.3\bin 双击 elasticsearch.bat 启动elasticsearch；

3.安装 ik 分词 https://github.com/medcl/elasticsearch-analysis-ik
   01.在github 上有相应的ik--> elasticsearch 版本，请按照对应表下载对应 version 的ik.zip; 本文使用elasticsearch-2.3.3 对应ik版本为 elasticsearch-analysis-ik-1.9.3。
   02.下载 elasticsearch-analysis-ik-1.9.3.zip ,解压后使用dos 命令进入到解压文件夹的根目录，使用 mvn clean package  命令对ik 进行打包。
      命令执行完毕创建elasticsearch-analysis-ik-1.9.3-sources.jar，elasticsearch-analysis-ik-1.9.3.jar 以及一些文件在target文件夹下表示打包完毕。
	  
   03.在elasticsearch-2.3.3中手动创建目录plugins\analysis-ik ，将 elasticsearch-analysis-ik-1.9.3\target\releases\elasticsearch-analysis-ik-1.9.3 目录下的plugin-descriptor.properties及整个文件夹config， 和elasticsearch-analysis-ik-1.9.3.jar 拷贝到该目录下。
     
   
   04.将 elasticsearch-analysis-ik-1.9.3\target\releases\elasticsearch-analysis-ik-1.9.3 所有.jar 拷贝到 elasticsearch-2.3.3 根目录lib下。
   
   05.在elasticsearch-2.3.3的配置文件config/elasticsearch.yml中最后增加ik的配置
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
	   
	06.重启elasticsearch-2.3.3，正常启动表示配置成功。
	   