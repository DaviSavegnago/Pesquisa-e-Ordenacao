
package util;

import model.Produto;
import java.util.ArrayList;
import java.util.Comparator;

public class Ordenacao {

    //comparando por nome
    public static final Comparator<Produto> POR_NOME =
            Comparator.comparing(
                    Produto::getNome,
                    String.CASE_INSENSITIVE_ORDER
            );

    //comparando por preço
    public static final Comparator<Produto> POR_PRECO =
            Comparator.comparingDouble(Produto::getPreco);

    
    //bubble
    public static long bubbleSort(
            ArrayList<Produto> produtos,
            Comparator<Produto> comparador) {

        long inicio = System.nanoTime();

        int n = produtos.size();

        for (int i = 0; i < n - 1; i++) {

            boolean trocou = false;

            for (int j = 0; j < n - 1 - i; j++) {

                if (comparador.compare(
                        produtos.get(j),
                        produtos.get(j + 1)) > 0) {

                    Produto auxiliar = produtos.get(j);

                    produtos.set(j, produtos.get(j + 1));

                    produtos.set(j + 1, auxiliar);

                    trocou = true;
                }
            }

            if (!trocou) {
                break;
            }
        }

        long fim = System.nanoTime();

        return fim - inicio;
    }


    //merge
    public static long mergeSort(
            ArrayList<Produto> produtos,
            Comparator<Produto> comparador) {

        Produto[] dados = produtos.toArray(new Produto[0]);
        Produto[] auxiliar = new Produto[dados.length];

        long inicio = System.nanoTime();

        ordenarMerge(
                dados,
                auxiliar,
                0,
                dados.length - 1,
                comparador
        );

        long fim = System.nanoTime();

        for (int i = 0; i < dados.length; i++) {
            produtos.set(i, dados[i]);
        }

        return fim - inicio;
    }

    private static void ordenarMerge(
            Produto[] dados,
            Produto[] auxiliar,
            int inicio,
            int fim,
            Comparator<Produto> comparador) {

        if (inicio < fim) {

            int meio = (inicio + fim) / 2;

            ordenarMerge(
                    dados, auxiliar,
                    inicio, meio, comparador
            );

            ordenarMerge(
                    dados, auxiliar,
                    meio + 1, fim, comparador
            );

            intercalar(
                    dados, auxiliar,
                    inicio, meio, fim, comparador
            );
        }
    }

    private static void intercalar(
            Produto[] dados,
            Produto[] auxiliar,
            int inicio,
            int meio,
            int fim,
            Comparator<Produto> comparador) {

        for (int i = inicio; i <= fim; i++) {
            auxiliar[i] = dados[i];
        }

        int esquerda = inicio;
        int direita = meio + 1;
        int atual = inicio;

        while (esquerda <= meio && direita <= fim) {

            if (comparador.compare(
                    auxiliar[esquerda],
                    auxiliar[direita]) <= 0) {

                dados[atual] = auxiliar[esquerda];
                esquerda++;

            } else {

                dados[atual] = auxiliar[direita];
                direita++;
            }

            atual++;
        }

        while (esquerda <= meio) {

            dados[atual] = auxiliar[esquerda];

            esquerda++;
            atual++;
        }
    }
}
