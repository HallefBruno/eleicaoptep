
package com.eleicao.ptep.relatorio;

import com.eleicao.ptep.relatorio.dto.RelatorioFinaldto;
import com.eleicao.ptep.relatorio.dto.Vontantedto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Component;

@Component
public class RelatorioImpl implements RelatorioRepository {
    
    @PersistenceContext
    private EntityManager manager;
    
    @Override
    public List<RelatorioFinaldto> listRelatorioFinal() {
        List<RelatorioFinaldto> relatorioFinaldtos = new ArrayList<>();
        String sql = "select candidato.nome as candidato, cargo.nome as cargo ,count(ec.candidato_id) as quantidade_votos\n" +
                        "from eleitor\n" +
                        "inner join eleitor_candidato ec on (eleitor.id = ec.eleitor_id)\n" +
                        "inner join candidato on (ec.candidato_id = candidato.id)\n" +
                        "inner join cargo on candidato.cargo_id = cargo.id\n" +
                        "group by candidato.nome,cargo.nome\n"+
                        "order by quantidade_votos desc;";
        Query query  = manager.createNativeQuery(sql);
        List<Object[]> relatorioFInal = query.getResultList();
        for (Object[] resultado : relatorioFInal) {
            RelatorioFinaldto relatorioFinaldto = 
            RelatorioFinaldto.builder().candidato(resultado[0].toString()).cargo(resultado[1].toString()).quantidadeVoto(resultado[2].toString()).build();
            relatorioFinaldtos.add(relatorioFinaldto);
        }
        return relatorioFinaldtos;
    }

    @Override
    public Vontantedto relatorioParcial() {
        String sql = "select count(*) votantes from eleitor;";
        Long quantidadeVotantes = Long.valueOf(manager.createNativeQuery(sql).getSingleResult().toString());
        return Vontantedto.builder().quantidadeVotantes(quantidadeVotantes).build();
    }
    
}
