package controller;

import model.Ordenacao;
import model.ResultadoOrdenacao;
import java.util.Arrays;
import java.util.Random;

public class OrdenacaoController {

    public void executar() {

        int tamanho = 10000;

        int[] vetorOriginal = gerarVetor(tamanho);

        int[] vetorBolha = Arrays.copyOf(vetorOriginal, vetorOriginal.length);
        int[] vetorSelecao = Arrays.copyOf(vetorOriginal, vetorOriginal.length);
        int[] vetorInsercao = Arrays.copyOf(vetorOriginal, vetorOriginal.length);
        int[] vetorAgitacao = Arrays.copyOf(vetorOriginal, vetorOriginal.length);

        ResultadoOrdenacao resultadoBolha = Ordenacao.bolha(vetorBolha);
        ResultadoOrdenacao resultadoSelecao = Ordenacao.selecao(vetorSelecao);
        ResultadoOrdenacao resultadoInsercao = Ordenacao.insercao(vetorInsercao);
        ResultadoOrdenacao resultadoAgitacao = Ordenacao.agitacao(vetorAgitacao);

        System.out.println("===== RESULTADOS =====");

        mostrarResultado("BOLHA", resultadoBolha);
        mostrarResultado("SELEÇÃO", resultadoSelecao);
        mostrarResultado("INSERÇÃO", resultadoInsercao);
        mostrarResultado("AGITAÇÃO", resultadoAgitacao);
    }

    private int[] gerarVetor(int tamanho) {

        int[] vetor = new int[tamanho];

        Random random = new Random();

        for (int i = 0; i < tamanho; i++) {
            vetor[i] = random.nextInt(1000);
        }

        return vetor;
    }

    private void mostrarResultado(String nome, ResultadoOrdenacao resultado) {

        System.out.println("\n" + nome);
        System.out.println("Comparações: " + resultado.getComparacoes());
        System.out.println("Trocas: " + resultado.getTrocas());
        System.out.println("Tempo: " + resultado.getTempo() + " ns");
    }
}