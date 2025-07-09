package com.security;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code;
    private String email;
    private String nickname;
    private String phoneNumber;
    private String address;
}