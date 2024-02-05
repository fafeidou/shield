# 重新认识IOC

## 什么是IoC?

简单地说,IoC是反转控制,类似于好莱坞原则,主要有依赖查
找和依赖注入实现

## 依赖查找和依赖注入的区别?

依赖查找是主动或手动的依赖查找方式,通常需要依赖容器或标准API实现。
而依赖注入则是手动或自动依赖绑定的方式,无需依赖特定的容器和ΑΡΙ

```dtd
org.geekbang.thinking.in.spring.ioc.overview.dependency.injection.DependencyInjectionDemo

        org.geekbang.thinking.in.spring.ioc.overview.dependency.lookup.DependencyLookupDemo
```

## 依赖查找VS.依赖注入

![img.png](img/依赖查找VS.依赖注入.png)

## Spring 作为 IoC 容器有什么优势?

- 典型的IoC管理,依赖查找和依赖注入
- AOP抽象
- 事务抽象
- 事件机制
- SPI扩展
- 强大的第三方整合
- 易测试性
- 更好的面向对象


## BeanFactory与FactoryBean?

- BeanFactory是IoC底层容器
- FactoryBean是创建Bean的一种方式,帮助实现复杂的初始化逻辑

## Spring IoC容器启动时做了哪些准备?

IoC配置元信息读取和解析、IoC容器生命周期、Spring事件发布
国际化等,更多答案将在后续专题章节逐一讨论

# spring bean 基础

## 初始化 Spring Bean

- Bean 初始化 (Initialization)
  - @PostConstruct标注方法
  - 实现 InitializingBean接口的afterPropertiesSet方法
  - 自定义初始化方法
    - XML配置:<bean init-method=" init".../>
    - Java注解:@Bean(initMethod="init")
    - Java API: AbstractBeanDefinition#setInitMethodName (String)
```dtd
org.geekbang.thinking.in.spring.bean.definition.BeanInitializationDemo
```

## 延迟初始化SpringBean

- Bean 延迟初始化(Lazy Initialization)
  - XML 配置:<bean lazy-init="true
  - Java注解:@Lazy(true)

```dtd
org.geekbang.thinking.in.spring.bean.definition.BeanInitializationDemo
@Lazy(value = true)
```

## 销毁 Spring Bean

- Bean 销毁(Destroy)
  - @PreDestroy标注方法
  - 实现 DisposableBean接口的 destroy()方法
  - 自定义销毁方法
    - XML配置:<beandestroy="destroy"
    - Java注解:@Bean(destroy="destroy")
    - Java API: AbstractBeanDefinition#setDestroyMethodNanme (String)

## 如何注册一个SpringBean?

通过 BeanDefinition和外部单体对象来注册
```dtd
org.geekbang.thinking.in.spring.bean.definition.SingletonBeanRegistrationDemo

```
## BeanDefinition 元信息

![img.png](img/BeanDefinition元信息.png)

- BeanDefinition 构建
  - 通过 BeanDefinitionBuilder
  - 通过AbstractBeanDefinition以及派生类

```dtd
org.geekbang.thinking.in.spring.bean.definition.BeanDefinitionCreationDemo
```






















