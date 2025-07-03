package br.com.wefit.WeFit.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
public class CadastroUsuarioDTO {

    @NotBlank(message = "O tipo de pessoa é obrigatório")
    @Pattern(regexp = "PESSOA_FISICA|PESSOA_JURIDICA", message = "Tipo de pessoa deve ser 'PESSOA_FISICA' ou 'PESSOA_JURIDICA'")
    private String tipoPessoa;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Celular é obrigatório")
    @Pattern(regexp = "^\\d{2}9\\d{8}$")
    private String celular;

    private String telefone;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Confirmação de email é obrigatória")
    @Email(message = "Confirmação de email inválida")
    private String confirmarEmail;

    @CPF(message = "CPF inválido. O valor deve conter 11 dígitos numéricos e estar no formato válido.")
    private String cpf;

    @CNPJ(message = "CNPJ inválido. Certifique-se de informar os 14 números corretamente.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cnpj;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "^\\d{5}-?\\d{3}$")
    private String cep;

    @NotBlank(message = "Logradouro é obrigatório")
    private String logradouro;

    @NotBlank(message = "Número é obrigatório")
    private String numero;

    private String complemento;

    @NotBlank(message = "Cidade é obrigatória")
    private String cidade;

    @NotBlank(message = "Bairro é obrigatório")
    private String bairro;

    @NotBlank(message = "Estado é obrigatório")
    private String estado;

    private boolean termosAceitos;

}
