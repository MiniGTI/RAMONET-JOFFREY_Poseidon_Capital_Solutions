package com.nnk.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dto object to parse data from form of the add User page.
 * user/add to save a new User, managed by the validate method in the UserController.
 * <p>
 * Password validation with regexp: 1+ lowerCase, 1+ upperCase, 1+ digit, 1+ special character and at least 8 length characters.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewUserDto {

    private Integer id;
    @NotBlank(message = "Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*+-/])[a-zA-Z0-9!@#$%^&*+-/]{8,50}$",
            message = "The password must be minimum 8 length, containing at least 1 uppercase, 1 lowercase, 1 special character and 1 digit.")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    private String role;
}
