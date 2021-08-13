
package com.eleicao.ptep.controller;

import com.eleicao.ptep.entidade.Eleicao;
import com.eleicao.ptep.exception.NegocioException;
import com.eleicao.ptep.relatorio.RelatorioService;
import com.eleicao.ptep.service.EleicaoService;
import java.io.IOException;
import java.time.LocalDate;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author hallef
 */
@Controller
public class RelatorioController {
    
    @Autowired
    private RelatorioService relatorioService;
    
    @Autowired
    private EleicaoService eleicaoService;
    
    @RequestMapping(method = RequestMethod.GET, path = {"/rel/final"})
    public void exportFinalCSV(HttpServletResponse response) {
        try {
            Eleicao eleicao = eleicaoService.eleicaoAtaul();
            if(eleicao != null && LocalDate.now().isAfter(eleicao.getDataFinal())) {
                relatorioService.gerarFinal(response);
            } else if (eleicao == null) {
                throw new NegocioException("Nenuhma eleição cadastrada!");
            } else {
                throw new NegocioException("Permitido para download somente após finalização da eleição!");
            }
        } catch (IOException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, path = {"/rel/parcial"})
    public void exportParcialCSV(HttpServletResponse response) {
        try {
            Eleicao eleicao = eleicaoService.eleicaoAtaul();
            if(eleicao != null) {
                if(LocalDate.now().isBefore(eleicao.getDataFinal()) || LocalDate.now().isEqual(eleicao.getDataFinal())) {
                    relatorioService.gerarParcial(response);
                } else {
                    throw new NegocioException("A votação foi finalizada, não é permitido emitir relatório parcial!");
                }
            } else if (eleicao == null) {
                throw new NegocioException("Nenuhma eleição cadastrada!");
            } 
        } catch (IOException ex) {
            throw new NegocioException(ex.getMessage());
        }
    }
    
}
