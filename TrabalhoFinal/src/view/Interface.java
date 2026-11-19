package view;

import model.Produto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import util.Buscador;
import util.GerenciarArquivos;
import util.Ordenacao;

public class Interface extends javax.swing.JFrame {
    
    private ArrayList<Produto> produtos = new ArrayList<>();

    private ArrayList<Produto> produtosPorId = new ArrayList<>();

    private HashMap<Integer, Produto> indiceID;
    private HashMap<String, ArrayList<Produto>> indiceCategoria;

    private final GerenciarArquivos gerenciador = new GerenciarArquivos();
    
    private DefaultTableModel modeloTabela;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Interface.class.getName());

    public Interface() {
        initComponents();
        configurarInterface();
    }
    
    private void configurarInterface() {

        cmbArquivo.removeAllItems();
        cmbArquivo.addItem("produtos_1000.txt");
        cmbArquivo.addItem("produtos_10000.txt");
        cmbArquivo.addItem("produtos_50000.txt");

        cmbCampo.removeAllItems();
        cmbCampo.addItem("Nome");
        cmbCampo.addItem("Preço");

        cmbAlgoritmo.removeAllItems();
        cmbAlgoritmo.addItem("Bubble Sort");
        cmbAlgoritmo.addItem("Merge Sort");

        cmbTipoBusca.removeAllItems();
        cmbTipoBusca.addItem("ID");
        cmbTipoBusca.addItem("Categoria");

        cmbMetodoBusca.removeAllItems();
        cmbMetodoBusca.addItem("Busca Linear");
        cmbMetodoBusca.addItem("Busca Binária");
        cmbMetodoBusca.addItem("HashMap");

        modeloTabela = new DefaultTableModel(
            new Object[]{
                "ID", "Nome", "Categoria", "Preço", "Estoque"
            }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblTabelaProdutos.setModel(modeloTabela);

        lblTempo.setText("Tempo: aguardando operação");
        lblQuantidade.setText("Produtos: 0");

        btnCarregar.addActionListener(e -> carregarProdutos());
        btnOrdenar.addActionListener(e -> ordenarProdutos());
        btnPesquisar.addActionListener(e -> pesquisarProdutos());
}
    
    private void carregarProdutos() {

    try {
        String arquivo = "dados/" + cmbArquivo.getSelectedItem();

        produtos = gerenciador.lerArquivo(arquivo);

        produtosPorId = new ArrayList<>(produtos);

        produtosPorId.sort(
            Comparator.comparingInt(Produto::getId)
        );

        indiceID = Buscador.criarIndiceID(produtos);

        indiceCategoria = Buscador.criarIndiceCategoria(produtos);

        atualizarTabela(produtos);

        lblQuantidade.setText(
            "Produtos: " + produtos.size()
        );

        lblTempo.setText("Arquivo carregado com sucesso!");

        JOptionPane.showMessageDialog(
            this,
            produtos.size() + " produtos carregados!"
        );

    } catch (IOException e) {

        JOptionPane.showMessageDialog(
            this,
            "Erro ao carregar arquivo: " + e.getMessage(),
            "Erro",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
    
    private void atualizarTabela(ArrayList<Produto> lista) {

    modeloTabela.setRowCount(0);

    for (Produto produto : lista) {

        modeloTabela.addRow(new Object[]{
            produto.getId(),
            produto.getNome(),
            produto.getCategoria(),
            String.format("%.2f", produto.getPreco()),
            produto.getEstoque()
        });
    }

    lblQuantidade.setText(
        "Produtos exibidos: " + lista.size()
    );
}
    
    private void ordenarProdutos() {

    if (produtos.isEmpty()) {

        JOptionPane.showMessageDialog(
            this,
            "Carregue os produtos primeiro!"
        );

        return;
    }

    Comparator<Produto> comparador;

    if (cmbCampo.getSelectedIndex() == 0) {

        comparador = Ordenacao.POR_NOME;

    } else {

        comparador = Ordenacao.POR_PRECO;
    }

    long tempo;

    if (cmbAlgoritmo.getSelectedIndex() == 0) {

        tempo = Ordenacao.bubbleSort(
            produtos,
            comparador
        );

    } else {

        tempo = Ordenacao.mergeSort(
            produtos,
            comparador
        );
    }

    atualizarTabela(produtos);

    lblTempo.setText(
        String.format(
            "Tempo da ordenação: %.4f ms",
            tempo / 1_000_000.0
        )
    );

    JOptionPane.showMessageDialog(
        this,
        "Ordenação concluída!"
    );
}
    
   
private void pesquisarProdutos() {

    if (produtos.isEmpty()) {

        JOptionPane.showMessageDialog(
            this,
            "Carregue os produtos primeiro!"
        );

        return;
    }

    String pesquisa = txtPesquisa.getText().trim();

    if (pesquisa.isEmpty()) {

        JOptionPane.showMessageDialog(
            this,
            "Digite um ID ou uma categoria!"
        );

        return;
    }

    ArrayList<Produto> encontrados = new ArrayList<>();

    int repeticoes = 1000;

    long inicio;
    long tempoTotal = 0;

    try {

        if (cmbTipoBusca.getSelectedIndex() == 0) {

            int id = Integer.parseInt(pesquisa);

            Produto resultado = null;

            if (cmbMetodoBusca.getSelectedIndex() == 0) {

                for (int i = 0; i < repeticoes; i++) {

                    inicio = System.nanoTime();

                    resultado = Buscador.buscaLinear(
                        produtos,
                        id
                    );

                    tempoTotal += System.nanoTime() - inicio;
                }

            } else if (cmbMetodoBusca.getSelectedIndex() == 1) {

                for (int i = 0; i < repeticoes; i++) {

                    inicio = System.nanoTime();

                    resultado = Buscador.buscaBinaria(
                        produtosPorId,
                        id
                    );

                    tempoTotal += System.nanoTime() - inicio;
                }

            } else {

                for (int i = 0; i < repeticoes; i++) {

                    inicio = System.nanoTime();

                    resultado = Buscador.buscaHashMap(
                        indiceID,
                        id
                    );

                    tempoTotal += System.nanoTime() - inicio;
                }
            }

            if (resultado != null) {

                encontrados.add(resultado);

            }

        } else {

            if (cmbMetodoBusca.getSelectedIndex() != 2) {

                JOptionPane.showMessageDialog(
                    this,
                    "A busca por categoria utiliza HashMap!"
                );

                return;
            }

            ArrayList<Produto> resultado = null;

            for (int i = 0; i < repeticoes; i++) {

                inicio = System.nanoTime();

                resultado = Buscador.buscaCategoria(
                    indiceCategoria,
                    pesquisa
                );

                tempoTotal += System.nanoTime() - inicio;
            }

            if (resultado != null) {
                encontrados.addAll(resultado);
            }
        }

        atualizarTabela(encontrados);

        double tempoMedio = tempoTotal
                / (double) repeticoes
                / 1_000_000.0;

        lblTempo.setText(
            String.format(
                "Tempo médio da busca: %.4f ms",
                tempoMedio
            )
        );

        if (encontrados.isEmpty()) {

            JOptionPane.showMessageDialog(
                this,
                "Nenhum produto encontrado!"
            );

        }

    } catch (NumberFormatException e) {

        JOptionPane.showMessageDialog(
            this,
            "Para pesquisar por ID, digite apenas números!"
        );
    }
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblArquivo = new javax.swing.JLabel();
        lblCampo = new javax.swing.JLabel();
        lblAlgoritmo = new javax.swing.JLabel();
        btnOrdenar = new javax.swing.JButton();
        lblMetodoBusca = new javax.swing.JLabel();
        cmbCampo = new javax.swing.JComboBox<>();
        cmbAlgoritmo = new javax.swing.JComboBox<>();
        cmbArquivo = new javax.swing.JComboBox<>();
        btnCarregar = new javax.swing.JButton();
        lblTipoBusca = new javax.swing.JLabel();
        cmbTipoBusca = new javax.swing.JComboBox<>();
        cmbMetodoBusca = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTabelaProdutos = new javax.swing.JTable();
        lblQuantidade = new javax.swing.JLabel();
        lblTempo = new javax.swing.JLabel();
        txtPesquisa = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitulo.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
        lblTitulo.setText("INTERFACE TRABALHO FINAL");

        lblArquivo.setText("PRIMEIRO SELECIONE O TIPO DO ARQUIVO:");

        lblCampo.setText("CAMPO:");

        lblAlgoritmo.setText("ALGORITMO:");

        btnOrdenar.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        btnOrdenar.setText("ORDENAR PRODUTOS");
        btnOrdenar.addActionListener(this::btnOrdenarActionPerformed);

        lblMetodoBusca.setText("MÉTODO:");

        cmbCampo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nome", "Preço" }));
        cmbCampo.addActionListener(this::cmbCampoActionPerformed);

        cmbAlgoritmo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bubble Sort", "Merge Sort" }));

        cmbArquivo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "produtos_1000.txt", "produtos_10000.txt", "produtos_50000.txt" }));

        btnCarregar.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        btnCarregar.setText("CARREGAR");

        lblTipoBusca.setText("TIPO DE BUSCA:");

        cmbTipoBusca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Categoria" }));

        cmbMetodoBusca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Linear", "Binária", "HashMap" }));
        cmbMetodoBusca.addActionListener(this::cmbMetodoBuscaActionPerformed);

        tblTabelaProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblTabelaProdutos);

        lblQuantidade.setText("-");

        lblTempo.setText("-");

        txtPesquisa.setText("Digite o ID ou a Categoria");

        btnPesquisar.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        btnPesquisar.setText("PESQUISAR");
        btnPesquisar.addActionListener(this::btnPesquisarActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(302, 302, 302)
                        .addComponent(lblTitulo))
                    .addComponent(btnCarregar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 26, Short.MAX_VALUE)
                                .addComponent(lblArquivo)
                                .addGap(18, 18, 18)
                                .addComponent(cmbArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 558, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(lblTipoBusca)
                                        .addGap(32, 32, 32)
                                        .addComponent(cmbTipoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(58, 58, 58)
                                        .addComponent(lblMetodoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbMetodoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lblAlgoritmo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblCampo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cmbAlgoritmo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(cmbCampo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(txtPesquisa))
                                .addGap(28, 28, 28)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnOrdenar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTempo, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(lblTitulo)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblArquivo)
                    .addComponent(cmbArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnCarregar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCampo)
                            .addComponent(cmbCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblAlgoritmo)
                            .addComponent(cmbAlgoritmo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(btnOrdenar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoBusca)
                    .addComponent(cmbTipoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMetodoBusca)
                    .addComponent(cmbMetodoBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addComponent(txtPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTempo)
                    .addComponent(lblQuantidade))
                .addGap(70, 70, 70))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbCampoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCampoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCampoActionPerformed

    private void btnOrdenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrdenarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOrdenarActionPerformed

    private void cmbMetodoBuscaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMetodoBuscaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMetodoBuscaActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPesquisarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Interface().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCarregar;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JComboBox<String> cmbAlgoritmo;
    private javax.swing.JComboBox<String> cmbArquivo;
    private javax.swing.JComboBox<String> cmbCampo;
    private javax.swing.JComboBox<String> cmbMetodoBusca;
    private javax.swing.JComboBox<String> cmbTipoBusca;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAlgoritmo;
    private javax.swing.JLabel lblArquivo;
    private javax.swing.JLabel lblCampo;
    private javax.swing.JLabel lblMetodoBusca;
    private javax.swing.JLabel lblQuantidade;
    private javax.swing.JLabel lblTempo;
    private javax.swing.JLabel lblTipoBusca;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JTable tblTabelaProdutos;
    private javax.swing.JTextField txtPesquisa;
    // End of variables declaration//GEN-END:variables
}
