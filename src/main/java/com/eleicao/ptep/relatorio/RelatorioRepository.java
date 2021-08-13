
package com.eleicao.ptep.relatorio;

import com.eleicao.ptep.relatorio.dto.RelatorioFinaldto;
import com.eleicao.ptep.relatorio.dto.Vontantedto;
import java.util.List;

/**
 *
 * @author hallef
 */
public interface RelatorioRepository {
    
    List<RelatorioFinaldto> listRelatorioFinal();
    Vontantedto relatorioParcial();
    
}
