package Main;

/**
 *
 * @author davis
 */

import controller.Pesquisa;
import model.BuscadorModel;
import view.InterfaceTelaDePesquisa;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfaceTelaDePesquisa view = new InterfaceTelaDePesquisa();
            BuscadorModel model = new BuscadorModel();
            
            // Conecta a sua View e o Model através do Controller
            new Pesquisa(view, model);
            
            view.setVisible(true);
        });
    }
}