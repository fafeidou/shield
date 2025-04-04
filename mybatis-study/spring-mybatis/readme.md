## 总结
Spring 在注入 UserMapper 时，会自动处理 FactoryBean 的逻辑。
如果 UserMapper 是通过 FactoryBean 创建的，Spring 会调用 FactoryBean 的 getObject() 方法来获取实际的 UserMapper 实例。
这是 Spring 容器管理 Bean 的一部分，确保你总是获取到正确的 Bean 实例。
因此，当你在 UserServiceImpl 中注入 UserMapper 时，Spring 会自动调用 FactoryBean 的 getObject() 方法来获取 UserMapper 的实例。

可以先看这个demo com.example.factorybean.FactoryBeanApplication


1. @MapperScan("com.example.mapper") 配置mapper.java文件所在的路径
2. @Import(MapperScannerRegistrar.class)  ，实现了ImportBeanDefinitionRegistrar，会回调registerBeanDefinitions
3. MapperScannerRegistrar中会注册MapperScannerConfigurer
4. MapperScannerConfigurer 实现了 BeanDefinitionRegistryPostProcessor ，会回调postProcessBeanDefinitionRegistry
5. org.mybatis.spring.mapper.ClassPathMapperScanner.doScan 这个时候会将mapper.java文件添加到BeanDefinition中，其中class为c
org.mybatis.spring.mapper.ClassPathMapperScanner.processBeanDefinitions
6. 那在mapper注入的时候会回调FeignClientFactoryBean getObject 方法，从而创建代理对象
----------------------------以上流程就是spring 整合mybatis的底层原理---------------
7. 接着就是动态代理的创建，通过代理的方式，将sql封装成MappedStatement，并添加到MybatisConfiguration中，从而实现动态代理的创建
   getSqlSession().getMapper(this.mapperInterface) 跟mybatis 的底层原理分析是一样的了



BeanDefinitionRegistryPostProcessor 是在生命周期的哪一步？

从BeanFactory入手,讲透Spring整合Mybatis的底层原理 https://mp.weixin.qq.com/s?__biz=MzU2NjU3Nzg2Mg==&mid=2247506749&idx=2&sn=df69cdc04926803ae04f286d96f1960f&chksm=fd9d8074305284c182f60a2e71d5a435e2e89b4a6e5a3d9fc4276b08e95726853924c8aeb8ed&mpshare=1&scene=23&srcid=0323tUqWMkR6uUkUS45XY6pp&sharer_shareinfo=c496faf032a6eda343af54a8785418f9&sharer_shareinfo_first=86fc76670c31e012ed02c821b5fd5cb1%23rd

聊透 Spring Bean 的生命周期 https://mp.weixin.qq.com/s?__biz=MzUxOTc4NjEyMw==&mid=2247556382&idx=3&sn=01285130ab0fd30aace1309e94301a98&chksm=f85f7bbaed962cb65f975c7f2bb205c17fbec8078892b1d0b6f64efc8060d9438ab790ba2b2c&mpshare=1&scene=23&srcid=0327Y7HGJv7YG6bvm0lrIgmW&sharer_shareinfo=469533689e274470acb5646035a72e48&sharer_shareinfo_first=469533689e274470acb5646035a72e48%23rd