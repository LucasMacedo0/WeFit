package br.com.wefit.WeFit.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BaseException extends RuntimeException{

    private final String titulo;
    private final String detalhe;
    private final HttpStatus status;

    public BaseException(String titulo, String detalhe, HttpStatus status) {
        super(detalhe);
        this.titulo = titulo;
        this.detalhe = detalhe;
        this.status = status;
    }

}
