package com.api.service;

import com.api.dto.UserDTO;
import com.api.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    // UserDTO를 User Entity로 변환
    public User convertToEntity(UserDTO userDTO) {
        return new User(userDTO.getUsername(), userDTO.getPassword(), userDTO.getEmail());
    }

    // User Entity를 UserDTO로 변환
    public UserDTO convertToDto(User user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getEmail());
    }
}
