package br.com.safezone.service;

import br.com.safezone.model.Parceiro;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class ParceiroService {

    public List<Parceiro> listarTodos() {
        return Parceiro.listAll();
    }

    public Parceiro buscarPorId(Long id) {
        return Parceiro.findById(id);
    }

    @Transactional
    public Parceiro criar(Parceiro parceiro) {
        parceiro.persist();
        return parceiro;
    }

    @Transactional
    public Parceiro atualizar(Long id, Parceiro dados) {
        Parceiro p = Parceiro.findById(id);
        if (p == null) throw new IllegalArgumentException("Parceiro não encontrado: " + id);
        p.nomeParceiro = dados.nomeParceiro;
        p.contato = dados.contato;
        p.cnpj = dados.cnpj;
        p.status = dados.status;
        return p;
    }

    @Transactional
    public void excluir(Long id) {
        Parceiro p = Parceiro.findById(id);
        if (p != null) {
            p.delete();
        }
    }
}