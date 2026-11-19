
package util;

import model.Produto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class TesteArquivos {

    public static void main(String[] args) {

        GerenciarArquivos gerenciador = new GerenciarArquivos();

        ArrayList<Produto> produtos = new ArrayList<>();

        try {

            // Ler arquivo com 1000 produtos
            produtos = gerenciador.lerArquivo(
                "dados/produtos_1000.txt"
            );

            System.out.println("Quantidade de produtos: "
                    + produtos.size());

            // Mostrar os primeiros 20 produtos
            System.out.println("\nPrimeiros produtos:");

            for (int i = 0; i < Math.min(20, produtos.size()); i++) {
                System.out.println(produtos.get(i));
            }

            // Exportar produtos para outro arquivo
            gerenciador.exportarArquivo(
                produtos,
                "dados/produtos_exportados.txt"
            );

            System.out.println("\nArquivo exportado com sucesso!");

            // ==========================================
            // TESTES DE ORDENACAO
            // ==========================================

            // Bubble Sort por nome
            ArrayList<Produto> listaBubbleNome =
                    new ArrayList<>(produtos);

            long tempoBubbleNome = Ordenacao.bubbleSort(
                listaBubbleNome,
                Ordenacao.POR_NOME
            );

            System.out.println("\nBubble Sort por nome: "
                    + tempoBubbleNome + " ns");

            // Merge Sort por nome
            ArrayList<Produto> listaMergeNome =
                    new ArrayList<>(produtos);

            long tempoMergeNome = Ordenacao.mergeSort(
                listaMergeNome,
                Ordenacao.POR_NOME
            );

            System.out.println("Merge Sort por nome: "
                    + tempoMergeNome + " ns");

            // Bubble Sort por preco
            ArrayList<Produto> listaBubblePreco =
                    new ArrayList<>(produtos);

            long tempoBubblePreco = Ordenacao.bubbleSort(
                listaBubblePreco,
                Ordenacao.POR_PRECO
            );

            System.out.println("Bubble Sort por preco: "
                    + tempoBubblePreco + " ns");

            // Merge Sort por preco
            ArrayList<Produto> listaMergePreco =
                    new ArrayList<>(produtos);

            long tempoMergePreco = Ordenacao.mergeSort(
                listaMergePreco,
                Ordenacao.POR_PRECO
            );

            System.out.println("Merge Sort por preco: "
                    + tempoMergePreco + " ns");

            // ==========================================
            // TESTES DE BUSCA POR ID
            // ==========================================

            // Preparar lista ordenada por ID
            ArrayList<Produto> produtosPorId =
                    new ArrayList<>(produtos);

            produtosPorId.sort(
                Comparator.comparingInt(Produto::getId)
            );

            // Escolher um ID para procurar
            int idProcurado = produtos.get(0).getId();

            int repeticoes = 1000;

            // Busca linear
            long inicioLinear = System.nanoTime();

            for (int i = 0; i < repeticoes; i++) {
                Buscador.buscaLinear(produtos, idProcurado);
            }

            long tempoLinear =
                    (System.nanoTime() - inicioLinear) / repeticoes;

            System.out.println("\nBusca linear: "
                    + tempoLinear + " ns (media)");

            // Busca binaria
            long inicioBinaria = System.nanoTime();

            for (int i = 0; i < repeticoes; i++) {
                Buscador.buscaBinaria(produtosPorId, idProcurado);
            }

            long tempoBinaria =
                    (System.nanoTime() - inicioBinaria) / repeticoes;

            System.out.println("Busca binaria: "
                    + tempoBinaria + " ns (media)");

            // Criar HashMap por ID
            long inicioHash = System.nanoTime();

            HashMap<Integer, Produto> indiceID =
                    Buscador.criarIndiceID(produtos);

            long tempoCriacaoHash =
                    System.nanoTime() - inicioHash;

            System.out.println("Construcao HashMap: "
                    + tempoCriacaoHash + " ns");

            // Busca HashMap
            long inicioBuscaHash = System.nanoTime();

            for (int i = 0; i < repeticoes; i++) {
                Buscador.buscaHashMap(indiceID, idProcurado);
            }

            long tempoBuscaHash =
                    (System.nanoTime() - inicioBuscaHash) / repeticoes;

            System.out.println("Busca HashMap: "
                    + tempoBuscaHash + " ns (media)");

            // ==========================================
            // TESTE DE BUSCA POR CATEGORIA
            // ==========================================

            HashMap<String, ArrayList<Produto>> indiceCategoria =
                    Buscador.criarIndiceCategoria(produtos);

            String categoriaProcurada =
                    produtos.get(0).getCategoria();

            ArrayList<Produto> encontrados =
                    Buscador.buscaCategoria(
                        indiceCategoria,
                        categoriaProcurada
                    );

            System.out.println("\nBusca por categoria: "
                    + categoriaProcurada);

            System.out.println("Produtos encontrados: "
                    + encontrados.size());

            for (Produto produto : encontrados) {
                System.out.println(produto);
            }

            // ==========================================
            // GERAR RELATORIO AUTOMATICAMENTE
            // ==========================================

            System.out.println("\nGerando relatorio...");

            Relatorio.gerarRelatorio();

        } catch (IOException e) {

            System.out.println("Erro ao acessar arquivo: "
                    + e.getMessage());

        } catch (NumberFormatException e) {

            System.out.println("Erro ao converter dados: "
                    + e.getMessage());

        } catch (Exception e) {

            System.out.println("Erro inesperado: "
                    + e.getMessage());

            e.printStackTrace();
        }
    }
}
