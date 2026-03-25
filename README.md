# 班级考勤管理系统

## 个人信息
- **学号**：42411201
- **姓名**：韩硕
- **班级**：计算机3班

## 项目简介
这是一个基于 Spring Boot 的班级考勤管理系统，实现了学生信息管理、考勤打卡、课程管理等核心功能。

## 技术栈
- Java 17
- Spring Boot 2.7.0
- Maven
- H2 Database（内存数据库）

## 项目结构
src/main/java/com/example/attendance/
├── controller/
│ └── StudentController.java
├── service/
│ ├── StudentService.java
│ └── impl/
│ └── StudentServiceImpl.java
├── dao/
│ └── StudentDao.java
├── AttendanceSystemApplication.java
├── Result.java
├── Student.java
└── Attendance.java

src/main/resources/
└── application.properties

## 功能列表
- 学生信息查询（GET /student/info）
- 学生列表分页查询（GET /student/list）
- 考勤打卡（POST /student/attendance）
- 课程列表查询（GET /student/courses）
- 学生信息查询（路径参数 GET /student/info/{studentId}）
- 学生新增（POST /api/student/add）
- 考勤记录更新（POST /attendance/update）

## 快速启动
1. 运行 `AttendanceSystemApplication.java` 启动项目
2. 访问 `http://localhost:8080/student/info` 测试接口

## 接口示例

### 查询学生信息
GET http://localhost:8080/student/info/42411201

### 新增学生
POST http://localhost:8080/api/student/add
Content-Type: application/json

{
"studentId": "42411201",
"name": "韩硕",
"className": "计算机3班",
"age": 20
}

text

## 作者
- 学号：42411201
- 姓名：韩硕