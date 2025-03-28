Spring 中的 `BeanDefinitionMap` 是存储所有 Bean 定义（`BeanDefinition`）的核心容器，它位于 `DefaultListableBeanFactory` 类中，本质是一个 `ConcurrentHashMap<String, BeanDefinition>`。其收集 `BeanDefinition` 的过程贯穿 Spring 容器的初始化阶段，涉及 **资源定位、解析、注册** 三个核心步骤。以下是详细机制：

---

### 1️⃣ **BeanDefinition 的来源**
Spring 支持多种方式定义 Bean，所有方式最终都会被转换为 `BeanDefinition` 对象并注册到 `BeanDefinitionMap` 中。常见来源包括：

| 来源                          | 描述                                                                 |
|-------------------------------|----------------------------------------------------------------------|
| **XML 配置文件**              | 通过 `<bean>` 标签定义的 Bean，由 `ClassPathXmlApplicationContext` 加载。 |
| **注解扫描**（如 `@Component`） | 通过组件扫描（`@ComponentScan`）自动发现并注册 Bean。                     |
| **Java 配置类**（`@Configuration`） | 使用 `@Bean` 方法显式定义 Bean。                                       |
| **编程式注册**                | 通过 `BeanDefinitionRegistry` 接口动态注册自定义 Bean 定义。              |
| **Spring Boot 自动配置**       | 通过 `spring.factories` 和条件注解（`@Conditional`）自动生成 Bean 定义。   |

---

### 2️⃣ **BeanDefinition 的收集流程**
Spring 容器的初始化过程（以 `ApplicationContext` 为例）通过 `refresh()` 方法触发，核心步骤如下：

#### 步骤 1：资源定位与加载
- **XML 配置**：  
  解析 `applicationContext.xml` 等配置文件，定位 `<bean>` 标签。
- **注解配置**：  
  扫描指定包路径下的 `@Component`、`@Service` 等注解的类。
- **Java 配置类**：  
  解析 `@Configuration` 类中的 `@Bean` 方法。

#### 步骤 2：解析为 BeanDefinition
- **XML 解析**：  
  使用 `BeanDefinitionReader`（如 `XmlBeanDefinitionReader`）将 `<bean>` 标签解析为 `GenericBeanDefinition`。
- **注解解析**：  
  通过 `ClassPathBeanDefinitionScanner` 扫描类，生成 `ScannedGenericBeanDefinition`。
- **Java 配置类解析**：  
  由 `ConfigurationClassPostProcessor` 处理 `@Configuration` 类，将 `@Bean` 方法转换为 `ConfigurationClassBeanDefinition`。

#### 步骤 3：注册到 BeanDefinitionMap
所有解析后的 `BeanDefinition` 通过 `BeanDefinitionRegistry` 接口（由 `DefaultListableBeanFactory` 实现）注册到 `BeanDefinitionMap` 中：

```java
public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
    this.beanDefinitionMap.put(beanName, beanDefinition);
    // ... 其他逻辑（如清理缓存）
}
```

---

### 3️⃣ **关键组件的协作**
#### (1) BeanFactoryPostProcessor
Spring 提供扩展点允许在 Bean 定义注册后、实例化前修改 `BeanDefinition`。例如：
- **`ConfigurationClassPostProcessor`**：  
  处理 `@Configuration` 类，解析 `@Bean` 方法并注册 Bean 定义。
- **`AutowiredAnnotationBeanPostProcessor`**：  
  处理 `@Autowired` 注解，但属于 `BeanPostProcessor`（影响实例化，不直接参与注册）。

#### (2) BeanDefinitionRegistryPostProcessor
允许在容器启动时动态添加或修改 Bean 定义。例如：
```java
public class CustomPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        RootBeanDefinition beanDef = new RootBeanDefinition(MyBean.class);
        registry.registerBeanDefinition("myBean", beanDef);
    }
}
```

#### (3) 组件扫描（Component Scan）
通过 `@ComponentScan` 或 `<context:component-scan>` 触发，底层使用 `ClassPathBeanDefinitionScanner` 扫描类路径：
```java
// 扫描包路径下的类
ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
scanner.scan("com.example");
```

---

### 4️⃣ **合并 BeanDefinition（Merged BeanDefinition）**
在 Bean 实例化前，Spring 会将父子 Bean 定义（如通过 `parent` 属性）合并为 `RootBeanDefinition`。合并后的定义存储在 `mergedBeanDefinitions` 缓存中，但原始 `BeanDefinitionMap` 仍保留原始定义。

---

### 5️⃣ **动态注册示例**
通过编程方式直接操作 `BeanDefinitionMap`（需谨慎）：
```java
DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getBeanFactory();
BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(MyBean.class);
beanFactory.registerBeanDefinition("dynamicBean", builder.getBeanDefinition());
```

---

### 6️⃣ **总结：BeanDefinitionMap 的填充流程**
1. **资源定位**：根据配置方式（XML、注解、Java Config）找到 Bean 定义源。
2. **解析定义**：将配置转换为 `BeanDefinition` 对象。
3. **注册到 Map**：通过 `BeanDefinitionRegistry` 将 `BeanDefinition` 存入 `BeanDefinitionMap`。
4. **后处理**：通过 `BeanFactoryPostProcessor` 修改已注册的定义。
5. **合并定义**：在实例化前合并父子定义，生成最终 Bean 实例化模板。

---

### 附：典型时序图
```plaintext
1. ApplicationContext.refresh()
   ├─ 2. invokeBeanFactoryPostProcessors()
   │    └─ 3. ConfigurationClassPostProcessor.processConfigBeanDefinitions() → 解析 @Configuration 类
   ├─ 4. finishBeanFactoryInitialization()
   │    └─ 5. preInstantiateSingletons() → 实例化所有单例 Bean
   └─ 6. BeanDefinitionMap 填充完成
```