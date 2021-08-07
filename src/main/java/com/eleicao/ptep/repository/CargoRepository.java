
package com.eleicao.ptep.repository;

import com.eleicao.ptep.entidade.Cargo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author hallef
 */
@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
    List<Cargo> findByNomeContainingIgnoreCase(String nome);
}
