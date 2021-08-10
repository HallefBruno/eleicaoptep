
package com.eleicao.ptep.entidade;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author hallef
 */
@Data
@Entity
@DynamicUpdate
@EqualsAndHashCode
public class Candidato implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long Id;
    
    @NotBlank(message = "Nome do candidato não pode ter espaços em branco!")
    @NotEmpty(message = "Nome do candidato não pode ser vazio!")
    @NotNull(message = "Nome do candidato não pode ser null!")
    @Column(nullable = false, unique = true)
    private String nome;
    
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Cargo cargo;
    
    @JoinColumn(nullable = false)
    @ManyToOne(fetch = FetchType.EAGER)
    private Eleicao eleicao;
    
    @Column(nullable = false, name = "nome_foto", unique = true)
    private String nomeFoto;
    
    @Column(nullable = false)
    private String extensao;
    
    @Version
    @Column(name = "versao_objeto", nullable = false)
    private Integer versaoObjeto;

    @PrePersist
    @PreUpdate
    private void prePersistPreUpdate() {
        this.nome = StringUtils.strip(this.nome);
    }
}
