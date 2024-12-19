package com.phonebook.dto;

import lombok.*;
//@Data
@Setter
@Getter
@ToString


@Builder
//@AllArgsConstructor
public class AuthRequestDto {


    private String username;
    private String password;
}
