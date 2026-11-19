package util;

import model.Produto;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GerenciarArquivos {

    public ArrayList<Produto> lerArquivo(String caminho)
            throws IOException {

        ArrayList<Produto> produtos = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(
                Paths.get(caminho), StandardCharsets.UTF_8)) {

            br.readLine();

            String linha;

            while ((linha = br.readLine()) != null) {

                String[] campos = linha.split(";", -1);

                int id = Integer.parseInt(campos[0]);
                String nome = campos[1];
                String categoria = campos[2];
                double preco = Double.parseDouble(campos[3]);
                int estoque = Integer.parseInt(campos[4]);

                Produto produto = new Produto(
                        id, nome, categoria, preco, estoque
                );

                produtos.add(produto);
            }
        }

        return produtos;
    }

    public void exportarArquivo(
            ArrayList<Produto> produtos,
            String caminho
    ) throws IOException {

        try (BufferedWriter bw = Files.newBufferedWriter(
                Paths.get(caminho), StandardCharsets.UTF_8)) {

            bw.write("id;nome;categoria;preco;estoque");
            bw.newLine();   

            for (Produto p : produtos) {

                bw.write(
                        p.getId() + ";"
                        + p.getNome() + ";"
                        + p.getCategoria() + ";"
                        + p.getPreco() + ";"
                        + p.getEstoque()
                );

                bw.newLine();
            }
        }
    }
}
