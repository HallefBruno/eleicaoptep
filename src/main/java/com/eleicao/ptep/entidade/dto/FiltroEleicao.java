
package com.eleicao.ptep.entidade.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author hallef
 */
@Data
@Builder
public class FiltroEleicao {
    
    private String nomeDaEleicao;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate iniciaEm;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finalizaEm;

    public FiltroEleicao() {
    }

    public FiltroEleicao(String nomeDaEleicao, LocalDate iniciaEm, LocalDate finalizaEm) {
        this.nomeDaEleicao = nomeDaEleicao;
        this.iniciaEm = iniciaEm;
        this.finalizaEm = finalizaEm;
    }
    
}
