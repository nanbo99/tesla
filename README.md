# 概述

* Tesla是一个微服务API网关，类似于netflix的zuul

# 功能

* 支持gRrpc，将http请求转换为gRpc请求
* 支持Dubbo，将http请求转换为Dubbo请求
* 支持Spring cloud
* 支持智能路由
* 支持限流、鉴权、Ip黑名单、Cookie黑名单、url黑名单，UA黑名单等规则的限制
* 支持动态路由规则的定义
* 有OPS控制台
* 不依赖于Web容器，基于Netty的开发，在性能上要比Zuul要好

# 详细说明
 要运行起来需要在pom.xml中手动添加saluki的jar包依赖，该jar包可以去拉取项目然后编译而获得。
<build>
  <resources>
				<resource>
					<directory>libs</directory>
					<targetPath>${project.build.directory}/libs</targetPath>
				</resource>
			</resources>
		</build>
