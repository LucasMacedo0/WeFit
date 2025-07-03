package br.com.wefit.WeFit.validation.Impl;


import br.com.wefit.WeFit.dto.CadastroUsuarioDTO;
import br.com.wefit.WeFit.exception.BusinessException;
import br.com.wefit.WeFit.repository.UsuarioRepository;
import br.com.wefit.WeFit.validation.ValidationFields;
import ch.qos.logback.core.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ValidationFieldsImpl implements ValidationFields {

    @Autowired
    private UsuarioRepository repository;

    public void validate(CadastroUsuarioDTO dto) {
        validateCpf(dto);
        validateCnpj(dto);
        validateTipoPessoa(dto);
        validateEmail(dto);
        validateFieldsNull(dto);
        validateTermos(dto);
    }

    private void validateTermos(CadastroUsuarioDTO dto) {
        if (!dto.isTermosAceitos()) {
            throw new BusinessException(
                    "Aceite dos termos obrigatório",
                    "O cadastro só pode ser realizado se os termos de uso forem aceitos.",
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }
    }

    private void validateFieldsNull(CadastroUsuarioDTO dto) {
        if (StringUtil.isNullOrEmpty(dto.getComplemento())) {
            dto.setComplemento("N/A");
        }
        if (StringUtil.isNullOrEmpty(dto.getTelefone())) {
            dto.setTelefone("N/A");
        }
    }

    private void validateEmail(CadastroUsuarioDTO dto) {
        if (!dto.getEmail().equalsIgnoreCase(dto.getConfirmarEmail())) {
            throw new BusinessException(
                    "Confirmação de e-mail inválida",
                    "Os campos de e-mail e confirmação de e-mail devem ser iguais.",
                    HttpStatus.UNPROCESSABLE_ENTITY
            );
        }
    }

    private void validateTipoPessoa(CadastroUsuarioDTO dto) {
        if ("PESSOA_FISICA".equalsIgnoreCase(dto.getTipoPessoa())) {
            if (StringUtil.isNullOrEmpty(dto.getCpf())) {
                throw new BusinessException(
                        "CPF obrigatório",
                        "Para pessoa física, o CPF deve ser informado.",
                        HttpStatus.UNPROCESSABLE_ENTITY);

            }
            if (!StringUtil.isNullOrEmpty(dto.getCnpj())) {
                throw new BusinessException(
                        "CNPJ não permitido",
                        "Para pessoa física, o campo CNPJ não deve ser informado.",
                        HttpStatus.UNPROCESSABLE_ENTITY
                );
            }

        }
        if ("PESSOA_JURIDICA".equalsIgnoreCase(dto.getTipoPessoa())) {
            if (StringUtil.isNullOrEmpty(dto.getCnpj())) {
                throw new BusinessException(
                        "CNPJ obrigatório",
                        "Para pessoa jurídica, o CNPJ deve ser informado.",
                        HttpStatus.UNPROCESSABLE_ENTITY
                );
            }
            if (StringUtil.isNullOrEmpty(dto.getCpf())) {
                throw new BusinessException(
                        "CPF do responsável obrigatório",
                        "Para pessoa jurídica, é necessário informar o CPF do responsável.",
                        HttpStatus.UNPROCESSABLE_ENTITY
                );
            }
        }
    }

    private void validateCpf(CadastroUsuarioDTO dto) {
        if ("PESSOA_FISICA".equalsIgnoreCase(dto.getTipoPessoa())) {
            repository.findByCpf(dto.getCpf()).ifPresent(u -> {
                log.error("Tentativa de cadastro com CPF duplicado");
                throw new BusinessException(
                        "Usuário já cadastrado",
                        "Já existe um usuário registrado com o CPF informado. Verifique os dados antes de prosseguir.",
                        HttpStatus.CONFLICT
                );
            });
        }
    }

    private void validateCnpj(CadastroUsuarioDTO dto) {
        if ("PESSOA_JURIDICA".equalsIgnoreCase(dto.getTipoPessoa())) {
            repository.findByCnpj(dto.getCnpj()).ifPresent(u -> {
                log.error("Tentativa de cadastro com CNPJ duplicado");
                throw new BusinessException(
                        "Empresa já cadastrada",
                        "Já existe um usuário registrado com o CNPJ informado. Verifique se os dados estão corretos ou se a empresa já possui cadastro.",
                        HttpStatus.CONFLICT
                );
            });
            repository.findByCpf(dto.getCpf()).ifPresent(u -> {
                log.error("CPF do responsável já está vinculado a outro cadastro.");
                throw new BusinessException(
                        "CPF Responsavel já cadastrado",
                        "Este CPF já está cadastrado. Verifique se o responsável já possui um cadastro anterior ou se os dados foram preenchidos corretamente.",
                        HttpStatus.CONFLICT
                );
            });
        }

    }
}