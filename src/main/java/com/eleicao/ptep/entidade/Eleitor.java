
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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

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
    
    @NotBlank(message = "CPF do(a) votante não pode ter espaços em branco!")
    @NotEmpty(message = "CPF do(a) votante não pode ser vazio!")
    @NotNull(message = "CPF do(a) votante não pode ser null!")
    @Column(nullable = false, name = "cpf")
    private String cpf;
    
    @Column(nullable = false, name = "protocolo")
    private String protocolo;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "eleitor_candidato",joinColumns = @JoinColumn(name = "eleitor_id"),inverseJoinColumns = @JoinColumn(name = "candidato_id"))
    private Set<Candidato> candidatos;

    @PrePersist
    @PreUpdate
    private void prePersistPreUpdate() {
        this.nome = StringUtils.strip(this.nome);
        this.cpf = StringUtils.getDigits(this.cpf);
    }
}
