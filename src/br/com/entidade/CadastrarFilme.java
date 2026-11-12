package br.com.entidade;
import br.com.controle.Filme;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class CadastrarFilme extends DAO {

    public void inserir(Filme f) throws Exception {
        abrirBanco();
        String existe = "select id_filme,titulo,ano_lancamento FROM filme where id_filme=?";
        pst = (PreparedStatement) con.prepareStatement(existe);//preparando a query para validacao de usuario cadastrado
        pst.setInt(1, f.getId_filme());//passando como parametro o ID
        ResultSet tr = pst.executeQuery();//Executando e recebendo um retorno do banco
        if (tr.next()) {//caso o tr.next for igual true entao é sinal que ja existe esse usuario
            JOptionPane.showMessageDialog(null, "Filme ja cadastrado anteriormente!");
        } else {//se ter o retorno de false, sinal que podemos inserir esse novo usuario.
            String query = "INSERT INTO filme (id_filme,titulo,ano_lancamento, duracao_minutos, id_genero, id_diretor, id_produtora, id_usuario) values(null,?,?,?,?,?,?,?)";//primeiro paramentro é null por conta que o SQL tem o autoincrement, entao nao precisamos colocar 
            pst = (PreparedStatement) con.prepareStatement(query);//prepara a query com valores faltando (???)
            //Esses 4 valores aqui preenche o buraco que o prepareStatement está esperando  
            pst.setString(1, f.getTitulo());
            pst.setInt(2, f.getAno_lancamento());
            pst.setInt(3, f.getDuracao_min());
            pst.setInt(4, f.getId_genero());
            pst.setInt(5, f.getId_diretor());
            pst.setInt(6, f.getId_produtora());
            pst.setInt(7, f.getId_usuario());
         
            
            //e essa linha .execute manda pro sql com tudo preenchido. Sem perigo de sql injection(o usuario inserir na mao a query e acessar o banco)
            pst.execute();
            JOptionPane.showMessageDialog(null, "Filme Inserido com sucesso!");
        }

        fecharBanco();
    }

  
    public void deletarFilme(Filme f) throws Exception {
   
        abrirBanco();
        String query = "delete from filme where Id_filme=?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setInt(1, f.getId_filme());
        pst.execute();
        JOptionPane.showMessageDialog(null, "Filme deletado com sucesso!");
        fecharBanco();
           
        
        
       
    }
  
    
    
   public int PesquisarRegistro(Filme f) throws Exception {
	try {
            //Abre conexao com o banco a cada chamada do metodo para nao deixar o banco rodando toda hora
		abrirBanco();
                //Prepara a query que sera enviada para o banco
		String query = "select id_filme,titulo,ano_lancamento,duracao_minutos,id_genero,id_diretor,id_produtora,id_usuario FROM filme where id_filme=?";
                //faz a conexao com o banco esperando o "?" ser preenchido
		pst = (PreparedStatement) con.prepareStatement(query);
                //Preenche o primeiro(e unico) "?" pelo retorno do objeto u que traz o ID do usuario 
		pst.setInt(1, f.getId_filme());
                //executa a query de fato e guarda a resposta do banco
		ResultSet tr = pst.executeQuery();
                //se o banco responder TRUE, siginifca que o usuario existe e ele nos da as colunas(que selecionamos na query) da tabela usuario.
		if (tr.next()) {
			f.setId_filme(tr.getInt("id_filme"));
			f.setTitulo(tr.getString("titulo"));
			f.setAno_lancamento(tr.getInt("ano_lancamento"));
                        f.setDuracao_min(tr.getInt("duracao_minutos"));
                        f.setId_genero(tr.getInt("id_genero"));
                        f.setId_diretor(tr.getInt("id_diretor"));
                        f.setId_produtora(tr.getInt("id_produtora"));
                        f.setId_usuario(tr.getInt("id_usuario"));
                        
                        return 1;
                //Se o banco ter qualquer outra resposta que nao satisfaça o primeiro if, tratamos o erro. 
		} else {
			JOptionPane.showMessageDialog(null, "Nenhum filme resultado encontrado! ");
		}
                
               fecharBanco();
               return 0;
	} catch (Exception e) {
		System.out.println("Erro " + e.getMessage());
	return 5;
        }
    }
   
 

    public ArrayList<Filme> PesquisarTudo() throws Exception {
        ArrayList<Filme> filmes = new ArrayList<Filme>();
        try {
            abrirBanco();
            String query = "select * FROM filme";
            pst = (PreparedStatement) con.prepareStatement(query);
            ResultSet tr = pst.executeQuery();
            Filme f;
            while (tr.next()) {
                f = new Filme();
                f.setId_filme(tr.getInt("id_filme"));
		f.setTitulo(tr.getString("titulo"));
		f.setAno_lancamento(tr.getInt("ano_lancamento"));
                f.setDuracao_min(tr.getInt("duracao_minutos"));
                f.setId_genero(tr.getInt("id_genero"));
                f.setId_diretor(tr.getInt("id_diretor"));
                f.setId_produtora(tr.getInt("id_produtora"));
                f.setId_usuario(tr.getInt("id_usuario"));
                filmes.add(f);
            }
            fecharBanco();
        } catch (Exception e) {
            System.out.println("Erro " + e.getMessage());
        }
        return filmes;
    } 
   
    public void editarFilme(Filme f) throws Exception {
        abrirBanco();
        //JOptionPane.showMessageDialog(null, a.getNome()+ a.getEmail() + a.getIdade());
        String query = "UPDATE filme set titulo = ?, ano_lancamento = ?, duracao_minutos = ?, where id_filme = ?";
        pst = (PreparedStatement) con.prepareStatement(query);
        pst.setString(1, f.getTitulo());
        pst.setInt(2, f.getAno_lancamento());
        pst.setInt(3, f.getDuracao_min());
        pst.executeUpdate();
        JOptionPane.showMessageDialog(null, "Filme Alterado com sucesso!");
        fecharBanco();
    }
} 


    

