package spittr.exceptions;

/**
 * Created by 273cn on 16/12/22.
 */
public class ErrorResponse {
    private int code;
    private String message;
    private String fieldName;

    public ErrorResponse(int code, String message) {
        this(code, message, null);
    }

    public ErrorResponse(int code, String message, String fieldName) {
        this.code = code;
        this.message = message;
        this.fieldName = fieldName;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getFieldName() {
        return fieldName;
    }
}
