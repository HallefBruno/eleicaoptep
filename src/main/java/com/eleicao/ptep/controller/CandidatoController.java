
package com.eleicao.ptep.controller;

import com.eleicao.ptep.entidade.Candidato;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author hallef
 */
@Controller
@RequestMapping("candidato")
public class CandidatoController {
    
    @GetMapping
    public ModelAndView pageCandidato(Candidato candidato) {
        ModelAndView modelAndView = new ModelAndView("candidato/Novo");
        modelAndView.addObject(candidato);
        return modelAndView;
    }
    
}
