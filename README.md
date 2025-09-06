# Nexmark Server

一个基于 Spring Boot 2.6.13 的用户管理系统，集成了 PostgreSQL 数据库和 MyBatis-Plus 框架。

## 技术栈

- **后端框架**: Spring Boot 2.6.13
- **数据库**: PostgreSQL
- **ORM框架**: MyBatis-Plus 3.5.2
- **连接池**: Druid 1.2.23
- **密码加密**: BCrypt
- **Java版本**: 1.8
- **构建工具**: Maven

## 快速开始

### 环境要求

- JDK 1.8+
- PostgreSQL 12+
- Maven 3.6+

### 数据库配置

1. 创建 PostgreSQL 数据库：
```sql
CREATE DATABASE nexmark;
```

2. 执行数据库脚本：
```bash
# 连接到数据库
psql -U postgres -d nexmark

# 执行脚本
\i src/main/resources/sql/sys_user_ddl.sql
```

3. 修改配置文件 `src/main/resources/appclication.yml`：
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/nexmark
    username: postgres
    password: yourpassword  # 修改为你的密码
```

### 启动应用

```bash
# 编译项目
mvn clean compile

# 运行项目
mvn spring-boot:run

# 或打包后运行
mvn clean package
java -jar target/nexmark-server-0.0.1-SNAPSHOT.jar
```

应用启动后访问：http://localhost:8080

## API 文档

### 用户管理接口

#### 创建用户
```http
POST /api/users
Content-Type: application/json

{
  "username": "newuser",
  "password": "123456",
  "email": "newuser@example.com",
  "phone": "13800138000",
  "nickname": "新用户",
  "gender": 0,
  "remark": "备注信息"
}
```

#### 更新用户
```http
PUT /api/users/{id}
Content-Type: application/json

{
  "email": "updated@example.com",
  "nickname": "更新的昵称"
}
```

#### 删除用户
```http
DELETE /api/users/{id}
```

#### 批量删除用户
```http
DELETE /api/users/batch
Content-Type: application/json

[1, 2, 3]
```

#### 获取用户详情
```http
GET /api/users/{id}
```

#### 根据用户名获取用户
```http
GET /api/users/username/{username}
```

#### 分页查询用户
```http
GET /api/users?current=1&size=10&nickname=用户
```

#### 获取所有用户
```http
GET /api/users/list
```

#### 检查用户名是否存在
```http
GET /api/users/check-username?username=testuser
```

#### 检查邮箱是否存在
```http
GET /api/users/check-email?email=test@example.com
```

#### 检查手机号是否存在
```http
GET /api/users/check-phone?phone=13800138000
```

#### 更新用户状态
```http
PUT /api/users/{id}/status?status=0
```

### 响应格式

成功响应：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

分页响应：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "current": 1,
    "size": 10,
    "total": 100,
    "pages": 10,
    "records": []
  }
}
```

错误响应：
```json
{
  "code": 500,
  "message": "错误信息",
  "data": null
}
```

## 项目结构

```
src/
├── main/
│   ├── java/
│   │   └── com/workmagic/demo/nexmarkserver/
│   │       ├── NexmarkServerApplication.java    # 启动类
│   │       ├── config/                          # 配置类
│   │       │   └── MybatisPlusConfig.java       # MyBatis-Plus配置
│   │       ├── controller/                      # 控制器层
│   │       │   └── UserController.java           # 用户控制器
│   │       ├── service/                         # 服务层
│   │       │   ├── UserService.java             # 用户服务接口
│   │       │   └── impl/UserServiceImpl.java     # 用户服务实现
│   │       ├── mapper/                          # 数据访问层
│   │       │   └── UserMapper.java              # 用户Mapper
│   │       ├── entity/                          # 实体类
│   │       │   └── User.java                    # 用户实体
│   │       ├── common/                          # 通用类
│   │       │   ├── Result.java                  # 统一响应结果
│   │       │   ├── PageQuery.java               # 分页查询参数
│   │       │   └── PageResult.java              # 分页查询结果
│   │       └── demos/web/                       # 演示代码
│   └── resources/
│       ├── appclication.yml                     # 应用配置
│       ├── mapper/                              # MyBatis映射文件
│       │   └── UserMapper.xml                   # 用户SQL映射
│       ├── sql/                                 # 数据库脚本
│       │   └── sys_user_ddl.sql                 # 用户表DDL
│       └── static/                              # 静态资源
│           └── index.html                       # 首页
```

## 开发指南

### 代码规范

- 使用 Lombok 简化代码
- 遵循 RESTful API 设计规范
- 统一的异常处理和响应格式
- 使用 MyBatis-Plus 简化数据库操作

### 数据库设计

用户表 `sys_user` 包含以下字段：
- `id`: 用户ID（主键）
- `username`: 用户名（唯一）
- `password`: 密码（BCrypt加密）
- `email`: 邮箱（唯一）
- `phone`: 手机号（唯一）
- `nickname`: 昵称
- `avatar`: 头像URL
- `gender`: 性别（0：未知，1：男，2：女）
- `status`: 状态（0：禁用，1：启用）
- `create_time`: 创建时间
- `update_time`: 更新时间
- `deleted`: 删除标记（0：未删除，1：已删除）
- `remark`: 备注

### 测试账号

系统预置了以下测试账号：
- admin/123456（管理员）
- user1/123456（普通用户）
- user2/123456（普通用户）
- test/123456（已禁用用户）

## 部署说明

### 开发环境

1. 克隆项目
2. 配置数据库连接
3. 执行数据库脚本
4. 运行 `mvn spring-boot:run`

### 生产环境

1. 打包：`mvn clean package`
2. 上传 `target/nexmark-server-0.0.1-SNAPSHOT.jar` 到服务器
3. 运行：`java -jar nexmark-server-0.0.1-SNAPSHOT.jar`

### Docker 部署

```bash
# 构建镜像
docker build -t nexmark-server .

# 运行容器
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod nexmark-server
```

## 常用命令

```bash
# 编译项目
mvn compile

# 运行测试
mvn test

# 打包项目
mvn clean package

# 跳过测试打包
mvn clean package -DskipTests

# 运行项目
mvn spring-boot:run

# 查看依赖树
mvn dependency:tree
```

## 许可证

MIT License

## 贡献

欢迎提交 Issue 和 Pull Request。

## 联系方式

如有问题请提交 Issue 或联系开发者。