package br.com.safezone.service;

import br.com.safezone.model.ResgateRecompensa;
import br.com.safezone.model.Usuario;
import br.com.safezone.model.Recompensa;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.LocalDate;

@ApplicationScoped
public class ResgateService {

    @Transactional
    public ResgateRecompensa resgatarPontos(Usuario usuario, Long recompensaId) {
        Recompensa recompensa = Recompensa.findById(recompensaId);
        if (recompensa == null || recompensa.status == 0) {
            throw new IllegalArgumentException("Recompensa inválida ou inativa");
        }

        // Cria registro de resgate
        ResgateRecompensa resgate = new ResgateRecompensa();
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