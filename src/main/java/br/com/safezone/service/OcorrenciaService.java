package br.com.safezone.service;


import br.com.safezone.dto.OcorrenciaDTO;
import br.com.safezone.model.Ocorrencia;
import br.com.safezone.model.Localizacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OcorrenciaService {

    @Inject
    LocalizacaoService localizacaoService;

    public List<OcorrenciaDTO> listarTodos() {
        List<Ocorrencia> ocorrencias =  Ocorrencia.listAll();

        List<OcorrenciaDTO> response = new ArrayList<>();
        for (Ocorrencia ocorrencia : ocorrencias) {
            response.add(new OcorrenciaDTO(ocorrencia));
        }

        return response;
    }

    public Ocorrencia buscarPorId(Long id) {
        return Ocorrencia.findByIdOptional(id)
                .map(l -> (Ocorrencia) l)
                .orElseThrow(() -> new NotFoundException("Ocorrencia com ID " + id + " não encontrada"));
    }

    public OcorrenciaDTO buscarPorIdCompleto(Long id) {
        Ocorrencia retornoOcorrencia = Ocorrencia.findByIdOptional(id)
                                        .map(l -> (Ocorrencia) l)
                                        .orElseThrow(() -> new NotFoundException("Ocorrencia com ID " + id + " não encontrada"));

        return  new OcorrenciaDTO(retornoOcorrencia);
    }

    @Transactional
    public Ocorrencia criar(OcorrenciaDTO ocorrencia) {

        if (ocorrencia.titulo == null || ocorrencia.titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("O titulo é obrigatório");
        }
        // Monta a entidade Ocorrencia
        Ocorrencia ocorrenciaModelo = new Ocorrencia();
        ocorrenciaModelo.prioridade = ocorrencia.prioridade;
        ocorrenciaModelo.dataCriacao = LocalDate.now();
        ocorrenciaModelo.titulo = ocorrencia.titulo;
        ocorrenciaModelo.descricao = ocorrencia.descricao;
        ocorrenciaModelo.raioAuxilio = ocorrencia.raioAuxilio;
        ocorrenciaModelo.categoria.id = ocorrencia.categoria.id;

        // persiste localização da Ocorrencia
        Localizacao localizacao = new Localizacao();
        localizacao.logradouro = ocorrencia.localizacao.logradouro;
        localizacao.bairro = ocorrencia.localizacao.bairro;
        localizacao.cidade = ocorrencia.localizacao.cidade;
        localizacao.estado = ocorrencia.localizacao.estado;
        localizacao.numero = ocorrencia.localizacao.numero;
        localizacao.CEP = ocorrencia.localizacao.cep;
        localizacao.latitudeLongitude = ocorrencia.localizacao.latitudeLongitude;

        ocorrenciaModelo.localizacao = localizacaoService.criar(localizacao);

        ocorrenciaModelo.persist();
        return ocorrenciaModelo;
    }

    @Transactional
    public Ocorrencia atualizar(Long id, OcorrenciaDTO ocorrenciaAtualizada) {
        Ocorrencia ocorrenciaExistente = buscarPorId(id);

        ocorrenciaExistente.status = ocorrenciaAtualizada.status;
        ocorrenciaExistente.prioridade = ocorrenciaAtualizada.prioridade;
        ocorrenciaExistente.titulo = ocorrenciaAtualizada.titulo;
        ocorrenciaExistente.descricao = ocorrenciaAtualizada.descricao;
        ocorrenciaExistente.raioAuxilio = ocorrenciaAtualizada.raioAuxilio;

        Localizacao localizacao = new Localizacao();
        localizacao.status = ocorrenciaExistente.status;
        localizacao.logradouro = ocorrenciaAtualizada.localizacao.logradouro;
        localizacao.bairro = ocorrenciaAtualizada.localizacao.bairro;
        localizacao.cidade = ocorrenciaAtualizada.localizacao.cidade;
        localizacao.estado = ocorrenciaAtualizada.localizacao.estado;
        localizacao.numero = ocorrenciaAtualizada.localizacao.numero;
        localizacao.CEP = ocorrenciaAtualizada.localizacao.cep;
        localizacao.latitudeLongitude = ocorrenciaAtualizada.localizacao.latitudeLongitude;
        Localizacao localicazaoAtualizada = localizacaoService.atualizar(ocorrenciaAtualizada.localizacao.id, localizacao);

        ocorrenciaExistente.localizacao= localicazaoAtualizada;

        ocorrenciaExistente.categoria.id = ocorrenciaAtualizada.categoria.id;

        return ocorrenciaExistente;
    }

    @Transactional
    public boolean deletar(Long id) {
        return Ocorrencia.deleteById(id);
    }
}