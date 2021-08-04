package com.rest.ai.myCallimo.exception;

import com.rest.ai.myCallimo.exception.city.CityNotFoundException;
import com.rest.ai.myCallimo.exception.offre.AlreadyAffectedException;
import com.rest.ai.myCallimo.exception.offre.OffreNotFoundException;
import com.rest.ai.myCallimo.exception.role.RoleAlreadyExistsException;
import com.rest.ai.myCallimo.exception.role.RoleNotFoundException;
import com.rest.ai.myCallimo.exception.user.UnAuthorizationUser;
import com.rest.ai.myCallimo.exception.user.UserAlreadyExist;
import com.rest.ai.myCallimo.exception.user.UserNotFoundException;
import com.rest.ai.myCallimo.shared.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AppExceptionHandler {
    //    user Exception debut
    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handlerUserNotFoundException(UserNotFoundException ex, WebRequest req) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserAlreadyExist.class})
    public ResponseEntity<Object> handlerUserAlreadyExistsException(UserAlreadyExist ex, WebRequest req) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {UnAuthorizationUser.class})
    public ResponseEntity<Object> handlerAuthorizationUserException(UnAuthorizationUser ex, WebRequest req) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    //    user Exception fin

    //    Role Exception Start
    @ExceptionHandler(value = {RoleNotFoundException.class})
    public ResponseEntity<Object> handlerUserException(RoleNotFoundException ex, WebRequest req) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {RoleAlreadyExistsException.class})
    public ResponseEntity<Object> handlerUserException(RoleAlreadyExistsException ex, WebRequest req) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    //    Role Exception fin
    // start city
    @ExceptionHandler(value = {CityNotFoundException.class})
    public ResponseEntity<Object> handlerCityException(CityNotFoundException ex, WebRequest req) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
    // end    city



    // start offre
    @ExceptionHandler(value = {AlreadyAffectedException.class})
    public ResponseEntity<Object> handlerOffreException(AlreadyAffectedException ex, WebRequest req) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value = {OffreNotFoundException.class})
    public ResponseEntity<Object> handlerOffreException(OffreNotFoundException ex, WebRequest req) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    // end    offre


//    InvalidOperationException

    @ExceptionHandler(value = {InvalidOperationException.class})
    public ResponseEntity<Object> invalidOperationException(InvalidOperationException ex, WebRequest req) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.CONFLICT);
    }

    //    InvalidOperationException
    //    global Exception
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> HandleOtherExceptions(Exception ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(new Date(), ex.getMessage());

        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> HandleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
}
