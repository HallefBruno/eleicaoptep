
package com.eleicao.ptep.service;

import com.eleicao.ptep.entidade.Eleicao;
import com.eleicao.ptep.entidade.dto.FiltroEleicao;
import com.eleicao.ptep.exception.NegocioException;
import com.eleicao.ptep.repository.EleicaoRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.PersistenceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hallef
 */
@Service
public class EleicaoService {
    
    @Autowired
    private EleicaoRepository eleicaoRepository;
    
    @Transactional
    public void salvar(Eleicao eleicao) {
        eleicaoRepository.save(eleicao);
    }
    
    @Transactional
    public void update(Eleicao eleicao, Long codigo) {
        if (Objects.isNull(codigo)) {
            throw new NegocioException("Código não pode ser null!");
        }
        Optional<Eleicao> opEleicao = eleicaoRepository.findById(codigo);
        if (opEleicao.isPresent()) {
            Eleicao atual = opEleicao.get();
            if(!Objects.equals(atual.getVersaoObjeto(), eleicao.getVersaoObjeto())) {
                throw new NegocioException("Erro de concorrência. Essa eleição já foi alterado anteriormente.");
            }
            BeanUtils.copyProperties(eleicao, atual, "id");
            eleicaoRepository.save(atual);
        }
    }

    @Transactional
    public void excluir(Eleicao eleicao) {
        try {
            eleicaoRepository.delete(eleicao);
            eleicaoRepository.flush();
        } catch (PersistenceException e) {
            throw new NegocioException("Impossível excluir a eleição");
        }
    }

    public List<Eleicao> buscarElecaoPor(FiltroEleicao filtroEleicao) {
        return eleicaoRepository.buscarEleicoesPor(filtroEleicao);
    }
    
    public List<Eleicao> eleicoesValidas() {
        return eleicaoRepository.findFirstByDataFinalGreaterThanEqualOrderByDataFinalAsc(LocalDate.now());
    }
}
