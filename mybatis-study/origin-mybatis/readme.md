## JDBC 是怎么操作数据库的？

```java
public static User getUserById(long id) throws SQLException {
    String sql = "SELECT * FROM users WHERE id = ?";

    try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setLong(1, id);
        pstmt.execute();
        ResultSet resultSet = pstmt.getResultSet();
        try (ResultSet rs = resultSet) {
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        }
    }
    return null;
}

private static User mapResultSetToUser(ResultSet rs) throws SQLException {
    return new User(rs.getLong("id"), rs.getString("username"), rs.getString("email"));
}
```

## 解析阶段解析原始sql，封装成MappedStatement?
可以断点到这个地方看下
org.apache.ibatis.builder.MapperBuilderAssistant.addMappedStatement(java.lang.String, org.apache.ibatis.mapping.SqlSource, org.apache.ibatis.mapping.StatementType, org.apache.ibatis.mapping.SqlCommandType, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Class<?>, java.lang.String, java.lang.Class<?>, org.apache.ibatis.mapping.ResultSetType, boolean, boolean, boolean, org.apache.ibatis.executor.keygen.KeyGenerator, java.lang.String, java.lang.String, java.lang.String, org.apache.ibatis.scripting.LanguageDriver, java.lang.String, boolean)
最终添加到Configuration中的map对象中
org.apache.ibatis.session.Configuration.mappedStatements
## 动态代理什么时候生成的?
org.apache.ibatis.session.defaults.DefaultSqlSession.getMapper
    org.apache.ibatis.binding.MapperRegistry.getMapper
        org.apache.ibatis.binding.MapperProxyFactory.newInstance(org.apache.ibatis.session.SqlSession)
            使用jdk动态代理生成代理对象
            org.apache.ibatis.binding.MapperProxyFactory.newInstance(org.apache.ibatis.binding.MapperProxy<T>)

## 何时使用动态代理的?
再真正调用mapper方法时，会调用MapperProxy的invoke方法
```declarative
UserMapper mapper = session.getMapper(UserMapper.class);
```
org.apache.ibatis.binding.MapperProxy.invoke
    org.apache.ibatis.binding.MapperProxy.PlainMethodInvoker.invoke
        给sql封装参数，调用jdbc
        org.apache.ibatis.scripting.defaults.DefaultParameterHandler.setParameters
            执行jdbc的execute
            org.apache.ibatis.executor.statement.PreparedStatementHandler.query
                    使用resultSet获取结果
                org.apache.ibatis.type.LongTypeHandler.getNullableResult(java.sql.ResultSet, java.lang.String)
## mybatis 一二级缓存怎么实现的?
* 一级SqlSession 级别（默认开启）

优先检查一级缓存localCache
org.apache.ibatis.executor.BaseExecutor.localCache
org.apache.ibatis.executor.BaseExecutor.query(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler, org.apache.ibatis.cache.CacheKey, org.apache.ibatis.mapping.BoundSql)

1. 关闭一级缓存
a. 方式一

```declarative
<!--默认为 SESSION 级别，一级缓存默认打开，设置为 STATEMENT 可以关闭一级缓存-->
<!--或者每个mapper中statement，也可以关闭一级缓存flushCache="true"-->
<setting name="localCacheScope" value="SESSION"/>
```
对应源码清理缓存位置 org.apache.ibatis.executor.BaseExecutor.query(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler, org.apache.ibatis.cache.CacheKey, org.apache.ibatis.mapping.BoundSql)

```java
    if (configuration.getLocalCacheScope() == LocalCacheScope.STATEMENT) {
        // issue #482
        clearLocalCache();
      }
```

b.方式二

```declarative
    <select id="findUserById" resultMap="userResultMap" parameterType="Long" flushCache="true">
```
对应源码清理缓存位置 org.apache.ibatis.executor.BaseExecutor.query(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler, org.apache.ibatis.cache.CacheKey, org.apache.ibatis.mapping.BoundSql)

```java
    if (configuration.getLocalCacheScope() == LocalCacheScope.STATEMENT) {
        // issue #482
        clearLocalCache();
      }
```


* 二级缓存Mapper 级别

1. 在myBatis-config 中添加开启二级缓存的条件

```declarative
<!-- 通知 MyBatis 框架开启二级缓存 -->
<settings>
  <setting name="cacheEnabled" value="true"/>
</settings>
```

2. 还需要在 Mapper 对应的xml中添加 cache 标签，表示对哪个mapper 开启缓存

```declarative
<!--开启二级缓存 -->
    <cache/>
```
对应源码位置
org.apache.ibatis.executor.CachingExecutor.query(org.apache.ibatis.mapping.MappedStatement, java.lang.Object, org.apache.ibatis.session.RowBounds, org.apache.ibatis.session.ResultHandler, org.apache.ibatis.cache.CacheKey, org.apache.ibatis.mapping.BoundSql)

```declarative
这个就是二级缓存
Cache cache = ms.getCache();
```

**二级缓存和一级缓存都开启的时间，先查询二级缓存，如果二级缓存没有命中，则查询一级缓存，如果一级缓存没有命中，则查询数据库，并将查询结果缓存到一级缓存和二级缓存中**

实际开发中两个缓存都不用，可以参考com.example.L1CacheDemo，两个不同session的查询结果可能是不一样的


## 执行阶段MappedStatement转换成PreparedStatement?
org.apache.ibatis.executor.SimpleExecutor.doUpdate
    org.apache.ibatis.executor.statement.StatementHandler.prepare
        org.apache.ibatis.executor.statement.PreparedStatementHandler.instantiateStatement


## 参考

https://juejin.cn/post/7314365539168993289#heading-2 Mybatis一级缓存问题

https://blog.csdn.net/Dongguabai/article/details/124052146 禁用 MyBatis 一级缓存

https://zhuanlan.zhihu.com/p/97879019 Mybatis运行原理

https://www.cnblogs.com/cxuanBlog/p/11333021.html MyBatis 二级缓存


深入详解Mybatis的架构原理与6大核心流程 https://mp.weixin.qq.com/s?__biz=MjM5NzM0MjcyMQ==&mid=2650157327&idx=4&sn=7b863e60f3afebc69838d9354b096c9c&chksm=bfa7383672f8978d6daf2c2210fff27b8fcfb2b2b2eea8997b321c52dd2309031f0d1fd06c1c&mpshare=1&scene=23&srcid=032379OO6Yl6ieQghZzHgyhU&sharer_shareinfo=49ffdf8d25cc73abc6b7cec4afbd86c3&sharer_shareinfo_first=e330dcdbaca57b1c5d298fa9747cd1f0%23rd
