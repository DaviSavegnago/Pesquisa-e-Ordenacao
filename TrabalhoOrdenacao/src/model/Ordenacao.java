package model;

public class Ordenacao {

    public static ResultadoOrdenacao bolha(int[] vetor) {

        long comparacoes = 0;
        long trocas = 0;

        long inicio = System.nanoTime();

        boolean houveTroca;

        do {
            houveTroca = false;

            for (int i = 0; i < vetor.length - 1; i++) {

                comparacoes++;

                if (vetor[i] > vetor[i + 1]) {

                    houveTroca = true;

                    int tmp = vetor[i];
                    vetor[i] = vetor[i + 1];
                    vetor[i + 1] = tmp;

                    trocas++;
                }
            }

        } while (houveTroca);

        long fim = System.nanoTime();

        return new ResultadoOrdenacao(comparacoes, trocas, fim - inicio);
    }


    public static ResultadoOrdenacao selecao(int[] vetor) {

        long comparacoes = 0;
        long trocas = 0;

        long inicio = System.nanoTime();

        for (int i = 0; i < vetor.length - 1; i++) {

            int posMenor = i;

            for (int j = i + 1; j < vetor.length; j++) {

                comparacoes++;

                if (vetor[j] < vetor[posMenor]) {
                    posMenor = j;
                }
            }

            if (i != posMenor) {

                int tmp = vetor[i];
                vetor[i] = vetor[posMenor];
                vetor[posMenor] = tmp;

                trocas++;
            }
        }

        long fim = System.nanoTime();

        return new ResultadoOrdenacao(comparacoes, trocas, fim - inicio);
    }


    public static ResultadoOrdenacao insercao(int[] vetor) {

        long comparacoes = 0;
        long trocas = 0;

        long inicio = System.nanoTime();

        for (int i = 1; i < vetor.length; i++) {

            int tmp = vetor[i];
            int j;

            for (j = i - 1; j >= 0; j--) {

                comparacoes++;

                if (tmp < vetor[j]) {

                    vetor[j + 1] = vetor[j];
                    trocas++;

                } else {
                    break;
                }
            }

            vetor[j + 1] = tmp;
            trocas++;
        }

        long fim = System.nanoTime();

        return new ResultadoOrdenacao(comparacoes, trocas, fim - inicio);
    }


    public static ResultadoOrdenacao agitacao(int[] vetor) {

        long comparacoes = 0;
        long trocas = 0;

        long inicio = System.nanoTime();

        boolean houveTroca;
        int ini = 0;
        int fim = vetor.length - 1;

        do {

            houveTroca = false;

            // esquerda → direita
            for (int i = ini; i < fim; i++) {

                comparacoes++;

                if (vetor[i] > vetor[i + 1]) {

                    houveTroca = true;

                    int tmp = vetor[i];
                    vetor[i] = vetor[i + 1];
                    vetor[i + 1] = tmp;

                    trocas++;
                }
            }

            fim--;

            if (!houveTroca) {
                break;
            }

            houveTroca = false;

            // direita → esquerda
            for (int i = fim; i > ini; i--) {

                comparacoes++;

                if (vetor[i] < vetor[i - 1]) {

                    houveTroca = true;

                    int tmp = vetor[i];
                    vetor[i] = vetor[i - 1];
                    vetor[i - 1] = tmp;

                    trocas++;
                }
            }

            ini++;

        } while (houveTroca && ini <= fim);

        long fimTempo = System.nanoTime();

        return new ResultadoOrdenacao(comparacoes, trocas, fimTempo - inicio);
    }
}