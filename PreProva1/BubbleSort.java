import java.util.Arrays;

public class BubbleSort {

    public static void main(String[] args) {

        int[] vetor = {5, 2, 8, 1, 4, 3};

        System.out.println("Antes:  " + Arrays.toString(vetor)); //Apenas p mostrar vetor antes da ordenação, transformando em texto com Arrays.toString()  

        for (int i = 0; i < vetor.length - 1; i++) { 

            for (int j = 0; j < vetor.length - 1 - i; j++) { // J controla a posição atual, andando da esquerda pra direita
                // O "- i" faz com que não precisemos comparar a parte que já foi ordenada.

                if (vetor[j] > vetor[j + 1]) { // Se o J for maior que o número da posição seguinte... trocamos a posição.

                    int temp = vetor[j]; //Guarda temporariamente o valor do vetor[j].
                    vetor[j] = vetor[j + 1]; // Coloca o valor da posição seguinte no lugar da posição do J.
                    vetor[j + 1] = temp;   // Coloca o valor guardado temporariamente na posição seguinte.
                }
            }
        }

        System.out.println("Depois: " + Arrays.toString(vetor)); //Agr ordenado.
    }
}