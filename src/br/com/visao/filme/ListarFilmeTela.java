package br.com.visao.filme;

import br.com.controle.Filme; // Ajuste para o pacote da sua classe Filme
import br.com.controle.Genero;
import br.com.entidade.CadastrarFilme; // Ajuste para o pacote da sua classe DAO
import br.com.entidade.CadastrarGenero;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ListarFilmeTela extends JFrame {

    private JTable tabelaFilmes;
    private DefaultTableModel modeloTabela;
    private JButton btnAtualizar;
    private JButton btnFechar;

    public ListarFilmeTela() {
        configurarInterface();
        carregarTabela();
    }

    private void configurarInterface() {
        setTitle("Listagem de Filmes");
        setSize(850, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblTitulo = new JLabel("Catálogo de Filmes Cadastrados");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        String[] colunas = {
            "ID", "Título", "Ano", "Duração (min)", "Descrição",
            "ID Gênero", "ID Diretor", "ID Produtora", "ID Usuário"
        };

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaFilmes = new JTable(modeloTabela);
        tabelaFilmes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaFilmes.setRowHeight(24);
        tabelaFilmes.getTableHeader().setReorderingAllowed(false);

        tabelaFilmes.getColumnModel().getColumn(0).setPreferredWidth(40);
        tabelaFilmes.getColumnModel().getColumn(1).setPreferredWidth(180);
        tabelaFilmes.getColumnModel().getColumn(4).setPreferredWidth(220);

        JScrollPane scrollPane = new JScrollPane(tabelaFilmes);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnAtualizar = new JButton("Atualizar Lista");
        btnFechar = new JButton("Fechar");

        btnAtualizar.addActionListener(e -> carregarTabela());
        btnFechar.addActionListener(e -> dispose());

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnFechar);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        setContentPane(painelPrincipal);
    }

    private void carregarTabela() {
        modeloTabela.setRowCount(0);

        try {
            // TROQUE 'SuaClasseDAO' pelo nome exato da classe onde está o PesquisarTudo()
            CadastrarFilme cf = new CadastrarFilme();
            ArrayList<Filme> lista = cf.PesquisarTudo();

            for (Filme f : lista) {
                modeloTabela.addRow(new Object[]{
                    f.getId_filme(),
                    f.getTitulo(),
                    f.getAno_lancamento(),
                    f.getDuracao_min(),
                    f.getDescricao(),
                    f.getId_genero(),
                    f.getId_diretor(),
                    f.getId_produtora(),
                    f.getId_usuario()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar filmes: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new ListarFilmeTela().setVisible(true));
    }
}