package dao;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
	 private Long getNextId() throws Exception {
	        String sql = "select max(id) from cadastro_de_macacos";
	        PreparedStatement statement = connection.prepareStatement(sql);
	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            return resultSet.getLong(1) + 1;
	        } else {
	            return 1L;
	        }
	    }

	 public List<Cliente> editar() {
		    List<Cliente> clientes = new ArrayList<>();
		    try {
		        String sql = "SELECT id, nome, email, telefone, datanascimento, profissao, documento, tipopessoa, endereco FROM tabela_cliente";
		        PreparedStatement select = connection.prepareStatement(sql);
		        ResultSet rs = select.executeQuery();
		        while (rs.next()) {
		            Cliente cliente = new Cliente(
		                    (int) rs.getLong("id"),
		                    rs.getString("nome"),
		                    rs.getString("email"),
		                    rs.getString("telefone"),
		                    rs.getString("datanascimento"),
		                    rs.getString("profissao"),
		                    rs.getString("documento"),
		                    rs.getString("tipopessoa"),
		                    rs.getString("endereco")
		            );
		            clientes.add(cliente);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return clientes;
		}

		public Cliente buscarPorId(long id) {
		    Cliente cliente = null;
		    try {
		        String sql = "SELECT nome, email, telefone, datanascimento, profissao, documento, tipopessoa, endereco FROM tabela_cliente WHERE id=?";
		        PreparedStatement select = connection.prepareStatement(sql);
		        select.setLong(1, id);
		        ResultSet rs = select.executeQuery();
		        if (rs.next()) {
		            cliente = new Cliente(
		                    (int) id,
		                    rs.getString("nome"),
		                    rs.getString("email"),
		                    rs.getString("telefone"),
		                    rs.getString("datanascimento"),
		                    rs.getString("profissao"),
		                    rs.getString("documento"),
		                    rs.getString("tipopessoa"),
		                    rs.getString("endereco")
		            );
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		    return cliente;
		}
	    
		public void update(Cliente cliente) {
		    try {
		        String sql = "UPDATE tabela_cliente SET nome=?, email=?, telefone=?, datanascimento=?, "
		        		+ "profissao=?, documento=?, tipopessoa=?, endereco=? WHERE id=?;";
		        PreparedStatement update = connection.prepareStatement(sql);
		        update.setString(1, cliente.getNome());
		        update.setString(2, cliente.getEmail());
		        update.setString(3, cliente.getTelefone());
		        
		        // Converter a String para java.sql.Date
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        java.util.Date date = sdf.parse(cliente.getDatanascimento());
		        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		        
		        update.setDate(4, sqlDate);
		        
		        update.setString(5, cliente.getProfissao());
		        update.setString(6, cliente.getDocumento());
		        update.setString(7, cliente.getTipopessoa());
		        update.setString(8, cliente.getEndereco());
		        update.setLong(9, cliente.getId());
		        update.execute();
		        connection.commit();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}

		public void excluir(long userId) {
		    try {
		        String sql = "DELETE FROM tabela_cliente WHERE id=?";
		        PreparedStatement delete = connection.prepareStatement(sql);
		        delete.setLong(1, userId);
		        delete.execute();
		        connection.commit();
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}

	   
}