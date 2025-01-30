package com.diskchop.model.dao;

import com.diskchop.model.entity.Cliente;
import com.diskchop.model.entity.Endereco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnderecoDao {

    private EntityManager em;

    public EnderecoDao() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("diskchop");
        this.em = emf.createEntityManager();
    }

    public void adicionarEnderecoAoCliente(Long idCliente, Endereco endereco) {
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
            enderecoExistente = endereco;
            enderecoExistente.setIdEndereco(idEndereco);
            enderecoExistente.setCliente(cliente);

            cliente.getEnderecos().add(enderecoExistente);
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



    public Optional<Endereco> buscarPorId(Long idEndereco) {
        Endereco endereco  = em.find(Endereco.class, idEndereco);
        return Optional.ofNullable(endereco);
    }

    public void remover(Long idEndereco) {
       Endereco endereco = buscarPorId(idEndereco).orElseThrow(() -> new RuntimeException("Endereco não encontrado"));
        em.remove(endereco);
    }
}
