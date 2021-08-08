package com.eleicao.ptep.service;

import com.eleicao.ptep.coud.StorageCloudnary;
import com.eleicao.ptep.entidade.Candidato;
import com.eleicao.ptep.exception.NegocioException;
import com.eleicao.ptep.repository.CandidatoRepository;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.PersistenceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author hallef
 */
@Service
public class CandidatoService {
    
    @Autowired
    private CandidatoRepository candidatoRepository;
    
    @Autowired
    private StorageCloudnary storageCloudnary;
    
    @Transactional
    public void salvar(Candidato candidato, MultipartFile multipartFile) {
        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            System.out.println(fileName+" "+multipartFile.getContentType());
            candidato.setNomeFoto(fileName);
            Candidato novo = candidatoRepository.save(candidato);
            storageCloudnary.uploadFoto(multipartFile.getBytes(), novo.getId());
        } catch(IOException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }
    
    @Transactional
    public void update(Candidato candidato, Long codigo) {
        if (Objects.isNull(codigo)) {
            throw new NegocioException("Código não pode ser null!");
        }
        Optional<Candidato> opCandidato = candidatoRepository.findById(codigo);
        if (opCandidato.isPresent()) {
            Candidato atual = opCandidato.get();
            if(!Objects.equals(atual.getVersaoObjeto(), candidato.getVersaoObjeto())) {
                throw new NegocioException("Erro de concorrência. Essa candidato já foi alterado anteriormente!");
            }
            BeanUtils.copyProperties(candidato, atual, "id");
            candidatoRepository.save(atual);
        }
    }

    @Transactional
    public void excluir(Candidato candidato) {
        try {
            candidatoRepository.delete(candidato);
            candidatoRepository.flush();
        } catch (PersistenceException e) {
            throw new NegocioException("Impossível excluir o candidato!");
        }
    }
    
}
