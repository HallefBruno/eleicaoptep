
package com.eleicao.ptep.controller;

import com.eleicao.ptep.entidade.Candidato;
import com.eleicao.ptep.entidade.Cargo;
import com.eleicao.ptep.exception.NegocioException;
import com.eleicao.ptep.service.CandidatoService;
import com.eleicao.ptep.service.CargoService;
import java.io.IOException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
        modelAndView.addObject("candidato",candidato);
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
    
}
