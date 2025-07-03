package br.com.wefit.WeFit.controller;

import br.com.wefit.WeFit.dto.CadastroUsuarioDTO;
import br.com.wefit.WeFit.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cadastro")
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    public ResponseEntity<CadastroUsuarioDTO> cadastrarUsuario(@RequestBody @Valid CadastroUsuarioDTO cadastroUsuarioDTO) {
        service.cadastrarUsuario(cadastroUsuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cadastroUsuarioDTO);
    }
}
