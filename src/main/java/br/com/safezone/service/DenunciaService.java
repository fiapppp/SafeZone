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
            dto.informacaoAdicional = d.informacaoAdicional;
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
            // evidencias: usar find().list() para manter tipagem
            List<Evidencia> evidenciasEntity = Evidencia.find("denuncia.id", d.id).list();
            List<EvidenciaDTO> evDTOs = evidenciasEntity.stream()
                    .map(ev -> new EvidenciaDTO(ev.id, ev.tipoEvidencia, ev.arquivo))
                    .collect(Collectors.toList());
            dto.evidencias = evDTOs;
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    public Denuncia criarComEvidencias(DenunciaDTO dto, Usuario usuario) {
        // Monta a entidade Denuncia
        Denuncia denuncia = new Denuncia();
        denuncia.dataDenuncia = dto.dataDenuncia != null ? dto.dataDenuncia : LocalDate.now();
        denuncia.descricao = dto.descricao;
        denuncia.informacaoAdicional = dto.informacaoAdicional;
        denuncia.prioridade = dto.prioridade != null ? dto.prioridade : 0;
        denuncia.status = 0;
        // definir usuário autenticado
        denuncia.usuario = usuario;
        // relacionamentos de categoria e localizacao
        denuncia.categoria = Categoria.findById(dto.idCategoria);
        denuncia.localizacao = Localizacao.findById(dto.idLocalizacao);

        // persiste denúncia
        denuncia.persist();

        // persiste todas evidências vinculadas, se houver
        if (dto.evidencia != null) {
            for (var evDto : dto.evidencia) {
                Evidencia ev = new Evidencia();
                ev.tipoEvidencia = evDto.tipoEvidencia;
                ev.arquivo = evDto.arquivo;
                ev.denuncia = denuncia;
                ev.persist();
            }
        }
        return denuncia;
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
        // Registro do histórico
        HistoricoDenuncia hist = new HistoricoDenuncia();
        hist.denuncia = denuncia;
        hist.dataAlteracao = LocalDate.now();
        hist.statusAnterior = denuncia.status;
        hist.statusAtual = novoStatus;
        hist.acao = observacao;
        Usuario responsavel = Usuario.findById(idUsuarioResponsavel);
        if (responsavel == null) {
            throw new IllegalArgumentException("Usuário responsável não encontrado: " + idUsuarioResponsavel);
        }
        hist.usuario = responsavel;
        hist.persist();

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
