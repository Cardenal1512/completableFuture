package com.practica.multihilos.infrastructure.out.client.myanimelist.auth;

import com.practica.multihilos.infrastructure.out.client.myanimelist.FileTokenStorageAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.*;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
@RequiredArgsConstructor
public class MyAnimeListAuthInterceptor implements ClientHttpRequestInterceptor {
  private final FileTokenStorageAdapter fileTokenStorageAdapter;

  @Override
  public ClientHttpResponse intercept(
      HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

    String token = fileTokenStorageAdapter.load().value();
    request.getHeaders().add("Authorization", "Bearer " + token);
    return execution.execute(request, body);
  }
}
