package br.com.visao.filme;

import br.com.controle.Filme; // Ajuste para o pacote da sua classe Filme
import br.com.entidade.CadastrarFilme;
// import br.com.entidade.ManterFilme; // Ajuste para o pacote da sua classe DAO
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DeletarFilmeTela extends JFrame {

    private JTextField txtIdFilme;
    private JButton btnBuscar;

    private JTextField txtTitulo;
    private JTextField txtAno;
    private JTextField txtDuracao;
    private JTextField txtDescricao;

    private JButton btnExcluir;
    private JButton btnLimpar;
    private JButton btnFechar;

    public DeletarFilmeTela() {
        configurarInterface();
    }

    private void configurarInterface() {
        setTitle("Deletar Filme");
        setSize(480, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Topo: Título + Busca por ID
        JPanel painelTopo = new JPanel(new BorderLayout(8, 8));
        JLabel lblTituloTela = new JLabel("Excluir Filme");
        lblTituloTela.setFont(new Font("Segoe UI", Font.BOLD, 18));
        painelTopo.add(lblTituloTela, BorderLayout.NORTH);

        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        painelBusca.add(new JLabel("ID do Filme:"));
        txtIdFilme = new JTextField(8);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarFilme());
        painelBusca.add(txtIdFilme);
        painelBusca.add(btnBuscar);
        painelTopo.add(painelBusca, BorderLayout.SOUTH);

        painel.add(painelTopo, BorderLayout.NORTH);

        // Centro: Dados do filme para conferência (somente leitura)
        JPanel painelDados = new JPanel(new GridLayout(4, 2, 8, 8));

        txtTitulo = new JTextField();
        txtAno = new JTextField();
        txtDuracao = new JTextField();
        txtDescricao = new JTextField();

        txtTitulo.setEditable(false);
        txtAno.setEditable(false);
        txtDuracao.setEditable(false);
        txtDescricao.setEditable(false);

        painelDados.add(new JLabel("Título:"));
        painelDados.add(txtTitulo);

        painelDados.add(new JLabel("Ano de Lançamento:"));
        painelDados.add(txtAno);

        painelDados.add(new JLabel("Duração (min):"));
        painelDados.add(txtDuracao);

        painelDados.add(new JLabel("Descrição:"));
        painelDados.add(txtDescricao);

        painel.add(painelDados, BorderLayout.CENTER);

        // Rodapé: Botões de ação
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");
        btnFechar = new JButton("Fechar");

        btnExcluir.addActionListener(e -> excluirFilme());
        btnLimpar.addActionListener(e -> limparCampos());
        btnFechar.addActionListener(e -> dispose());

        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnFechar);
        painel.add(painelBotoes, BorderLayout.SOUTH);

        setContentPane(painel);
    }

    private void buscarFilme() {
        try {
            int idProcurado = Integer.parseInt(txtIdFilme.getText().trim());

            // TROQUE 'SuaClasseDAO' pelo nome da sua classe onde está o PesquisarTudo()
            CadastrarFilme cf = new CadastrarFilme();
            ArrayList<Filme> lista = cf.PesquisarTudo();

            Filme encontrado = null;
            for (Filme f : lista) {
                if (f.getId_filme() == idProcurado) {
                    encontrado = f;
                    break;
                }
            }

            if (encontrado == null) {
                JOptionPane.showMessageDialog(this, "Filme não encontrado!");
                limparDadosFilme();
                return;
            }

            txtTitulo.setText(encontrado.getTitulo());
            txtAno.setText(String.valueOf(encontrado.getAno_lancamento()));
            txtDuracao.setText(String.valueOf(encontrado.getDuracao_min()));
            txtDescricao.setText(encontrado.getDescricao());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um ID numérico válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar filme: " + e.getMessage());
        }
    }

    private void excluirFilme() {
        try {
            if (txtIdFilme.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Informe o ID do filme para excluir.");
                return;
            }

            int idFilme = Integer.parseInt(txtIdFilme.getText().trim());
            Filme idF = new Filme();
            idF.setId_filme(idFilme);
            
            
            int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja excluir o filme ID " + idFilme + "?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION
            );

            if (confirmacao != JOptionPane.YES_OPTION) {
                return;
            }

            
            
             CadastrarFilme cf = new CadastrarFilme();
             cf.deletarFilme(idF);

            JOptionPane.showMessageDialog(this, "Filme excluído com sucesso!");
            limparCampos();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Digite um ID numérico válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao excluir filme: " + e.getMessage());
        }
    }

    private void limparDadosFilme() {
        txtTitulo.setText("");
        txtAno.setText("");
        txtDuracao.setText("");
        txtDescricao.setText("");
    }

    private void limparCampos() {
        txtIdFilme.setText("");
        limparDadosFilme();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new DeletarFilmeTela().setVisible(true));
    }
}