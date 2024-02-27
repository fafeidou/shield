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
# Spring依赖查找

## 单一类型依赖查找

- 单一类型依赖查找接口-BeanFactory
  - 根据Bean名称查找
    - getBean (String)
    - Spring 2.5覆盖默认参数:getBean(String,Object...)
  - 根据 Bean类型查找
    - Bean实时查找
      - Spring 3.0getBean (Class)
      - Spring4.1覆盖默认参数:getBean(Class, Object...)
    - Spring 5.1 Bean 延迟查找
      - getBeanProvider (Class)
      - getBeanProvider (ResolvableType)
  - 根据Bean名称+类型查找:getBean(String,Class)

```dtd
org.geekbang.thinking.in.spring.dependency.lookup.ObjectProviderDemo
```

## 集合类型依赖查找

- 集合类型依赖查找接口-ListableBeanFactory
  - 根据 Bean 类型查找
    - 获取同类型Bean名称列表
      - getBeanNamesForType (Class)
      - Spring 4.2 getBeanNamesForType (ResolvableType)
    - 获取同类型Bean实例列表
      - getBeansOfType(Class) 以及重载方法
  - 通过注解类型查找
    - Spring3.0获取标注类型Bean名称列表
      - getBeanNamesForAnnotation (Class<? extends Annotation)
    - Spring3.0获取标注类型Bean实例列表
      - getBeansWithAnnotation(Class<? extends Annotation>)
    - Spring3.0获取指定名称+标注类型Bean实例
      - findAnnotationOnBean (String, Class<? extends Annotatioon>

```dtd
org.geekbang.thinking.in.spring.ioc.overview.dependency.lookup.DependencyLookupDemo
```

## 层次性依赖查找

- 层次性依赖查找接口-HierarchicalBeanFactory
  - 双亲BeanFactory:getParentBeanFactory()
  - 层次性查找
    - 根据Bean名称查找
      - 基于containsLocalBean方法实现
    - 根据 Bean 类型查找实例列表
      - 单一类型:BeanFactoryUtils#bean0fType
      - 集合类型:BeanFactoryUtils#beansOfTypeIncludingAncesstors
    - 根据Java注解查找名称列表
      - BeanFactoryUtils#beanNamesForTypeIncludingAncesttors

```dtd
org.geekbang.thinking.in.spring.dependency.lookup.HierarchicalDependencyLookupDemo
```
## 延迟依赖查找

- Bean 延迟依赖查找接口
  - org. springframework.beans.factory.ObjectFactory
  - org. springframework.beans.factory.ObjectProvider
    - Spring 5对 Java 8特性扩展
      - 函数式接口
        - get IfAvailable (Supplier)
        - ifAvailable (Consumer)
      - Stream 扩展-stream()

```dtd
org.geekbang.thinking.in.spring.dependency.lookup.ObjectProviderDemo
```

## 安全依赖查找

![img.png](img/安全依赖查找.png)

## 依赖查找中的经典异常

![img.png](img/依赖查找中的经典异常.png)

## ObjectFactory与BeanFactory的区别 ? 

答: 
1. ObjectFactory与BeanFactory均提供依赖查找的能力。
2. 不过ObjectFactory仅关注一个或一种类型的Bean依赖查找,并
且自身不具备依赖查找的能力,能力则由BeanFactory输出。
BeanFactory则提供了单一类型、集合类型以及层次性等多种依赖查
找方式。

## BeanFactory.getBean操作是否线程安全

答:BeanFactory.getBean方法的执行是线程安全的,换操作过程中会增加互
斥锁

# Spring IoC 注入

## 依赖注入的模式和类型

- 手动模式-配置或者编程的方式,提前安排注入规则
  - XML资源配置元信息
  - Java注解配置元信息
  - API配置元信息
- 自动模式-实现方提供依赖自动关联的方式,按照内建的注入规则
  - AutoWiring(自动绑定)

## 依赖注入的模式和类型

![img.png](img/依赖注入的模式和类型.png)


## 自动绑定(Autowiring)模式

![img.png](img/自动绑定(Autowiring)模式.png)


## Setter 方法注入

- 手动模式
  - XML资源配置元信息 injection.XmlDependencySetterInjectionDemo
  - Java注解配置元信息 injection.AnnotationDependencySetterInjectionDemo
  - API 配置元信息 injection.AnnotationDependencySetterInjectionDemo
- 自动模式
  - byName injection.AutoWiringByNameDependencySetterInjectionDemo
  - byType

## 构造器注入

- 实现方法
  - 手动模式
    - XML资源配置元信息 injection.XmlDependencyConstructorInjectionDemo
    - Java注解配置元信息 injection.AnnotationDependencyConstructorInjectionDemo
    - API 配置元信息 injection.ApiDependencyConstructorInjectionDemo
- 自动模式
  - constructor injection.AutoWiringConstructorDependencyConstructorInjectionDemo


## 字段注入

- 实现方法
  - 手动模式
    - Java注解配置元信息 injection.AnnotationDependencyFieldInjectionDemo
      - @Autowired
      - @Resource 
      - @Inject(可选)

## 方法注入

- 实现方法
  - 手动模式
    - Java注解配置元信息 injection.AnnotationDependencyMethodInjectionDemo
      - @Autowired
      - @Resource
      - @Inject(可选)
      - @Bean

## 接口回调注入

- injection.AwareInterfaceDependencyInjectionDemo

![img.png](img/接口回调注入.png)

![img.png](img/接口回调注入2.png)

## 依赖注入类型选择

- 注入选型
  - 低依赖:构造器注入
  - 多依赖:Setter方法注入
  - 便利性:字段注入
  - 声明类:方法注入

## 基础类型注入

- 基础类型
  - 原生类型(Primitive):boolean、byte、char、short、int、float、long、double
  - 标量类型(Scalar):Number、Character、Boolean、Enum、Locale、Charset、Currency、Properties、UUID
  - 常规类型(General):Object、String、TimeZone、Calendar、Optional等
  - Spring类型:Resource、InputSource、Formatter等


## 集合类型注入

- 集合类型
  - 数组类型(Array):原生类型、标量类型、常规类型、Sprin类型
  - 集合类型(Collection)
    - Collection: List、Set (SortedSet、NavigableSet、EnuimSet
    - Map: Properties

## 限定注入

- 使用注解@Qualifier限定 injection.QualifierAnnotationDependencyInjectionDemo
  - 通过Bean名称限定
  - 通过分组限定
- 基于注解@Qualifier扩展限定
  - 自定义注解-如SpringCloud@LoadBalanced

## 延迟依赖注入
- injection.LazyAnnotationDependencyInjectionDemo

- 使用APIObjectFactory延迟注入
  - 单一类型
  - 集合类型
- 使用APIObjectProvider延迟注入(推荐)
  - 单一类型
  - 集合类型


## 依赖处理过程

- 基础知识
  - 入口 - DefaultListableBeanFactory#resolveDependenLcy
  - 依赖描述符-DependencyDescriptor
  - 自定绑定候选对象处理器-AutowireCandidateResolver


## @Autowired 注入

- @Autowired注入规则
  - 非静态字段
  - 非静态方法
  - 构造器

- @Autowired注入过程
  - 元信息解析
  - 依赖查找
  - 依赖注入(字段、方法)

## @Inject注入

- @Inject注入过程
  - 如果JSR-330存在于ClassPath中,复用AutowiredAnnotationBeanPostProcessor实现

## Java通用注解注入原理

- CommonAnnotationBeanPostProcessor
  - 注入注解
    - javax.xml.ws.WebServiceRef
    - javax.ejb.EJB
    - javax.annotation.Resource
  - 生命周期注解
    - javax.annotation.PostConstruct
    - javax.annotation.PreDestroy

## 自定义依赖注入注解

- injection.AnnotationDependencyInjectionResolutionDemo

- 基于AutowiredAnnotationBeanPostProcessor实现
- 自定义实现
  - 生命周期处理
    - InstantiationAwareBeanPostProcessor
    - MergedBeanDefinitionPostProcessor
  - 元数据
    - InjectedElement
    - InjectionMetadata

## 面试题

### 有多少种依赖注入的方式?

答:构造器注入 Setter注入 字段注入 方法注入 接口回调注入

### 你偏好构造器注入还是Setter注入?

答:两种依赖注入的方式均可使用,如果是必须依赖的话,那么推荐使用构造器注入,Setter注入用于可选依赖。














































