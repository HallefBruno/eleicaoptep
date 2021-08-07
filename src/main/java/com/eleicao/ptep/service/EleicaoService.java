
package com.eleicao.ptep.service;

import com.eleicao.ptep.entidade.Eleicao;
import com.eleicao.ptep.repository.EleicaoRepository;
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
    public void salvarEleicao(Eleicao eleicao) {
        eleicaoRepository.save(eleicao);
    }
    
}
