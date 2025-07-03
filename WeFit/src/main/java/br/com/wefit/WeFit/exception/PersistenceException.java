package br.com.wefit.WeFit.exception;

import org.springframework.http.HttpStatus;

public class PersistenceException extends BaseException{

    public PersistenceException(String titulo, String detalhe, HttpStatus status) {
        super(titulo, detalhe, status);
    }
}
