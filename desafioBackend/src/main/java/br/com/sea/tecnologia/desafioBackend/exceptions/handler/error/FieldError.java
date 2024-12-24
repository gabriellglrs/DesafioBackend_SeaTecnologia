package br.com.sea.tecnologia.desafioBackend.exceptions.handler.error;

public class FieldError {
     private String fieldName;
     private String errorMessage;

     public FieldError() {
     }

     public FieldError(String fieldName, String errorMessage) {
          this.fieldName = fieldName;
          this.errorMessage = errorMessage;
     }

     public String getFieldName() {
          return fieldName;
     }

     public String getErrorMessage() {
          return errorMessage;
     }
}
