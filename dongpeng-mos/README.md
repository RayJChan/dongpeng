<h2>一、技术选型</h2>

<ul>
<li>JDK：1.8</li>
<li>Spring cloud boot 2.0.1.RELEASE</li>
<li>Spring cloud Finchley.RC1</li>
<li>路由网关：Zuul</li>
<li>ORM框架：Mybatis 3.4.6</li>
<li>数据库：MySql 5.7</li>
<li>数据连接池：alibaba Druid 1.1.9</li>
<li>数据库读写分离框架：sharding sphere 3.00.M1</li>
<li>缓存框架：oschina j2cache 2.3.17-release</li>
<li>集中式缓存：Redis 3.2.100</li>
<li>进程内缓存：Caffeine 2.6.2</li>
<li>接口安全认证：jwt（json web token） 0.9.0</li>
<li>消息中间件：rabbitMQ 3.7.5</li>
<li>服务链路跟踪：zipkin 2.8.3</li>
</ul>

<h2>二、项目模块简介</h2>

<h3>1.Common模块</h3>
所有的开发项目都应引用该模块。<br />
包含：公用的工具类、基础实体类、配置类；数据库对应的实体bean也放在该项目；j2cache的配置文件<br />

<h3>2.Common-db模块</h3>
所有需要数据库操作的项目都应引用该模块。引用该模块后无需再引用common模块<br />
包含：mybatis配置、数据连接池配置、基础dao、基础service、分页插件<br />

<h3>3.Eureka-server 模块</h3>
注册中心。该模块启用了安全设置，需要在配置文件中设置账号密码<br />

<h3>4.Config-server 模块</h3>
配置中心。需要在该模块设置远程git仓库、配置访问配置中心的账号密码、开启消息总线spring cloud bus、配置rabbitMQ。其他模块的application.yml主要配置都放在git仓库，通过访问该模块获取<br />

<h3>5.Api-gateway 模块</h3>
网关。该模块实现了接口安全jwt token验证、zuul熔断、跨域请求<br />

<h3>6.System 模块</h3>
数据库服务生产者demo。存放Dao、Service、对外访问controller（接口）、mybatis mapper文件。新开发的项目可以参考该demo<br />
服务消费者demo。该模块调用了producer-db的接口。实现了feign调用微服务、hystrix熔断、rabbitMq和turbine。新开发项目可以参考该demo<br />

<h3>7.Turbine-stream-rabbitmq 模块</h3>
hystrix熔断数据采集。基于turbine 和 rabbitmq。需要依赖于rabbitmq的启动<br />

<h3>8.Hystrix-dashboard 模块</h3>
hystrix熔断监控仪表板。需要依赖turbine-stream-rabbitmq的启动<br />

<h3>9.Sleuth 和 zipkin 服务链路跟踪</h3>
该模块没有对应代码。因为从spring cloud 2.0开始，官方不建议自行打包，而改为提供统一包的方式。只需要启动zipkin.jar 即可。<br />
启动命令：java -jar zipkin-server-2.8.3-exec.jar --RABBIT_URI=amqp://用户名:密码@域名或IP:端口号/virtualHost名称<br />

<h3>10.Shedulejob 模块</h3>
定时任务模块，定时任务需要在此处理<br />

<h3>11.Websocket 模块</h3>
浏览器通知模块<br />
