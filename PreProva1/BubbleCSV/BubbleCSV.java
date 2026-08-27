import java.io.File;
import java.util.Scanner;

public class BubbleCSV {

    public static void main(String[] args) throws Exception {

        File arquivo = new File("vetor.csv");

        Scanner leitor = new Scanner(arquivo);

        String linha = leitor.nextLine();

        leitor.close();

        String[] partes = linha.split(",");

        int[] vetor = new int[partes.length];

        for (int i = 0; i < partes.length; i++) {

            vetor[i] = Integer.parseInt(partes[i]);
        }

        // BUBBLE SORT

        for (int i = 0; i < vetor.length - 1; i++) {

            for (int j = 0; j < vetor.length - 1 - i; j++) {

                if (vetor[j] > vetor[j + 1]) {

                    int temp = vetor[j];

                    vetor[j] = vetor[j + 1];

                    vetor[j + 1] = temp;
                }
            }
        }

        System.out.println("Vetor ordenado:");

        for (int i = 0; i < vetor.length; i++) {

            System.out.print(vetor[i] + " ");
        }
    }
}