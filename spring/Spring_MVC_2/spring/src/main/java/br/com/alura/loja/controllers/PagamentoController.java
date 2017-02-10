package br.com.alura.loja.controllers;

import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.alura.loja.models.CarrinhoCompras;
import br.com.alura.loja.models.DadosPagamento;

@RequestMapping("/pagamento")
@Controller
public class PagamentoController {
	
	@Autowired
	CarrinhoCompras carrinho;
	
	@Autowired
	RestTemplate restTemplate;
	
	

	@RequestMapping(value="/finalizar", method=RequestMethod.POST)
	public Callable<ModelAndView>  finalizar(RedirectAttributes model){
		System.out.println(carrinho.getTotal());
		return () -> {
	        try {
	            String uri = "http://book-payment.herokuapp.com/payment";
	            String response = restTemplate.postForObject(uri, new DadosPagamento(carrinho.getTotal()), String.class);
	            model.addFlashAttribute("sucesso", response);
	            System.out.println(response);
	            return new ModelAndView("redirect:/produtos");
	        } catch (Exception e) {
	            e.printStackTrace();
	            model.addFlashAttribute("sucesso", "deu erro!");
	            return new ModelAndView("redirect:/produtos");
	        }
	    };
	}
}
