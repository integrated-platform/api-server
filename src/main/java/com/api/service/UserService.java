package com.api.service;

import com.api.dto.UserDTO;
import com.api.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // User 엔티티를 UserDTO로 변환
    public UserDTO convertToDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

    // UserDTO를 User 엔티티로 변환
    public User convertToEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
