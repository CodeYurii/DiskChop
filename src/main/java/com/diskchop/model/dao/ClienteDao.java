package com.diskchop.model.dao;

import com.diskchop.model.entity.Cliente;
import com.diskchop.model.util.MensagensSistema;
import com.diskchop.model.util.TelaMensagensSistema;
import jakarta.persistence.*;

import java.util.List;

public class ClienteDao {

    private EntityManager em;

    // Construtor que inicializa o EntityManager manualmente
    public ClienteDao() {
        // Criando a EntityManagerFactory e o EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diskchop");
        this.em = emf.createEntityManager();
    }

    // Método para fechar o EntityManager quando não for mais necessário
    public void close() {
        if (em.isOpen()) {
            em.close();
        }
    }

    public void salvarCliente(Cliente cliente) {
        try {
            em.getTransaction().begin();
            Cliente clienteSalvo = cliente;
            em.persist(clienteSalvo);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            TelaMensagensSistema.mostrarErro(MensagensSistema.ERRO_BANCO_DADOS);
        }
    }

    public List<Cliente> buscarClientePorNome(String nome) {
        try {
            return em.createQuery("SELECT c FROM Cliente c WHERE c.nome LIKE :nome", Cliente.class)
                    .setParameter("nome", "%" + nome + "%")
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cliente nome", e);
        }
    }

    public Cliente buscarClientePorCpf(String cpf) {
        try {
            return em.createQuery("SELECT c FROM Cliente c WHERE c.cpf = :cpf", Cliente.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar clientecpfe", e);
        }
    }

    public Cliente buscarClientePorId(Long id) {
        try {
            // A busca é feita diretamente pelo ID, que é uma chave primária
            return em.find(Cliente.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar cliente por ID", e);
        }
    }

    public boolean isCpfCadastrado(String cpf) {
        try {
            Long count = em.createQuery("SELECT COUNT(c) FROM Cliente c WHERE c.cpf = :cpf", Long.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao verificar CPF", e);
        }
    }


    public List<Cliente> listarTodos() {
        return em.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
    }

    public Long buscarIdPorCpf(String cpf) {
        Long id = null;
        TypedQuery<Long> query = em.createQuery(
                "SELECT c.id FROM Cliente c WHERE c.cpf = :cpf", Long.class);
        query.setParameter("cpf", cpf);
        id = query.getSingleResult();
        return id;
    }



    public Cliente buscarClienteId(Long idCliente) {
        try {
            return em.find(Cliente.class, idCliente); // Retorna um único cliente ou null
        } catch (Exception e) {
           throw new RuntimeException("Erro ao buscar cliente", e);
        }
    }

}
