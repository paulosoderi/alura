package br.com.alura.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.models.CarrinhoCompras;
import br.com.alura.loja.models.CarrinhoItem;
import br.com.alura.loja.models.Produto;
import br.com.alura.loja.models.TipoPreco;

@Controller
@RequestMapping("/carrinho")
@Scope(value=WebApplicationContext.SCOPE_REQUEST)
public class CarrinhoComprasController {
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
    @Autowired
    private CarrinhoCompras carrinho;
	
	@RequestMapping("/add")
	public ModelAndView add(Integer id, TipoPreco tipo){
		ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");
		CarrinhoItem carrinhoItem = criarItem(id, tipo);
		carrinho.add(carrinhoItem);
		return modelAndView;		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView itens(){
	    return new ModelAndView("/carrinho/itens");
	}
	
	@RequestMapping(value="/remover")
	public ModelAndView remover(Integer produtoId, TipoPreco tipoPreco){
		carrinho.remover(produtoId, tipoPreco);
	    return new ModelAndView("redirect:/carrinho");
	}
	
	private CarrinhoItem criarItem(Integer produtoId, TipoPreco tipo){
		Produto produto = produtoDAO.find(produtoId);
		CarrinhoItem carrinhoItem = new CarrinhoItem(produto, tipo);
		return carrinhoItem;
	}

}
