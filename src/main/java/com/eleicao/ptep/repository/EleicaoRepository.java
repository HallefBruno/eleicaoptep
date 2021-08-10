
package com.eleicao.ptep.repository;

import com.eleicao.ptep.entidade.Eleicao;
import com.eleicao.ptep.repository.querys.EleicaoRepositoryCustom;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hallef
 */
@Repository
public interface EleicaoRepository extends JpaRepository<Eleicao, Long>, EleicaoRepositoryCustom {
    List<Eleicao> findByDataFinalGreaterThanEqual(LocalDate atual);
}
