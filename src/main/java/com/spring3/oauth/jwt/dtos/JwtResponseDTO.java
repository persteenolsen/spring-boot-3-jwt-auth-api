package com.spring3.oauth.jwt.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponseDTO {

   private String accessToken;
   
   // Test - 20-02-2025
   private String userName;
   
}
