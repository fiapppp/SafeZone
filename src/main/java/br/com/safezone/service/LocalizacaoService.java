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
        if (localizacao.nomeEstacao == null || localizacao.nomeEstacao.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da estação é obrigatório");
        }

        localizacao.persist();
        return localizacao;
    }

    @Transactional
    public Localizacao atualizar(Long id, Localizacao localizacaoAtualizada) {
        Localizacao localizacaoExistente = buscarPorId(id);

        localizacaoExistente.nomeEstacao = localizacaoAtualizada.nomeEstacao;
        localizacaoExistente.linha = localizacaoAtualizada.linha;
        localizacaoExistente.plataforma = localizacaoAtualizada.plataforma;
        localizacaoExistente.latitudeLongitude = localizacaoAtualizada.latitudeLongitude;
        localizacaoExistente.status = localizacaoAtualizada.status;

        return localizacaoExistente;
    }

    @Transactional
    public boolean deletar(Long id) {
        return Localizacao.deleteById(id);
    }
}