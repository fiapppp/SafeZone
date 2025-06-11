package br.com.safezone.service;

import br.com.safezone.model.Denuncia;
import br.com.safezone.model.Pontuacao;
import br.com.safezone.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@ApplicationScoped
public class PontuacaoService {

    private static final Map<Long, Integer> BASE_POINTS;
    static {
        BASE_POINTS = new HashMap<>();
        BASE_POINTS.put(1L, 800); // Enchente
        BASE_POINTS.put(2L, 640); // Queimada
        BASE_POINTS.put(3L, 600); // Tsunami
        BASE_POINTS.put(4L, 500); // Furacão
        BASE_POINTS.put(5L, 450); // Erupção
        BASE_POINTS.put(6L, 350); // Terremoto
        BASE_POINTS.put(7L, 250); // Deslizamento
        BASE_POINTS.put(8L, 200); // Tempestade
        BASE_POINTS.put(9L, 180); // Tempestade
    }

    /**
     * Concede pontos ao usuário com base na denúncia validada.
     * Pontos = BasePoints(categoria) * multiplier(prioridade)
     */
    @Transactional
    public void concederPontos(Denuncia denuncia) {
        int pontosCalculados = calcularPontos(denuncia);
        Pontuacao pontos = new Pontuacao();
        pontos.valorPontos = pontosCalculados;
        pontos.dataConcessao = LocalDate.now();
        pontos.denuncia = denuncia;
        pontos.status = 1;
        pontos.persist();
    }

    private int calcularPontos(Denuncia denuncia) {
        Long categoriaId = denuncia.categoria.id;
        Integer base = BASE_POINTS.getOrDefault(categoriaId, 0);
        int prioridade = denuncia.prioridade != null ? denuncia.prioridade : 1;
        int multiplier;
        switch (prioridade) {
            case 2:
                multiplier = 2; // media
                break;
            case 3:
                multiplier = 3; // alta
                break;
            default:
                multiplier = 1; // baixa ou undefined
        }
        return base * multiplier;
    }

    /**
     * Retorna a soma de todos os pontos concedidos para um usuário.
     */
    public int getPontuacaoTotal(Usuario usuario) {
        List<Pontuacao> lista = Pontuacao.list("denuncia.usuario.id", usuario.id);
        return lista.stream().mapToInt(p -> p.valorPontos).sum();
    }
}
