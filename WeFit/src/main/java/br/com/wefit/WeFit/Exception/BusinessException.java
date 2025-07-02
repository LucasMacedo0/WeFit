package br.com.wefit.WeFit.Exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends BaseException {

    public BusinessException(String titulo, String detalhe, HttpStatus status) {
        super(titulo, detalhe, status);
    }
}