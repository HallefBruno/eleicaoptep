
package com.store.drinks.service;

import com.store.drinks.entidade.AbrirCaixa;
import com.store.drinks.entidade.Usuario;
import com.store.drinks.repository.AbrirCaixaRepository;
import com.store.drinks.repository.IAuthenticationFacade;
import com.store.drinks.repository.UsuarioRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AbrirCaixaService {

    @Autowired
    private AbrirCaixaRepository abrirCaixaRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @Transactional
    public void salvar(AbrirCaixa abrirCaixa) {
        AbrirCaixa acx = new AbrirCaixa();
        acx.setAberto(Boolean.TRUE);
        acx.setDataHoraAbertura(LocalDateTime.now());
        acx.setUsuario(usuario());
        acx.setValorInicialTroco(abrirCaixa.getValorInicialTroco());
        abrirCaixaRepository.save(acx);
    }
    
    public boolean abrirCaixaPorUsuarioLogado() {
        Optional<AbrirCaixa> caixaAberto = abrirCaixaRepository.findByAbertoTrueAndUsuario(usuario());
        return caixaAberto.isPresent();
    }
    
    private Usuario usuario() {
        Usuario usuario = (Usuario) authenticationFacade.getAuthentication().getPrincipal();
        usuarioRepository.findById(usuario.getId());
        return usuario;
    }
}
