package br.com.safezone.service;

import br.com.safezone.model.ResgateDoacao;
import br.com.safezone.model.Usuario;
import br.com.safezone.model.Doacao;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.LocalDate;

@ApplicationScoped
public class ResgateService {

    @Transactional
    public ResgateDoacao resgatarPontos(Usuario usuario, Long doacaoId) {
        Doacao doacao = Doacao.findById(doacaoId);
        if (doacao == null || doacao.status == 0) {
            throw new IllegalArgumentException("Doacao inválida ou inativa");
        }

        // Cria registro de resgate
        ResgateDoacao resgate = new ResgateDoacao();
        resgate.dataResgate = LocalDate.now();
        resgate.status = 1;
        resgate.usuario = usuario;
        resgate.doacao = doacao;
        resgate.persist();

        // Atualiza disponibilidade da recompensa
        doacao.quantidadeDisponivel -= 1;
        doacao.persist();

        return resgate;
    }

    @Transactional
    public ResgateDoacao resgatarConversao(Usuario usuario, Long recompensaId, int unidades) {
        Doacao r = Doacao.findById(recompensaId);
        if (r == null || r.quantidadeConversao == null || r.quantidadeConversao <= 0) {
            throw new IllegalArgumentException("Doacao não disponível para conversão");
        }
        if (unidades < 1) {
            throw new IllegalArgumentException("Unidades inválidas");
        }
        int disponivel = r.quantidadeConversao;
        int aResgatar = Math.min(unidades, disponivel);
        r.quantidadeConversao = disponivel - aResgatar;
        // A persistência da Doacao acontece ao final da transação

        ResgateDoacao resgate = new ResgateDoacao();
        resgate.dataResgate = LocalDate.now();
        resgate.status = 1;
        resgate.usuario = usuario;
        resgate.doacao = r;
        resgate.persist();

        return resgate;
    }
}