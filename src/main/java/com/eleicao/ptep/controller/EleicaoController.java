
package com.eleicao.ptep.controller;

import com.eleicao.ptep.entidade.Eleicao;
import com.eleicao.ptep.entidade.dto.FiltroEleicao;
import com.eleicao.ptep.exception.NegocioException;
import com.eleicao.ptep.service.EleicaoService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            eleicaoService.salvar(eleicao);
        } catch (NegocioException ex) {
            ObjectError error = new ObjectError("erro", ex.getMessage());
            result.addError(error);
            return pageNovaEleicao(eleicao);
        }
        attributes.addFlashAttribute("mensagem", "Eleição salva com sucesso!");
        return new ModelAndView("redirect:/eleicao", HttpStatus.CREATED);
    }
    
    @PostMapping("update/{codigo}")
    public ModelAndView update(@PathVariable(required = true, name = "codigo") Long codigo,  @Valid Eleicao eleicao, BindingResult result, RedirectAttributes attributes) {
        try {
            if (result.hasErrors()) {
                return pageNovaEleicao(eleicao);
            }
            eleicaoService.update(eleicao,codigo);
        } catch (NegocioException ex) {
            ObjectError error = new ObjectError("erro", ex.getMessage());
            result.addError(error);
            return pageNovaEleicao(eleicao);
        }
        attributes.addFlashAttribute("mensagem", "Eleição alterada com sucesso!");
        return new ModelAndView("redirect:/eleicao", HttpStatus.OK);
    }
    
    @DeleteMapping("{codigo}")
    public ResponseEntity<?> excluir(@PathVariable("codigo") Eleicao eleicao) {
        try {
            eleicaoService.excluir(eleicao);
        } catch (NegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("pesquisar")
    public ModelAndView pesqisar(FiltroEleicao filtroEleicao) {
        ModelAndView mv = new ModelAndView("eleicao/Pesquisar");
        mv.addObject("listaEleicao", eleicaoService.buscarElecaoPor(filtroEleicao));
        return mv;
    }
    
    @GetMapping("{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Eleicao eleicao) {
        ModelAndView mv = pageNovaEleicao(eleicao);
        mv.addObject(eleicao);
        return mv;
    }
    
}
