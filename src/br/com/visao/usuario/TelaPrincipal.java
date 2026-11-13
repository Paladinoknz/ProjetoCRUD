package br.com.visao;

//front-end, sou pessimo em mexer com isso, fiz usando auxilo da IA, mas vou comentar tudo
//@kaioRamos


//Bibliotecas para fazer frufru(deixar menos feio que o padrao), sao padrao java.
import br.com.controle.Usuario;
//import br.com.entidade.CadastrarUsuario;
import br.com.visao.filme.CadastrarFilmeTela;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



//Classe principal que fica rodando, segue a mesma logica do Jframe, o unico motivo de uso numa classe .java padrao é por conta que o
//java proibi as modificacoes de forma interina no Jframe.

public class TelaPrincipal extends javax.swing.JFrame {

    
    //Como um usuario nao podera excluir outros usuarios então a tela sera diferente para o perfil "Usuario"
    private String perfilUsuario = "Comum"; // Padrão como colaborador comum

    private Usuario usuarioLogado;
    //Botoes compostos no CRUD
    private JButton btnCadastrar;
    private JButton btnListar;
    private JButton btnAlterar;
    private JButton btnDeletar;
    private JButton btnExcluirUsuario;
    
   public void setUsuarioLogado(Usuario u) {
    this.usuarioLogado = u; 
   }
   
    public int getIdUsuarioLogado() {
        if (usuarioLogado != null) {
            return usuarioLogado.getId_usuario();
        }
        return 0;
    }
    
    
    //Iniciando a tela principal 
    public TelaPrincipal() {
        initComponentsCustom();
        setSize(700, 450);
        setLocationRelativeTo(null);
        aplicarControleDeAcesso();
    }

    private void initComponentsCustom() {
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Filmes - Painel Principal");
        setLayout(new BorderLayout());

        // 1. Painel Superior (Cabeçalho)
        JPanel painelTopo = new JPanel();
        painelTopo.setBackground(new Color(45, 52, 54)); // Tom de cinza escuro moderno
        painelTopo.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("🎬 Painel de Controle - Catálogo de Filmes");
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        painelTopo.add(lblTitulo);
        
        add(painelTopo, BorderLayout.NORTH);

        // 2. Painel Central (Grade de Botões Estilizados)
        JPanel painelCentro = new JPanel(new GridLayout(3, 2, 20, 20)); // Grade 2x2 com espaçamento
        painelCentro.setBorder(new EmptyBorder(30, 30, 30, 30));
        painelCentro.setBackground(new Color(245, 246, 250)); // Fundo claro

        btnCadastrar = new JButton("➕ Cadastrar Filme");
        btnListar = new JButton("📋 Listar Filmes");
        btnAlterar = new JButton("✏️ Alterar Filme");
        btnDeletar = new JButton("🗑️ Deletar Filme");
        btnExcluirUsuario = new JButton("👤 Excluir Usuário");

        // Estilizando os botões
        estilizarBotao(btnCadastrar, new Color(9, 132, 227));   // Azul
        estilizarBotao(btnListar, new Color(0, 184, 148));      // Verde
        estilizarBotao(btnAlterar, new Color(225, 112, 85));    // Laranja
        estilizarBotao(btnDeletar, new Color(214, 48, 49));     // Vermelho
        estilizarBotao(btnExcluirUsuario, new Color(108, 92, 231));
        
        // Ações dos botões (chame suas telas aqui)
        btnCadastrar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrindo Cadastro de Filmes...");
            CadastrarFilmeTela cf = new CadastrarFilmeTela(); 
            cf.setUsuarioLogado(this.usuarioLogado);
            cf.setVisible(true);
        });

        btnListar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrindo Listagem de Filmes...");
            // FormListarFilmes fl = new FormListarFilmes();
            // fl.setVisible(true);
        });

        btnAlterar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrindo Alteração de Filme...");
            // FormAlterarFilme fa = new FormAlterarFilme();
            // fa.setVisible(true);
        });

        btnDeletar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrindo Exclusão de Filme...");
            // FormDeletarFilme da = new FormDeletarFilme();
            // da.setVisible(true);
        });
        
        btnExcluirUsuario.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Abrindo Exclusão de Usuário...");
            FormDeletarUsuario eu = new FormDeletarUsuario();
            eu.setVisible(true);
        });

        // Adicionando botões ao painel central
        painelCentro.add(btnCadastrar);
        painelCentro.add(btnListar);
        painelCentro.add(btnAlterar);
        painelCentro.add(btnDeletar);
        painelCentro.add(btnExcluirUsuario); 

        add(painelCentro, BorderLayout.CENTER);
    }

    private void estilizarBotao(JButton btn, Color corFundo) {
        btn.setBackground(corFundo);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
    }

    // Método para definir se o usuário é Admin ou Comum
    public void setPerfilUsuario(String perfil) {
        this.perfilUsuario = perfil;
        aplicarControleDeAcesso();
    }

    //metodo usado para limitar acesso as demais telas.
    private void aplicarControleDeAcesso() {
        //comparacao para identificar qual usuario que está acessando.
        if ("Usuário".equalsIgnoreCase(perfilUsuario)) {
            // Colaborador comum não pode alterar nem deletar
            btnAlterar.setEnabled(false);
            btnAlterar.setBackground(new Color(200, 200, 200)); // Fica cinza
            
            btnDeletar.setEnabled(false);
            btnDeletar.setBackground(new Color(200, 200, 200)); // Fica cinza
            
            btnExcluirUsuario.setEnabled(false);
            btnExcluirUsuario.setBackground(new Color(200, 200, 200));
            
            
            setTitle("Painel Principal - Colaborador (Acesso Limitado)");
        } else {
            // Admin tem acesso total
            btnAlterar.setEnabled(true);
            btnAlterar.setBackground(new Color(225, 112, 85));
            
            btnDeletar.setEnabled(true);
            btnDeletar.setBackground(new Color(214, 48, 49));
            
            btnExcluirUsuario.setEnabled(true);
            btnExcluirUsuario.setBackground(new Color(108, 92, 231));
            
            
            setTitle("Painel Principal - Administrador (Acesso Total)");
        }
    }


    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            try{
            TelaPrincipal tela = new TelaPrincipal();
            // Teste mudando para "Admin" ou "Comum" aqui se quiser testar:
            //pegando o get do id usuario e vendo aqui quais funcionalidades estarao disponives na tela principal.
//            tela.setVisible(true);
            
            }
            
                catch(Exception e){
                JOptionPane.showMessageDialog(null, "Erro de : " + e.getMessage());
            }
        
        });
    }
}