package mongoProject;

import java.sql.Connection;
import java.sql.SQLException;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import DataBase.JDBC;

public class MongoAppMain {

	public static void main(String[] args) {
		
		executarJDBC();

		System.out.println("===================================");
		
		executarMongoDB();

	}
	
	private static void executarJDBC() {
		System.out.println("Conectando com o JDBC");
		
		JDBC loja = new JDBC();
		Connection conn = JDBC.conectarJDBC("root","INF335UNICAMP","jdbc:mysql://localhost/loja");
		
		if (conn != null) {
			System.out.println("Lista Original de Produtos");
			loja.listaProdutosJDBC(conn);	
			loja.insereProdutoJDBC(conn, "7", "Prod7", "Bla Bla", "500.0", "Bla Bla");
			
			System.out.println("Lista com Novo Produto");
			loja.listaProdutosJDBC(conn);
			loja.alteraValorProdutoJDBC(conn, "7", "400");
			
			System.out.println("Lista com Valor do Produto Alterado");
			loja.listaProdutosJDBC(conn);
			
			System.out.println("Apaga Produto Número 7");
			loja.apagaProdutoJDBC(conn, "7");
			
			System.out.println("Volta a Lista Original de Produtos");
			loja.listaProdutosJDBC(conn);
			
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("Erro ao fechar conexão : " + e);
				e.printStackTrace();
			}	
		}
	}

	private static void executarMongoDB() {
		System.out.println("Conectando com o MongoDB");
		
		MongoClient client = MongoClients.create("mongodb://localhost");
		
		System.out.println("Conectando a base test");
		MongoDatabase db = client.getDatabase("test");
		
		System.out.println("Lista as colecoes da base test");
		Iterable<Document> collections = db.listCollections();
		for (Document col : collections) {
			System.out.println(col.get("name"));
		}
		
		MongoCollection<Document> colection = db.getCollection("produtos");
		
		System.out.println("Imprimindo Produtos");
		Iterable<Document> produtos = colection.find();
		for (Document produto : produtos) {
			String nome = produto.getString("nome");
			String descricao = produto.getString("descricao");
			String valor = produto.getString("valor");
			String estado =  produto.getString("estado");
			System.out.println(nome + " -- " + descricao + " -- " + valor + " -- " + estado);
		}
	}
	
	
}


