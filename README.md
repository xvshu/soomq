![logo](https://img-blog.csdnimg.cn/20201016145727514.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3h2c2h1,size_16,color_FFFFFF,t_70#pic_center)

# 为什么重复造轮子
MQ（Message Queue）是互联网公司绕不过去的一个技术，核心功能就是进行消息的收发管理，已达到某些特殊功能，比如流量削峰，异步执行，日志聚合等。
介于这个原因，看了很多mateQ，kafka，roccketMQ的资料，虽然算是一点点的入门了，依然感觉隔着层窗户纸，后来经过考虑，觉得mq经过多年的演变，已经是一个复杂的事物，对于初学者，不利于理解，我决定根据自己的理解，自己手写一个最简单mq，以帮助自己理解，也给后来者提供一个简单的参考。
如有大神，可直接看源码：[git-soomq](https://github.com/xvshu/soomq)

# 快速启动
1，启动 broker
定位到项目 soomq-server
运行SooMQBootstrap.mian()
默认绑定 9870 端口

2，测试producer
定位到项目 soomq-client
运行test包中 ProducerTest.mian()

3，测试consumer
定位到项目 soomq-client
运行test包中 ConsumerTest.mian()


# 便于初学者理解：

| 术语 |说明  |
|--|--|
|Producer|消息的生产者，用户将消息发送给消息队列服务器|
|Broker  | 消息队列的服务器，用于存储消息 |
|Consumer|消息的消费者，用户读取消息服务器上的消息，用于自己的业务处理|
|Topic|消息分组的组名，用于区分不同的业务类型|
|Message|传递的内容，也就是常说的消息|

# 设计篇
## 1，服务端
服务端的设计就非常简单了，最核心的就是消息的存取，以及响应生产者和消费者的网络请求
分为2部分：
### 1.1 消息文件
消息的存储我们参考kafka，并简化其逻辑，因为是最简单的mq，我们只考虑单机的情况的就行，每个topic存储2个文件

topicname.index
topicname.data

.index 文件存储格式为：
消息顺序号:消息截止位置
.data 文件按照顺序存储具体的消息

### 1.2 网络编程
利用netty 开放端口，响应生产者与消费者，每个消息包装成一个commod，commod类型
- 消息类型（消费/生产）
- 消息topic
- 消息体（生产时用）
- 消息顺序号(消费时用)
- 处理结果（成功/失败）
- 处理消息（失败时添加原因）


## 2，client
### 2.1 连接管理
通过netty与mq服务器进行连接，并相应生产者与消费者的请求，通过netty自带的序列化工具，将消息序列化未byte字节进行传输
### 2.2 生产者
获取服务连接后，将消息发送给消息队列服务器
参数：
- 消息类型：生产
- 消息topic
- 消息体

### 2.3 消费者
获取服务连接后，从服务端获取消息
参数：
- 消息类型：消费
- 消息topic
- 消息顺序号

