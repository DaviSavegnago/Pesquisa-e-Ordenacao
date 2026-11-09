package View;

import java.util.Scanner;

public class SessaoView {

    private Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {
        System.out.println("\n===== SISTEMA DE TELEFONIA =====");
        System.out.println("1 - Buscar um celular");
        System.out.println("2 - Buscar vários celulares (Threads)");
        System.out.println("0 - Sair");
        System.out.print("Escolha a opção: ");
    }

    public String solicitarOpcao() {
        return scanner.nextLine().trim();
    }

    public String solicitarNumero() {
        System.out.print("Digite o número do celular: ");
        return scanner.nextLine().trim();
    }

    public String[] solicitarNumeros() {
        System.out.print("Digite os números separados por vírgula: ");
        String entrada = scanner.nextLine();
        String[] numeros = entrada.split(",");
        for (int i = 0; i < numeros.length; i++) {
            numeros[i] = numeros[i].trim(); // Remove espaços em branco
        }
        return numeros;
    }

    public void exibirResultado(Object resultado) {
        if (resultado != null) {
            System.out.println("\nSessão encontrada:");
            System.out.println(resultado);
        } else {
            System.out.println("\nSessão não encontrada!");
        }
    }
}