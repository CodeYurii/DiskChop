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
            if (cliente != null) {
                cliente.getTelefones().add(telefone);
                telefone.setCliente(cliente);

                em.merge(cliente);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Erro ao adicionar telefone ao cliente", e);
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



    public Optional<Endereco> buscarPorId(Long idEndereco) {
        Endereco endereco  = em.find(Endereco.class, idEndereco);
        return Optional.ofNullable(endereco);
    }
}
