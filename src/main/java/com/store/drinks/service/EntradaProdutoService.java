package com.store.drinks.service;

import com.store.drinks.entidade.EntradaProduto;
import com.store.drinks.entidade.Produto;
import com.store.drinks.entidade.SituacaoCompra;
import com.store.drinks.entidade.dto.ProdutoSelect2;
import com.store.drinks.entidade.dto.ResultSelectProdutos;
import com.store.drinks.execption.NegocioException;
import com.store.drinks.repository.EntradaProdutoRepository;
import com.store.drinks.repository.ProdutoRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntradaProdutoService {

    @Autowired
    private EntradaProdutoRepository entradaProdutoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public void salvar(EntradaProduto entradaProduto) {
        Produto produto = buscarProdutoPorCodBarra(entradaProduto.getCodigoBarra());
        setarNovosValores(entradaProduto, produto);
        produto = produtoRepository.save(produto);
        entradaProduto.setProduto(produto);
        entradaProdutoRepository.save(entradaProduto);
    }

    private void setarNovosValores(EntradaProduto entradaProduto, Produto produto) {
        if (((entradaProduto.getNovaQuantidade() == null || entradaProduto.getNovaQuantidade() == 0) && (entradaProduto.getNovoValorCusto() == null || entradaProduto.getNovoValorCusto().doubleValue() == 0) && (entradaProduto.getNovoValorVenda() == null || entradaProduto.getNovoValorVenda().doubleValue() == 0))) {
            if(entradaProduto.getSituacaoCompra() == SituacaoCompra.CONFIRMADA) {
                entradaProduto.setNovaQuantidade(produto.getQuantidade() + produto.getQuantidade());
            } else {
                entradaProduto.setNovaQuantidade(produto.getQuantidade());
            }
            entradaProduto.setNovoValorCusto(produto.getValorCusto());
            entradaProduto.setNovoValorVenda(produto.getValorVenda());
            BigDecimal somaTotal = BigDecimal.valueOf(produto.getQuantidade() * produto.getValorCusto().doubleValue());
            entradaProduto.setValorTotal(somaTotal);
            produto.setQuantidade(entradaProduto.getNovaQuantidade());
        } else if (((entradaProduto.getNovaQuantidade() != null && entradaProduto.getNovaQuantidade() > 0) && (entradaProduto.getNovoValorCusto() == null || entradaProduto.getNovoValorCusto().doubleValue() == 0) && (entradaProduto.getNovoValorVenda() == null || entradaProduto.getNovoValorVenda().doubleValue() == 0))) {
            if(entradaProduto.getSituacaoCompra() == SituacaoCompra.CONFIRMADA) {
                produto.setQuantidade(produto.getQuantidade() + entradaProduto.getNovaQuantidade());
            } else {
                produto.setQuantidade(produto.getQuantidade());
            }
            entradaProduto.setNovoValorCusto(produto.getValorCusto());
            entradaProduto.setNovoValorVenda(produto.getValorVenda());
            BigDecimal somaTotal = BigDecimal.valueOf(entradaProduto.getNovaQuantidade() * produto.getValorCusto().doubleValue());
            entradaProduto.setValorTotal(somaTotal);
            entradaProduto.setNovaQuantidade(produto.getQuantidade());
        } else if (((entradaProduto.getNovoValorCusto() != null && entradaProduto.getNovoValorCusto().doubleValue() > 0) && (entradaProduto.getNovoValorVenda() == null || entradaProduto.getNovoValorVenda().doubleValue() == 0) && (entradaProduto.getNovaQuantidade() == null || entradaProduto.getNovaQuantidade() == 0))) {
            if(entradaProduto.getSituacaoCompra() == SituacaoCompra.CONFIRMADA) {
                entradaProduto.setNovaQuantidade(produto.getQuantidade() + produto.getQuantidade());
            } else {
                entradaProduto.setNovaQuantidade(produto.getQuantidade());
            }
            produto.setValorCusto(entradaProduto.getNovoValorCusto());
            entradaProduto.setNovoValorVenda(produto.getValorVenda());
            BigDecimal somaTotal = BigDecimal.valueOf(produto.getQuantidade() * entradaProduto.getNovoValorCusto().doubleValue());
            entradaProduto.setValorTotal(somaTotal);
            produto.setQuantidade(entradaProduto.getNovaQuantidade());
        } else if (((entradaProduto.getNovoValorVenda() != null && entradaProduto.getNovoValorVenda().doubleValue() > 0) && (entradaProduto.getNovoValorCusto() == null || entradaProduto.getNovoValorCusto().doubleValue() == 0) && (entradaProduto.getNovaQuantidade() == null || entradaProduto.getNovaQuantidade() == 0))) {
            if(entradaProduto.getSituacaoCompra() == SituacaoCompra.CONFIRMADA) {
                entradaProduto.setNovaQuantidade(produto.getQuantidade() + produto.getQuantidade());
            } else {
                entradaProduto.setNovaQuantidade(produto.getQuantidade());
            }
            produto.setValorVenda(entradaProduto.getNovoValorVenda());
            entradaProduto.setNovoValorCusto(produto.getValorCusto());
            BigDecimal somaTotal = BigDecimal.valueOf(produto.getQuantidade() * produto.getValorCusto().doubleValue());
            entradaProduto.setValorTotal(somaTotal);
            produto.setQuantidade(entradaProduto.getNovaQuantidade());
        } else if (((entradaProduto.getNovaQuantidade() != null && entradaProduto.getNovaQuantidade() > 0) && (entradaProduto.getNovoValorCusto() != null && entradaProduto.getNovoValorCusto().doubleValue() > 0) && (entradaProduto.getNovoValorVenda() == null || entradaProduto.getNovoValorVenda().doubleValue() == 0))) {
            if(entradaProduto.getSituacaoCompra() == SituacaoCompra.CONFIRMADA) {
                produto.setQuantidade(produto.getQuantidade() + entradaProduto.getNovaQuantidade());
            } else {
                produto.setQuantidade(produto.getQuantidade());
            }
            produto.setValorCusto(entradaProduto.getNovoValorCusto());
            entradaProduto.setNovoValorVenda(produto.getValorVenda());
            BigDecimal somaTotal = BigDecimal.valueOf(entradaProduto.getNovaQuantidade() * entradaProduto.getNovoValorCusto().doubleValue());
            entradaProduto.setValorTotal(somaTotal);
            entradaProduto.setNovaQuantidade(produto.getQuantidade());
        } else if (((entradaProduto.getNovaQuantidade() != null && entradaProduto.getNovaQuantidade() > 0) && (entradaProduto.getNovoValorVenda() != null && entradaProduto.getNovoValorVenda().doubleValue() > 0) && (entradaProduto.getNovoValorCusto() == null || entradaProduto.getNovoValorCusto().doubleValue() == 0))) {
            if(entradaProduto.getSituacaoCompra() == SituacaoCompra.CONFIRMADA) {
                produto.setQuantidade(produto.getQuantidade() + entradaProduto.getNovaQuantidade());
            } else {
                produto.setQuantidade(produto.getQuantidade());
            }
            entradaProduto.setNovoValorCusto(produto.getValorCusto());
            produto.setValorVenda(entradaProduto.getNovoValorVenda());
            BigDecimal somaTotal = BigDecimal.valueOf(entradaProduto.getNovaQuantidade() * produto.getValorCusto().doubleValue());
            entradaProduto.setValorTotal(somaTotal);
            entradaProduto.setNovaQuantidade(produto.getQuantidade());
        } else if (((entradaProduto.getNovoValorCusto() != null && entradaProduto.getNovoValorCusto().doubleValue() > 0) &&  (entradaProduto.getNovoValorVenda() != null && entradaProduto.getNovoValorVenda().doubleValue() > 0) && (entradaProduto.getNovaQuantidade() == null || entradaProduto.getNovaQuantidade() == 0))) {
            if(entradaProduto.getSituacaoCompra() == SituacaoCompra.CONFIRMADA) {
                produto.setQuantidade(produto.getQuantidade() + produto.getQuantidade());
            } else {
                produto.setQuantidade(produto.getQuantidade());
            }
            produto.setValorCusto(entradaProduto.getNovoValorCusto());
            produto.setValorVenda(entradaProduto.getNovoValorVenda());
            entradaProduto.setNovaQuantidade(produto.getQuantidade());
            BigDecimal somaTotal = BigDecimal.valueOf(produto.getQuantidade() * entradaProduto.getNovoValorCusto().doubleValue());
            entradaProduto.setValorTotal(somaTotal);
        } else {
            if(entradaProduto.getSituacaoCompra() == SituacaoCompra.CONFIRMADA) {
                produto.setQuantidade(produto.getQuantidade() + entradaProduto.getNovaQuantidade());
            } else {
                produto.setQuantidade(produto.getQuantidade());
            }
            produto.setValorCusto(entradaProduto.getNovoValorCusto());
            produto.setValorVenda(entradaProduto.getNovoValorVenda());
            entradaProduto.setNovaQuantidade(produto.getQuantidade());
            BigDecimal somaTotal = BigDecimal.valueOf(entradaProduto.getNovaQuantidade() * entradaProduto.getNovoValorCusto().doubleValue());
            entradaProduto.setValorTotal(somaTotal);
        }
    }

    public List<Produto> todos() {
        return produtoRepository.findAll();
    }
    
    public List<EntradaProduto> todas() {
        return entradaProdutoRepository.findAll();
    }

    public Produto buscarProdutoPorId(Long id) {
        Optional<Produto> opProduto = produtoRepository.findById(id);
        if (opProduto.isPresent()) {
            return opProduto.get();
        }
        throw new NegocioException("Produto não encontrado");
    }

    public Produto buscarProdutoPorCodBarra(String codBarra) {
        if (!StringUtils.isEmpty(codBarra)) {
            Optional<Produto> produtoOptional = produtoRepository.findByCodigoBarra(codBarra);
            if (produtoOptional.isPresent()) {
                return produtoOptional.get();
            }
            throw new NegocioException("Nenhum produto encontrado!");
        }
        throw new NegocioException("Código produto inválido!");
    }

    public ResultSelectProdutos pesquisarProdutosAutoComplete(String descricao, String pagina) {
        ProdutoSelect2 produtoDTO = new ProdutoSelect2();
        ResultSelectProdutos resultSelectAutomoveis = new ResultSelectProdutos();
        List<ProdutoSelect2> produtosDTO = new ArrayList<>();
        Pageable pageable = PageRequest.of(Integer.valueOf(pagina), 10);
        Page page = entradaProdutoRepository.filtrarProdutosSelect(descricao, pageable);
        if (page.hasContent()) {
            List<Produto> automovels = page.getContent();
            resultSelectAutomoveis.setTotalItens(page.getTotalElements());
            for (Produto produto : automovels) {
                produtoDTO.setId(produto.getId().toString());
                produtoDTO.setText(produto.getCodigoBarra() + " " + produto.getDescricaoProduto() + " " + produto.getCodProduto());
                produtosDTO.add(produtoDTO);
                produtoDTO = new ProdutoSelect2();
            }
            resultSelectAutomoveis.setItems(produtosDTO);
        }
        return resultSelectAutomoveis;
    }
}
