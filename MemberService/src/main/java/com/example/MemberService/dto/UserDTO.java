package com.example.MemberService.dto;


import com.example.MemberService.entity.UserEntity;

public record UserDTO(
        Long id,
        String loginId,
        String userName,
        String password
) {


    public static UserEntity of(Long id, String loginId, String userName, String password) {
        return UserEntity.Builder()
                .id(id)
                .loginId(loginId)
                .userName(userName)
                .password(password)
                .build();
    }

    public static UserEntity of(UserDTO userDTO) {
        return UserEntity.Builder()
                .loginId(userDTO.loginId)
                .userName(userDTO.userName)
                .password(userDTO.password)
                .build();
    }
}
