package org.iesvdm.mhm.dto;

import java.util.List;

public record JwtDTO(String token, List<String> roles, String estado) {


}
