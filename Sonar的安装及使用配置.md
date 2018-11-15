## Sonar的安装及使用配置

下载相关资料：

```
1.JDK1.8；
2.SonarQube 7.4->https://www.sonarqube.org/downloads/
3.SonarQube Scanner3.2->https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner  
根据不同系统进行下载；
4.mysql5.7；
5.本人window10本地安装配置，亲测可用。
```

安装SonarQube 7.4：

```
1.解压SonarQube 7.4，进入bin目录，根据不同系统选择不同的版本，在D:\...\sonarqube-7.4\bin\windows-x86-64里面启动StartSonar.bat，访问http://localhost:9000即可，如出现SonarQube页面，则表示安装成功。
2.

```

SonarQube 7.4 配置：

```
1.打开Mysql数据库，新建一个数据库；
2.打开SonarQube安装目录下的D:\...\sonarqube-7.4\conf\sonar.properties文件
3.在mysql5.X节点下输入以下信息:
	数据库配置信息：
	sonar.jdbc.url=jdbc:mysql://IP:3306/数据库名称?		useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true&useConfigs=maxPerformance
	sonar.jdbc.username=root
	sonar.jdbc.password=123456
	sonar.sorceEncoding=UTF-8
	sonar.login=admin  //默认admin
	sonar.password=admin //默认admin
4.重启sonarqube服务，再次访问http://localhost:9000，会稍微有点慢，因为要初始化数据库信息（不建议重启，除非你安装汉化包，不推荐使用汉语，因为西方列强的软件服务对中文支持不够友好）。
  重启步骤：重新启动SonarQube，首先关闭SonarQube.bat窗口，打开windows资源管理器，关闭所有java.exe进程，再重新点击StartSonar.bat文件。	

```

SonarQube Scanner3.2 配置：

```

1.配置环境变量：
	变量名：SONAR_SCANNER_HOME，
	变量值：SonarQube Scanner3.2的根目录,
	修改path，新增%SONAR_SCANNER_HOME%\bin，
	打开cmd，输入sonar-scanner -version，出现如下信息，表示安装成功。
		ERROR: Unrecognized option: -version
		INFO:
		INFO: usage: sonar-scanner [options]
		INFO:
		INFO: Options:
		INFO:  -D,--define <arg>     Define property
		INFO:  -h,--help             Display help information
		INFO:  -v,--version          Display version information
		INFO:  -X,--debug            Produce execution debug output
2.sonar-scanner配置：
	解压SonarQube Scanner3.2，进入D:\...\sonar-scanner-3.2\conf目录,打开文件sonar-scanner.properties输入如下信息：
		sonar.host.url=http://localhost:9000
		sonar.jdbc.url=jdbc:mysql://IP:3306/数据库名称?useUnicode=true&characterEncoding=utf8&rewriteBatchedStatements=true&useConfigs=maxPerformance
		sonar.jdbc.username=root
		sonar.jdbc.password=123456
```

项目文件配置：

```
在项目根目录下创建sonar-project.properties，输入如下信息：
	sonar.projectKey=my:project       //默认my:project
	sonar.projectName=你的项目名称
	sonar.projectVersion=1.0          //版本号
	sonar.sources=src        //默认src
 	sonar.java.binaries=D:/.../你的项目名称/target/classes
```

运行分析：

```
1.设置完后，打开cmd，进入项目根目录下，输入sonar-scanner命令，有显示成功，则表示成功。
	注意：项目单元测试编译成功，sonar才能执行成功，一定要确保项目单元测试mvn clean install成功。
2.打开http://localhost:9000/，静候十几秒，就会看到主页出现了分析项目的概要图
```

