
package com.eleicao.ptep.entidade;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.br.CPF;

/**
 *
 * @author hallef
 */
@Data
@Entity
@EqualsAndHashCode
public class Eleitor implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long Id;
    
    @NotBlank(message = "Nome do(a) votante não pode ter espaços em branco!")
    @NotEmpty(message = "Nome do(a) votante não pode ser vazio!")
    @NotNull(message = "Nome do(a) votante não pode ser null!")
    @Column(nullable = false)
    private String nome;
    
    @CPF
    @NotBlank(message = "CPF do(a) votante não pode ter espaços em branco!")
    @NotEmpty(message = "CPF do(a) votante não pode ser vazio!")
    @NotNull(message = "CPF do(a) votante não pode ser null!")
    @Column(nullable = false, name = "cpf", unique = true)
    private String cpf;
    
    @Column(nullable = false, name = "protocolo", unique = true)
    private String protocolo;
    
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "eleitor_candidato",joinColumns = @JoinColumn(name = "eleitor_id"),inverseJoinColumns = @JoinColumn(name = "candidato_id"))
    private Set<Candidato> candidatos;

    
}
