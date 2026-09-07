/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mini_notion.controller;

import com.example.mini_notion.model.Document;
import com.example.mini_notion.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 *
 * @author Admin
 */
@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*") // Cho phép Frontend gọi API dễ dàng mà không bị chặn
public class DocumentController {
    @Autowired
    private DocumentRepository documentRepository;

    // 1. API lấy toàn bộ danh sách tài liệu (GET Method)
    @GetMapping
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    // 2. API thêm mới tài liệu (POST Method)
    @PostMapping
    public Document createDocument(@RequestBody Document document) {
        return documentRepository.save(document);
    }
    
    // 3. API xóa tài liệu theo ID (DELETE Method)
    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable Long id) {
        documentRepository.deleteById(id);
    }
}
