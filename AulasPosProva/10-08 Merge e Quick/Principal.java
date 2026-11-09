
import java.util.Arrays;

public class Principal {

    public static void mergeSort(int[] vetor) {

        if (vetor.length < 2) {
            return;
        }

        int meio = vetor.length / 2;

        int[] esquerda = Arrays.copyOfRange(vetor, 0, meio);
        int[] direita = Arrays.copyOfRange(vetor, meio, vetor.length);

        mergeSort(esquerda);
        mergeSort(direita);

        merge(vetor, esquerda, direita);
    }

    public static void merge(int[] vetor, int[] esquerda, int[] direita) {

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < esquerda.length && j < direita.length) {

            if (esquerda[i] < direita[j]) {
                vetor[k] = esquerda[i];
                i++;
            } else {
                vetor[k] = direita[j];
                j++;
            }

            k++;
        }

        while (i < esquerda.length) {
            vetor[k] = esquerda[i];
            i++;
            k++;
        }

        while (j < direita.length) {
            vetor[k] = direita[j];
            j++;
            k++;
        }
    }

    public static void quickSort(int[] vetor, int inicio, int fim) {

        if (inicio < fim) {

            int pivo = particionar(vetor, inicio, fim);

            quickSort(vetor, inicio, pivo - 1);
            quickSort(vetor, pivo + 1, fim);
        }
    }

    public static int particionar(int[] vetor, int inicio, int fim) {

        int pivo = vetor[fim];
        int i = inicio - 1;

        for (int j = inicio; j < fim; j++) {

            if (vetor[j] < pivo) {
                i++;

                int aux = vetor[i];
                vetor[i] = vetor[j];
                vetor[j] = aux;
            }
        }

        int aux = vetor[i + 1];
        vetor[i + 1] = vetor[fim];
        vetor[fim] = aux;

        return i + 1;
    }

    public static void main(String[] args) {

        int[] vetor1 = {8, 3, 5, 1, 9, 2, 7, 4};
        int[] vetor2 = {8, 3, 5, 1, 9, 2, 7, 4};

        mergeSort(vetor1);
        quickSort(vetor2, 0, vetor2.length - 1);

        System.out.println("Merge Sort: " + Arrays.toString(vetor1));
        System.out.println("Quick Sort: " + Arrays.toString(vetor2));
    }
}
