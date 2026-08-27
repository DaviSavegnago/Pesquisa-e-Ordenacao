import java.util.Arrays;

public class InsertionSort {

    public static void main(String[] args) {

        int[] vetor = {7, 3, 5, 1, 8, 2};

        System.out.println("Antes: " + Arrays.toString(vetor));

        // Começa o "i" na posição 1.
        // A posição 0 já é considerada uma parte ordenada.
        for (int i = 1; i < vetor.length; i++) {

            // Guarda o elemento que queremos colocar
            // na posição correta.
            int tmp = vetor[i];

            // "j" vai representar a posição anterior ao elemento.
            int j = i - 1;


            // Enquanto "j" não sair do vetor
            // E enquanto o elemento de "tmp" for menor
            // que o elemento que está em "j",
            // precisamos deslocar o elemento de "j" para a direita.
            while (j >= 0 && tmp < vetor[j]) {

                // O elemento que está em "j" é maior que "tmp".
                // Então ele precisa andar uma posição para a direita.
                vetor[j + 1] = vetor[j];

                // Faz "j" andar uma posição para trás.
                // Assim podemos comparar "tmp" com o próximo elemento.
                j--;
            }

            // Quando o while termina, encontramos
            // a posição correta para colocar "tmp".
            vetor[j + 1] = tmp;
        }

        System.out.println("Depois: " + Arrays.toString(vetor));
    }
}