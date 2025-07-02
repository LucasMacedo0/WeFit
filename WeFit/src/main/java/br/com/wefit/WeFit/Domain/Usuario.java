package br.com.wefit.WeFit.Domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipoPessoa;
    private String nome;
    private String celular;
    private String telefone;
    private String email;
    private String confirmarEmail;
    private String cpf;
    private String cnpj;
    private String cpfResponsavel;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String cidade;
    private String bairro;
    private String estado;
    private boolean termosAceitos;
}
