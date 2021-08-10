
package com.eleicao.ptep.service;

import com.eleicao.ptep.entidade.Candidato;
import com.eleicao.ptep.repository.CandidatoRepository;
import com.eleicao.ptep.repository.EleicaoRepository;
import com.eleicao.ptep.repository.VotoRepository;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hallef
 */
@Service
public class VotoService {
    
    @Autowired
    private VotoRepository votoRepository;
    
    @Autowired
    private EleicaoRepository eleicaoRepository;
    
    @Autowired
    private CandidatoRepository candidatoRepository;
    
    public Map<String, List<Candidato>> mapCandidatosPorCargo() {
        return candidatoRepository.mapCandidatosPorCargo();
    }
    
    public void teste() {
        
    }
    
}
