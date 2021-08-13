
package com.eleicao.ptep.controller;

import com.eleicao.ptep.entidade.Eleitor;
import com.eleicao.ptep.exception.NegocioException;
import com.eleicao.ptep.repository.CandidatoRepository;
import com.eleicao.ptep.service.EleicaoService;
import com.eleicao.ptep.service.EleitorService;
import java.time.LocalDate;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hallef
 */
@Controller
@RequestMapping("votar")
public class EleitorController {
    
    @Autowired
    private CandidatoRepository candidatoRepository;
    
    @Autowired
    private EleicaoService eleicaoService;
    
    @Autowired
    private EleitorService eleitorService;
    
    @GetMapping
    public ModelAndView pageEleitor(Eleitor eleitor) {
        ModelAndView mv = new ModelAndView("areaeleitor/AreaEleitor");
        mv.addObject("eleicoesCandidatos", candidatoRepository.mapCandidatosPorCargo());
        mv.addObject("eleicaoAtual", eleicaoService.eleicaoAtaul());
        return mv;
    }
    
    @PostMapping("salvar")
    public ModelAndView salvar(@Valid Eleitor eleitor, BindingResult result, Model model, RedirectAttributes attributes) {
        try {
            if (result.hasErrors()) {
                return pageEleitor(eleitor);
            }
            String protocolo = eleitorService.salvar(eleitor);
            attributes.addFlashAttribute("mensagem", "Voto salvo com sucesso, protocolo de votação: "+protocolo);
        } catch (NegocioException ex) {
            ObjectError error = new ObjectError("erro", ex.getMessage());
            result.addError(error);
            return pageEleitor(eleitor);
        }
        return new ModelAndView("redirect:/votar", HttpStatus.CREATED);
    }
}
