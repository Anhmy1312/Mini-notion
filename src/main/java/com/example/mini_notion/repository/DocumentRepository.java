/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mini_notion.repository;

import com.example.mini_notion.model.Document;
import java.util.List;
import java.util.Optional;
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

    // Thêm hàm truy vấn tự động theo môn học
    List<Document> findBySubject(String subject);

    List<Document> findByNameContainingIgnoreCase(String name);

    // Thêm hàm kết hợp cả Môn học và Tên tài liệu
    List<Document> findBySubjectAndNameContainingIgnoreCase(String subject, String name);
    
    // Fetch all documents for a specific user
    List<Document> findByUserUsername(String name);
    
    // Filter by subject AND user
    List<Document> findBySubjectAndUserUsername(String subject, String username);
    
    // Search by keyword AND user
    List<Document> findByNameContainingIgnoreCaseAndUserUsername(String search, String username);
    
    // Filter by subject, search keyword, AND user
    List<Document> findBySubjectAndNameContainingIgnoreCaseAndUserUsername(String subString, String search, String username);
    
    // Find a specific document by ID ensuring it belong to the logged-in user
    Optional<Document> findByIdAndUserUsername(Long id, String name);
    
}
