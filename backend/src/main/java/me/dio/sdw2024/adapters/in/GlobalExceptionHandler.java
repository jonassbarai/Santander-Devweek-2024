package me.dio.sdw2024.adapters.in;

import me.dio.sdw2024.domain.exception.ChampionNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = (Logger) LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ChampionNotFoundException.class)
    public ResponseEntity<ApiError> handleDomainExceptioin(ChampionNotFoundException domainError){
      return ResponseEntity.unprocessableEntity().body(new ApiError(domainError.getMessage()));    //ApiError(domainError.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleDomainExceptioin(Exception unexpectedError){
        logger.error("ocorreu um erro inesperado",unexpectedError);
        return ResponseEntity.unprocessableEntity().body(new ApiError(unexpectedError.getMessage()));    //ApiError(domainError.getMessage());
    }
    public record ApiError(String message){

    }
}
