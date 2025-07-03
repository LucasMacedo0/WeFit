package br.com.wefit.WeFit.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class CustomHandleException {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> handleBaseException(BaseException ex) {
        var response = buildErrorResponse(
                ex,
                ex.getDetalhe(),
                ex.getTitulo(),
                ex.getStatus(),
                null
        );
        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.toList());

        var response = buildErrorResponse(
                ex,
                "Erros de validação nos campos enviados.",
                "Erro de validação",
                HttpStatus.UNPROCESSABLE_ENTITY,
                fieldErrors
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        String message = String.format("Método HTTP %s não permitido para esse endpoint", ex.getMethod());

        var response = buildErrorResponse(
                ex,
                message,
                "Método não suportado",
                HttpStatus.METHOD_NOT_ALLOWED,
                null
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        var response = buildErrorResponse(
                ex,
                "O corpo da requisição está inválido. Verifique se o JSON está correto.",
                "Erro ao enviar requisição",
                HttpStatus.BAD_REQUEST,
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpectedError(Exception ex) {
        log.error("Erro não tratado:", ex);

        var response = buildErrorResponse(
                ex,
                "Erro interno",
                "Falha ao processar requisição.",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    private ErrorResponse buildErrorResponse(Exception ex, String message, String title, HttpStatus status, List<String> fieldErrors) {
        log.error("[{} - {}] {}", title, status.value(), message, ex);

        return ErrorResponse.builder()
                .titulo(title)
                .detalhe(message)
                .status(status.value())
                .errors(fieldErrors)
                .build();
    }
}
