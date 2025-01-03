## 生物车间管理系统 - 后端

### 项目运行

准备 mysql 和 redis 环境，修改 application-dev.yml 中的数据库和 redis 配置，然后执行以下命令启动项目：

```
./mvnw clean install
./mvnw spring-boot:run -pl biology-admin
```

建议JDK版本：17

### 测试环境

```
后端: http://home.icepie.net:9020
```
