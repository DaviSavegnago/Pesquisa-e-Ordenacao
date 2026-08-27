import java.util.Arrays;

public class CombSort { // O PENTE.

    public static void main(String[] args) {

        int[] vetor = {5, 2, 8, 1, 4, 3};

        System.out.println("Antes: " + Arrays.toString(vetor));

        // gap representa a DISTÂNCIA entre os dois elementos que serão comparados.
        // Começamos com o tamanho do vetor.
        // Porém, antes da primeira comparação, o gap será dividido por 1.3.
        // Neste exemplo, o vetor possui 6 elementos:
        // 6 / 1.3 = 4.
        // Portanto, a primeira comparação será feita com uma distância de 4 posições.
        int gap = vetor.length;

        // trocou informa se alguma troca aconteceu.
        // Começamos com true para garantir que o while seja executado.
        boolean trocou = true;


        // CONTINUA enquanto o gap ainda for maior que 1
        // OU enquanto ainda estiver acontecendo alguma troca.
        //
        // O Comb Sort começa comparando elementos que estão distantes.
        // A cada passagem, o gap diminui.
        // Quando o gap chega a 1, ele funciona de forma parecida com
        // o Bubble Sort, fazendo as últimas trocas necessárias.
        //
        // Neste exemplo:
        //
        // 1ª passagem: gap = 4
        // 5 com 4 → TROCA
        // 2 com 3 → NÃO TROCA
        // Resultado: [4, 2, 8, 1, 5, 3]
        //
        // 2ª passagem: gap = 3
        // 4 com 1 → TROCA
        // 2 com 5 → NÃO TROCA
        // 8 com 3 → TROCA
        // Resultado: [1, 2, 3, 4, 5, 8]
        //
        // Depois disso, o gap continua diminuindo até chegar a 1.
        // As passagens com gap = 1 verificam se ainda existe alguma
        // troca necessária para deixar o vetor completamente ordenado.


        while (gap > 1 || trocou) {

            // Diminui o tamanho do gap.
            // O Comb Sort começa comparando elementos distantes
            // e vai diminuindo essa distância a cada passagem.
            gap = (int) (gap / 1.3);


            // SE o cálculo fez o gap ficar menor que 1,
            // colocamos gap = 1.
            if (gap < 1) {
                gap = 1;
            }


            // Antes de começar essa nova passagem,
            // assumimos que nenhuma troca aconteceu.
            trocou = false;


            // "i" indica a primeira posição que estamos analisando.
            // "i + gap" indica a posição do segundo elemento.
            // O segundo elemento precisa existir dentro do vetor.
            for (int i = 0; i + gap < vetor.length; i++) {

                // SE o valor da posição i for MAIOR
                // que o valor que está a "gap" posições à frente,
                // significa que eles estão na ordem errada.
                if (vetor[i] > vetor[i + gap]) {

                    // Guarda temporariamente o valor da posição i.
                    int temp = vetor[i];

                    // Coloca o elemento que estava à frente na posição i.
                    vetor[i] = vetor[i + gap];

                    // Coloca o valor guardado na posição distante.
                    vetor[i + gap] = temp;

                    // Informa que uma troca aconteceu.
                    trocou = true;
                }
            }
        }

        System.out.println("Depois do CS   : " + Arrays.toString(vetor));
    }
}