package app;

import java.sql.Connection;
import java.sql.SQLException;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import dataBase.JDBC;
import dataBase.MongoDB;

public class AppMain {

	public static void main(String[] args) {
		
		executarJDBC();

		System.out.println("===================================");
		
		executarMongoDB();

	}
	
	private static void executarJDBC() {
		System.out.println("Conectando com o JDBC");
		
		JDBC lista = new JDBC();
		Connection conn = JDBC.conectarJDBC("root","INF335UNICAMP","jdbc:mysql://localhost/loja");
		
		if (conn != null) {
			System.out.println("Lista Original de Produtos");
			lista.listaProdutosJDBC(conn);	
			lista.insereProdutoJDBC(conn, "7", "Prod7", "Bla Bla", "500.0", "Bla Bla");
			
			System.out.println();
			System.out.println("Lista com Novo Produto");
			lista.listaProdutosJDBC(conn);
			lista.alteraValorProdutoJDBC(conn, "7", "400");
			
			System.out.println();
			System.out.println("Lista com Valor do Produto Alterado");
			lista.listaProdutosJDBC(conn);
			
			System.out.println();
			System.out.println("Apaga Produto Número 7");
			lista.apagaProdutoJDBC(conn, "7");
			
			System.out.println("Volta a Lista Original de Produtos");
			lista.listaProdutosJDBC(conn);
			
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar conexão : " + e);
				e.printStackTrace();
			}	
		}
	}

	private static void executarMongoDB() {
		MongoDB lista = new MongoDB();
		
		System.out.println("Conectando com o MongoDB");
		MongoCollection<Document> colection = MongoDB.conectarMongoDB("test", "produtos", "mongodb://localhost");
		
		System.out.println("Lista Original de Produtos");
		lista.listaProdutosMongoDB(colection);
		
		lista.insereProdutoMongoDB(colection, "Prod7", "Bla Bla", "500.0", "Bla Bla");
		System.out.println();
		System.out.println("Lista com Novo Produto");
		lista.listaProdutosMongoDB(colection);
		
		lista.alteraValorProdutoMongoDB(colection, "Prod7", "400.0");
		System.out.println();
		System.out.println("Lista com Valor do Produto Alterado");
		lista.listaProdutosMongoDB(colection);
		
		System.out.println();
		System.out.println("Apaga Produto Número 7");
		lista.apagaProdutoMongoDB(colection, "Prod7");
		
		System.out.println("Volta a Lista Original de Produtos");
		lista.listaProdutosMongoDB(colection);
		
		try {
			lista.fecharConexao();
		} catch (Exception e) {
			System.out.println("Erro ao fechar conexão : " + e);
			e.printStackTrace();
		}
	}
}


