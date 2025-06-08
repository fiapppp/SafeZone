package br.com.safezone.service;

import br.com.safezone.dto.*;
import br.com.safezone.model.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class DenunciaService {

    @Inject
    PontuacaoService pontuacaoService;

    @Inject
    LocalizacaoService localizacaoService;

    @Inject
    OcorrenciaService ocorrenciaService;

    /**
     * Retorna todas denúncias de um usuário, com objetos associados.
     */
    public List<DenunciaResponseDTO> listarPorUsuarioComDetalhes(Long usuarioId) {
        List<Denuncia> denuncias = Denuncia.list("usuario.id", usuarioId);
        return denuncias.stream().map(d -> {
            DenunciaResponseDTO dto = new DenunciaResponseDTO();
            dto.id = d.id;
            dto.dataDenuncia = d.dataDenuncia;
            dto.descricao = d.descricao;
            dto.status = d.status;
            dto.dataConclusao = d.dataConclusao;
            dto.prioridade = d.prioridade;
            // categoria
            Categoria cat = d.categoria;
            dto.categoria = new CategoriaDTO(cat.id, cat.nome, cat.descricao);
            // localizacao
            Localizacao loc = d.localizacao;
            dto.localizacao = new LocalizacaoDTO(
                    loc.id, loc.logradouro, loc.bairro, loc.cidade, loc.estado,loc.CEP, loc.numero, loc.latitudeLongitude
            );
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public Denuncia criarDenunciaCompleta(DenunciaDTO dto, Usuario usuario) {

        // Monta a entidade Denuncia
        Denuncia denuncia = new Denuncia();
        denuncia.dataDenuncia = dto.dataDenuncia != null ? dto.dataDenuncia : LocalDate.now();
        denuncia.titulo = dto.titulo;
        denuncia.descricao = dto.descricao;
        denuncia.prioridade = dto.prioridade != null && dto.prioridade > 0 ? dto.prioridade : 0;
        if (dto.idOcorrencia > 0)
            denuncia.ocorrencia = ocorrenciaService.buscarPorId(dto.idOcorrencia);
        else
            throw new IllegalArgumentException("Denúncia não pode ser aberta sem uma ocorrencia");

        // definir usuário autenticado como autor da denuncia
        denuncia.usuario = usuario;

        denuncia.categoria = Categoria.findById(dto.idCategoria);

        // inseri localização da Denuncia
        Localizacao localizacao = new Localizacao();
        localizacao.CEP = dto.CEP;
        localizacao.logradouro = dto.logradouro;
        localizacao.bairro = dto.bairro;
        localizacao.cidade = dto.cidade;
        localizacao.estado = dto.estado;
        localizacao.numero = dto.numero;
        Localizacao localizacaoCriada = localizacaoService.criar(localizacao);

        denuncia.localizacao = localizacaoCriada;

        CidadaoPrejudicadoOUApoiador(denuncia);

        // persiste denúncia
        denuncia.persist();

        return denuncia;
    }

    public void CidadaoPrejudicadoOUApoiador(Denuncia denuncia) {
        boolean prejudicado;
        switch (denuncia.ocorrencia.raioAuxilio){
            case "LOGRADOURO":
                prejudicado = denuncia.ocorrencia.localizacao.logradouro.equals(denuncia.usuario.localizacao.logradouro);
            case "BAIRRO":
                prejudicado = denuncia.ocorrencia.localizacao.bairro.equals(denuncia.usuario.localizacao.bairro);
                break;
            case "CIDADE":
                prejudicado = denuncia.ocorrencia.localizacao.cidade.equals(denuncia.usuario.localizacao.cidade);
                break;
            case "ESTADO":
                prejudicado = denuncia.ocorrencia.localizacao.estado.equals(denuncia.usuario.localizacao.estado);
                break;
            default:
                prejudicado = false;
                break;
        }

        if(prejudicado)
            denuncia.cidadao_prejudicado = denuncia.usuario;
        else
            denuncia.cidadao_apoidador = denuncia.usuario;

    }

    /**
     * Cria uma nova denúncia: define data, status inicial e persiste.
     */
    @Transactional
    public Denuncia criar(Denuncia denuncia) {
        denuncia.dataDenuncia = LocalDate.now();
        denuncia.status = 0; // aberto
        denuncia.persist();
        return denuncia;
    }

    /**
     * Lista denúncias: se status for nulo, retorna todas; senão filtra.
     */
    public List<Denuncia> listar(Integer status) {
        if (status == null) {
            return Denuncia.listAll();
        }
        return Denuncia.list("status", status);
    }

    /**
     * Lista todas as denúncias realizadas por um usuário específico.
     */
    public List<Denuncia> listarPorUsuario(Long usuarioId) {
        return Denuncia.list("usuario.id", usuarioId);
    }

    /**
     * Atualiza status e observação; registra conclusão e pontuação se validada.
     */
    @Transactional
    public Denuncia atualizarStatus(
            Long id,
            Integer novoStatus,
            String observacao,
            Long idUsuarioResponsavel
    ) {
        Denuncia denuncia = Denuncia.findById(id);
        if (denuncia == null) {
            throw new IllegalArgumentException("Denúncia não encontrada: " + id);
        }

        // Atualização da denúncia
        denuncia.status = novoStatus;
        denuncia.observacaoResponsavel = observacao;
        if (novoStatus == 2) { // validada
            denuncia.dataConclusao = LocalDate.now();
            // gera pontuação para o usuário
            pontuacaoService.concederPontos(denuncia);
        }
        return denuncia;
    }
}
