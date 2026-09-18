/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mini_notion.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
/**
 *
 * @author Admin
 */
@Entity
@Table(name = "documents")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String link;
    private String subject;

    // Thêm trường trạng thái đã học (mặc định là false - chưa học)
    private Boolean completed = false;
    
    // NEW: Link this document to a specific user
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore // Ngăn chặn vòng lặp vô hạn khi chuyển đổi JSON
    
    private User user;

    
    
    // --- Constructor mặc định (bắt buộc cho JPA) ---
    public Document() {
    }
    
    // --- Getters và Setters (để Java lấy và gán dữ liệu) ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public Boolean getCompleted() { 
        return completed; 
    }
    
    public void setCompleted(Boolean completed) {
        this.completed = completed; 
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}