package br.com.sea.tecnologia.desafioBackend.exceptions.handler;

import br.com.sea.tecnologia.desafioBackend.exceptions.DatabaseException;
import br.com.sea.tecnologia.desafioBackend.exceptions.ResourceAlreadyExistsException;
import br.com.sea.tecnologia.desafioBackend.exceptions.ResourceNotFoundException;
import br.com.sea.tecnologia.desafioBackend.exceptions.handler.error.CustomError;
import br.com.sea.tecnologia.desafioBackend.exceptions.handler.error.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

// ControllerAdvice para centralizar o tratamento de exceções
@ControllerAdvice
public class GlobalExceptionHandler {

     // Tratando ResourceNotFoundException
     @ExceptionHandler(ResourceNotFoundException.class)
     public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
          HttpStatus status = HttpStatus.NOT_FOUND; // Código de erro 404
          CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
          return ResponseEntity.status(status).body(err);
     }

     @ExceptionHandler(ResourceAlreadyExistsException.class)
     public ResponseEntity<CustomError> handleResourceAlreadyExistsException(ResourceAlreadyExistsException e, HttpServletRequest request) {
          HttpStatus status = HttpStatus.BAD_REQUEST; // Código de erro 400
          CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
          return ResponseEntity.status(status).body(err);
     }

     // Tratando DatabaseException
     @ExceptionHandler(DatabaseException.class)
     public ResponseEntity<CustomError> database(DatabaseException e, HttpServletRequest request) {
          HttpStatus status = HttpStatus.BAD_REQUEST; // Código de erro 400
          CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());
          return ResponseEntity.status(status).body(err);
     }

     @ExceptionHandler({MethodArgumentNotValidException.class})
     public ResponseEntity<ValidationError> MethodArgumentNotValid(MethodArgumentNotValidException e, HttpServletRequest request) {

          // Define o status HTTP que será retornado
          HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

          // Cria o objeto de erro de validação
          ValidationError err = new ValidationError(Instant.now(), status.value(), "Erro de validação", request.getRequestURI());

          // Extraindo erros reais do MethodArgumentNotValidException com forEach
          e.getBindingResult().getFieldErrors()
                  .forEach(fieldError -> err.addError(fieldError.getField(), fieldError.getDefaultMessage()));

          // Retorna a resposta com o erro e o status HTTP adequado
          return ResponseEntity.status(status).body(err);
     }

     @ExceptionHandler(HttpMessageNotReadableException.class)
     public ResponseEntity<CustomError> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpServletRequest request) {
          HttpStatus status = HttpStatus.BAD_REQUEST;
          String errorMessage = "Erro de leitura do JSON: " + e.getMostSpecificCause().getMessage();
          CustomError err = new CustomError(Instant.now(), status.value(), errorMessage, request.getRequestURI());
          return ResponseEntity.status(status).body(err);
     }
}