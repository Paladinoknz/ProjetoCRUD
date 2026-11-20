package br.com.visao.filme;

import br.com.controle.Filme; // Ajuste para o pacote da sua classe Filme
import br.com.controle.Genero;
import br.com.entidade.CadastrarFilme;
import br.com.entidade.CadastrarGenero;
// import br.com.entidade.ManterFilme; // Ajuste para o pacote da sua classe DAO
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AlterarFilmeTela extends JFrame {

    private JTextField txtIdFilme;
    private JButton btnBuscar;

    private JTextField txtTitulo;
    private JTextField txtAno;
    private JTextField txtDuracao;
    private JTextField txtDescricao;
    private JTextField txtIdGenero;
    private JTextField txtIdDiretor;
    private JTextField txtIdProdutora;

    private JButton btnSalvar;
    private JButton btnFechar;

    public AlterarFilmeTela() {
        configurarInterface();
    }

    private void configurarInterface() {
        setTitle("Alterar Filme");
        setSize(480, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Linha de busca pelo ID do filme
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        painelBusca.add(new JLabel("ID do Filme:"));
        txtIdFilme = new JTextField(8);
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(e -> buscarFilme());
        painelBusca.add(txtIdFilme);
        painelBusca.add(btnBuscar);
        painel.add(painelBusca, BorderLayout.NORTH);

        // Campos editáveis
        JPanel painelCampos = new JPanel(new GridLayout(7, 2, 8, 8));

        txtTitulo = new JTextField();
        txtAno = new JTextField();
        txtDuracao = new JTextField();
        txtDescricao = new JTextField();
        txtIdGenero = new JTextField();
        txtIdDiretor = new JTextField();
        txtIdProdutora = new JTextField();
        

        painelCampos.add(new JLabel("Título:"));
        painelCampos.add(txtTitulo);

        painelCampos.add(new JLabel("Ano de Lançamento:"));
        painelCampos.add(txtAno);

        painelCampos.add(new JLabel("Duração (min):"));
        painelCampos.add(txtDuracao);

        painelCampos.add(new JLabel("Descrição:"));
        painelCampos.add(txtDescricao);

        painelCampos.add(new JLabel("ID Gênero:"));
        painelCampos.add(txtIdGenero);

        painelCampos.add(new JLabel("ID Diretor:"));
        painelCampos.add(txtIdDiretor);

        painelCampos.add(new JLabel("ID Produtora:"));
        painelCampos.add(txtIdProdutora);

        painel.add(painelCampos, BorderLayout.CENTER);

        // Botões inferiores
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnSalvar = new JButton("Salvar");
        btnFechar = new JButton("Fechar");

        btnSalvar.addActionListener(e -> salvarFilme());
        btnFechar.addActionListener(e -> dispose());

        painelBotoes.add(btnSalvar);
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
                return;
            }

            txtTitulo.setText(encontrado.getTitulo());
            txtAno.setText(String.valueOf(encontrado.getAno_lancamento()));
            txtDuracao.setText(String.valueOf(encontrado.getDuracao_min()));
            txtDescricao.setText(encontrado.getDescricao());
            
            
            //Fazendo a busca do nome do genero pelo ID armazenado em Filme.
            CadastrarGenero cg = new CadastrarGenero();
            Genero nomeGenero = new Genero();
            nomeGenero.setId_genero(encontrado.getId_genero());
            cg.PesquisarRegistro(nomeGenero);
            
            
            txtIdGenero.setText(nomeGenero.getNome());
            txtIdDiretor.setText(String.valueOf(encontrado.getId_diretor()));
            txtIdProdutora.setText(String.valueOf(encontrado.getId_produtora()));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar filme: " + e.getMessage());
        }
    }
    
//    private String montarReferenciaIds() {
//    StringBuilder sb = new StringBuilder();
//
//    try {
//        
//        CadastrarGenero cg = new CadastrarGenero();
//        
//        // Exemplo chamando seus métodos separados (ajuste nomes/classes conforme o seu projeto):
//        sb.append("=== GÊNEROS ===\n");
//            for (Genero g : cg.PesquisarTudo()) {
//            sb.append("ID ").append(g.getId_genero()).append(" - ").append(g.getNome()).append("\n");
//         }
//
//        sb.append("\n=== DIRETORES ===\n");
//        // for (Diretor d : dao.listarDiretores()) {
//        //     sb.append("ID ").append(d.getId_diretor()).append(" - ").append(d.getNome()).append("\n");
//        // }
//
//        sb.append("\n=== PRODUTORAS ===\n");
//        // for (Produtora p : dao.listarProdutoras()) {
//        //     sb.append("ID ").append(p.getId_produtora()).append(" - ").append(p.getNome()).append("\n");
//        // }
//
//    } catch (Exception e) {
//        sb.append("Erro ao carregar opções: ").append(e.getMessage());
//    }
//
//    return sb.toString();
//}
//    

    private void salvarFilme() {
        try {
            Filme f = new Filme();
            f.setId_filme(Integer.parseInt(txtIdFilme.getText().trim()));
            f.setTitulo(txtTitulo.getText().trim());
            f.setAno_lancamento(Integer.parseInt(txtAno.getText().trim()));
            f.setDuracao_min(Integer.parseInt(txtDuracao.getText().trim()));
            f.setDescricao(txtDescricao.getText().trim());
            f.setId_genero(Integer.parseInt(txtIdGenero.getText().trim()));
            f.setId_diretor(Integer.parseInt(txtIdDiretor.getText().trim()));
            f.setId_produtora(Integer.parseInt(txtIdProdutora.getText().trim()));

            
            CadastrarFilme cf = new CadastrarFilme();
            cf.editarFilme(f);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar alteração: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new AlterarFilmeTela().setVisible(true));
    }
}