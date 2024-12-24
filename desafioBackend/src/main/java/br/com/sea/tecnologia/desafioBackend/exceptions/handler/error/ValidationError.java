package br.com.sea.tecnologia.desafioBackend.exceptions.handler.error;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends CustomError{
     private List<FieldError> errors = new ArrayList<>();  // Lista de erros de campo

     // Método para adicionar um novo erro de campo à lista
     public void addError(String fieldName, String message) {
          errors.add(new FieldError(fieldName, message));
     }

     public ValidationError() {
     }

     public ValidationError(Instant timestamp, Integer status, String error, String path) {
          super(timestamp, status, error, path);
     }

     public List<FieldError> getErrors() {
          return errors;
     }
}
