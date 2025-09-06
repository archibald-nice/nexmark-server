# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 项目概述

这是一个基于 Spring Boot 2.6.13 的 nexmark-server 项目，使用 Java 8 开发。项目集成了 PostgreSQL 数据库、MyBatis-Plus ORM 框架和 Druid 连接池。

## 构建和运行命令

### Maven 命令
```bash
# 编译项目
mvn compile

# 运行项目
mvn spring-boot:run

# 打包项目
mvn clean package

# 运行测试
mvn test

# 跳过测试打包
mvn clean package -DskipTests
```

### 直接运行 JAR
```bash
java -jar target/nexmark-server-0.0.1-SNAPSHOT.jar
```

## 技术栈

- **框架**: Spring Boot 2.6.13
- **数据库**: PostgreSQL (42.7.7)
- **ORM**: MyBatis-Plus 3.5.2
- **连接池**: Druid 1.2.23
- **Java 版本**: 1.8
- **构建工具**: Maven

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/workmagic/demo/nexmarkserver/
│   │       ├── NexmarkServerApplication.java      # 应用程序主类
│   │       └── demos/
│   │           └── web/                          # Web 层演示代码
│   │               ├── BasicController.java       # 基础控制器示例
│   │               ├── PathVariableController.java # 路径变量控制器示例
│   │               └── User.java                  # 用户实体类
│   └── resources/
│       ├── appclication.yml                      # 应用配置文件
│       └── static/
│           └── index.html                        # 静态页面
```

## 数据库配置

项目使用 PostgreSQL 数据库，配置信息在 `src/main/resources/appclication.yml` 中：

- **数据库**: nexmark_server
- **端口**: 5432
- **用户名**: postgres
- **密码**: yourpassword (需要修改为实际密码)

## 开发注意事项

1. **数据库连接**: 运行前需要确保 PostgreSQL 服务已启动，并创建 `nexmark_server` 数据库
2. **端口配置**: 应用默认运行在 8080 端口
3. **MyBatis-Plus**: 已配置逻辑删除功能，删除字段为 `deleted`
4. **Druid 连接池**: 配置了连接池参数，可根据实际需要调整

## 示例接口

项目包含了一些演示接口：

- `GET /hello?name={name}` - 基础问候接口
- `GET /user` - 返回用户信息
- `GET /user/{userId}/roles/{roleId}` - 路径变量示例
- `GET /html` - 返回静态页面