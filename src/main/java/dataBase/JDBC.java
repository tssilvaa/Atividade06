package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC {

	public static Connection conectarJDBC(String usuario, String senha, String url) {
		Connection conn = null;
	  
	    try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(url,usuario, senha);
		} catch (Exception e) {
			System.out.println("Erro de Conexão :" + e);
			e.printStackTrace();
		}
	    
		return conn;		
				
	}
	
	public void listaProdutosJDBC(Connection conn) {
		PreparedStatement stmt;
		
		try {
			stmt = (PreparedStatement) conn.prepareStatement("select * from Produto;");

			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				String idProduto = rs.getString("idProduto");
				String nome = rs.getString("nome");
				String descricao = rs.getString("descricao");
				String valor = rs.getString("valor");
				String estado =  rs.getString("estado");
				System.out.println(idProduto + " -- " + nome + " -- " + descricao + " -- " + valor + " -- " + estado);
			}
			
		} catch (SQLException e) {
			System.out.println("Erro ao executar select :" + e);
			e.printStackTrace();
		}	
	}
	
	public void insereProdutoJDBC(Connection conn, String idProduto, String nome, String descricao, String valor, String estado) {

		Statement stmt;
				
			try {
				stmt = (Statement) conn.createStatement();
				String insere = "insert into Produto VALUES ('" 
						+ idProduto + "','"
						+ nome + "','"
						+ descricao + "','"
						+ valor + "','" 
						+ estado + "');";
				
				stmt.executeUpdate(insere);
			} catch (SQLException e) {
				e.printStackTrace();
			}	
	}

	public void alteraValorProdutoJDBC(Connection conn, String idProduto, String valor) {
		Statement stmt;
				
			try {
				stmt = (Statement) conn.createStatement();
				String atualiza = "update Produto set valor = '"
						 + valor + "' where idProduto = '" 
						 + idProduto + "';";
				
				stmt.executeUpdate(atualiza);
			} catch (SQLException e) {
				e.printStackTrace();
			}	
	}

	public void apagaProdutoJDBC(Connection conn, String idProduto) {
		Statement stmt;
		
			try {
				stmt = (Statement) conn.createStatement();
				String atualiza = "delete from Produto where idProduto = '" 
						 + idProduto + "';";
				
				stmt.executeUpdate(atualiza);
			} catch (SQLException e) {
				e.printStackTrace();
			}	
	}
	
}
