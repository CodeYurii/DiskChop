package com.diskchop.model.dao;

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
}
