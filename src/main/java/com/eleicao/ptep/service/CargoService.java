
package com.eleicao.ptep.service;

import com.eleicao.ptep.entidade.Cargo;
import com.eleicao.ptep.entidade.dto.FiltroCargo;
import com.eleicao.ptep.exception.NegocioException;
import com.eleicao.ptep.repository.CargoRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.PersistenceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hallef
 */
@Repository
public class CargoService {
    
    @Autowired
    private CargoRepository cargoRepository;
    
    @Transactional
    public void salvar(Cargo cargo) {
        cargoRepository.save(cargo);
    }
    
    public List<Cargo> filtrarPorNome(FiltroCargo filtroCargo) {
        return cargoRepository.findByNomeContainingIgnoreCase(filtroCargo.getNomeDoCargo().toUpperCase());
    }
    
    @Transactional
    public void update(Cargo cargo, Long codigo) {
        if (Objects.isNull(codigo)) {
            throw new NegocioException("Código não pode ser null!");
        }
        Optional<Cargo> opCargo = cargoRepository.findById(codigo);
        if (opCargo.isPresent()) {
            Cargo atual = opCargo.get();
            if(!Objects.equals(atual.getVersaoObjeto(), cargo.getVersaoObjeto())) {
                throw new NegocioException("Erro de concorrência. Essa cargo já foi alterado anteriormente.");
            }
            BeanUtils.copyProperties(cargo, atual, "id");
            cargoRepository.save(atual);
        }
    }

    @Transactional
    public void excluir(Cargo cargo) {
        try {
            cargoRepository.delete(cargo);
            cargoRepository.flush();
        } catch (PersistenceException e) {
            throw new NegocioException("Impossível excluir a eleição");
        }
    }
    
}
