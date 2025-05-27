package org.iesvdm.mhm.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record LoginRequestDTO(
        @NotBlank @Size(min = 3, max = 100) String username,
        @NotBlank @Size(min = 3, max = 255) String password
) {


}
