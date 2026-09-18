DROP DATABASE IF EXISTS mini_notion_db;

CREATE DATABASE mini_notion_db;

USE mini_notion_db;

-- 4. TẠO BẢNG 'users' (LƯU THÔNG TIN TÀI KHOẢN ĐĂNG NHẬP)
-- Lý do: Để hệ thống có thể xác thực người dùng bằng JWT, chúng ta cần lưu trữ tên đăng nhập và mật khẩu.
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY, -- ID tự động tăng (1, 2, 3...)
    username VARCHAR(100) NOT NULL UNIQUE, -- Tên đăng nhập không được trùng lặp
    password VARCHAR(255) NOT NULL, -- Mật khẩu 
    role VARCHAR(50) DEFAULT 'USER' -- Quyền của tài khoản (USER hoặc ADMIN)
);

-- 5. TẠO BẢNG 'documents' (LƯU TÀI LIỆU HỌC TẬP CỦA TỪNG NGƯỜI)
-- Lý do: Thêm cột `user_id` để kết nối mỗi tài liệu với một tài khoản duy nhất (Quan hệ 1-N).
CREATE TABLE documents (
    id INT AUTO_INCREMENT PRIMARY KEY, -- ID tự động tăng cho từng tài liệu
    name VARCHAR(255) NOT NULL, -- Tên tài liệu
    link VARCHAR(500) NOT NULL, -- Link tài liệu
    subject VARCHAR(100) NOT NULL, -- Phân loại môn học (toan, ly, hoa...)
    completed BOOLEAN DEFAULT FALSE, -- Trạng thái hoàn thành (0=Chưa, 1=Xong)
    
    -- Khóa ngoại kết nối tài liệu với bảng users
    user_id INT NOT NULL, 
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
    -- ON DELETE CASCADE: Nghĩa là nếu bạn xóa một user, toàn bộ tài liệu của user đó cũng bị xóa theo.
);

-- 6. THÊM TÀI KHOẢN MẪU (TEST USER)
-- Mật khẩu ở đây đang lưu dạng văn bản thô (thực tế đi làm sẽ được mã hóa bằng BCrypt).
INSERT INTO users (username, password, role) VALUES 
('user', '123', 'USER');

-- 7. THÊM TÀI LIỆU MẪU (GẮN VỚI TÀI KHOẢN 'USER' CÓ ID = 1)
-- Những tài liệu này chỉ có tài khoản 'USER' mới nhìn thấy được.
INSERT INTO documents (name, link, subject, completed, user_id) VALUES 
('', '', '', FALSE, 1);



