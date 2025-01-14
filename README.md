# 项目说明

### 外部依赖
除JDK之外的外部依赖库:

* spring-boot-starter-web 用于快速创建spring mvc项目
* spring-boot-starter-test 用于项目单元测试

### 环境说明
* JDK：21
(调试使用版本：azul-21.0.5)
* spring-boot-starter-parent：3.4.1
* maven：3.9.5

### 项目结构
```
├─src
   ├─main
   │  ├─java
   │  │  └─com
   │  │      └─xiaolu
   │  │          └─transaction
   │  │              ├─config         配置类
   │  │              ├─controller     控制器
   │  │              ├─entity         实体类
   │  │              └─service        服务层
   │  │                  └─impl       服务层实现类
   │  └─resources
   │      ├─static
   │      └─templates                 项目配置
   └─test
       └─java
           └─com
               └─xiaolu
                   └─transaction      测试文件

```

### 功能设计
实现简单的交易管理系统

主要功能包含：
* 交易创建
* 交易修改
* 交易删除
* 交易列表查询（使用account查询）

调用链路：controller -> service -> memory

模拟银行交易过程，交易实体主要字段：
* 交易ID
* 交易账户
* 交易账户名
* 银行网点号
* 交易类型
* 交易金额
* 交易时间
* 交易状态
* 目标账户

transactionId：交易ID 本项目中字段会用于幂等判断，外部传入，后续也可根据需要单独增加内部的唯一业务层面ID标识一笔交易

其中本服务最关注交易ID，交易账户，交易金额，交易功能会校验此参数正确性。
http状态码规则：正常返回2XX,异常返回5xx


### 存储说明
使用ConcurrentHashMap做存储，保证线程安全，同时交易操作：创建、修改、删除都能在O(1)的时间复杂度内校验、存储，
对于查询操作,单独使用简单实现的缓存(Map)来提高查询性能，在其他操作中会清理缓存，保证查询正确性。
这里没有选择list做存储结构的目的是提供交易操作校验和存储的时间复杂度。

### 健康检查
启动项目后可调用接口```/check```确认服务正常。

### 部署相关
容器相关配置：
* [Dockerfile](Dockerfile)
* [deployment.yaml](deployment.yaml)
* [service.yaml](service.yaml)

相关命令举例：
* 构建镜像```docker build```
* 推送镜像到仓库```docker push```
* deployment配置```kubectl apply -f deployment.yaml```
* service配置```kubectl apply -f service.yaml```

### 其他相关链接
[单测报告](./unit-test.md)

[压测报告](./stress-test.md)
