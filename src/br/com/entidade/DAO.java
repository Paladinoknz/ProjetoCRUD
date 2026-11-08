/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.entidade;

import java.sql.Connection;//basicamente e o caminho por onde vai passar as query, funciona como uma rota que diz aonde cada comando vai
import java.sql.DriverManager;//faz a traducao do SQL para JAVA e virse versa, sem isso nao podiamos acessar o banco e enviar comandos
import java.sql.PreparedStatement;//Serve para enviar uma resposta ao banco em formato de query, passando como paramentros os valores insiridos no backend substituindo os ?
import java.sql.ResultSet;//nao estar sendo utilizado aqui(nessa classe), mas basicamente e uma classe que serve para receber a resposta vinda do banco 
import java.sql.SQLException;//Tratamento de erros
import java.util.logging.Level;//gravidade dos erros, registro para o logs
import java.util.logging.Logger;//biblioteca para logs, mapeamento de erros, e como eles ocorreram 

public class DAO {
  Connection con;// criação do objeto conexao
    PreparedStatement pst;//criação do objeto preparação de ambiente
    ResultSet rs;// criação do objeto que recebe resultados
   
    public void abrirBanco() throws SQLException {// criando metodo para acessar o banco
      try {
        Class.forName("com.mysql.jdbc.Driver");//classe para utilização do arquivo com configurções do serivdor mysql
        String url = "jdbc:mysql://localhost:3306/crud_java?useSSL=false&serverTimezone=UTC";// drive servidor e banco de dados a serem utilizados e indicação do banco a ser utilizado
        String user ="root";// usuario do banco de dados
        String senha ="";//senha do usuario do banco de dados    
        con=(Connection) DriverManager.getConnection(url,user,senha);//metodo que usa os parametros para conectar com o banco
        System.out.println("Conectado ao banco de dados ");
      } catch (ClassNotFoundException ex) {//tratamento de erro de drive
          System.out.println("Classe não encontrada, adicione o driver nas bibliotecas.");
          Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, "Rapaz, quebrou aqui!", ex);
        } catch(SQLException e) {//tratamento de erro de SQL
          System.out.println(e);
          throw new RuntimeException(e);
       } 
     }    
    
    public void fecharBanco() throws Exception{//criando metodo que fechao conexão com o banco
       if (pst!= null) { //limpando os dados de conexão   
            pst.close();//fechando o ambiente de conexão
            System.out.println("Execuçao da Query fechada\n");
    }    }
}
