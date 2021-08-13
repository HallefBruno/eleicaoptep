
package com.eleicao.ptep.repository.querys;

import com.eleicao.ptep.entidade.Eleicao;
import com.eleicao.ptep.entidade.dto.FiltroEleicao;
import java.util.List;

/**
 *
 * @author hallef
 */
public interface EleicaoRepositoryCustom {
    List<Eleicao> buscarEleicoesPor(FiltroEleicao filtroEleicao);
    Eleicao findByEleicaoOrderByDataFinalAsc();
}
