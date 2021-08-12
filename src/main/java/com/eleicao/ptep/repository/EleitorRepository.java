package com.eleicao.ptep.repository;

import com.eleicao.ptep.entidade.Eleitor;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author halle
 */
@Repository
public interface EleitorRepository extends JpaRepository<Eleitor, Long>{
    Optional<Eleitor> findByCpf(String cpf);
}
