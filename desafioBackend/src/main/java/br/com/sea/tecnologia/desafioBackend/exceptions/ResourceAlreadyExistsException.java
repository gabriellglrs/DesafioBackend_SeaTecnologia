package br.com.sea.tecnologia.desafioBackend.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {
     public ResourceAlreadyExistsException(String message) {
          super(message);
     }
}
