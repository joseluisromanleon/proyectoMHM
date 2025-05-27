package org.iesvdm.mhm.dto;

import jakarta.validation.constraints.*;

public record MensajeDTO(
        @NotBlank @Size(min = 3, max = 50) String nombreEmpresa,
        @NotBlank @Size(min = 3, max = 100) String direccionEmpresa,
        @NotBlank @Size(min = 9, max = 12) String telEmpresa,
        @NotBlank @Email String emailEmpresa,
        @NotBlank @Size(min = 3, max = 50) String nombreContacto,
        @NotBlank @Pattern(regexp = "\\d{9}") String telContacto,
        @NotBlank @Email String emailContacto,
        @NotBlank @Size(min = 3, max = 255) String observaciones,
        @AssertTrue Boolean aceptaCondiciones
){}
