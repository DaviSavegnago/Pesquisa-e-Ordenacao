import Controller.SessaoController;
import View.SessaoView;
import Model.Sessao;

public class Main {

    public static void main(String[] args) {

        SessaoController controller = new SessaoController();
        SessaoView view = new SessaoView();

        controller.carregarArquivo();

        while (true) {

            view.exibirMenu();
            String opcao = view.solicitarOpcao(); // Alterado aqui

            if (opcao.equals("1")) {
                String numero = view.solicitarNumero();
                Sessao sessao = controller.buscarSessao(numero);
                view.exibirResultado(sessao);

            } else if (opcao.equals("2")) {
                String[] numeros = view.solicitarNumeros();
                controller.buscarMultiplasSessoes(numeros);

            } else if (opcao.equals("0")) {
                System.out.println("Encerrando programa...");
                break;

            } else {
                System.out.println("Opção inválida!");
            }
        }
    }
}