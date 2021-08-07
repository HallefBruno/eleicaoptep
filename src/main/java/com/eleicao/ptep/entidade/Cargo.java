
package com.eleicao.ptep.entidade;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
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
public class Cargo implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long Id;
    
    @NotBlank(message = "Nome do cargo não pode ter espaços em branco!")
    @NotEmpty(message = "Nome do cargo não pode ser vazio!")
    @NotNull(message = "Nome do cargo não pode ser null!")
    @Column(unique = true, nullable = false)
    private String nome;
    
    @Version
    @Column(name = "versao_objeto", nullable = false)
    private Integer versaoObjeto;
    
    @PrePersist
    @PreUpdate
    private void prePersistPreUpdate() {
        this.nome = StringUtils.strip(this.nome);
    }
    
}
