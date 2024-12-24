package br.com.sea.tecnologia.desafioBackend.util;

import br.com.sea.tecnologia.desafioBackend.exceptions.handler.error.ViaCepError;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiViaCep {
     private final RestTemplate restTemplate;
     private final String VIACEP_API = "https://viacep.com.br/ws/";

     public ApiViaCep() {
          this.restTemplate = new RestTemplate();
     }

     public boolean isValidCep(String cep) {
          try {
               String url = VIACEP_API + cep.replace("-", "") + "/json";
               ViaCepError response = restTemplate.getForObject(url, ViaCepError.class);
               return response != null && response.getErro() == null;
          } catch (Exception e) {
               return false;
          }
     }
}