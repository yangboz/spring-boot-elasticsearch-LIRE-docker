package info.smartkit.eip.obtuse_octo_prune.VOs;

import org.springframework.http.HttpStatus;

/**
 * Created by smartkit on 2016/11/13.
 */
public class HttpResponseVO extends Throwable {
    private int  statusCode = 200;
    private String body="SUCCESS";

    public HttpResponseVO(int httpStatus, String body) {
        this.statusCode = httpStatus;
        this.body = body;
    }

    public HttpResponseVO() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
