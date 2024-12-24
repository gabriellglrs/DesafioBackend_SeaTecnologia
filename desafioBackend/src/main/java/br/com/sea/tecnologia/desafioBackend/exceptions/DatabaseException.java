package br.com.sea.tecnologia.desafioBackend.exceptions;

public class DatabaseException extends RuntimeException {
     public DatabaseException(String message) {
          super(message);
     }
}
