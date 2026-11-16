package model;

/**
 *
 * @author davis
 */

import java.util.ArrayList;
import java.util.List;

public class BuscadorModel {

    // recebe o texto e a palavra, e devolve uma lista com as posições
    public List<int[]> buscarOcorrencias(String textoCompleto, String termoBusca) {
        List<int[]> posicoes = new ArrayList<>();

        if (termoBusca == null || termoBusca.isEmpty() || textoCompleto == null) {
            return posicoes;
        }

        // mutável e mais rápido que StringBuffer (sem trava de threads)
        StringBuilder sbTexto = new StringBuilder(textoCompleto.toLowerCase());
        String termoMinusculo = termoBusca.toLowerCase();

        int indice = 0;
        while ((indice = sbTexto.indexOf(termoMinusculo, indice)) != -1) {
            int fim = indice + termoMinusculo.length();
            posicoes.add(new int[]{indice, fim});
            indice = fim; // Continua a busca a partir do fim da palavra achada
        }

        return posicoes;
    }
}