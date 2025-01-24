package com.diskchop.model.dao;

import com.diskchop.model.entity.Cliente;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public class ClienteDao {

    @PersistenceContext
    private EntityManager em;

    public ClienteDao() {
        try {
            // Criando o EntityManagerFactory
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("diskchop");
            this.em = emf.createEntityManager();
        } catch (Exception e) {
            // Capturando exceção para depuração
            e.printStackTrace();
        }
    }


    @Transactional
    public void salvar(Cliente cliente) {
        em.getTransaction().begin();
        if (cliente.getIdCliente() == null){
            em.persist(cliente);
        } else {
            em.merge(cliente);
        }
        em.getTransaction().commit();
    }

    public List<Cliente> listarTodos() {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }

    public Optional<Cliente> buscarPorId(Long idCliente) {
        Cliente cliente = em.find(Cliente.class, idCliente);
        return Optional.ofNullable(cliente);  // Retorna um Optional para evitar NPE
    }

    public Optional<Cliente> buscarPorNome(String nome) {
        try {
            Cliente cliente = em.createQuery("SELECT c FROM Cliente c WHERE c.nome = :nome", Cliente.class)
                    .setParameter("nome", nome)
                    .getSingleResult();
            return Optional.of(cliente);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Transactional
    public void remover(Long idCliente) {
        Cliente cliente = buscarPorId(idCliente).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        em.remove(cliente);  // Remove o cliente do banco
    }
}
