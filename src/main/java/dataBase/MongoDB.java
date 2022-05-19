package dataBase;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class MongoDB {

	private static MongoClient client = null;
	
	public static MongoCollection<Document> conectarMongoDB(String baseName, String collection, String url) {
		MongoCollection<Document> colection = null;
	  
	    try {
	    	MongoClient client = MongoClients.create(url);   	
	    	MongoDatabase db = client.getDatabase(baseName);
	    	colection = db.getCollection(collection);
	    	
		} catch (Exception e) {
			System.out.println("Erro de Conexão :" + e);
			e.printStackTrace();
		}
	    
		return colection;				
	}
	
	public void fecharConexao() {
		if (client != null) {
			client.close();
		}
			
	}
	
	public void listaProdutosMongoDB(MongoCollection<Document> colection) {
		
		Iterable<Document> produtos = colection.find();
		for (Document produto : produtos) {
			String nome = produto.getString("nome");
			String descricao = produto.getString("descricao");
			String valor = produto.getString("valor");
			String estado =  produto.getString("estado");
			System.out.println(nome + " -- " + descricao + " -- " + valor + " -- " + estado);
		}
	}
	
	public void insereProdutoMongoDB(MongoCollection<Document> colection, String name, String descricao, String valor, String estado) {
		Document produtos = new Document();
		produtos.append("nome", name);
		produtos.append("descricao", descricao);
		produtos.append("valor", valor);
		produtos.append("estado", estado);
		
		colection.insertOne(produtos);
	}

	public void alteraValorProdutoMongoDB(MongoCollection<Document> colection, String name, String valor) {
		BasicDBObject produto = new BasicDBObject();
		produto.append("nome", name);
		
		BasicDBObject novoValor = new BasicDBObject();
		novoValor.append("$set", new BasicDBObject().append("valor", valor));

		colection.updateMany(produto, novoValor);
	}
	
	public void apagaProdutoMongoDB(MongoCollection<Document> colection, String name) {
		
		colection.deleteOne(Filters.eq("nome", name));
	}
}
