
package com.eleicao.ptep.service;

import com.eleicao.ptep.entidade.Eleitor;
import com.eleicao.ptep.exception.NegocioException;
import com.eleicao.ptep.repository.CandidatoRepository;
import com.eleicao.ptep.repository.EleitorRepository;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hallef
 */
@Service
public class EleitorService {
    
    @Autowired
    private EleitorRepository eleitorRepository;
    
    @Autowired
    private CandidatoRepository candidatoRepository;
    
    @Transactional
    public String salvar(Eleitor eleitor) {
        String cpf = StringUtils.getDigits(eleitor.getCpf());
        Optional<Eleitor> opEleitor = eleitorRepository.findByCpf(cpf);
        if(opEleitor.isPresent()) {
            throw new NegocioException("Eleitor com voto contabilizado!");
        }
        
        if(candidatoRepository.mapCandidatosPorCargo().keySet().size() > eleitor.getCandidatos().size()) {
            throw new NegocioException("É necessário selecionar um cadidato de cada cargo!");
        }

        String generatedString = RandomStringUtils.randomAlphanumeric(16);
        String protocolo = generatedString.replaceAll("(.{4})(?!$)", "$1-");
        eleitor.setProtocolo(protocolo);
        eleitorRepository.save(eleitor);
        return protocolo;
    }

}
