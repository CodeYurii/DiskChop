package com.diskchop.model.dao;

import com.diskchop.model.entity.Cliente;
import com.diskchop.model.entity.Endereco;
import com.diskchop.model.entity.Telefone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TelefoneDao {
    private EntityManager em;

    public TelefoneDao() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diskchop");
        this.em = emf.createEntityManager();
    }

    public void adicionarTelefoneCliente(Long idCliente, Telefone telefone) {
        try {
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, idCliente);
            if (cliente == null) {
                throw new RuntimeException("Cliente não encontrado para o ID: " + idCliente);
            }

            telefone.setCliente(cliente);
            if(telefone.getIdTelefone() == null){
                em.persist(telefone);
            } else {
                telefone = em.merge(telefone);
                em.flush();
            }
            em.getTransaction().commit();
            em.clear();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao adicionar telefone ao cliente", e);
        }
    }

    public void atualizarTelefoneCLiente(Long idCliente, Long idTelefone, Telefone telefone) {
        try{
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, idCliente);
            if (cliente == null) {
                throw new IllegalArgumentException("Cliente não encontrado");
            }
            Telefone telefoneExistente = em.find(Telefone.class, idTelefone);
            if (telefoneExistente == null) {
                throw new IllegalArgumentException("Telefone nao encontrado");
            }
            telefoneExistente.setTelefone(telefone.getTelefone());
            telefoneExistente.setContato(telefone.getContato());
            em.merge(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao atualizar o telefone", e);
        }
    }

    public List<Telefone> buscarTelefonesDoCliente(Long idCliente) {
        Cliente cliente = em.find(Cliente.class, idCliente);
        if (cliente != null) {
            cliente.getTelefones().size(); // Força o carregamento dos endereços
            return cliente.getTelefones();
        }
        return new ArrayList<>();
    }

    public void excluirTelefone(Long id) {
        try {
            Telefone telefone = em.find(Telefone.class, id);
            if (telefone != null) {
                Cliente cliente = telefone.getCliente();

                if (cliente != null) {
                    // Removendo o endereço da lista do cliente
                    cliente.getTelefones().remove(telefone);
                    em.merge(cliente); // Atualizando o cliente para refletir a remoção
                }
            } else {
                throw new RuntimeException("Telefone não encontrado para exclusão.");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao excluir Telefone", e);
        }
    }
}
