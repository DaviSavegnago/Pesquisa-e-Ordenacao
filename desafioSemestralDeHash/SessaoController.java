package Controller;

import Model.Sessao;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SessaoController {

    private HashMap<String, Sessao> hashmap = new HashMap<>();

    public void carregarArquivo() {

        try {

            Scanner scanner = new Scanner(
                new File("Data/telephony_sessions.txt")
            );

            while (scanner.hasNextLine()) {

                String linha = scanner.nextLine();

                String[] partes = linha.split(";", 2);

                if (partes.length == 2) {

                    String numeroCelular = partes[0];

                    String dadosSessao = partes[1];

                    Sessao sessao = new Sessao(
                        numeroCelular,
                        dadosSessao
                    );

                    hashmap.put(numeroCelular, sessao);
                }
            }

            scanner.close();

            System.out.println(hashmap.size() + " sessões carregadas!");

        } catch (FileNotFoundException e) {

            System.out.println("Arquivo não encontrado!");
        }
    }

    public Sessao buscarSessao(String numeroCelular) {

        return hashmap.get(numeroCelular);
    }

    public void buscarMultiplasSessoes(String[] numeros) {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (String numero : numeros) {

            executor.execute(() -> {

                Sessao sessao = buscarSessao(numero);

                System.out.println(
                    Thread.currentThread().getName() + ": " + sessao
                );

                if (sessao == null) {
                    System.out.println("Número não encontrado: " + numero);
                }
            });
        }

        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}