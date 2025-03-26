将扩展的 MappedStatement 添加到 MybatisConfiguration 中
com.baomidou.mybatisplus.core.MybatisConfiguration.addMappedStatement

可以看到具体有哪些扩展注入
com.baomidou.mybatisplus.core.injector.AbstractSqlInjector.inspectInject
methodList.forEach(m -> m.inject(builderAssistant, mapperClass, modelClass, tableInfo));

可以看到具体的注入的sql
```declarative
com.baomidou.mybatisplus.core.injector.methods.Insert.injectMappedStatement
String sql = String.format(sqlMethod.getSql(), tableInfo.getTableName(), columnScript, valuesScript);
```

动态设置参数值
org.apache.ibatis.executor.statement.StatementHandler.parameterize
## 参考文档

Mybatis-Plus的应用场景及注入SQL原理分析 https://mp.weixin.qq.com/s?__biz=MzI4NjY4MTU5Nw==&mid=2247491188&idx=2&sn=a6ced0bae0d50a8661c17a99dc9cc02a&chksm=ea2402f06f5e7c72df7dd1fa770cd9cadd3c31556b308b19ac6e480f57ee93b865a27e3cf793&mpshare=1&scene=23&srcid=0323AffS3nvijz0piMQhP99L&sharer_shareinfo=a49e3d36695f610041b3a70b971a1be9&sharer_shareinfo_first=a49e3d36695f610041b3a70b971a1be9%23rd