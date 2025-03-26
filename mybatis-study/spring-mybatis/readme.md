## 总结
Spring 在注入 UserMapper 时，会自动处理 FactoryBean 的逻辑。
如果 UserMapper 是通过 FactoryBean 创建的，Spring 会调用 FactoryBean 的 getObject() 方法来获取实际的 UserMapper 实例。
这是 Spring 容器管理 Bean 的一部分，确保你总是获取到正确的 Bean 实例。
因此，当你在 UserServiceImpl 中注入 UserMapper 时，Spring 会自动调用 FactoryBean 的 getObject() 方法来获取 UserMapper 的实例。

可以先看这个demo com.example.factorybean.FactoryBeanApplication


org.mybatis.spring.mapper.ClassPathMapperScanner.doScan 这个时候会将mapper.java文件添加到BeanDefinition中，其中class为MapperFactoryBean
org.mybatis.spring.mapper.ClassPathMapperScanner.processBeanDefinitions


从BeanFactory入手,讲透Spring整合Mybatis的底层原理 https://mp.weixin.qq.com/s?__biz=MzU2NjU3Nzg2Mg==&mid=2247506749&idx=2&sn=df69cdc04926803ae04f286d96f1960f&chksm=fd9d8074305284c182f60a2e71d5a435e2e89b4a6e5a3d9fc4276b08e95726853924c8aeb8ed&mpshare=1&scene=23&srcid=0323tUqWMkR6uUkUS45XY6pp&sharer_shareinfo=c496faf032a6eda343af54a8785418f9&sharer_shareinfo_first=86fc76670c31e012ed02c821b5fd5cb1%23rd