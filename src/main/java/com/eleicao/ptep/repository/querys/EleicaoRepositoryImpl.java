package com.eleicao.ptep.repository.querys;

import com.eleicao.ptep.entidade.Eleicao;
import com.eleicao.ptep.entidade.dto.FiltroEleicao;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author hallef
 */
public class EleicaoRepositoryImpl implements EleicaoRepositoryCustom {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Eleicao> buscarEleicaoPor(FiltroEleicao filtroEleicao) {

        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Eleicao> query = cb.createQuery(Eleicao.class);
        Root<Eleicao> rootEleicao = query.from(Eleicao.class);
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isBlank(filtroEleicao.getNomeDaEleicao())) {
            Predicate pdNome = cb.like(cb.upper(rootEleicao.get("nome")), "%" + filtroEleicao.getNomeDaEleicao().toUpperCase() + "%");
            predicates.add(pdNome);
        }

        if (Objects.nonNull(filtroEleicao.getIniciaEm())) {
            Predicate pdDataInicio = cb.greaterThanOrEqualTo(rootEleicao.get("dataInicio"), filtroEleicao.getIniciaEm());
            predicates.add(pdDataInicio);
        }

        if (Objects.nonNull(filtroEleicao.getFinalizaEm())) {
            Predicate pdDataFinal = cb.lessThanOrEqualTo(rootEleicao.get("dataFinal"), filtroEleicao.getFinalizaEm());
            predicates.add(pdDataFinal);
        }
        query.select(rootEleicao);
        query.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Eleicao> typedQuery = manager.createQuery(query);
        return typedQuery.getResultList();
    }

}
