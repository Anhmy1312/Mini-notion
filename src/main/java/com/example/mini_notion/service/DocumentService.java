/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mini_notion.service;

import com.example.mini_notion.dto.DocumentDTO;
import java.util.List;

/**
 *
 * @author Admin
 */
public interface DocumentService {
    List<DocumentDTO> getDocuments(String subject, String search, String username);
    DocumentDTO createDocument(DocumentDTO documentDTO, String username);
    DocumentDTO updateDocument(Long id, DocumentDTO documentDTO, String username);
    void deleteDocument(Long id, String username);
}
