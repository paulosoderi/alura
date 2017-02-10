package br.com.alura.loja.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.loja.models.Produto;

@Repository
@Transactional
public class ProdutoDAO {

	@PersistenceContext
	private EntityManager em;
	
	public void gravar(Produto produto){
		em.persist(produto);
	}
	
	public List<Produto> listarProdutos(){
		return em.createQuery("select p from Produto p").getResultList();
	}
	
	public Produto find(int id){
		return em.createQuery("select distinct(p) from Produto p join fetch p.precos precos where p.id = :id", Produto.class).setParameter("id", id).getSingleResult();
	}

}
