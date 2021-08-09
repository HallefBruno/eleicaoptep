package com.eleicao.ptep.service;

import com.eleicao.ptep.cloud.StorageCloudnary;
import com.eleicao.ptep.entidade.Candidato;
import com.eleicao.ptep.entidade.dto.FiltroCandidato;
import com.eleicao.ptep.exception.NegocioException;
import com.eleicao.ptep.repository.CandidatoRepository;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.persistence.PersistenceException;
import org.apache.commons.io.FilenameUtils;
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
        String nomeArquivo = "";
        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            candidato.setNomeFoto(fileName.substring(0, fileName.lastIndexOf(".")));
            candidato.setExtensao(extension);
            Candidato novo = candidatoRepository.save(candidato);
            nomeArquivo =  novo.getId().toString()+"-"+candidato.getNomeFoto();
            storageCloudnary.uploadFoto(multipartFile.getBytes(),nomeArquivo);
        } catch(IOException ex) {
            try {
                storageCloudnary.deleteFoto(nomeArquivo);
            } catch (IOException ex1) {
                throw new NegocioException(ex1.getMessage());
            }
            throw new NegocioException(ex.getMessage());
        }
    }
    
    @Transactional
    public void update(Candidato candidato, Long codigo, MultipartFile multipartFile) {
        String filedelete = "";
        if (Objects.isNull(codigo)) {
            throw new NegocioException("Código não pode ser null!");
        }
        Optional<Candidato> opCandidato = candidatoRepository.findById(codigo);
        if (opCandidato.isPresent()) {
            try {
                Candidato atual = opCandidato.get();
                if(!Objects.equals(atual.getVersaoObjeto(), candidato.getVersaoObjeto())) {
                    throw new NegocioException("Erro de concorrência. Essa candidato já foi alterado anteriormente!");
                }
                BeanUtils.copyProperties(candidato, atual, "id", "nomeFoto","extensao");
                
                if(!org.apache.commons.lang3.StringUtils.isBlank(multipartFile.getOriginalFilename())) {
                    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
                    String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
                    filedelete = atual.getNomeFoto();
                    atual.setNomeFoto(fileName.substring(0, fileName.lastIndexOf(".")));
                    atual.setExtensao(extension);
                    String nomeArquivo =  atual.getId().toString()+"-"+atual.getNomeFoto();
                    storageCloudnary.uploadFoto(multipartFile.getBytes(), nomeArquivo);
                }
                candidatoRepository.save(atual);
            } catch (IOException ex) {
                try {
                    storageCloudnary.deleteFoto(filedelete);
                } catch (IOException ex1) {
                    throw new NegocioException(ex1.getMessage());
                }
                throw new NegocioException(ex.getMessage());
            }
        }
    }

    @Transactional
    public void excluir(Candidato candidato) {
        try {
            candidatoRepository.delete(candidato);
            candidatoRepository.flush();
            storageCloudnary.deleteFoto(candidato.getNomeFoto());
        } catch (PersistenceException | IOException e) {
            throw new NegocioException("Impossível excluir o candidato!");
        }
    }
    
    public List<Candidato> buscarCandidatoPor(FiltroCandidato filtroCandidato) {
        return candidatoRepository.buscarCandidatoPor(filtroCandidato);
    }
    
}
