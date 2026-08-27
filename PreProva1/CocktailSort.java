import java.util.Arrays;

public class CocktailSort {

    public static void main(String[] args) {

        int[] vetor = {5, 2, 8, 1, 4, 3};

        System.out.println("Antes: " + Arrays.toString(vetor));

        boolean houveTroca;

        int ini = 0; //inicio posição 0

        int fim = vetor.length - 1;

        do {

            houveTroca = false;

            // PRIMEIRA PARTE: ESQUERDA → DIREITA

            // "i" começa em ini.
            // i vai aumentando até chegar em fim.
            // Portanto, estamos andando da ESQUERDA para a DIREITA.
            for (int i = ini; i < fim; i++) {


                // SE o elemento atual for MAIOR, que o elemento que está imediatamente à sua direita, estão errados
                if (vetor[i] > vetor[i + 1]) {

                    // Guarda temporariamente o valor atual.
                    int temp = vetor[i];

                    // Coloca o elemento da direita na posição atual.
                    vetor[i] = vetor[i + 1];

                    // Coloca o valor antigo na posição da direita. SWAP.
                    vetor[i + 1] = temp;

                    // Informa que aconteceu uma troca.
                    houveTroca = true;
                }
            }

            fim--; 
            
            // ou seja, nesse exemplo, o 8 chegou no final na "1ª RODADA" então ele NÃO PARTICIPA MAIS

            // SE nenhuma troca aconteceu durante a passagem,
            // significa que o vetor já está ordenado.
            // Então podemos sair do algoritmo.
            if (!houveTroca) {
                break;
            }


            // Zera novamente a variável.
            // Agora vamos fazer uma nova passagem,
            // mas no sentido contrário.
            houveTroca = false;

            // SEGUNDA PARTE: DIREITA → ESQUERDA

            // Agora "i" começa no fim e vai DIMINUINDO.
            for (int i = fim; i > ini; i--) {


                // SE o elemento atual for MENOR
                // que o elemento que está imediatamente à sua esquerda,
                // significa que eles estão na ordem errada.
                if (vetor[i] < vetor[i - 1]) {

                    // Guarda temporariamente o elemento atual.
                    int temp = vetor[i];

                    // Coloca o elemento da esquerda na posição atual.
                    vetor[i] = vetor[i - 1];

                    // Coloca o valor guardado na posição da esquerda.
                    vetor[i - 1] = temp;

                    // Informa que aconteceu uma troca.
                    houveTroca = true;
                }
            }


            // Terminamos a passagem da direita para a esquerda.
            // O MENOR elemento da parte analisada chegou ao início.
            // Então podemos aumentar o início da área que ainda falta ordenar.
            ini++;


        // REPETE tudo enquanto:
        // 1. alguma troca aconteceu
        // E
        // 2. ainda existir uma parte do vetor para ordenar.
        } while (houveTroca && ini <= fim);


        // Mostra o vetor depois que a ordenação terminou.
        System.out.println("Depois do Cock: " + Arrays.toString(vetor));
    }
}