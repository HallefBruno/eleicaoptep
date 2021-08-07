
package com.eleicao.ptep.entidade;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author hallef
 */
@Data
@Entity
@EqualsAndHashCode
public class Candidato implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long Id;
    
    //Poderia ser configurado como unique
    @NotBlank(message = "Nome do candidato não pode ter espaços em branco!")
    @NotEmpty(message = "Nome do candidato não pode ser vazio!")
    @NotNull(message = "Nome do candidato não pode ser null!")
    @Column(nullable = false)
    private String nome;
    
    @JoinColumn
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Cargo cargo;
    
    @Version
    @Column(name = "versao_objeto", nullable = false)
    private Integer versaoObjeto;
    
}