package com.example.MemberService.controller;

import com.example.MemberService.dto.UserDTO;
import com.example.MemberService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private UserService userService;

    @PostMapping("/users/registration")
    public ResponseEntity<UserDTO> userRegister(
            @RequestBody UserDTO userDTO
    ) {
        UserDTO registerUser = userService.registerUser(userDTO);

        return ResponseEntity.ok(registerUser);
    }

    @PutMapping("/users/{userId}/modify")
    public ResponseEntity<UserDTO> userModify(
            @PathVariable(value = "userId") Long userId,
            @RequestBody UserDTO userDTO
    ){
        UserDTO updateUser = userService.updateUser(userId, userDTO);

        return ResponseEntity.ok(updateUser);
    }

    @PostMapping("/users/login")
    public ResponseEntity<UserDTO> loginUser(
            @RequestBody UserDTO userDTO
    ) {
        UserDTO loginUser = userService.loginUser(userDTO);

        return ResponseEntity.ok(loginUser);
    }

}
