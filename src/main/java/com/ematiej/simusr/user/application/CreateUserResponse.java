package com.ematiej.simusr.user.application;

import com.ematiej.simusr.user.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserResponse {
    private Long id;
    private String username;
    boolean success;
    private String errorMessage;

    public static CreateUserResponse success(UserEntity user) {
        return new CreateUserResponse(user.getId(), user.getUsername(), true, null);
    }

    public static CreateUserResponse failure(String errorMessage) {
        return new CreateUserResponse(null, null, false, errorMessage);
    }
}
