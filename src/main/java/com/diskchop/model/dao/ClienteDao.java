package com.diskchop.model.dao;

import com.diskchop.model.entity.Cliente;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public class ClienteDao {

    private EntityManager em;

    // Construtor que inicializa o EntityManager manualmente
    public ClienteDao() {
        // Criando a EntityManagerFactory e o EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diskchop");
        this.em = emf.createEntityManager();
    }

    public void salvar(Cliente cliente) {
        try {
            // Iniciar a transação manualmente
            em.getTransaction().begin();

            if (cliente.getIdCliente() == null) {
                em.persist(cliente); // Inserir um novo cliente
            } else {
                em.merge(cliente); // Atualizar um cliente existente
            }

            // Confirmar a transação
            em.getTransaction().commit();
        } catch (Exception e) {
            // Em caso de erro, realizar o rollback da transação
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao salvar cliente", e);
        }
    }

    // Método para fechar o EntityManager quando não for mais necessário
    public void close() {
        if (em.isOpen()) {
            em.close();
        }
    }

    public List<Cliente> listarTodos() {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }

    public Long buscarPorCpf(String cpf) {
        Long id = null;
        TypedQuery<Long> query = em.createQuery(
                "SELECT c.id FROM Cliente c WHERE c.cpf = :cpf", Long.class);
        query.setParameter("cpf", cpf);
        id = query.getSingleResult();
        return id;
    }

    public List<Cliente> buscarCliente(String cpf) {
        return em.createQuery("SELECT c FROM Cliente c WHERE c.cpf = :cpf", Cliente.class)
                .setParameter("cpf", cpf)  // Atribuindo o valor ao parâmetro 'cpf'
                .getResultList();
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
