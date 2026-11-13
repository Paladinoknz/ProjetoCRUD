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
import java.util.HashSet;
import java.util.Set;


public class CadastrarUsuario extends DAO {

    public int inserir(Usuario u) throws Exception {
        abrirBanco();
        String existe = "select id_usuario,nome,email FROM usuario where email=?";
        pst = (PreparedStatement) con.prepareStatement(existe);//preparando a query para validacao de usuario cadastrado
        pst.setString(1, u.getEmail());//passando como parametro o ID
        ResultSet tr = pst.executeQuery();//Executando e recebendo um retorno do banco
        if (tr.next()) {//caso o tr.next for igual true entao é sinal que ja existe esse usuario
            JOptionPane.showMessageDialog(null, "E-mail ja cadastrado anteriormente!");
            ResultSet rsKeys = pst.getGeneratedKeys();
            if (rsKeys.next()) {
                u.setId_usuario(rsKeys.getInt(1));
            }
        } else {//se ter o retorno de false, sinal que podemos inserir esse novo usuario.
            String query = "INSERT INTO usuario (id_usuario,nome,email,senha,perfil) values(null,?,?,?,?)";//primeiro paramentro é null por conta que o SQL tem o autoincrement, entao nao precisamos colocar 
            pst = (PreparedStatement) con.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);//prepara a query com valores faltando (???)
            //Esses 4 valores aqui preenche o buraco que o prepareStatement está esperando
            pst.setString(1, u.getNome());
            pst.setString(2, u.getEmail());
            pst.setString(3, u.getSenha());
            pst.setString(4, u.getPerfil());
            //e essa linha .execute manda pro sql com tudo preenchido. Sem perigo de sql injection(o usuario inserir na mao a query e acessar o banco)
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Usuario Inserido com sucesso!");
            
            ResultSet rsKeys = pst.getGeneratedKeys();
            if (rsKeys.next()) {
                u.setId_usuario(rsKeys.getInt(1));
            }
        }

        fecharBanco();
        return u.getId_usuario();
    }

  
    public void deletarAluno(Usuario u) throws Exception {
   
        abrirBanco();
        String query = "delete from usuario where Id_usuario=?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setInt(1, u.getId_usuario());
        pst.execute();
        JOptionPane.showMessageDialog(null, "Usuario deletado com sucesso!");
        fecharBanco();
           
        
        
       
    }
  
    
    
   public int PesquisarRegistro(Usuario u) throws Exception {
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
                        u.setPerfil(tr.getString("perfil"));
                        return 1;
                //Se o banco ter qualquer outra resposta que nao satisfaça o primeiro if, tratamos o erro. 
		} else {
			JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado! ");
		}
                
               fecharBanco();
               return 0;
	} catch (Exception e) {
		System.out.println("Erro " + e.getMessage());
	return 5;
        }
    }
   
     public int PesquisarLogin(Usuario u) throws Exception {
	try {
            //Abre conexao com o banco a cada chamada do metodo para nao deixar o banco rodando toda hora
		abrirBanco();
                //Prepara a query que sera enviada para o banco
		String query = "select id_usuario,nome,email,perfil, senha FROM usuario where email=?";
                //faz a conexao com o banco esperando o "?" ser preenchido
		pst = (PreparedStatement) con.prepareStatement(query);
                //Preenche os "?" pelo retorno do objeto (Usuario u) que traz o email do usuario 
		pst.setString(1, u.getEmail());
                //executa a query de fato e guarda a resposta do banco
		ResultSet tr = pst.executeQuery();
                //se o banco responder TRUE, siginifca que o usuario existe e ele nos da as colunas(que selecionamos na query) da tabela usuario.
		if (tr.next()) {
			u.setId_usuario(tr.getInt("id_usuario"));
			u.setNome(tr.getString("nome"));
			u.setEmail(tr.getString("email"));
                        u.setPerfil(tr.getString("perfil"));//pegamos isso para saber se o usuario tera acesso a tela toda ou limitada.
                        u.setSenha(tr.getString("senha"));//pegamos a senha nesse metodo para comparar com a senha digitada na tela de login, isso isso 
                        //nao conseguimos fazer a comparaçao que vem da tela.
                    
                        return 1;//o retorno aqui para que na tela login tenhamos um retorno de que o usuario existe. 
                //Se o banco der qualquer outra resposta que nao satisfaça o primeiro if, tratamos o erro. 
		} else {
			
                    JOptionPane.showMessageDialog(null, "Nenhum login encontrado! ");
                   
		}
                
               
		fecharBanco();
                return 0;//retorno aqui logo depois do fechar banco pq nao podemos colocar antes dele, paia :(
	} catch (Exception e) {
		System.out.println("Erro " + e.getMessage());
                return 5;
	}
    }


    public ArrayList<Usuario> PesquisarTudo() throws Exception {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
        try {
            abrirBanco();
            String query = "select * FROM usuario";
            pst = (PreparedStatement) con.prepareStatement(query);
            ResultSet tr = pst.executeQuery();
            Usuario u;
            while (tr.next()) {
                u = new Usuario();
                u.setId_usuario(tr.getInt("id_usuario"));
                u.setNome(tr.getString("nome"));
		u.setEmail(tr.getString("email"));
                usuarios.add(u);
            }
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
        }
        return usuarios;
    } 
   
    public void editarUsuario(Usuario u) throws Exception {
        abrirBanco();
        //JOptionPane.showMessageDialog(null, a.getNome()+ a.getEmail() + a.getIdade());
        String query = "UPDATE usuario set nome = ?, email = ?, perfil = ? where id_usuario = ?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setString(1, u.getNome());
        pst.setString(2, u.getEmail());
        pst.setString(3, u.getPerfil());
        pst.setInt(4, u.getId_usuario());
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Usuário Alterado com sucesso!");
        fecharBanco();
    }
} 

