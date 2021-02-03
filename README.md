# yqf-admin-modula-form


项目简介
---------
提供一个定制化表单，为业务系统提供服务支持，依赖于yqf-admin后台。<br>
1、springboot2.x+spring+mybatis plus+swagger2+aoplog [MYSQL]

2、提供统一接口，调用sdk，含加解密验签功能。 

3、提供Redis缓存集成和Redis分布式锁工具类。

4、引入liquibase建表,mybatis plus 方便增删改查<br>


功能特性
---------

环境依赖
---------
JDK1.8+

部署步骤
---------
下载版本的jar包。启动命令
<br>
```java 
java -jar 编译后的JAR.jar [-Dfile.encoding=utf-8 乱码情况下使用]
     --spring.datasource.username=数据库用户 默认sa
     --spring.datasource.password=数据库密码 默认123
     --druid.loginUsername=Druid登陆用户名 默认slo
     --druid.loginPassword=Druid登陆密码 默认slo
```



