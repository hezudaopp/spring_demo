package spittr.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import spittr.exceptions.ErrorResponse;
import spittr.exceptions.InvalidArgumentException;
import spittr.exceptions.ResourceNotFoundException;

import java.util.List;

/**
 * Created by 273cn on 16/12/22.
 */
@RestController
public class BaseController {
    @ExceptionHandler(InvalidArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ErrorResponse> invalidArgument(InvalidArgumentException e) {
        return e.getErrorResponseList();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFound(ResourceNotFoundException e) {
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getResourceName() + " " + e.getResourceId() + " not found.");
    }
}
