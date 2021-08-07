
package com.eleicao.ptep.entidade;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicUpdate;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author hallef
 */
@Data
@Entity
@DynamicUpdate
@EqualsAndHashCode
public class Eleicao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long Id;
    
    @NotBlank(message = "Nome da eleição não pode ter espaços em branco!")
    @NotEmpty(message = "Nome da eleição não pode ser vazio!")
    @NotNull(message = "Nome da eleição não pode ser null!")
    @Column(unique = true, nullable = false)
    private String nome;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Data de inicio é obrigatória!")
    @Column(nullable = false, name = "data_inicio")
    private LocalDate dataInicio;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Data final é obrigatória!")
    @Column(nullable = false, name = "data_final")
    private LocalDate dataFinal;
    
    @Version
    @Column(name = "versao_objeto", nullable = false)
    private Integer versaoObjeto;
    
    @PrePersist
    @PreUpdate
    private void prePersistPreUpdate() {
        this.nome = StringUtils.strip(this.nome);
    }
}
