
package util;

import model.Produto;
import java.util.ArrayList;
import java.util.HashMap;

public class Buscador {

    // Busca linear por ID
    public static Produto buscaLinear(
            ArrayList<Produto> produtos,
            int id) {

        for (int i = 0; i < produtos.size(); i++) {

            if (produtos.get(i).getId() == id) {

                return produtos.get(i);
            }
        }

        return null;
    }

    // Busca binária por ID
    public static Produto buscaBinaria(
            ArrayList<Produto> produtos,
            int id) {

        int inicio = 0;
        int fim = produtos.size() - 1;

        while (inicio <= fim) {

            int meio = (inicio + fim) / 2;

            int idAtual = produtos.get(meio).getId();

            if (idAtual == id) {

                return produtos.get(meio);

            } else if (idAtual < id) {

                inicio = meio + 1;

            } else {

                fim = meio - 1;
            }
        }

        return null;
    }

    // Criar HashMap por ID
    public static HashMap<Integer, Produto> criarIndiceID(
            ArrayList<Produto> produtos) {

        HashMap<Integer, Produto> indice = new HashMap<>();

        for (Produto produto : produtos) {

            indice.put(produto.getId(), produto);
        }

        return indice;
    }

    // Busca por HashMap usando ID
    public static Produto buscaHashMap(
            HashMap<Integer, Produto> indice,
            int id) {

        return indice.get(id);
    }

    // Criar HashMap por categoria
    public static HashMap<String, ArrayList<Produto>> criarIndiceCategoria(
            ArrayList<Produto> produtos) {

        HashMap<String, ArrayList<Produto>> indice = new HashMap<>();

        for (Produto produto : produtos) {

            String categoria = produto.getCategoria();

            if (!indice.containsKey(categoria)) {

                indice.put(categoria, new ArrayList<>());
            }

            indice.get(categoria).add(produto);
        }

        return indice;
    }

    // Busca produtos por categoria
    public static ArrayList<Produto> buscaCategoria(
            HashMap<String, ArrayList<Produto>> indice,
            String categoria) {

        return indice.getOrDefault(categoria, new ArrayList<>());
    }
}
