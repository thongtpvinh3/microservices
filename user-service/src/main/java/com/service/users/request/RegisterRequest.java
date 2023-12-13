package com.service.users.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterRequest {

    @NotBlank(message = "Username không được để trống")
    private String username;

    @Email(message = "Email không đúng định dạng")
    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotBlank(message = "Password không được để trống")
    @Size(min = 8, message = "Mật khẩu phải chứa ít nhất 8 kí tự")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=])(?=.*[0-9]).{8,}$",
            message = "Mật khẩu phải có ít nhất 8 kí tự, chứa ít nhất 1 chữ số, 1 chữ hoa, 1 kí tự đặc biệt và 1 chữ cái thường")
    private String password;

    private String address;

}
