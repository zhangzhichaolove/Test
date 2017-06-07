package rxjava.example;

public class ApiException extends RuntimeException {

    private int errorCode;

    public ApiException(int errorCode, String errorMsg){
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}