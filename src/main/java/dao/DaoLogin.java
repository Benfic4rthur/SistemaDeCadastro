package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexaoJdbc.SingleConnection;

public class DaoLogin {

	private Connection connection;

	public DaoLogin() {
		connection = SingleConnection.getConnection();
	}

	public Long buscarLogin(String login, String senha) throws Exception {
	    String sql = "SELECT id FROM login WHERE login = ? AND senha = ?";
	    PreparedStatement preparedStatement = null;
	    ResultSet resultado = null;
	    try {
	        preparedStatement = connection.prepareStatement(sql);
	        preparedStatement.setString(1, login);
	        preparedStatement.setString(2, senha);
	        resultado = preparedStatement.executeQuery();
	        if (resultado.next()) {
	            return resultado.getLong("id");
	        } else {
	            return null;
	        }
	    } catch (SQLException e) {
	        throw new Exception("Erro ao buscar usuário no banco de dados: " + e.getMessage());
	    } finally {
	        if (resultado != null) {
	            resultado.close();
	        }
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	    }
	}
}
