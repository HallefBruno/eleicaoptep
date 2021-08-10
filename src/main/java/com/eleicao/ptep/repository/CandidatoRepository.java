
package com.eleicao.ptep.repository;

import com.eleicao.ptep.entidade.Candidato;
import com.eleicao.ptep.repository.querys.CandidatoRepositoryCustom;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hallef
 */
@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long>, CandidatoRepositoryCustom {
    List<Candidato> findByEleicao_DataFinalGreaterThanEqual(LocalDate dataAtual);
}
