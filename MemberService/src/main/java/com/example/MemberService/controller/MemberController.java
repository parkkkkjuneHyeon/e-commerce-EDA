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

    @PutMapping("/users/{memberId}/modify")
    public ResponseEntity<UserDTO> userModify(
            @PathVariable(value = "memberId") Long memberId,
            @RequestBody UserDTO userDTO
    ){
        UserDTO updateUser = userService.updateUser(memberId, userDTO);

        return ResponseEntity.ok(updateUser);
    }

    @GetMapping("/users/{userId}/search-email")
    public ResponseEntity<UserDTO> userModify(
            @PathVariable(value = "userId") String userId
    ){
        UserDTO updateUser = userService.findByUserId(userId);

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
