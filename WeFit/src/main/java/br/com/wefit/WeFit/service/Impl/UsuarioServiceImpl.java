package br.com.wefit.WeFit.service.Impl;

import br.com.wefit.WeFit.domain.Usuario;
import br.com.wefit.WeFit.dto.CadastroUsuarioDTO;
import br.com.wefit.WeFit.exception.PersistenceException;
import br.com.wefit.WeFit.service.UsuarioService;
import br.com.wefit.WeFit.repository.UsuarioRepository;
import br.com.wefit.WeFit.validation.ValidationFields;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;

    private final ObjectMapper objectMapper;

    private final ValidationFields validationFields;

    public void cadastrarUsuario(CadastroUsuarioDTO cadastroUsuarioDTO) {
        validationFields.validate(cadastroUsuarioDTO);
        try {
            repository.save(objectMapper.convertValue(cadastroUsuarioDTO, Usuario.class));
        } catch (Exception ex) {
            log.error("não foi possivel salvar o usuário no banco");
            throw new PersistenceException(
                    "Erro ao salvar usuário",
                    "Não foi possível persistir os dados do usuário.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
