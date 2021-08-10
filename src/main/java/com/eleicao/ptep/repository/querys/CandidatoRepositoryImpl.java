
package com.eleicao.ptep.repository.querys;

import com.eleicao.ptep.entidade.Candidato;
import com.eleicao.ptep.entidade.Cargo;
import com.eleicao.ptep.entidade.Eleicao;
import com.eleicao.ptep.entidade.dto.FiltroCandidato;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author hallef
 */
public class CandidatoRepositoryImpl implements CandidatoRepositoryCustom {
    
    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Candidato> buscarCandidatoPor(FiltroCandidato filtroCandidato) {
        
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Candidato> query = cb.createQuery(Candidato.class);
        Root<Candidato> rootEleicao = query.from(Candidato.class);
        Join<Candidato, Cargo> cargo = (Join) rootEleicao.fetch("cargo", JoinType.LEFT);
        Join<Candidato, Eleicao> eleicao = (Join) rootEleicao.fetch("eleicao", JoinType.LEFT);
        List<Predicate> predicates = new ArrayList<>();
        
        if(!StringUtils.isBlank(filtroCandidato.getNomeDoCandidato())) {
            Predicate pdNome = cb.like(cb.upper(rootEleicao.get("nome")), "%" + filtroCandidato.getNomeDoCandidato().toUpperCase() + "%");
            predicates.add(pdNome);
        }
        
        if(!StringUtils.isBlank(filtroCandidato.getCargo())) {
            Predicate pdCargo = cb.equal(cargo.get("nome"),filtroCandidato.getCargo());
            predicates.add(pdCargo);
        }
        
        if(!StringUtils.isBlank(filtroCandidato.getEleicao())) {
            Predicate pdEleicao = cb.equal(eleicao.get("nome"),filtroCandidato.getEleicao());
            predicates.add(pdEleicao);
        }
        
        query.select(rootEleicao);
        query.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Candidato> typedQuery = manager.createQuery(query);
        
        return typedQuery.getResultList();
    }

    @Override
    public Map<String, List<Candidato>> mapCandidatosPorCargo() {
        Map<String,List<Candidato>> mapCandidados = new LinkedHashMap<>();
        TypedQuery<Candidato> query = manager.createQuery("SELECT c FROM Candidato c WHERE c.eleicao.dataFinal >= :dataAtual", Candidato.class);
        query.setParameter("dataAtual", LocalDate.now());
        List<Candidato> candidatos = query.getResultList();
        List<Candidato> listaCandidatosPorCargo = new LinkedList<>();
        boolean novaKey;
        
        for(int i = 0; i < candidatos.size(); i++) {
            String nomeCargo = candidatos.get(i).getCargo().getNome();
            listaCandidatosPorCargo.add(candidatos.get(i));
            novaKey = true;
            for(int j = 0; j < candidatos.size(); j++) {
                if(nomeCargo.equalsIgnoreCase(candidatos.get(j).getCargo().getNome())) {
                    if(!listaCandidatosPorCargo.contains(candidatos.get(j))) {
                        listaCandidatosPorCargo.add(candidatos.get(j));
                        mapCandidados.put(nomeCargo, listaCandidatosPorCargo);
                        novaKey = false;
                    }
                }
            }
            if(novaKey) {
                mapCandidados.put(nomeCargo, listaCandidatosPorCargo);
            }
            listaCandidatosPorCargo = new ArrayList<>();
        }
        return mapCandidados;
    }
    
    

}
