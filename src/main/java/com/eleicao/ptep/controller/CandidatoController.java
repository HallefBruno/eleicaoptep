
package com.eleicao.ptep.controller;

import com.eleicao.ptep.entidade.Candidato;
import com.eleicao.ptep.entidade.dto.FiltroCandidato;
import com.eleicao.ptep.exception.NegocioException;
import com.eleicao.ptep.relatorio.RelatorioService;
import com.eleicao.ptep.service.CandidatoService;
import com.eleicao.ptep.service.CargoService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hallef
 */
@Controller
@RequestMapping("candidato")
public class CandidatoController {
    
    @Autowired
    private CargoService cargoService;
    
    @Autowired
    private CandidatoService candidatoService;
    
    @GetMapping
    public ModelAndView pageCandidato(Candidato candidato) {
        ModelAndView modelAndView = new ModelAndView("candidato/Novo");
        modelAndView.addObject("cargos", cargoService.todos());
        return modelAndView;
    }
    
    @PostMapping("salvar")
    public ModelAndView salvar(@RequestParam("image") MultipartFile multipartFile, @Valid Candidato candidato, BindingResult result, Model model, RedirectAttributes attributes) throws IOException {
        try {
            if (result.hasErrors()) {
                return pageCandidato(candidato);
            }
            candidatoService.salvar(candidato,multipartFile);
        } catch (NegocioException ex) {
            ObjectError error = new ObjectError("erro", ex.getMessage());
            result.addError(error);
            return pageCandidato(candidato);
        }
        attributes.addFlashAttribute("mensagem", "Candidato salvo com sucesso!");
        return new ModelAndView("redirect:/candidato", HttpStatus.CREATED);
    }
    
    @PostMapping("update/{codigo}")
    public ModelAndView update(@PathVariable(required = true, name = "codigo") Long codigo, @RequestParam("image") MultipartFile multipartFile,  @Valid Candidato candidato, BindingResult result, RedirectAttributes attributes) {
        try {
            if (result.hasErrors()) {
                return pageCandidato(candidato);
            }
            candidatoService.update(candidato,codigo, multipartFile);
        } catch (NegocioException ex) {
            ObjectError error = new ObjectError("erro", ex.getMessage());
            result.addError(error);
            return pageCandidato(candidato);
        }
        attributes.addFlashAttribute("mensagem", "Candidato alterado com sucesso!");
        return new ModelAndView("redirect:/candidato", HttpStatus.OK);
    }
    
    @DeleteMapping("{codigo}")
    public ResponseEntity<?> excluir(@PathVariable("codigo") Candidato candidato) {
        try {
            candidatoService.excluir(candidato);
        } catch (NegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("pesquisar")
    public ModelAndView pesquisar(FiltroCandidato filtroCandidato) {
        ModelAndView mv = new ModelAndView("candidato/Pesquisar");
        mv.addObject("cargos", cargoService.todos());
        mv.addObject("listaCandidato", candidatoService.buscarCandidatoPor(filtroCandidato));
        return mv;
    }
    
    @GetMapping("{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Candidato candidato) {
        ModelAndView mv = pageCandidato(candidato);
        mv.addObject(candidato);
        return mv;
    }

}
