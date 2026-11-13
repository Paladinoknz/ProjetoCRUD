/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import br.com.controle.Diretor;

import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;



/**
 *
 * @author ubuntu
 */


public class CadastrarDiretor extends DAO  {
    
     public int inserir(Diretor d) throws Exception {
        abrirBanco();
        String existe = "select id_diretor,nome,nacionalidade FROM diretor where id_diretor=?";
        pst = (PreparedStatement) con.prepareStatement(existe);//preparando a query para validacao de usuario cadastrado
        pst.setInt(1, d.getId_diretor());//passando como parametro o ID
        ResultSet tr = pst.executeQuery();//Executando e recebendo um retorno do banco
        if (tr.next()) {//caso o tr.next for igual true entao é sinal que ja existe esse usuario
            JOptionPane.showMessageDialog(null, "E-mail ja cadastrado anteriormente!");
            ResultSet rsKeys = pst.getGeneratedKeys();
            if (rsKeys.next()) {
                d.setId_diretor(rsKeys.getInt(1));
            }
        } else {//se ter o retorno de false, sinal que podemos inserir esse novo usuario.
            String query = "INSERT INTO diretor (id_diretor,nome,nacionalidade) values(null,?,?)";//primeiro paramentro é null por conta que o SQL tem o autoincrement, entao nao precisamos colocar 
            pst = (PreparedStatement) con.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS);//prepara a query com valores faltando (???)
            //Esses 4 valores aqui preenche o buraco que o prepareStatement está esperando
            pst.setString(1, d.getNome());
            pst.setString(2, d.getNacionaldade());
            //e essa linha .execute manda pro sql com tudo preenchido. Sem perigo de sql injection(o usuario inserir na mao a query e acessar o banco)
//            d.setId_diretor(tr.getInt("id_diretor"));
            pst.executeUpdate();
            
            // Pega o ID gerado pelo auto_increment do MySQL
            ResultSet rsKeys = pst.getGeneratedKeys();
            if (rsKeys.next()) {
                d.setId_diretor(rsKeys.getInt(1));
            }
   
            JOptionPane.showMessageDialog(null, "Diretor do filme Inserido com sucesso!");
        }
        
      
        fecharBanco();
        return d.getId_diretor();
    }
     
     
     public void deletarAluno(Diretor d) throws Exception {
   
        abrirBanco();
        String query = "delete from diretor where id_diretor=?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setInt(1, d.getId_diretor());
        pst.execute();
        JOptionPane.showMessageDialog(null, "Diretor deletado com sucesso!");
        fecharBanco();
           
        
        
       
    }
  
     
     public int PesquisarRegistro(Diretor d) throws Exception {
	try {
            //Abre conexao com o banco a cada chamada do metodo para nao deixar o banco rodando toda hora
		abrirBanco();
                //Prepara a query que sera enviada para o banco
		String query = "select id_diretor,nome,nacionalidade FROM diretor WHERE id_diretor=?";
                //faz a conexao com o banco esperando o "?" ser preenchido
		pst = (PreparedStatement) con.prepareStatement(query);
                //Preenche o primeiro(e unico) "?" pelo retorno do objeto u que traz o ID do usuario 
		pst.setInt(1, d.getId_diretor());
                //executa a query de fato e guarda a resposta do banco
		ResultSet tr = pst.executeQuery();
                //se o banco responder TRUE, siginifca que o usuario existe e ele nos da as colunas(que selecionamos na query) da tabela usuario.
		if (tr.next()) {
			d.setId_diretor(tr.getInt("id_diretor"));
			d.setNome(tr.getString("nome"));
			d.setNacionaldade(tr.getString("nacionalidade"));
                        fecharBanco();
                        return 1;
                //Se o banco ter qualquer outra resposta que nao satisfaça o primeiro if, tratamos o erro. 
		} else {
			JOptionPane.showMessageDialog(null, "Nenhum resultado encontrado! ");
		}
                
               fecharBanco();
               return 0;
	} catch (Exception e) {
		System.out.println("Erro " + e.getMessage());
                 fecharBanco();
	return 5;
        }
    }
   
     
     public void editarDiretor(Diretor d) throws Exception {
        abrirBanco();
        //JOptionPane.showMessageDialog(null, a.getNome()+ a.getEmail() + a.getIdade());
        String query = "UPDATE diretor set nome = ?, nacionalidade = ? where id_diretor = ?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setString(1, d.getNome());
        pst.setString(2, d.getNacionaldade());
        pst.setInt(3, d.getId_diretor());
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Diretor Alterado com sucesso!");
        fecharBanco();
    }
} 


     
    

