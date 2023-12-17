## thread线程相关

| 参数名称     | 参数说明                              |
| ------------ | ------------------------------------- |
| 数字         | 线程id                                |
| [n:]         | 指定最忙的前N个线程并打印堆栈         |
| [b]          | 找出当前阻塞其他线程的线程            |
| [i \<value>] | 指定cpu占比统计的采样间隔，单位为毫秒 |

```
展示当前最忙的前3个线程并打印堆栈
thread -n 3
```

>以往操作：
> 1. 执行“top”命令：查看所有进程占系统CPU的排序
> 2. top -Hp 进程号 ,查看java进程下的所有线程占CPU的情况
> 3. 行“printf "%x\n 10"命令 ：后续查看线程堆栈信息展示的都是十六进制
> 4. 执行 “jstack 进程号 | grep 线程ID”  查找某进程下 -> 线程ID


找出当前阻塞其他线程的线程，有时候我们发现应用卡住了， 通常是由于某个线程拿住了某个锁， 
并且其他线程都在等待这把锁造成的。 为了排查这类问题， arthas提供了thread -b， 一键找出那个罪魁祸首。

```
com.study.arthas.DeadLockDemo

thread -b
```

从上面直接输出了造成死锁的线程ID和具体的代码位置以及当前线程一共阻塞的线程数量

## Stack

| 参数名称            | 参数说明                             |
| ------------------- | ------------------------------------ |
| *class-pattern*     | 类名表达式匹配                       |
| *method-pattern*    | 方法名表达式匹配                     |
| *condition-express* | 条件表达式，OGNL                     |
| [E]                 | 开启正则表达式匹配，默认为通配符匹配 |
| `[n:]`              | 执行次数限制                         |

```dtd
获取primeFactors的调用路径
stack com.study.arthas.MathGame primeFactors
```

```dtd
条件表达式来过滤，第1个参数的值小于0，-n表示获取2次
stack com.study.arthas.MathGame primeFactors 'params[0]<0' -n 2
```

```dtd
据执行时间来过滤，耗时大于0.5毫秒
stack com.study.arthas.MathGame primeFactors '#cost>0.5'
```

## Sc

> Search-Class 的简写 ，查看JVM已加载的类信息。有的时候，你只记得类的部分关键词，你可以用sc获取完整名称 
> 当你碰到这个错的时候“ClassNotFoundException”或者“ClassDefNotFoundException”，你可以用这个命令验证下


| 参数名称         | 参数说明                                                     |
| ---------------- | ------------------------------------------------------------ |
| *class-pattern*  | 类名表达式匹配，支持全限定名，如com.taobao.test.AAA，也支持com/taobao/test/AAA这样的格式，这样，我们从异常堆栈里面把类名拷贝过来的时候，不需要在手动把`/`替换为`.`啦。 |
| *method-pattern* | 方法名表达式匹配                                             |
| [d]              | 输出当前类的详细信息，包括这个类所加载的原始文件来源、类的声明、加载的ClassLoader等详细信息。 如果一个类被多个ClassLoader所加载，则会出现多次 |
| [E]              | 开启正则表达式匹配，默认为通配符匹配                         |
| [f]              | 输出当前类的成员变量信息（需要配合参数-d一起使用）           |

```dtd
模糊搜索，com.study.arthas.service 包下所有的类
sc com.study.arthas.service.*
com.study.arthas.service.HelloService
com.study.arthas.service.HelloServiceLoaderUtils
```

```dtd
打印类的详细信息
sc -d com.study.arthas.service.HelloService
```

```dtd
打印出类的Field信息
sc -df com.study.arthas.service.HelloService
```

### 排查ClassNotFoundException

#### 异常复现
如果通过本地idea进行调用，控制台会正常打印出

```dtd
classLoader is : sun.misc.Launcher$AppClassLoader@18b4aac2
hello loader
```

将服务打包，通过

```dtd
java -jar arthas-study-0.0.1-SNAPSHOT.jar
```

出现了ClassNotFoundException异常,class存在，却找不到class，
要么就是类加载器错了，要么是class的位置错了

```dtd
sc -d com.study.arthas.service.HelloService
```

做法就是将ClassLoader.getSystemClassLoader()改成
Thread.currentThread().getContextClassLoader()即可


## 热更新文件

```dtd
1. 使用jad反编译com.study.arthas.MathGame输出到/tmp/test/MathGame.java
jad --source-only com.study.arthas.MathGame > /tmp/test/MathGame.java
```

```dtd
2.按上面的代码编辑完毕以后，使用mc内存中对新的代码编译
mc /tmp/test/MathGame.java -d /tmp/test
```

```dtd
3.使用redefine命令加载新的字节码
redefine /tmp/test/com/study/arthas/MathGame.class
```

> 特别说明
redefine 命令和 jad/watch/trace/monitor/tt 等命令会冲突。
执行完 redefine 之后，如果再执行上面提到的命令，则会把 redefine 的字节码重置。
原因是 jdk 本身 redefine 和 Retransform 是不同的机制，同时使用两种机制来更新字节码，只有最后修改的会生效。

## Logger 

```dtd
查询指定名字的logger信息
logger -n com.study.arthas.controller
```

```dtd
更新日志级别为debug
logger -n com.study.arthas.controller --level debug
```

```dtd
指定classLoaderHash 更新日志级别为debug
logger -c 18b4aac2 -n com.study.arthas.controller --level debug
```

## tt
记录下指定方法每次调用的入参和返回信息，并能对这些不同时间下调用的信息进行观测

| tt的参数  | 说明                             |
| --------- | -------------------------------- |
| -t        | 记录某个方法在一个时间段中的调用 |
| -l        | 显示所有已经记录的列表           |
| -n 次数   | 只记录多少次                     |
| -s 表达式 | 搜索表达式                       |
| -i 索引号 | 查看指定索引号的详细调用信息     |
| -p        | 重新调用指定的索引号时间碎片     |

```dtd
最基本的使用来说，就是记录下当前方法的每次调用环境现场。
tt -t com.study.arthas.MathGame *
```

```dtd
查询之前调用记录
tt -l
```

```dtd
需要筛选出 primeFactors 方法的调用信息
tt -s 'method.name=="primeFactors"'
```

```dtd
重做一次调用

tt -i 1000 -p
```

### 调用spring bean中的方法

```dtd
tt -t org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter invokeHandlerMethod

tt -i 1001 -w 'target.getApplicationContext().getBean("helloService").hello()'
```

## 开启远程debug

```dtd
准备测试类
com.study.arthas.MathGame
```

> 在VM Options里加入以下参数：

```dtd
-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8000
```

> 然后'Run'运行该项目。运行后输出如下：

```dtd
 8000
```

### 配置Arthas 远程debug

```dtd
git checkout -b 3.7.1 arthas-all-3.7.1
```

```dtd
添加Remote JVM Debug，并配置端口为8000（和上一步配置的相同）

然后启动debug，输出如下：
```

```dtd
方法中打入断点。
com.taobao.arthas.agent334.AgentBootstrap#main
```

```dtd
idea debug 运行

com.taobao.arthas.boot.Bootstrap
```

> 输入数字，选择目标应用进行attach，
此时可以看到我们的断点被命中了。


https://www.jianshu.com/p/28b93f697550 Arthas 启动过程分析

https://blog.bigcoder.cn/archives/a043441e Arthas源码分析

https://segmentfault.com/a/1190000021278869 认识 JavaAgent --获取目标进程已加载的所有类