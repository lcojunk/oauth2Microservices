package leo.demo.democreate.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by odzhara-ongom on 01.02.2016.
 */
public class RestResponse<T, V> {

    private T result;
    private V request;
    private boolean success;
    private String error;
    private List<Exception> exceptions = new ArrayList<>();

    public RestResponse() {
    }

    public RestResponse(V request) {
        this.request = request;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public V getRequest() {
        return request;
    }

    public void setRequest(V request) {
        this.request = request;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
