package com.diskchop.model.dao;

import com.diskchop.model.entity.Cliente;
import com.diskchop.model.util.MensagensSistema;
import com.diskchop.model.util.TelaMensagensSistema;
import jakarta.persistence.*;

import java.util.List;

public class ClienteDao {

    private EntityManager em;

    public ClienteDao() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diskchop");
        this.em = emf.createEntityManager();
    }

    public void close() {
        if (em.isOpen()) {
            em.close();
        }
    }

    public boolean salvarCliente(Cliente cliente) {
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            TelaMensagensSistema.mostrarErro(MensagensSistema.ERRO_BANCO_DADOS);
            return false;
        }
    }

    public void atualizarCliente(Cliente cliente) {
        try {
            em.getTransaction().begin();
            Cliente clienteSalvo = cliente;
            em.flush();
            em.merge(clienteSalvo);
            em.getTransaction().commit();
            em.clear();
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
            Cliente cliente = em.find(Cliente.class, id);
            em.refresh(cliente);
            return cliente;
        } catch (Exception e) {
            TelaMensagensSistema.mostrarErro("Erro ao buscar cliente por ID");
            return null;
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

    public void excluirCliente(Long id) {
        try {
            Cliente cliente = em.find(Cliente.class, id);
            if (cliente != null) {
                em.getTransaction().begin();
                em.remove(cliente);
                em.getTransaction().commit();
            } else {
                throw new RuntimeException("Cliente não encontrado para exclusão.");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao excluir cliente", e);
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
}
