package spittr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 273cn on 16/12/22.
 */
public class InvalidArgumentException extends RuntimeException implements ErrorResponseListException {
    private List<ErrorResponse> errorResponseList = new ArrayList<>();

    public InvalidArgumentException(Errors errors) {
        List<FieldError> errorList = errors.getFieldErrors();
        for (FieldError error : errorList) {
            errorResponseList.add(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), error.getDefaultMessage(), error.getField()));
        }
    }

    public List<ErrorResponse> getErrorResponseList() {
        return errorResponseList;
    }
}
