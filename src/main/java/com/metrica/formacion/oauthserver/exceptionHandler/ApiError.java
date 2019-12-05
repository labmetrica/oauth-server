package com.metrica.formacion.oauthserver.exceptionHandler;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@JsonTypeIdResolver(LowerCaseClassName.class)
@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM,
        property = "error", visible = true)
public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime fecha;
    private String mensaje;
    private String exceptionMessage;

    public ApiError(HttpStatus status) {
        this.status = status;
        this.fecha = LocalDateTime.now();
    }

    public ApiError(HttpStatus status, String mensaje) {
        this.status = status;
        this.fecha = LocalDateTime.now();
        this.mensaje = mensaje;
    }

    public ApiError(HttpStatus status, String mensaje, String exceptionMessage) {
        this.status = status;
        this.fecha = LocalDateTime.now();
        this.mensaje = mensaje;
        this.exceptionMessage = exceptionMessage;
    }
}
