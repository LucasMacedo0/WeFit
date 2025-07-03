package br.com.wefit.WeFit.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String titulo;
    private String detalhe;
    private int status;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<String> errors;


}
