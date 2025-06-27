package com.example.MemberService.service;


import com.example.MemberService.dto.UserDTO;
import com.example.MemberService.entity.UserEntity;
import com.example.MemberService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public UserDTO registerUser(UserDTO userDTO) {

        validateRegistration(userDTO);

        UserEntity userEntity = UserDTO.of(userDTO);

        UserEntity savedUserEntity = userRepository.save(userEntity);

        return new UserDTO(
                savedUserEntity.getId(),
                savedUserEntity.getLoginId(),
                savedUserEntity.getUserName(),
                null
        );
    }

    public UserDTO loginUser(UserDTO userDTO) {
        UserEntity userEntity = userRepository.findByLoginId(userDTO.loginId())
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        validatePassword(userEntity, userDTO);

        return new UserDTO(
                userEntity.getId(),
                userEntity.getLoginId(),
                userEntity.getUserName(),
                null
        );
    }

    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if(Objects.nonNull(userDTO.userName()))
            userEntity.setUserName(userDTO.userName());
        if(Objects.nonNull(userDTO.password()))
            userEntity.setPassword(userDTO.password());

        UserEntity updatedUserEntity = userRepository.save(userEntity);

        return new UserDTO(
                updatedUserEntity.getId(),
                updatedUserEntity.getLoginId(),
                updatedUserEntity.getUserName(),
                null
        );
    }


    private void validatePassword(UserEntity userEntity, UserDTO userDTO) {
        if(!userEntity.getPassword().equals(userDTO.password()))
            throw new RuntimeException("ID or password Mismatch");
    }
    private void validateRegistration(UserDTO userDTO) {
        userRepository.findByLoginId(userDTO.loginId())
                .ifPresent(userEntity -> {
                    throw new RuntimeException("User already exists");
                });
    }

    private void validateLoginId(UserEntity userEntity, UserDTO userDTO) {
        if(!userEntity.getLoginId().equals(userDTO.loginId()))
            throw new RuntimeException("ID or password Mismatch");
    }
}
