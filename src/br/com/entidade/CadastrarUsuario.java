/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package br.com.entidade;
import br.com.controle.Usuario;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;


public class CadastrarUsuario extends DAO {

    public void inserir(Usuario u) throws Exception {
        abrirBanco();
        String existe = "select id_usuario,nome,email FROM usuario where email=?";
        pst = (PreparedStatement) con.prepareStatement(existe);//preparando a query para validacao de usuario cadastrado
        pst.setString(1, u.getEmail());//passando como parametro o ID
        ResultSet tr = pst.executeQuery();//Executando e recebendo um retorno do banco
        if (tr.next()) {//caso o tr.next for igual true entao é sinal que ja existe esse usuario
            JOptionPane.showMessageDialog(null, "E-mail ja cadastrado anteriormente!");
        } else {//se ter o retorno de false, sinal que podemos inserir esse novo usuario.
            String query = "INSERT INTO usuario (id_usuario,nome,email,senha,perfil) values(null,?,?,?,?)";//primeiro paramentro é null por conta que o SQL tem o autoincrement, entao nao precisamos colocar 
            pst = (PreparedStatement) con.prepareStatement(query);//prepara a query com valores faltando (???)
            //Esses 4 valores aqui preenche o buraco que o prepareStatement está esperando
            pst.setString(1, u.getNome());
            pst.setString(2, u.getEmail());
            pst.setString(3, u.getSenha());
            pst.setString(4, u.getPerfil());
            //e essa linha .execute manda pro sql com tudo preenchido. Sem perigo de sql injection(o usuario inserir na mao a query e acessar o banco)
            pst.execute();
            JOptionPane.showMessageDialog(null, "Aluno Inserido com sucesso!");
        }

        fecharBanco();
    }

//  
//    public void deletarAluno(Aluno a) throws Exception {
//        abrirBanco();
//        String query = "delete from aluno where codigo=?";
//        pst = (PreparedStatement) con.prepareStatement(query);
//        pst.setInt(1, a.getCodigo());
//        pst.execute();
//        JOptionPane.showMessageDialog(null, "Aluno deletado com sucesso!");
//        fecharBanco();
//    }
//  
   public void PesquisarRegistro(Usuario u) throws Exception {
	try {
            //Abre conexao com o banco a cada chamada do metodo para nao deixar o banco rodando toda hora
		abrirBanco();
                //Prepara a query que sera enviada para o banco
		String query = "select id_usuario,nome,email,perfil FROM usuario where id_usuario=?";
                //faz a conexao com o banco esperando o "?" ser preenchido
		pst = (PreparedStatement) con.prepareStatement(query);
                //Preenche o primeiro(e unico) "?" pelo retorno do objeto u que traz o ID do usuario 
		pst.setInt(1, u.getId_usuario());
                //executa a query de fato e guarda a resposta do banco
		ResultSet tr = pst.executeQuery();
                //se o banco responder TRUE, siginifca que o usuario existe e ele nos da as colunas(que selecionamos na query) da tabela usuario.
		if (tr.next()) {
			u.setId_usuario(tr.getInt("id_usuario"));
			u.setNome(tr.getString("nome"));
			u.setEmail(tr.getString("email"));
                        u.setPerfil("perfil");
                //Se o banco ter qualquer outra resposta que nao satisfaça o primeiro if, tratamos o erro. 
		} else {
			JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado! ");
		}
                
               
		fecharBanco();
	} catch (Exception e) {
		System.out.println("Erro " + e.getMessage());
	}
    }
}

//    public ArrayList<Aluno> PesquisarTudo() throws Exception {
//        ArrayList<Aluno> alunos = new ArrayList<Aluno>();
//        try {
//            abrirBanco();
//            String query = "select * FROM aluno";
//            pst = (PreparedStatement) con.prepareStatement(query);
//            ResultSet tr = pst.executeQuery();
//            Aluno a;
//            while (tr.next()) {
//                a = new Aluno();
//                a.setCodigo(tr.getInt("codigo"));
//                a.setNome(tr.getString("nome"));
//		a.setEmail(tr.getString("email"));
//                alunos.add(a);
//            }
//            fecharBanco();
//        } catch (Exception e) {
//            System.out.println("Erro " + e.getMessage());
//        }
//        return alunos;
//    } 
//    // Metodo da Classe Entidade
//public void editarAluno(Aluno a) throws Exception {
//        abrirBanco();
//        //JOptionPane.showMessageDialog(null, a.getNome()+ a.getEmail() + a.getIdade());
//        String query = "UPDATE aluno set nome = ?, email = ? where codigo = ?";
//        pst = (PreparedStatement) con.prepareStatement(query);
//        pst.setString(1, a.getNome());
//        pst.setString(2, a.getEmail());
//        pst.setInt(3, a.getCodigo());
//        pst.executeUpdate();
//        JOptionPane.showMessageDialog(null, "Aluno Alterado com sucesso!");
//        fecharBanco();
//    }
//} 
