package br.com.safezone.service;

import br.com.safezone.model.Localizacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class LocalizacaoService {

    public List<Localizacao> listarTodos() {
        return Localizacao.listAll();
    }

    public Localizacao buscarPorId(Long id) {
        return Localizacao.findByIdOptional(id)
                .map(l -> (Localizacao) l)
                .orElseThrow(() -> new NotFoundException("Localização com ID " + id + " não encontrada"));
    }

    @Transactional
    public Localizacao criar(Localizacao localizacao) {
        if (localizacao.cidade == null || localizacao.cidade.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da cidade é obrigatório");
        }

        localizacao.persist();
        return localizacao;
    }

    @Transactional
    public Localizacao atualizar(Long id, Localizacao localizacaoAtualizada) {
        Localizacao localizacaoExistente = buscarPorId(id);

        localizacaoExistente.status = localizacaoAtualizada.status;
        localizacaoExistente.logradouro = localizacaoAtualizada.logradouro;
        localizacaoExistente.bairro = localizacaoAtualizada.bairro;
        localizacaoExistente.cidade = localizacaoAtualizada.cidade;
        localizacaoExistente.estado = localizacaoAtualizada.estado;
        localizacaoExistente.CEP = localizacaoAtualizada.CEP;
        localizacaoExistente.numero = localizacaoAtualizada.numero;
        localizacaoExistente.latitudeLongitude = localizacaoAtualizada.latitudeLongitude;

        return localizacaoExistente;
    }

    @Transactional
    public boolean deletar(Long id) {
        return Localizacao.deleteById(id);
    }
}