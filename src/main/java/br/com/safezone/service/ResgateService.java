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
    public ResgateDoacao resgatarPontos(Usuario usuario, Long recompensaId) {
        Doacao recompensa = Doacao.findById(recompensaId);
        if (recompensa == null || recompensa.status == 0) {
            throw new IllegalArgumentException("Doacao inválida ou inativa");
        }

        // Cria registro de resgate
        ResgateDoacao resgate = new ResgateDoacao();
        resgate.dataResgate = LocalDate.now();
        resgate.status = 1;
        resgate.usuario = usuario;
        resgate.recompensa = recompensa;
        resgate.persist();

        // Atualiza disponibilidade da recompensa
        recompensa.quantidadeDisponivel -= 1;
        recompensa.persist();

        return resgate;
    }
}