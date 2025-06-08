package br.com.safezone.service;

import br.com.safezone.dto.RecompensaDTO;
import br.com.safezone.model.Recompensa;
import br.com.safezone.model.TipoRecompensa;
import br.com.safezone.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class RecompensaService {

    /**
     * Lista todas recompensas ativas convertidas para DTO
     */
    public List<RecompensaDTO> listarAtivas() {
        // Usa find() para retornar List<Recompensa>
        List<Recompensa> lista = Recompensa.find("status", 1).list();
        return lista.stream()
                .map(r -> new RecompensaDTO(r))
                .collect(Collectors.toList());
    }

    /**
     * Cria uma nova recompensa associada ao usuário criador
     */
    @Transactional
    public RecompensaDTO criar(RecompensaDTO dto, Usuario criadoPor) {
        Recompensa r = new Recompensa();
        r.custoPontos = dto.custoPontos;
        r.dataValidade = dto.dataValidade;
        r.descricao = dto.descricao;
        r.quantidadeDisponivel = dto.quantidadeDisponivel;
        r.valor = dto.valor;
        r.status = dto.status != null ? dto.status : 1;
        r.tipoRecompensa = TipoRecompensa.findById(dto.tipoRecompensaId);
        r.persist();
        return new RecompensaDTO(r);
    }

    /**
     * Atualiza uma recompensa existente e retorna DTO atualizado
     */
    @Transactional
    public RecompensaDTO atualizar(Long id, RecompensaDTO dto) {
        Recompensa r = Recompensa.findById(id);
        if (r == null) {
            throw new IllegalArgumentException("Recompensa não encontrada: " + id);
        }
        r.custoPontos = dto.custoPontos;
        r.dataValidade = dto.dataValidade;
        r.descricao = dto.descricao;
        r.quantidadeDisponivel = dto.quantidadeDisponivel;
        r.valor = dto.valor;
        r.status = dto.status;
        r.tipoRecompensa = TipoRecompensa.findById(dto.tipoRecompensaId);
        return new RecompensaDTO(r);
    }

    /**
     * Exclui uma recompensa pelo ID
     */
    @Transactional
    public void excluir(Long id) {
        Recompensa r = Recompensa.findById(id);
        if (r != null) {
            r.delete();
        }
    }
}
