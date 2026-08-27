import java.util.Arrays;

public class SelectionSort {

    public static void main(String[] args) {

        int[] vetor = {5, 2, 8, 1, 4, 3};

        System.out.println("Antes: " + Arrays.toString(vetor));

        for (int i = 0; i < vetor.length - 1; i++) {

            int posMenor = i; //Considerar que o menor elemento está na posição i.

            for (int j = i + 1; j < vetor.length; j++) { //"j" começa na posição seguinte de i. 
            // Ele vai percorrer o restante do vetor procurando algo MENOR.

                if (vetor[j] < vetor[posMenor]) {  // SE o valor que está na posição j for MENOR que o atual menor, 
                    //temos um novo menor elemento.

                    posMenor = j; //Guardar a poosição do novo menor elemento.
                }
            }


            // SE a posição do menor elemento for diferente da posição i,
            // significa que precisamos fazer uma troca.
            if (i != posMenor) {

                // Guarda temporariamente o valor que está na posição i.
                int temp = vetor[i];

                // Coloca o menor elemento encontrado na posição i.
                vetor[i] = vetor[posMenor];

                // Coloca o valor antigo de i na posição onde estava o menor.
                vetor[posMenor] = temp;
            }
        }

        System.out.println("Depois do SS: " + Arrays.toString(vetor));
    }
}