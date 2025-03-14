package com.diskchop.model.dao;

import com.diskchop.model.entity.CategoriaProduto;
import com.diskchop.model.entity.Maquina;
import com.diskchop.model.entity.Produto;
import com.diskchop.model.util.MensagensSistema;
import com.diskchop.model.util.TelaMensagensSistema;
import jakarta.persistence.*;

import java.util.List;

public class ProdutoDao {
    private EntityManager em;

    public ProdutoDao() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diskchop");
        this.em = emf.createEntityManager();
    }

    public void salvarProduto(Produto produto) {
        try{
            em.getTransaction().begin();
            em.persist(produto);
            em.getTransaction().commit();
            em.clear();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            TelaMensagensSistema.mostrarErro(MensagensSistema.ERRO_BANCO_DADOS);
        }
    }

    public void atualizarProduto(Produto produto) {
        try {
            em.getTransaction().begin();
            em.merge(produto);
            em.flush();
            em.getTransaction().commit();
            em.clear();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                throw new RuntimeException("Erro ao atualizar produto", e);
            }

        }
    }

    public Produto buscarId(Long id) {
        return em.find(Produto.class, id);
    }

    public List<Produto> buscarTodosProdutos() {
        try{
            TypedQuery<Produto> query = em.createQuery("select p from Produto p", Produto.class);
            return query.getResultList();
        } catch (Exception e) {
          throw new RuntimeException("Erro buscando todos os produtos");
        }
    }

    public List<Maquina> buscarMaquinas() {
        try {
            TypedQuery<Maquina> query = em.createQuery("SELECT m FROM Maquina m", Maquina.class);
            return query.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar clientecpfe", e);
        }
    }
    public List<Produto> buscarProdutoCategoria(CategoriaProduto categoria) {
        try {
            String query = "SELECT p FROM Produto p WHERE p.categoria = :categoria";
            TypedQuery<Produto> result = em.createQuery(query, Produto.class);
            result.setParameter("categoria", categoria);
            return result.getResultList();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar clientecpfe", e);
        }
    }

    public void excluirProduto(Produto produto) {
        try {
            em.getTransaction().begin();
            em.remove(produto);
            em.getTransaction().commit();
            em.clear();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
                throw new RuntimeException("Erro ao atualizar produto", e);
            }

        }
    }
}
