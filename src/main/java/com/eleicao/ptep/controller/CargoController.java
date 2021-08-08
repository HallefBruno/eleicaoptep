
package com.eleicao.ptep.controller;

import com.eleicao.ptep.entidade.Cargo;
import com.eleicao.ptep.entidade.dto.FiltroCargo;
import com.eleicao.ptep.exception.NegocioException;
import com.eleicao.ptep.service.CargoService;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author hallef
 */
@Controller
@RequestMapping("cargo")
public class CargoController {
    
    @Autowired
    private CargoService cargoService;
    
    @GetMapping
    public ModelAndView pageNovoCargo(Cargo cargo) {
        ModelAndView modelAndView = new ModelAndView("cargo/Novo");
        modelAndView.addObject(cargo);
        return modelAndView;
    }
    
    @PostMapping("salvar")
    public ModelAndView salvar(@Valid Cargo cargo, BindingResult result, Model model, RedirectAttributes attributes) {
        try {
            if (result.hasErrors()) {
                return pageNovoCargo(cargo);
            }
            cargoService.salvar(cargo);
        } catch (NegocioException ex) {
            ObjectError error = new ObjectError("erro", ex.getMessage());
            result.addError(error);
            return pageNovoCargo(cargo);
        }
        attributes.addFlashAttribute("mensagem", "Cargo salvo com sucesso!");
        return new ModelAndView("redirect:/cargo", HttpStatus.CREATED);
    }
    
    @PostMapping("update/{codigo}")
    public ModelAndView update(@PathVariable(required = true, name = "codigo") Long codigo,  @Valid Cargo cargo, BindingResult result, RedirectAttributes attributes) {
        try {
            if (result.hasErrors()) {
                return pageNovoCargo(cargo);
            }
            cargoService.update(cargo,codigo);
        } catch (NegocioException ex) {
            ObjectError error = new ObjectError("erro", ex.getMessage());
            result.addError(error);
            return pageNovoCargo(cargo);
        }
        attributes.addFlashAttribute("mensagem", "Cargo alterado com sucesso!");
        return new ModelAndView("redirect:/cargo", HttpStatus.OK);
    }
    
    @DeleteMapping("{codigo}")
    public ResponseEntity<?> excluir(@PathVariable("codigo") Cargo cargo) {
        try {
            cargoService.excluir(cargo);
        } catch (NegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("pesquisar")
    public ModelAndView pesquisar(FiltroCargo filtroCargo) {
        ModelAndView mv = new ModelAndView("cargo/Pesquisar");
        mv.addObject("listaCargo", cargoService.filtrarPorNome(filtroCargo));
        return mv;
    }
    
    @GetMapping("{codigo}")
    public ModelAndView editar(@PathVariable("codigo") Cargo cargo) {
        ModelAndView mv = pageNovoCargo(cargo);
        mv.addObject(cargo);
        return mv;
    }
    
}
