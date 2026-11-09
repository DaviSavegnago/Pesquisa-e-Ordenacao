
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

class TabelaHash {

    public static void inicializar(List[] tabela, int n) {
        for (int i = 0; i < n; i++) {
            tabela[i] = new ArrayList<String>();
        }
    }

    public static int minhaHash(String nome, int tamanho) {
        return Math.abs(nome.hashCode() % tamanho);
    }

    public static void inserir(String nome, List[] tabela) {
        int endereco = minhaHash(nome, tabela.length);
        tabela[endereco].add(nome);

        System.out.println(nome + " inserido na tabela no endereço " + endereco);
    }

    public static void exibir(List[] tabela) {
        for (int i = 0; i < tabela.length; i++) {
            System.out.println("Endereço " + i + ":");

            for (Object nome : tabela[i]) {
                System.out.println("  " + nome);
            }
        }
    }
}

public class Principal {

    public static void main(String[] args) {
        final int TAMANHO = 10;

        List[] tabelaHash = new List[TAMANHO];
        TabelaHash.inicializar(tabelaHash, TAMANHO);

        String nome;

        do {
            nome = JOptionPane.showInputDialog(
                null, "Informe um nome (ou digite SAIR para terminar)"
            );

            if (nome == null || nome.equalsIgnoreCase("SAIR")) {
                break;
            }

            if (!nome.trim().isEmpty()) {
                TabelaHash.inserir(nome, tabelaHash);
            }

        } while (true);

        TabelaHash.exibir(tabelaHash);

        JOptionPane.showMessageDialog(null, "Programa finalizado!");
    }
}
