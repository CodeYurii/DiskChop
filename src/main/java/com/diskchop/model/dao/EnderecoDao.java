package com.diskchop.model.dao;

import com.diskchop.model.entity.Cliente;
import com.diskchop.model.entity.Endereco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

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
                em.merge(cliente);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao adicionar endereço ao cliente", e);
        }
    }

    public void atualizarEndereco(Long idCliente, Long idEndereco, Endereco endereco) {
        try{
            em.getTransaction().begin();
            Cliente cliente = em.find(Cliente.class, idCliente);
            if (cliente == null) {
                throw new IllegalArgumentException("Cliente nao encontrado");
            }
            Endereco enderecoExistente = em.find(Endereco.class, idEndereco);
            if (enderecoExistente == null) {
                throw new IllegalArgumentException("endereço nao encontrado");
            }
            enderecoExistente.setLogradouro(endereco.getLogradouro());
            enderecoExistente.setNumero(endereco.getNumero());
            enderecoExistente.setBairro(endereco.getBairro());
            enderecoExistente.setCidade(endereco.getCidade());
            enderecoExistente.setCep(endereco.getCep());
            enderecoExistente.setComplemento(endereco.getComplemento());
            enderecoExistente.setObservacaoEndereco(endereco.getObservacaoEndereco());
            em.merge(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao atualizar o endereço", e);
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
