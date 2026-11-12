/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import br.com.controle.Genero;

import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;



/**
 *
 * @author ubuntu
 */


public class CadastrarGenero extends DAO  {
    
     public void inserir(Genero g) throws Exception {
        abrirBanco();
        String existe = "select id_genero,nome,descricao FROM genero where id_genero=?";
        pst = (PreparedStatement) con.prepareStatement(existe);//preparando a query para validacao de usuario cadastrado
        pst.setInt(1, g.getId_genero());//passando como parametro o ID
        
        
        ResultSet tr = pst.executeQuery();//Executando e recebendo um retorno do banco
        if (tr.next()) {//caso o tr.next for igual true entao é sinal que ja existe esse usuario
            JOptionPane.showMessageDialog(null, "E-mail ja cadastrado anteriormente!");
            g.setId_genero(tr.getInt("id_genero"));
        } else {//se ter o retorno de false, sinal que podemos inserir esse novo usuario.
            String query = "INSERT INTO genero (id_genero,nome,descricao) values(null,?,?)";//primeiro paramentro é null por conta que o SQL tem o autoincrement, entao nao precisamos colocar 
            pst = (PreparedStatement) con.prepareStatement(query);//prepara a query com valores faltando (???)
            //Esses 4 valores aqui preenche o buraco que o prepareStatement está esperando
            pst.setString(1, g.getNome());
            pst.setString(2, g.getDescricao());
            //e essa linha .execute manda pro sql com tudo preenchido. Sem perigo de sql injection(o usuario inserir na mao a query e acessar o banco)
            pst.execute();
            JOptionPane.showMessageDialog(null, "Descricão e genêro do filme Inseridas com sucesso!");
               if (rs.next()) {
        g.setId_genero(rs.getInt(1)); 
}
        }
             ResultSet rs = pst.getGeneratedKeys();
     
   
        fecharBanco();
    }
     
     
     public void deletarGenero(Genero g) throws Exception {
   
        abrirBanco();
        String query = "delete from genero where id_genero=?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setInt(1, g.getId_genero());
        pst.execute();
        JOptionPane.showMessageDialog(null, "Genero deletado com sucesso!");
        fecharBanco();
           
        
        
       
    }
  
     
     public int PesquisarRegistro(Genero g) throws Exception {
	
         try {
            //Abre conexao com o banco a cada chamada do metodo para nao deixar o banco rodando toda hora
		abrirBanco();
                //Prepara a query que sera enviada para o banco
		String query = "select id_id_genero,nome,descricao FROM genero where id_genero=?";
                //faz a conexao com o banco esperando o "?" ser preenchido
		pst = (PreparedStatement) con.prepareStatement(query);
                //Preenche o primeiro(e unico) "?" pelo retorno do objeto u que traz o ID do usuario 
		pst.setInt(1, g.getId_genero());
                //executa a query de fato e guarda a resposta do banco
		ResultSet tr = pst.executeQuery();
                //se o banco responder TRUE, siginifca que o usuario existe e ele nos da as colunas(que selecionamos na query) da tabela usuario.
		if (tr.next()) {
                    
			g.setId_genero(tr.getInt("id_genero"));
			g.setNome(tr.getString("nome"));
			g.setDescricao(tr.getString("descricao"));
            
                        return g.getId_genero();
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
   
     
     
     public void editarGenero(Genero g) throws Exception {
        abrirBanco();
        //JOptionPane.showMessageDialog(null, a.getNome()+ a.getEmail() + a.getIdade());
        String query = "UPDATE genero set nome = ?, descricao = ? where id_genero = ?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setString(1, g.getNome());
        pst.setString(2, g.getDescricao());
        pst.setInt(3, g.getId_genero());
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Descrição Alterada com sucesso!");
        fecharBanco();
    }
} 


     
    

