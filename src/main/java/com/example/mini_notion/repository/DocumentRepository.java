/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mini_notion.repository;

import com.example.mini_notion.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author Admin
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    // Chỉ với 1 dòng extends JpaRepository này, Spring Boot đã tự động cung cấp 
    // các hàm thao tác sẵn như: findAll() (lấy tất cả), save() (thêm/sửa), deleteById() (xóa)...
}
