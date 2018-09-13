# JXWL物流业务系统 
## 开发环境
* tomcat8 + jdk8 + maven3.5.3 + intellij2018.2

## 下载依赖 

* `git clone` 
* 只打开`jxwl-root`
* 配置maven选自己安装的代替默认，自动下载依赖
* 查看`pom.xml`文件是否有报错

## 项目编译 

* 在intellij的右侧边打开`MavenProjects`
* 展开`jxwl-root/liftcycle`
* 双击install
* 控制台出现`BUILD SUCCESS`即为编译成功

## 项目运行

- 最新源码在配置好tomcat即可运行
- 只能显示login页面，登陆功能未实现
- 建议重新`git clone`代码，因为会有编译残存影响运行
- 运行成功是第一步
- word天太不容易了QAQ

## UAC配置

	AuthConfig配置成功，并成功引入拦截器。

## reids缓存

	reids缓存加入，并测试成功。

	redis.host=192.168.1.79