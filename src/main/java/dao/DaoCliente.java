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
import processamentoDeDados.Cliente;

public class DaoCliente {

	private Connection connection;

	public DaoCliente() {
		connection = SingleConnection.getConnection();
	}

	public void salvar(Cliente cliente) {
		PreparedStatement insert = null;
		try {
			String sql = "insert into tabela_cliente (id, nome, email, telefone, datanascimento, profissao, documento, tipopessoa,"
					+ " endereco, cep, numero, tipomoradia, cidade, estado )"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			insert = connection.prepareStatement(sql);
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
			insert.setString(10, cliente.getCep());
			insert.setString(11, cliente.getNumero());
			insert.setString(12, cliente.getTipomoradia());
			insert.setString(13, cliente.getCidade());
			insert.setString(14, cliente.getEstado());

			insert.execute();
			connection.commit(); // salva no banco
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (insert != null) {
					insert.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public String buscaId() {
		PreparedStatement select = null;
		String id = null; // Inicializa a vari??vel com um valor padr??o
		try {
			String sql = "SELECT MAX(id) FROM tabela_cliente";
			select = connection.prepareStatement(sql);
			ResultSet rs = select.executeQuery();
			if (rs.next()) {
				int ultimoId = rs.getInt(1);
				id = Integer.toString(ultimoId); // Converte o valor do ID para uma String
			}
			rs.close();
			select.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (select != null) {
					select.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return id; // Retorna o valor do ID como uma String
	}

	private Long getNextId() throws Exception {
		PreparedStatement getNextId = null;
		String sql = "select max(id) from cadastro_de_macacos";
		getNextId = connection.prepareStatement(sql);
		ResultSet resultSet = getNextId.executeQuery();
		if (resultSet.next()) {
			return resultSet.getLong(1) + 1;
		} else {
			return 1L;
		}
	}

	public List<Cliente> editar() {
		PreparedStatement editar = null;
		List<Cliente> clientes = new ArrayList<>();
		try {
			String sql = "SELECT id, nome, email, telefone, datanascimento, profissao, documento, tipopessoa, endereco, cep, numero, tipomoradia, cidade, estado FROM tabela_cliente";
			editar = connection.prepareStatement(sql);
			ResultSet rs = editar.executeQuery();
			while (rs.next()) {
				Cliente cliente = new Cliente((int) rs.getLong("id"), rs.getString("nome"), rs.getString("email"),
						rs.getString("telefone"), rs.getString("datanascimento"), rs.getString("profissao"),
						rs.getString("documento"), rs.getString("tipopessoa"), rs.getString("endereco"),
						rs.getString("cep"), rs.getString("numero"), rs.getString("tipomoradia"),
						rs.getString("cidade"), rs.getString("estado"));
				clientes.add(cliente);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (editar != null) {
					editar.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return clientes;
	}

	public Cliente buscarPorId(long id) {
		PreparedStatement select = null;
		Cliente cliente = null;
		try {
			String sql = "SELECT nome, email, telefone, datanascimento, profissao, documento, tipopessoa, endereco, cep, numero, tipomoradia, cidade, estado FROM tabela_cliente WHERE id=?";
			select = connection.prepareStatement(sql);
			select.setLong(1, id);
			ResultSet rs = select.executeQuery();
			if (rs.next()) {
				cliente = new Cliente((int) id, rs.getString("nome"), rs.getString("email"),
						rs.getString("telefone"), rs.getString("datanascimento"), rs.getString("profissao"),
						rs.getString("documento"), rs.getString("tipopessoa"), rs.getString("endereco"),
						rs.getString("cep"), rs.getString("numero"), rs.getString("tipomoradia"),
						rs.getString("cidade"), rs.getString("estado"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (select != null) {
					select.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cliente;
	}

	public void update(Cliente cliente) throws SQLException {
		PreparedStatement update = null;
		try {
			String sql = "UPDATE tabela_cliente SET nome=?, email=?, telefone=?, datanascimento=?, "
					+ "profissao=?, documento=?, tipopessoa=?, endereco=?, numero=?, cidade=?, estado=?, cep=?, tipomoradia=? WHERE id=?;";
			update = connection.prepareStatement(sql);
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
			update.setString(9, cliente.getNumero());
			update.setString(10, cliente.getCidade());
			update.setString(11, cliente.getEstado());
			update.setString(12, cliente.getCep());			
			update.setString(13, cliente.getTipomoradia());
			
			
			update.setLong(14, cliente.getId());

			update.execute();
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			try {
				if (update != null) {
					update.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void excluir(long userId) {
		PreparedStatement delete = null;
		try {
			String sql = "DELETE FROM tabela_cliente WHERE id=?";
			delete = connection.prepareStatement(sql);
			delete.setLong(1, userId);
			delete.execute();
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (delete != null) {
					delete.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}