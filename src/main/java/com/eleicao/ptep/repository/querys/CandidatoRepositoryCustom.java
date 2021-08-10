
package com.eleicao.ptep.repository.querys;

import com.eleicao.ptep.entidade.Candidato;
import com.eleicao.ptep.entidade.dto.FiltroCandidato;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hallef
 */
public interface CandidatoRepositoryCustom {
    List<Candidato> buscarCandidatoPor(FiltroCandidato filtroCandidato);
    Map<String, List<Candidato>> mapCandidatosPorCargo();
}
