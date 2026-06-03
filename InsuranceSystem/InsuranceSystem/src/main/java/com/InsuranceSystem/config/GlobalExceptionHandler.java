package com.InsuranceSystem.config;

import com.InsuranceSystem.exceptions.ResourceNotFoundException;
import com.InsuranceSystem.utility.ResponseUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;
import java.io.IOException;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

   private final ResponseUtility responseUtility;
   @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseUtility> handleResourceNotFoundException( ResourceNotFoundException e) {
       responseUtility.setMessage(e.getMessage());
       return ResponseEntity.badRequest()
               .body(responseUtility);
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseUtility> handleValidationException(MethodArgumentNotValidException e) {
       String message = e.getBindingResult()
               .getFieldErrors()
               .stream()
               .findFirst()
               .map(error -> error.getDefaultMessage())
               .orElse("Invalid request");
       responseUtility.setMessage(message);
       return ResponseEntity.badRequest()
               .body(responseUtility);
   }
   @ExceptionHandler(FileNotFoundException.class)
   public ResponseEntity<ResponseUtility> handleFileNotFoundException(
           FileNotFoundException e
   ){
      responseUtility.setMessage(e.getMessage());
      return ResponseEntity
              .badRequest()
              .body(responseUtility);
   }
   // IOException
   @ExceptionHandler(IOException.class)
   public ResponseEntity<ResponseUtility> handleIOException(
           IOException e
   ){
      responseUtility.setMessage(e.getMessage());
      return ResponseEntity
              .badRequest()
              .body(responseUtility);
   }

   @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseUtility> handleRuntimeException(RuntimeException e) {
       responseUtility.setMessage(e.getMessage());
       return ResponseEntity.badRequest()
               .body(responseUtility);
   }
}
