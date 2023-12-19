```
在主程序之前运行的 Agent
<Premain-Class>com.example.agent.PreMainAgent</Premain-Class>

idea vm option:

-javaagent:/agentJar路径/target/java-agent-0.0.1.jar
```

```dtd
在主程序运行之后的 Agent
<Agent-Class>com.example.agent.AfterMainAgent</Agent-Class>

Run com.example.agent.TestMain

Run com.example.agent.attach.Attach 

params : attach com.example.agent.TestMain /agentJar路径/target/java-agent-0.0.1.jar
```

```dtd
debug
        
com.example.agent.TestMain

-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8000

Run 运行

```

## 参考文档

```dtd
认识 JavaAgent --获取目标进程已加载的所有类 https://paper.seebug.org/1099/
Java 基于 Instrument 的 Agent  https://www.cnblogs.com/jhxxb/p/11567337.html
通过实战走近Java Agent探针技术   https://juejin.cn/post/7025410644463583239#heading-4
```
