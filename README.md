# 📚 Mini-Notion: Hệ Thống Quản Lý & Theo Dõi Tiến Độ Học Tập

<p align="center">
  <img src="https://img.shields.io/badge/Java-17+-orange.svg?style=for-the-badge&logo=java" alt="Java" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen.svg?style=for-the-badge&logo=springboot" alt="Spring Boot" />
  <img src="https://img.shields.io/badge/MySQL-8.0-blue.svg?style=for-the-badge&logo=mysql" alt="MySQL" />
  <img src="https://img.shields.io/badge/JavaScript-ES6+-yellow.svg?style=for-the-badge&logo=javascript" alt="JavaScript" />
  <img src="https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge" alt="License" />
</p>

> 🚀 **Mini-Notion** là ứng dụng Web Fullstack hỗ trợ sinh viên tổ chức, quản lý tài liệu học tập, lọc theo môn học, tìm kiếm thời gian thực và theo dõi tiến độ hoàn thành trực quan.

---

## 🛠️ Công Nghệ Sử Dụng (Tech Stack)

| Tầng (Layer) | Công nghệ / Thư viện |
| :--- | :--- |
| **Frontend** | HTML5, CSS3 (Variables, Flexbox), JavaScript ES6+ (Fetch API, Async/Await, LocalStorage) |
| **Backend** | Java 17+, Spring Boot, Spring Data JPA, Hibernate |
| **Database** | MySQL 8.0 |
| **Công cụ (Tools)** | Maven, Git / GitHub, NetBeans / IntelliJ, MySQL Workbench |

---

## ✨ Tính Năng Nổi Bật (Key Features)

- 🔄 **CRUD Trọn vẹn:** Xem, Thêm mới, Sửa thông tin và Xóa tài liệu khỏi hệ thống.
- 📊 **Dashboard Tiến Độ:** Thanh tiến độ phần trăm (%) tự động tính toán và cập nhật thời gian thực.
- ☑️ **Đánh Dấu Đã Học:** Checkbox tương tác hỗ trợ lưu trạng thái hoàn thành kèm hiệu ứng gạch ngang.
- 🔍 **Tìm Kiếm & Lọc Kết Hợp:** Tìm kiếm theo từ khóa real-time kết hợp bộ lọc môn học trên Sidebar.
- 🔀 **Sắp Xếp Linh Hoạt:** Hỗ trợ sắp xếp tài liệu theo Tên (A-Z, Z-A) hoặc Thời gian tạo (Mới nhất, Cũ nhất).
- 🌙 **Chế Độ Sáng / Tối (Dark / Light Mode):** Tự động lưu trạng thái giao diện qua LocalStorage.

---

## 🔌 Danh Sách API (RESTful Endpoints)

| Phương thức | Đường dẫn API | Mô tả |
| :---: | :--- | :--- |
| `GET` | `/api/documents` | Lấy danh sách tất cả tài liệu |
| `POST` | `/api/documents` | Tạo mới một tài liệu |
| `PUT` | `/api/documents/{id}` | Cập nhật thông tin/trạng thái tài liệu |
| `DELETE` | `/api/documents/{id}` | Xóa tài liệu khỏi hệ thống |

---

## 🚀 Hướng Dẫn Cài Đặt & Chạy Dự Án (Getting Started)

### 1. Clone Repository
```bash
git clone [https://github.com/Anhmy1312/Mini-notion.git](https://github.com/Anhmy1312/Mini-notion.git)
cd Mini-notion
```
### 2. Cấu hình Cơ sở dữ liệu (MySQL)
* Mở MySQL Workbench và tạo một database mới với tên:
  ```sql
  CREATE DATABASE mini_notion_db;
  ```
* Mở file cấu hình trong dự án tại đường dẫn: 
  `src/main/resources/application.properties`
* Kiểm tra và điền đúng thông tin kết nối MySQL của bạn (username/password).
   ```properties
  spring.datasource.url=jdbc:mysql://localhost:3306/mini_notion_db
  spring.datasource.username=root
  spring.datasource.password=mật_khẩu_của_bạn
  spring.jpa.hibernate.ddl-auto=update
  ```
  
### 3. Chạy Backend (Spring Boot)
* Mở dự án bằng IDE (NetBeans / IntelliJ).
* Tìm đến file chạy chính `MiniNotionApplication.java` và chạy ứng dụng (Run).
* Đảm bảo server khởi động thành công ở cổng `8080`.

### 4. Trải nghiệm Frontend
* Mở file `index.html` (hoặc chạy qua Live Server trên VS Code) trên trình duyệt để tương tác với giao diện.
