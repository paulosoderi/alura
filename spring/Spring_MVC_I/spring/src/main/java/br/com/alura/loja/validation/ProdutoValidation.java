package br.com.alura.loja.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.alura.loja.models.Produto;

public class ProdutoValidation implements Validator {

	public void valida(Produto produto, Errors erros) {
		
	}

	public boolean supports(Class<?> clazz) {
		return Produto.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors erros) {
		System.out.println("validando produto!");
		ValidationUtils.rejectIfEmpty(erros, "titulo", "required.field");
		ValidationUtils.rejectIfEmpty(erros, "descricao", "field.required");
		ValidationUtils.rejectIfEmpty(erros, "paginas", "field.required");
		ValidationUtils.rejectIfEmpty(erros, "dataLancamento", "field.required");
		
		Produto produto = (Produto) target;
		if(produto.getPaginas() !=null && produto.getPaginas() <= 0){
			erros.rejectValue("paginas", "field.required");
	    }
		
	}

}
