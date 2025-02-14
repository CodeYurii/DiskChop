package com.diskchop.model.dao;

import com.diskchop.model.entity.Cliente;
import com.diskchop.model.entity.Endereco;
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
            if (cliente != null) {
                endereco.setCliente(cliente);
                cliente.getEnderecos().add(endereco);
                em.persist(endereco); // Persistindo o novo endereço
            } else {
                throw new RuntimeException("Cliente não encontrado para o ID: " + idCliente);
            }
            em.getTransaction().commit();
            em.clear();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao adicionar endereço ao cliente", e);
        }
    }

    public void atualizarEndereco(Long idCliente, Endereco endereco) {
        try{
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, idCliente);
            if (cliente == null) {
                throw new IllegalArgumentException("Cliente nao encontrado");
            }
            em.merge(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao atualizar o endereço", e);
        }
    }

    public void selecrionarEndereco(Endereco endereco){
        try{
            em.getTransaction().begin();
            Endereco enderecoSelecionado = em.find(Endereco.class, endereco.getIdEndereco());
            if (enderecoSelecionado != null) {
                em.merge(enderecoSelecionado);
                em.getTransaction().commit();
                em.clear();
                em.refresh(enderecoSelecionado);
            }
        } catch (Exception e) {}
    }

    public List<Endereco> buscarEnderecosDoCliente(Long idCliente) {
        Cliente cliente = em.find(Cliente.class, idCliente);
        if (cliente != null) {
            cliente.getEnderecos().size(); // Força o carregamento dos endereços
            return cliente.getEnderecos();
        }
        return new ArrayList<>();
    }

    public void excluirEndereco(Long id) {
        try {
            Endereco endereco = em.find(Endereco.class, id);
            if (endereco != null) {
                // Obtendo o cliente dono do endereço
                Cliente cliente = endereco.getCliente();

                if (cliente != null) {
                    // Removendo o endereço da lista do cliente
                    cliente.getEnderecos().remove(endereco);
                    em.merge(cliente); // Atualizando o cliente para refletir a remoção
                }
            } else {
                throw new RuntimeException("Endereço não encontrado para exclusão.");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao excluir Endereço", e);
        }
    }
}
