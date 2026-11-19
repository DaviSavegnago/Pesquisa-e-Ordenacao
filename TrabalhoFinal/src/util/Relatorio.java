
package util;

import model.Produto;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;

public class Relatorio {

    private static final int REPETICOES = 1000;
    private static final long LIMITE_BUBBLE = 120_000_000_000L;

    public static void gerarRelatorio() throws IOException {

        GerenciarArquivos gerenciador = new GerenciarArquivos();

        String[] arquivos = {
            "dados/produtos_1000.txt",
            "dados/produtos_10000.txt",
            "dados/produtos_50000.txt"
        };

        StringBuilder relatorio = new StringBuilder();

        relatorio.append("RELATORIO - PESQUISA E ORDENACAO\n");
        relatorio.append("====================================\n\n");

        relatorio.append(String.format(
            "%-38s | %-15s | %-15s | %-15s%n",
            "Operacao", "1.000", "10.000", "50.000"
        ));

        relatorio.append("-".repeat(95)).append("\n");

        String[][] resultados = new String[6][3];

        for (int i = 0; i < arquivos.length; i++) {

            System.out.println("Testando arquivo: " + arquivos[i]);

            ArrayList<Produto> produtos =
                    gerenciador.lerArquivo(arquivos[i]);

            ArrayList<Produto> listaBubble =
                    new ArrayList<>(produtos);

            long tempoBubble;

            if (produtos.size() == 50000) {

                tempoBubble = bubbleSortComLimite(
                    listaBubble,
                    Ordenacao.POR_PRECO,
                    LIMITE_BUBBLE
                );

            } else {

                tempoBubble = Ordenacao.bubbleSort(
                    listaBubble,
                    Ordenacao.POR_PRECO
                );
            }

            if (tempoBubble == -1) {
                resultados[0][i] = "nao concluido (> 2 min)";
            } else {
                resultados[0][i] = formatarTempo(tempoBubble);
            }

            ArrayList<Produto> listaMerge =
                    new ArrayList<>(produtos);

            long tempoMerge = Ordenacao.mergeSort(
                listaMerge,
                Ordenacao.POR_PRECO
            );

            resultados[1][i] = formatarTempo(tempoMerge);

            ArrayList<Produto> listaPorId =
                    new ArrayList<>(produtos);

            listaPorId.sort(
                Comparator.comparingInt(Produto::getId)
            );

            long inicioHash = System.nanoTime();

            HashMap<Integer, Produto> indice =
                    Buscador.criarIndiceID(produtos);

            long tempoHash = System.nanoTime() - inicioHash;

            resultados[5][i] = formatarTempo(tempoHash);

            long tempoLinear = 0;
            long tempoBinaria = 0;
            long tempoBuscaHash = 0;

            for (int repeticao = 0;
                    repeticao < REPETICOES;
                    repeticao++) {

                int posicao = (int) (
                    (long) repeticao * produtos.size()
                    / REPETICOES
                );

                int id = produtos.get(posicao).getId();

                long inicio = System.nanoTime();

                Buscador.buscaLinear(produtos, id);

                tempoLinear += System.nanoTime() - inicio;

                inicio = System.nanoTime();

                Buscador.buscaBinaria(listaPorId, id);

                tempoBinaria += System.nanoTime() - inicio;

                inicio = System.nanoTime();

                Buscador.buscaHashMap(indice, id);

                tempoBuscaHash += System.nanoTime() - inicio;
            }

            resultados[2][i] =
                    formatarTempo(tempoLinear / REPETICOES);

            resultados[3][i] =
                    formatarTempo(tempoBinaria / REPETICOES);

            resultados[4][i] =
                    formatarTempo(tempoBuscaHash / REPETICOES);

            System.out.println("Arquivo concluido!\n");
        }

        String[] operacoes = {
            "Ordenacao simples (por preco)",
            "Ordenacao eficiente (por preco)",
            "Busca linear por ID (media)",
            "Busca binaria por ID (media)",
            "Busca por HashMap (media)",
            "Construcao do HashMap"
        };

        for (int linha = 0; linha < operacoes.length; linha++) {

            relatorio.append(String.format(
                "%-38s | %-22s | %-22s | %-22s%n",
                operacoes[linha],
                resultados[linha][0],
                resultados[linha][1],
                resultados[linha][2]
            ));
        }

        relatorio.append("\nANALISE DOS RESULTADOS\n");
        relatorio.append("======================\n");

        relatorio.append(
            "A busca por HashMap geralmente apresenta o menor "
            + "tempo medio, pois utiliza acesso direto pela chave.\n"
        );

        relatorio.append(
            "A busca linear precisa percorrer os elementos, "
            + "enquanto a busca binaria reduz o intervalo de busca.\n"
        );

        relatorio.append(
            "O HashMap exige um tempo inicial para sua construção, "
            + "mas compensa quando varias consultas sao realizadas "
            + "sobre os mesmos dados.\n"
        );

        try (FileWriter escritor =
                new FileWriter("relatorio.txt")) {

            escritor.write(relatorio.toString());
        }

        System.out.println("Relatório gerado com sucesso!");
    }

    private static long bubbleSortComLimite(
            ArrayList<Produto> lista,
            Comparator<Produto> comparador,
            long limite) {

        long inicio = System.nanoTime();

        int tamanho = lista.size();

        for (int i = 0; i < tamanho - 1; i++) {

            boolean trocou = false;

            for (int j = 0; j < tamanho - i - 1; j++) {

                if (comparador.compare(
                        lista.get(j),
                        lista.get(j + 1)) > 0) {

                    Produto temporario = lista.get(j);

                    lista.set(j, lista.get(j + 1));

                    lista.set(j + 1, temporario);

                    trocou = true;
                }

                // Verificar o limite periodicamente
                if ((j & 4095) == 0
                        && System.nanoTime() - inicio > limite) {

                    return -1;
                }
            }

            if (!trocou) {
                break;
            }
        }

        return System.nanoTime() - inicio;
    }

    private static String formatarTempo(long nanos) {

        double milissegundos = nanos / 1_000_000.0;

        return String.format(
            Locale.US,
            "%.4f ms",
            milissegundos
        );
    }
}