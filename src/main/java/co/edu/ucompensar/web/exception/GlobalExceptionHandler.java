package co.edu.ucompensar.web.exception;

import org.springframework.web.ErrorResponse;

import java.util.List;

public class GlobalExceptionHandler {
    private int status;
    private String error;
    private String mensaje;
    private List<String> campos;

    public GlobalExceptionHandler(int status, String error, String mensaje){
        this.status = status;
        this.error = error;
        this.mensaje = mensaje;
    }

    public GlobalExceptionHandler(int status, String error, String mensaje, List<String> campos){
        this.status = status;
        this.error = error;
        this.mensaje = mensaje;
        this.campos = campos;
    }


    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public List<String> getCampos() {
        return campos;
    }
}
