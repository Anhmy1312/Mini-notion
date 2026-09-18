/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.mini_notion.repository;

import com.example.mini_notion.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Admin
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    // Hàm tìm kiếm người dùng theo username để kiểm tra lúc đăng nhập
    Optional<User> findByUsername(String username);
    
}
