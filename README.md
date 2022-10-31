### [Spring Cloud Alibaba 大型微服务项目实战](https://s.juejin.cn/ds/6tgXRVH/)

### 1. 为什么要使用微服务？

1. 随着系统业务规模不断扩大，初始的系统无法执行越来越复杂的业务场景，此时便需要对系统进行优化，包括缓存、集群、前后端分离、数据库读写分离、
   做分布式和拆分微服务处理
   
2. 从开发团队来讲，当技术团队中人越来越多，越需要对系统的分工进行明确，拆分微服务便于职责分配

### 2. 服务治理
在没有服务治理之前，服务与服务之间的通信需要通过硬编码或者静态配置服务名称、IP、端口号的形式来满足通信条件，但是随着业务的发展，
微服务实例在不断增多，如果再用以上方法来配置就比较麻烦，当然可以采用Nginx反向代理来实现通信，
但是两个服务需要借助中间层代理来完成，就出现了新的**无法直连**的问题，增加了一次网络消耗，而且也需要在代理处配置被代理的服务器IP。
为了解决以上的问题，便出现了服务治理的思想。

服务治理中的三个核心问题：**服务注册**、**服务发现**和**服务的健康检查**

1. 服务注册
   
每个服务的实例在启动时向注册中心发出通知，将自己的服务名称、服务地址等信息提交到注册中心，注册中心将这些信息维护到实例清单中，这个过程就是服务注册。

2. 服务发现

服务调用方会从服务的注册中心获取被调用方的服务列表，或者由服务的注册中心将被调用方的服务列表变动信息推送给服务调用方，这个过程叫做**服务发现**。
接下来的服务通信是从这些服务列表通过负载均衡的算法选取一个服务进行调用。

3. 健康检查

所有的服务实例在注册中心注册成功后，每个服务实例都需要定时发送请求，告知注册中心自己的状态，业界一般称这个过程为 `heartbeat`，即 **“心跳”**。
如果注册中心在一段时间内没有收到某个服务实例的心跳信息，就会将这个服务实例标记为不可用或者不可达的状态，进而从可用的服务列表中剔除将该服务实例的信息，
在调用方查询可用的服务实例清单时，该服务实例的信息就不会返回给调用方。

### 3. 服务网关

![img.png](static/images/demo/img_0.png)

- 为什么使用服务网关？
  - 统一通信方式，减少客户端的接入难度。可以使用服务网关对外提供RESTful风格的接口，请求到达网关后，由网关组件将请求转发到对应的微服务中
  - 服务网关作为客户端到微服务架构的唯一入口，管理所有的接入请求，可以统一接口的URL写法。同时，也能够作为一道屏障，屏蔽一些后端服务的处理细节
  - 服务网关可以对后端各个服务做统一的管控和配置管理，用于保护、增强和控制对于后端服务的访问
  - 服务网关可以做一些定制化编码，对请求进行统一的处理和拦截，从而完成一些前置处理，让隐藏在微服务网关后面的业务系统就可以更加专注于业务本身

服务网关是微服务架构中一个非常关键的角色，作为后端服务的统一入口，负责统筹和管理后端服务。
能够提供的功能有路由功能、负载均衡、安全认证、流量染色、灰度发布、限流熔断、协议转换、接口文档中心、日志收集等等

### 4. Seata分布式事务控制

![img.png](static/images/demo/img.png)

分布式事务样例：**在订单服务中**，调用商品服务和购物车服务的删除方法，代码中有异常，那么事务执行过程中，任何删除操作都不能执行成功

分别启动商品服务、购物车服务和订单服务，在控制台中可以发现如下日志，可以发现注册了RM和TM两个角色

![img_1.png](static/images/demo/img_1.png)

也就是说分别注册了**商品服务的RM、TM**和**购物车服务的RM、TM**和**订单服务的RM、TM**

那么什么是RM？什么又是TM？来看看官方文档的介绍图

![img_2.png](static/images/demo/img_2.png)

- **RM**: Resource Manager，资源管理器。负责管理分支事务处理的资源，与TC交互，控制分支事务提交或回滚
- **TM**: Transaction Manager，事务管理器。负责定义全局事务的范围，开启、提交或回滚全局事务

其中还有TC，也一起看一下
- **TC**: Transaction Coordinator，事务协调者，是**Seata Server本体**。负责维护全局、分支事务的状态，驱动全局事务提交或回滚

如果以我们的分布式微服务系统为例，订单服务是事务的开端，所以全局事务管理器是订单服务的TM，做图示的话，如下

![img_2.png](static/images/demo/未命名绘图.jpg)

测试这个事务，控制台日志如下，可以发现事务发生异常后已经回滚
![img_3.png](static/images/demo/img_3.png)

#### 4.1 AT模式下分布式事务控制原理

- AT模式: 基于支持本地 ACID 事务的关系型数据库; Java 应用，通过 JDBC 访问数据库

整体机制: 两阶段提交
- 一阶段: 业务数据和回滚日志记录在同一个本地事务中提交，释放本地锁和连接资源
- 二阶段: 
  - 提交异步化，非常快速地完成
  - 回滚通过一阶段的回滚日志进行反向补偿

[官方文档举例](https://seata.io/zh-cn/docs/overview/what-is-seata.html)

以系统中例子来描述：

OrderServer作为TC开启全局事务，拿到全局锁。

正常情况下：商品服务开启本地事务，执行业务数据更改并记录回滚日志，提交本地事务；购物车服务开启本地事务，执行业务数据更改并记录回滚日志，提交本地事务。

TC作为全局事务的管理者，根据事务执行过程中有没有发生异常来判断提交全局事务或者回滚全局事务，若回滚全局事务，则商品服务和购物车服务还要开启本地事务，
获取到本地锁，执行回滚操作并删除回滚数据。

### 5. Sentinel限流
#### 5.1 限流策略（controlBehavior）
- **直接流控**: 直接作用于对应资源上，如果访问压力超过阈值，那么后续请求就会被拦截
![img_4.png](static/images/demo/img_4.png)
  
- **关联流控**: 作用于`/order/testRelateApi1`资源如果超过阈值，那么会限制对`/order/testRelateApi2`资源的访问，如下图
![img_5.png](static/images/demo/img_5.png)
  
- **链路流控**: 使用`@SentinelResource`把`/order/testChainApi1`和`/order/testChainApi2`，标记为同一个资源，添加如下配置
![img_6.png](static/images/demo/img_6.png)
  如果对该资源的访问超过了阈值，那么会对`/order/testChainApi1`的资源进行限制，但是`/order/testChainApi2`不会受到任何影响
  
使链路流控生效需要添加如下配置
```properties
spring.cloud.sentinel.web-context-unify=false
```

#### 5.2 流控效果（strategy）
- **快速失败**: 也叫**直接拒绝**，当QPS超过任意规则的阈值后，新的请求就会被立即拒绝，拒绝方式为抛出FlowException异常
- **Warm Up**: 预热或冷启动，Sentinel会在规定的预热时间内，将流量控制慢慢的加到给定阈值，避免大规模流量直接进来将服务器压垮
- **匀速排队**: 让请求以均匀的速度通过，如果指定QPS为2，那么在该模式下，会500毫秒通过一个请求，其他请求进入队列排队，
  如果超过配置的等待时间，则会触发限流机制

- 测试限流策略: QPS超过1采用直接流控+快速失败的模式
```json
[
    {
        "resource": "testSentinel",
        "limitApp": "default",
        "grade": 1,
        "count": 1,
        "clusterMode": false,
        "controlBehavior": 0,
        "strategy": 0
    }
]
```

### 6. Sentinel熔断器
#### 6.1 熔断策略
- **慢调用比例SLOW_REQUEST_RATIO**: 当单位统计时长（statIntervalMs）内请求数目大于设置的最小请求数目（minRequestAmount），
  并且慢调用的比例（slowRatioThreshold）大于阈值，则接下来的熔断时长内请求会自动被熔断。
  经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN状态），若接下来的一个请求响应时间小于设置的慢调用RT（最大响应时间）则结束熔断，
  若大于设置的慢调用 RT 则会再次被熔断
- **异常比例ERROR_RATIO**: 当单位统计时长（statIntervalMs）内请求数目大于设置的最小请求数目（minRequestAmount），
  并且异常的比例大于阈值，则接下来的熔断时长内请求会自动被熔断。经过熔断时长（timeWindow）后熔断器会进入探测恢复状态（HALF-OPEN状态），
  若接下来的一个请求成功完成（没有错误）则结束熔断，否则会再次被熔断。异常比率的阈值范围是 [0.0, 1.0]，代表 0%~100%
- **异常数ERROR_COUNT**: 当单位统计时长（statIntervalMs）内的异常数目超过阈值之后会自动进行熔断。
  经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN状态），若接下来的一个请求成功完成（没有错误）则结束熔断，否则会再次被熔断

> 其中慢调用最大响应时长RT、异常的比例阈值和异常数模式下的阈值都是用 count 字段进行配置

- 测试熔断策略：慢调用比例，1s内的请求数量大于1且慢调用比例大于0.1则熔断1min
```json
[
    {
        "count": 1,
        "grade": 0,
        "limitApp": "default",
        "minRequestAmount": 1,
        "resource": "testSentinelBreak",
        "slowRatioThreshold": 0.1,
        "statIntervalMs": 1000,
        "timeWindow": 60
    }
]
```

### 7. 链路追踪
![img_7.png](static/images/demo/img_7.png)
链路追踪为分布式请求调用记录调用链路，方便定位问题。

- 样例日志
```
2022-10-21 17:37:29.221 DEBUG [demo-order-service,7cf8d8cb6c32b1b5,7cf8d8cb6c32b1b5] 66763 --- [nio-8092-exec-3] c.e.o.service.DemoGoodsService           : [DemoGoodsService#getGoodsDetail] ---> GET http://demo-goods-service/goods/10938 HTTP/1.1
2022-10-21 17:37:29.221 DEBUG [demo-order-service,7cf8d8cb6c32b1b5,7cf8d8cb6c32b1b5] 66763 --- [nio-8092-exec-3] c.e.o.service.DemoGoodsService           : [DemoGoodsService#getGoodsDetail] ---> END HTTP (0-byte body)
2022-10-21 17:37:29.224 DEBUG [demo-order-service,7cf8d8cb6c32b1b5,7cf8d8cb6c32b1b5] 66763 --- [nio-8092-exec-3] c.e.o.service.DemoGoodsService           : [DemoGoodsService#getGoodsDetail] <--- HTTP/1.1 200 (2ms)
2022-10-21 17:37:29.224 DEBUG [demo-order-service,7cf8d8cb6c32b1b5,7cf8d8cb6c32b1b5] 66763 --- [nio-8092-exec-3] c.e.o.service.DemoGoodsService           : [DemoGoodsService#getGoodsDetail] connection: keep-alive
2022-10-21 17:37:29.224 DEBUG [demo-order-service,7cf8d8cb6c32b1b5,7cf8d8cb6c32b1b5] 66763 --- [nio-8092-exec-3] c.e.o.service.DemoGoodsService           : [DemoGoodsService#getGoodsDetail] content-length: 45
2022-10-21 17:37:29.224 DEBUG [demo-order-service,7cf8d8cb6c32b1b5,7cf8d8cb6c32b1b5] 66763 --- [nio-8092-exec-3] c.e.o.service.DemoGoodsService           : [DemoGoodsService#getGoodsDetail] content-type: text/plain;charset=UTF-8
2022-10-21 17:37:29.224 DEBUG [demo-order-service,7cf8d8cb6c32b1b5,7cf8d8cb6c32b1b5] 66763 --- [nio-8092-exec-3] c.e.o.service.DemoGoodsService           : [DemoGoodsService#getGoodsDetail] date: Fri, 21 Oct 2022 09:37:29 GMT
2022-10-21 17:37:29.224 DEBUG [demo-order-service,7cf8d8cb6c32b1b5,7cf8d8cb6c32b1b5] 66763 --- [nio-8092-exec-3] c.e.o.service.DemoGoodsService           : [DemoGoodsService#getGoodsDetail] keep-alive: timeout=60
2022-10-21 17:37:29.224 DEBUG [demo-order-service,7cf8d8cb6c32b1b5,7cf8d8cb6c32b1b5] 66763 --- [nio-8092-exec-3] c.e.o.service.DemoGoodsService           : [DemoGoodsService#getGoodsDetail] 
2022-10-21 17:37:29.224 DEBUG [demo-order-service,7cf8d8cb6c32b1b5,7cf8d8cb6c32b1b5] 66763 --- [nio-8092-exec-3] c.e.o.service.DemoGoodsService           : [DemoGoodsService#getGoodsDetail] 商品10938，当前服务的端口号为8091
2022-10-21 17:37:29.224 DEBUG [demo-order-service,7cf8d8cb6c32b1b5,7cf8d8cb6c32b1b5] 66763 --- [nio-8092-exec-3] c.e.o.service.DemoGoodsService           : [DemoGoodsService#getGoodsDetail] <--- END HTTP (45-byte body)
```
其中日志三项`demo-order-service,7cf8d8cb6c32b1b5,7cf8d8cb6c32b1b5`分别为服务名称，Trace ID和Span ID
- **Trace ID**: 标记请求链路的全局唯一ID，用于标记出整个请求链路
- **Span ID**: 请求链路中的每个微服务都会生成一个不同的Span ID。一个TraceId拥有多个SpanId，而SpanId只能隶属于某一个TraceId