
package com.eleicao.ptep.controller;

import com.eleicao.ptep.entidade.Eleicao;
import com.eleicao.ptep.exception.NegocioException;
import com.eleicao.ptep.service.EleicaoService;
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
@RequestMapping("eleicao")
public class EleicaoController {
    
    @Autowired
    private EleicaoService eleicaoService;
    
    @GetMapping
    public ModelAndView pageNovaEleicao(Eleicao eleicao) {
        ModelAndView modelAndView = new ModelAndView("eleicao/Nova");
        modelAndView.addObject(eleicao);
        return modelAndView;
    }
    
    @PostMapping("salvar")
    public ModelAndView salvar(@Valid Eleicao eleicao, BindingResult result, Model model, RedirectAttributes attributes) {
        try {
            if (result.hasErrors()) {
                return pageNovaEleicao(eleicao);
            }
            eleicaoService.salvarEleicao(eleicao);
        } catch (NegocioException ex) {
            ObjectError error = new ObjectError("erro", ex.getMessage());
            result.addError(error);
            return pageNovaEleicao(eleicao);
        }
        attributes.addFlashAttribute("mensagem", "Eleição salva com sucesso!");
        return new ModelAndView("redirect:/eleicao", HttpStatus.CREATED);
    }
    
}
