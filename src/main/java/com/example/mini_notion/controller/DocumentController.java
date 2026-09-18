package com.example.mini_notion.controller;

import com.example.mini_notion.dto.DocumentDTO;
import com.example.mini_notion.service.DocumentService;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*") // Cho phép Frontend gọi API dễ dàng mà không bị chặn
public class DocumentController {

    // Inject Service thay vì Repository
    @Autowired
    private DocumentService documentService;

    
    
    // 1. API lấy danh sách tài liệu (GET Method) - Hỗ trợ lọc theo môn học và lấy tất cả
    @GetMapping
    public List<DocumentDTO> getDocuments(
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String search,
            Principal principal) { // Extract user from JWT
        // Giao toàn bộ việc tính toán, truy vấn cho Service
        return documentService.getDocuments(subject, search, principal.getName());
    }
    
    // 2. API thêm mới tài liệu (POST Method)
    @PostMapping
    public DocumentDTO createDocument(@RequestBody DocumentDTO documentDTO, Principal principal) {
        return documentService.createDocument(documentDTO, principal.getName());
    }
    
    // 3. API xóa tài liệu theo ID (DELETE Method)
    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable Long id, Principal principal) {
        documentService.deleteDocument(id, principal.getName());
    }
    
    // 4. API cập nhật tài liệu (PUT Method)
    @PutMapping("/{id}")
    public DocumentDTO updateDocument(@PathVariable Long id, @RequestBody DocumentDTO documentDTO, Principal principal) {
        return documentService.updateDocument(id, documentDTO, principal.getName());
    }
}