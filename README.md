# 苍穹外卖 (Sky Take-Out)

一个前后端分离的外卖点餐系统，包含**管理端**（商家后台）和**用户端**（微信小程序），是本项目学习 Spring Boot 全栈开发的完整实战案例。

## 项目简介

- 管理端：员工/分类/菜品/套餐管理、店铺营业状态、订单处理与催单、数据统计（营业额、用户、订单、销量排名）与 Excel 导出、工作台概览
- 用户端：微信登录、浏览菜品/套餐、地址簿、购物车、下单与微信支付、订单管理

## 技术栈

| 分类 | 技术 |
| ---- | ---- |
| 后端框架 | Spring Boot 2.7.3 / Java 17 / Maven 多模块 |
| 持久层 | MyBatis + PageHelper，MySQL 8 + Druid 连接池 |
| 缓存 | Redis（Spring Cache） |
| 接口文档 | Knife4j（Swagger） |
| 认证 | JWT（管理端 + 用户端双拦截器） |
| 实时通信 | WebSocket（订单催单提醒） |
| 文件存储 | 阿里云 OSS |
| 支付/登录 | 微信支付、微信小程序登录 |
| 数据导出 | Apache POI（Excel） |
| 其他 | AOP 公共字段自动填充、@Scheduled 定时任务（订单超时自动处理） |

## 项目结构

```
sky-take-out
├── sky-common            # 公共模块：常量、异常、工具类、统一返回结果、配置属性
├── sky-pojo              # 实体层：Entity、DTO、VO
├── sky-server            # 服务端：Controller、Service、Mapper、配置、切面、定时任务
├── frontend              # 编译好的管理端前端页面（nginx 静态资源）
├── frontend-source       # 管理端前端源码（Vue 3 + TypeScript + Element UI）
├── nginx.conf            # nginx 反向代理参考配置
└── pom.xml               # Maven 父工程
```

## 功能清单

### 管理端（/admin/**）

- **员工管理**：登录、新增、分页查询、编辑、启用/禁用、修改密码
- **分类管理**：新增、修改、删除、启停、分页查询
- **菜品管理**：新增（含口味）、分页查询、修改、启停、删除
- **套餐管理**：新增（关联菜品）、分页查询、修改、启停、删除
- **店铺状态**：营业状态查询与切换
- **订单管理**：条件搜索、状态查询、接单/拒单、取消、派送、完成、催单（WebSocket 通知）
- **数据统计**：营业额统计、用户统计、订单统计、销量排名 Top10、数据导出（Excel）
- **工作台**：今日订单数、营业额、有效订单、待接单/待派送/派送中/已完成概览

### 用户端（/user/**）

- 微信登录
- 分类、菜品、套餐浏览（Redis 缓存）
- 地址簿管理
- 购物车
- 下单、微信支付、历史订单查询

## 快速启动

### 环境要求

- JDK 17
- Maven 3.x
- MySQL 8.x
- Redis

### 1. 数据库

创建数据库 `sky_take_out`，导入项目资料中的初始化 SQL 脚本。

### 2. 修改配置

编辑 `sky-server/src/main/resources/application-dev.yml`，将占位符替换为本地真实配置：

```yaml
sky:
  datasource:
    host: localhost
    port: 3306
    database: sky_take_out
    username: root
    password: <你的MySQL密码>
  redis:
    host: localhost
    port: 6379
  alioss:        # 阿里云OSS（文件上传，可自行申请或复用已有）
    endpoint: <你的endpoint>
    access-key-id: <你的AccessKey ID>
    access-key-secret: <你的AccessKey Secret>
    bucket-name: <你的Bucket>
  wechat:        # 微信小程序
    appid: <你的appid>
    secret: <你的secret>
```

> 注：阿里云 OSS、微信支付/登录需要自行在对应平台申请并填入。仅跑通基础功能时，数据库和 Redis 配置即可启动，文件上传、登录/支付接口会因缺少密钥而失败。

### 3. 启动后端

```bash
mvn -pl sky-server -am spring-boot:run
```

或直接在 IDE 中运行 `com.sky.SkyApplication`（`sky-server` 模块）。

- 管理端接口：`http://localhost:8080/admin`
- 接口文档（Knife4j）：`http://localhost:8080/doc.html`

### 4. 启动前端

**方式一：nginx（推荐）**

1. 将 `frontend` 目录部署到 nginx 的 `html/sky`
2. 参考仓库根目录的 `nginx.conf` 配置反向代理（将 `/api` 转发到后端 `8080`）

**方式二：源码运行（开发调试）**

```bash
cd frontend-source
npm install        # 或 yarn
npm run serve      # 或 yarn serve
```

修改 `frontend-source/.env.development` 中的 `VUE_APP_URL` 指向后端地址。

### 5. 访问

- 管理端：`http://localhost:8080`
- 默认账号：`admin` / `123456`

## 相关资源

- 前端源码（管理端）：`frontend-source/`
- 编译好的前端页面：`frontend/`
- nginx 反向代理参考配置：`nginx.conf`

## 说明

本项目为苍穹外卖课程配套实战项目，数据库初始化 SQL、微信小程序源码等依赖课程资料。
