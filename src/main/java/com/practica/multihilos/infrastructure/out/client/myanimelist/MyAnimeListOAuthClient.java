package com.practica.multihilos.infrastructure.out.client.myanimelist;

import com.practica.multihilos.domain.port.secondary.TokenExchangePort;
import com.practica.multihilos.infrastructure.config.MalProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Component
public class MyAnimeListOAuthClient implements TokenExchangePort {

  private final RestTemplate restTemplate;
  private final MalProperties malProperties;

  @Override
  public String exchangeAuthorizationCode(String code) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    headers.setBasicAuth(malProperties.getClientId(), malProperties.getClientSecret());

    MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
    form.add("grant_type", "authorization_code");
    form.add("code", code);
    form.add("redirect_uri", malProperties.getRedirectUri());
    form.add("code_verifier", malProperties.getCodeVerifier());

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);

    ResponseEntity<String> response =
            restTemplate.exchange(malProperties.getUrlToken(), HttpMethod.POST, request, String.class);
    return response.getBody();
  }
}
