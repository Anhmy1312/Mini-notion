 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mini_notion.service;

import com.example.mini_notion.dto.DocumentDTO;
import com.example.mini_notion.model.Document;
import com.example.mini_notion.model.User;
import com.example.mini_notion.repository.DocumentRepository;
import com.example.mini_notion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Admin
 */

@Service // Đánh dấu đay là tầng Service
public class DocumentServiceImpl implements DocumentService{
    
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;
    
    
    
    // Hàm phụ trợ: Chuyển đổi từ Entity sang DTO
    private DocumentDTO mapToDTO(Document document) {
        DocumentDTO dto = new DocumentDTO();
        dto.setId(document.getId());
        dto.setName(document.getName());
        dto.setLink(document.getLink());
        dto.setSubject(document.getSubject());
        dto.setCompleted(document.getCompleted());
        return dto;
    }

    // Hàm phụ trợ: Chuyển đổi từ DTO sang Entity
    private Document mapToEntity(DocumentDTO dto) {
        Document document = new Document();
        document.setName(dto.getName());
        document.setLink(dto.getLink());
        document.setSubject(dto.getSubject());
        document.setCompleted(dto.getCompleted() != null ? dto.getCompleted() : false);
        return document;
    }

    @Override
    public List<DocumentDTO> getDocuments(String subject, String search, String username) {
        List<Document> documents;
        boolean hasSubject = subject != null && !subject.isEmpty();
        boolean hasSearch = search != null && !search.isEmpty();

        // Chuyển logic if/else từ Controller cũ sang đây
        if (hasSubject && hasSearch) {
            documents = documentRepository.findBySubjectAndNameContainingIgnoreCaseAndUserUsername(subject, search, username);
        } else if (hasSubject) {
            documents = documentRepository.findBySubjectAndUserUsername(subject, username);
        } else if (hasSearch) {
            documents = documentRepository.findByNameContainingIgnoreCaseAndUserUsername(search, username);
        } else {
            documents = documentRepository.findByUserUsername(username);
        }

        // Chuyển danh sách Entity thành danh sách DTO
        return documents.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public DocumentDTO createDocument(DocumentDTO documentDTO, String username) {
        Document document = mapToEntity(documentDTO);
        
        // Find the user by the username extracted from the JWT token
        User user = userRepository.findByUsername(username).orElseThrow();
        document.setUser(user); // Link the document to the user
        
        Document savedDoc = documentRepository.save(document);
        return mapToDTO(savedDoc);
    }

    @Override
    public DocumentDTO updateDocument(Long id, DocumentDTO documentDetails, String username) {
        // Ensure the document being updated belongs to the logged-in user
        Document document = documentRepository.findByIdAndUserUsername(id, username).orElseThrow();
        
        document.setName(documentDetails.getName());
        document.setLink(documentDetails.getLink());
        document.setSubject(documentDetails.getSubject());
        document.setCompleted(documentDetails.getCompleted());
        
        Document updatedDoc = documentRepository.save(document);
        return mapToDTO(updatedDoc);
    }

    @Override
    public void deleteDocument(Long id, String username) {
        // Ensure the document being deleted belongs to the logged-in user
        Document document = documentRepository.findByIdAndUserUsername(id, username).orElseThrow();
        documentRepository.delete(document);
    }
}
