package br.com.sea.tecnologia.desafioBackend.exceptions;

public class ResourceNotFoundException extends RuntimeException {
     public ResourceNotFoundException(String message) {
          super(message);
     }
}
