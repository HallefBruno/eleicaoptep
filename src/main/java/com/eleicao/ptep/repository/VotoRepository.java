
package com.eleicao.ptep.repository;

import com.eleicao.ptep.entidade.Eleitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hallef
 */
@Repository
public interface VotoRepository extends JpaRepository<Eleitor, Long> {
    
}
