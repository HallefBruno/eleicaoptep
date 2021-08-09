
package com.eleicao.ptep.repository.querys;

import com.eleicao.ptep.entidade.Candidato;
import com.eleicao.ptep.entidade.Cargo;
import com.eleicao.ptep.entidade.dto.FiltroCandidato;
import java.util.ArrayList;
import java.util.List;
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
        List<Predicate> predicates = new ArrayList<>();
        
        if(!StringUtils.isBlank(filtroCandidato.getNomeDoCandidato())) {
            Predicate pdNome = cb.like(cb.upper(rootEleicao.get("nome")), "%" + filtroCandidato.getNomeDoCandidato().toUpperCase() + "%");
            predicates.add(pdNome);
        }
        
        if(!StringUtils.isBlank(filtroCandidato.getCargo())) {
            Predicate pdCargo = cb.equal(cargo.get("nome"),filtroCandidato.getCargo());
            predicates.add(pdCargo);
        }
        
        query.select(rootEleicao);
        query.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Candidato> typedQuery = manager.createQuery(query);
        
        return typedQuery.getResultList();
    }

}
