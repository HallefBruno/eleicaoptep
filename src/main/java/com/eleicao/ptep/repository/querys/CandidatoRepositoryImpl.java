
package com.eleicao.ptep.repository.querys;

import com.eleicao.ptep.entidade.Candidato;
import com.eleicao.ptep.entidade.Cargo;
import com.eleicao.ptep.entidade.dto.FiltroCandidato;
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
        Root<Candidato> root = query.from(Candidato.class);
        Join<Candidato, Cargo> cargo = (Join) root.fetch("cargo", JoinType.LEFT);
        List<Predicate> predicates = new ArrayList<>();
        
        if(!StringUtils.isBlank(filtroCandidato.getNomeDoCandidato())) {
            Predicate pdNome = cb.like(cb.upper(root.get("nome")), "%" + filtroCandidato.getNomeDoCandidato().toUpperCase() + "%");
            predicates.add(pdNome);
        }
        
        if(!StringUtils.isBlank(filtroCandidato.getCargo())) {
            Predicate pdCargo = cb.equal(cargo.get("nome"),filtroCandidato.getCargo());
            predicates.add(pdCargo);
        }

        query.select(root);
        query.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Candidato> typedQuery = manager.createQuery(query);
        
        return typedQuery.getResultList();
    }

    @Override
    public Map<String, List<Candidato>> mapCandidatosPorCargo() {
        Map<String,List<Candidato>> mapCandidados = new LinkedHashMap<>();
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Candidato> q = cb.createQuery(Candidato.class);
        Root<Candidato> candidato = q.from(Candidato.class);
        candidato.fetch("cargo");
        q.select(candidato);
        TypedQuery<Candidato> query = manager.createQuery(q);
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
