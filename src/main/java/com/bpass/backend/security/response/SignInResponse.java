package com.bpass.backend.security.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignInResponse {
  private String accessToken;
  private String id;
  private String name;
  private String storeName;
}
