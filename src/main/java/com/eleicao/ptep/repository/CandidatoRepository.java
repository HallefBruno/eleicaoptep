
package com.eleicao.ptep.repository;

import com.eleicao.ptep.entidade.Candidato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hallef
 */
@Repository
public interface CandidatoRepository extends JpaRepository<Candidato, Long> {
    
}
