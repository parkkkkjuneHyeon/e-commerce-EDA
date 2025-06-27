package com.example.MemberService.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Column
    private String userName;

    public UserEntity() {}

    public UserEntity(Builder builder) {
        this.id = builder.id;
        this.loginId = builder.loginId;
        this.password = builder.password;
        this.userName = builder.userName;
    }

    public Long getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public static Builder Builder() {
        return new Builder();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public static class Builder {
        private Long id;

        private String loginId;

        private String password;

        private String userName;

        public Builder() {}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder loginId(String loginId) {
            this.loginId = loginId;
            return this;
        }
        public Builder password(String password) {
            this.password = password;
            return this;
        }
        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }
        public UserEntity build() {
            return new UserEntity(this);
        }
    }
}
