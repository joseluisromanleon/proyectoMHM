package org.iesvdm.mhm.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


    public record UsuarioDTO(
            @NotBlank @Size(min = 3, max = 100) String userName,
            @NotBlank @Email String userEmail,
            @NotBlank @Size(min = 3, max = 255) String userPassword,
            @AssertTrue Boolean enabled,
            @AssertTrue Boolean userAceptaCondiciones
    ){}

