package br.com.safezone.service;

import br.com.safezone.dto.DoacaoDTO;
import br.com.safezone.model.Doacao;
import br.com.safezone.model.TipoDoacao;
import br.com.safezone.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class DoacaoService {

    /**
     * Lista todas recompensas ativas convertidas para DTO
     */
    public List<DoacaoDTO> listarAtivas() {
        // Usa find() para retornar List<Doacao>
        List<Doacao> lista = Doacao.find("status", 1).list();
        return lista.stream()
                .map(r -> new DoacaoDTO(r))
                .collect(Collectors.toList());
    }

    /**
     * Cria uma nova recompensa associada ao usuário criador
     */
    @Transactional
    public DoacaoDTO criar(DoacaoDTO dto, Usuario criadoPor) {
        Doacao r = new Doacao();
        r.custoPontos = dto.custoPontos;
        r.dataValidade = dto.dataValidade;
        r.descricao = dto.descricao;
        r.quantidadeDisponivel = dto.quantidadeDisponivel;
        r.status = dto.status != null ? dto.status : 1;
        r.tipoDoacao = TipoDoacao.findById(dto.tipoDoacaoId);
        r.persist();
        return new DoacaoDTO(r);
    }

    /**
     * Atualiza uma recompensa existente e retorna DTO atualizado
     */
    @Transactional
    public DoacaoDTO atualizar(Long id, DoacaoDTO dto) {
        Doacao r = Doacao.findById(id);
        if (r == null) {
            throw new IllegalArgumentException("Doacao não encontrada: " + id);
        }
        r.custoPontos = dto.custoPontos;
        r.dataValidade = dto.dataValidade;
        r.descricao = dto.descricao;
        r.quantidadeDisponivel = dto.quantidadeDisponivel;
        r.status = dto.status;
        r.tipoDoacao = TipoDoacao.findById(dto.tipoDoacaoId);
        return new DoacaoDTO(r);
    }

    /**
     * Exclui uma recompensa pelo ID
     */
    @Transactional
    public void excluir(Long id) {
        Doacao r = Doacao.findById(id);
        if (r != null) {
            r.delete();
        }
    }
}
