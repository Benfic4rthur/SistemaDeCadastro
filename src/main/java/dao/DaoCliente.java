package dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import conexaoJdbc.SingleConnection;
import pacoteDados.Cliente;

public class DaoCliente {

	private Connection connection;

	public DaoCliente() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Cliente cliente) {
		try {
			String sql = "insert into tabela_cliente (id, nome, email, telefone, datanascimento, profissao, documento, tipopessoa, endereco)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setLong(1, cliente.getId());
			insert.setString(2, cliente.getNome());
			insert.setString(3, cliente.getEmail());
			insert.setString(4, cliente.getTelefone());
			
			// Converter a String para java.sql.Date
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date = sdf.parse(cliente.getDatanascimento());
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			
			insert.setDate(5, sqlDate);
			
			insert.setString(6, cliente.getProfissao());
			insert.setString(7, cliente.getDocumento());
			insert.setString(8, cliente.getTipopessoa());
			insert.setString(9, cliente.getEndereco());
			insert.execute();
			connection.commit(); // salva no banco
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String buscaId() {
	    String id = null; // Inicializa a variável com um valor padrão
	    try {
	        String sql = "SELECT MAX(id) FROM tabela_cliente";
	        PreparedStatement select = connection.prepareStatement(sql);
	        ResultSet rs = select.executeQuery();
	        if (rs.next()) {
	            int ultimoId = rs.getInt(1);
	            id = Integer.toString(ultimoId); // Converte o valor do ID para uma String
	        }
	        rs.close();
	        select.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return id; // Retorna o valor do ID como uma String
	}
}