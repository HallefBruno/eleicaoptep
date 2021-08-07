
package com.eleicao.ptep.repository;

import com.eleicao.ptep.entidade.Eleicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hallef
 */
@Repository
public interface EleicaoRepository extends JpaRepository<Eleicao, Long> {
    
}
