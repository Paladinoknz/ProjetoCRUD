/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import br.com.controle.Diretor;
import br.com.controle.Produtora;

import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;



/**
 *
 * @author ubuntu
 */


public class CadastrarProdutora extends DAO  {
    
     public void inserir(Produtora p) throws Exception {
        abrirBanco();
        String existe = "select id_diretor,nome,nacionalidade FROM diretor where id_diretor=?";
        pst = (PreparedStatement) con.prepareStatement(existe);//preparando a query para validacao de usuario cadastrado
        pst.setInt(1, p.getId_produtora());//passando como parametro o ID
        ResultSet tr = pst.executeQuery();//Executando e recebendo um retorno do banco
        if (tr.next()) {//caso o tr.next for igual true entao é sinal que ja existe esse usuario
            JOptionPane.showMessageDialog(null, "E-mail ja cadastrado anteriormente!");
        } else {//se ter o retorno de false, sinal que podemos inserir esse novo usuario.
            String query = "INSERT INTO produtora (id_produtora,nome,pais_origem) values(null,?,?)";//primeiro paramentro é null por conta que o SQL tem o autoincrement, entao nao precisamos colocar 
            pst = (PreparedStatement) con.prepareStatement(query);//prepara a query com valores faltando (???)
            //Esses 4 valores aqui preenche o buraco que o prepareStatement está esperando
            pst.setString(1, p.getNome());
            pst.setString(2, p.getPais());
            //e essa linha .execute manda pro sql com tudo preenchido. Sem perigo de sql injection(o usuario inserir na mao a query e acessar o banco)
            pst.execute();
            JOptionPane.showMessageDialog(null, "Produtora do filme Inserido com sucesso!");
        }

        fecharBanco();
    }
     
     
     public void deletarProdutora(Produtora p) throws Exception {
   
        abrirBanco();
        String query = "delete from produtora where id_produtora=?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setInt(1, p.getId_produtora());
        pst.execute();
        JOptionPane.showMessageDialog(null, "Produtora deletado com sucesso!");
        fecharBanco();
           
        
        
       
    }
  
     
     public int PesquisarRegistro(Produtora p) throws Exception {
	try {
            //Abre conexao com o banco a cada chamada do metodo para nao deixar o banco rodando toda hora
		abrirBanco();
                //Prepara a query que sera enviada para o banco
		String query = "select id_produtora,nome,pais_origem FROM produtora where id_produtora=?";
                //faz a conexao com o banco esperando o "?" ser preenchido
		pst = (PreparedStatement) con.prepareStatement(query);
                //Preenche o primeiro(e unico) "?" pelo retorno do objeto u que traz o ID do usuario 
		pst.setInt(1, p.getId_produtora());
                //executa a query de fato e guarda a resposta do banco
		ResultSet tr = pst.executeQuery();
                //se o banco responder TRUE, siginifca que o usuario existe e ele nos da as colunas(que selecionamos na query) da tabela usuario.
		if (tr.next()) {
			p.setId_produtora(tr.getInt("id_produtora"));
			p.setNome(tr.getString("nome"));
			p.setPais(tr.getString("pais_origem"));
            
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
   
     
     public void editarProdutora(Produtora p) throws Exception {
        abrirBanco();
        //JOptionPane.showMessageDialog(null, a.getNome()+ a.getEmail() + a.getIdade());
        String query = "UPDATE produtora set nome = ?, pais_origem = ? where id_produtora = ?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setString(1, p.getNome());
        pst.setString(2, p.getPais());
        pst.setInt(3, p.getId_produtora());
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Produtora Alterada com sucesso!");
        fecharBanco();
    }
} 


     
    

