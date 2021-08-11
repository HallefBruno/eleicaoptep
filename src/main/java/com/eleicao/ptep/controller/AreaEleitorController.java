
package com.eleicao.ptep.controller;

import com.eleicao.ptep.entidade.Voto;
import com.eleicao.ptep.repository.CandidatoRepository;
import com.eleicao.ptep.service.CandidatoService;
import com.eleicao.ptep.service.EleicaoService;
import com.eleicao.ptep.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hallef
 */
@Controller
@RequestMapping("votar")
public class AreaEleitorController {
    
    @Autowired
    private VotoService votoService;
    
    @Autowired
    private CandidatoRepository candidatoRepository;
    
    @Autowired
    private EleicaoService eleicaoService;
    
    @GetMapping
    public ModelAndView pageAreaEleitor(Voto voto) {
        ModelAndView mv = new ModelAndView("areaeleitor/AreaEleitor");
        mv.addObject("eleicoesCandidatos", candidatoRepository.mapCandidatosPorCargo());
        mv.addObject("listaEleicoes", eleicaoService.eleicoesValidas());
        return mv;
    }
    
}
