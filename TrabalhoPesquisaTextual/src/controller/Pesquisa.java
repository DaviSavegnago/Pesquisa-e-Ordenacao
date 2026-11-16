package controller;

/**
 *
 * @author davis
 */

import model.BuscadorModel;
import view.InterfaceTelaDePesquisa;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class Pesquisa {

    private InterfaceTelaDePesquisa view;
    private BuscadorModel model;

    public Pesquisa(InterfaceTelaDePesquisa view, BuscadorModel model) {
        this.view = view;
        this.model = model;
        iniciarEventos();
    }

    private void iniciarEventos() {
        // KeyReleased: dispara na VOLTA da tecla, quando a letra já entrou no txtPesquisar
        this.view.getTxtPesquisar().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                executarBusca();
            }
        });
    }

    private void executarBusca() {
        
        Highlighter marcador = view.getTxfAreaTexto().getHighlighter();
        marcador.removeAllHighlights();

        String texto = view.getTxfAreaTexto().getText();
        String termo = view.getTxtPesquisar().getText();

        List<int[]> resultados = model.buscarOcorrencias(texto, termo);

        // pinta de amarelo na txfAreaTexto
        Highlighter.HighlightPainter pintorAmarelo = 
            new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);

        for (int[] posicao : resultados) {
            try {
                marcador.addHighlight(posicao[0], posicao[1], pintorAmarelo);
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
        }

        int total = resultados.size();
        view.getLblOcorrencias().setText(total + (total == 1 ? " ocorrência" : " ocorrências"));
    }
}