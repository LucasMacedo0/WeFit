package br.com.wefit.WeFit.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class CustomHandleException {

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<Map<String, Object>> handlePersistencia(PersistenceException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("titulo", ex.getTitulo());
        body.put("detalhe", ex.getDetalhe());
        body.put("status", ex.getStatus().value());
        body.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(ex.getStatus()).body(body);
    }

}
