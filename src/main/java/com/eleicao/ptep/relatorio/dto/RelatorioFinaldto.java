
package com.eleicao.ptep.relatorio.dto;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author hallef
 */
@Data
@Builder
public class RelatorioFinaldto {
    
    private String candidato;
    private String cargo;
    private String quantidadeVoto;
    
}
