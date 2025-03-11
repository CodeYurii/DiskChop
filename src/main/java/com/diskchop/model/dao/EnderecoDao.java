package com.diskchop.model.dao;

import com.diskchop.model.entity.Cliente;
import com.diskchop.model.entity.Endereco;
import com.diskchop.model.util.MensagensSistema;
import com.diskchop.model.util.TelaMensagensSistema;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnderecoDao {

    private EntityManager em;

    public EnderecoDao() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diskchop");
        this.em = emf.createEntityManager();
    }

    public void adicionarEnderecoCliente(Long idCliente, Endereco endereco) {
        try {
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, idCliente);
            if (cliente == null) {
                throw new RuntimeException("Cliente não encontrado para o ID: " + idCliente);
            }
            endereco.setCliente(cliente);
            if(endereco.getIdEndereco() == null){
                em.persist(endereco);
            } else {
                endereco = em.merge(endereco);
                em.flush();
            }
            cliente.getEnderecos().add(endereco);
            em.getTransaction().commit();
            em.clear();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao adicionar endereço ao cliente", e);
        }
    }

    public List<Endereco> buscarEnderecosDoCliente(Long idCliente) {
        Cliente cliente = em.find(Cliente.class, idCliente);
        if (cliente != null) {
            cliente.getEnderecos().size(); // Força o carregamento dos endereços
            return cliente.getEnderecos();
        }
        return new ArrayList<>();
    }

    public void excluirEndereco(Long enderecoId) {
        try {
            em.getTransaction().begin();

            // Encontrar o Endereco pelo ID
            Endereco endereco = em.find(Endereco.class, enderecoId);
            if (endereco == null) {
                throw new RuntimeException("Endereço não encontrado para exclusão. ID: " + enderecoId);
            }
            Cliente cliente = endereco.getCliente();
            // Remover o Endereco da lista de enderecos do Cliente
            cliente.getEnderecos().remove(endereco);

            // Sincronizar a entidade Cliente (essa operação também pode causar a exclusão do Endereco se orphanRemoval estiver ativado)
            em.merge(cliente);
            em.flush();
            em.getTransaction().commit();
            em.refresh(cliente);
            em.clear();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            throw new RuntimeException("Erro ao excluir Endereço: " + e.getMessage(), e);
        }
    }


}
