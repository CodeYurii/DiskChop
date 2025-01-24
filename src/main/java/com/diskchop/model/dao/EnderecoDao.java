package com.diskchop.model.dao;

import com.diskchop.model.entity.Cliente;
import com.diskchop.model.entity.Endereco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public class EnderecoDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void salvar(Endereco endereco) {
        if (endereco.getIdEndereco() == null){
            em.persist(endereco);
        } else {
            em.merge(endereco);
        }
    }

    public List<Endereco> listarTodos() {
        return em.createQuery("SELECT e FROM Endereco e", Endereco.class).getResultList();
    }

    public Optional<Endereco> buscarPorId(Long idEndereco) {
        Endereco endereco  = em.find(Endereco.class, idEndereco);
        return Optional.ofNullable(endereco);
    }

    @Transactional
    public void remover(Long idEndereco) {
       Endereco endereco = buscarPorId(idEndereco).orElseThrow(() -> new RuntimeException("Endereco não encontrado"));
        em.remove(endereco);
    }
}
