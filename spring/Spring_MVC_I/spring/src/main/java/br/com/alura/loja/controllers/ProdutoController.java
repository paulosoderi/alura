package br.com.alura.loja.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.infra.FileSaver;
import br.com.alura.loja.models.Produto;
import br.com.alura.loja.models.TipoPreco;
import br.com.alura.loja.validation.ProdutoValidation;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
	
    @Autowired
    private FileSaver fileSaver;
	
	@Autowired
	private ProdutoDAO produtoDAO;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder){
	    binder.addValidators(new ProdutoValidation());
	}

	@RequestMapping("/form")
	public ModelAndView form(Produto produto){
        ModelAndView modelAndView = new ModelAndView("produtos/form");
        modelAndView.addObject("tipos", TipoPreco.values());
        return modelAndView;
    }
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView gravar(MultipartFile sumario, @Valid Produto produto,  BindingResult result, RedirectAttributes ra){
		System.out.println(sumario.getOriginalFilename());
	    System.out.println(produto);
	  //  fileSaver.write("arquivos-sumario", sumario);
	    if(result.hasErrors()){
	        return form(produto);
	    }
	    produtoDAO.gravar(produto);
	    ra.addFlashAttribute("sucesso", "Produto cadastrado com sucesso!!!");
	    return new ModelAndView("redirect:produtos");
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView listar(){
		List<Produto> produtos = produtoDAO.listarProdutos();
		ModelAndView modelAndView = new ModelAndView("/produtos/lista");
		modelAndView.addObject("produtos", produtos);
		return modelAndView;		
	}
	
	/*
	@RequestMapping(value="/detalhe", method=RequestMethod.GET)
	public ModelAndView detalhe(int id){
		ModelAndView modelAndView = new ModelAndView("/produtos/detalhe");
	    Produto produto = produtoDAO.find(id);
	    modelAndView.addObject("produto", produto);
	    return modelAndView;
	}*/
	
	@RequestMapping(value="/detalhe/{id}", method=RequestMethod.GET)
	public ModelAndView detalhe(@PathVariable("id") Integer id){
		ModelAndView modelAndView = new ModelAndView("/produtos/detalhe");
	    Produto produto = produtoDAO.find(id);
	    modelAndView.addObject("produto", produto);
	    return modelAndView;
	}
}
