package sistema_pessoal.sistema_pessoal_cadastro;

import java.sql.SQLException;

import org.junit.Test;

import dao.DaoLogin;
import model.DadosLogin;

public class MinhaTesteBancoJdbc {
	private Long id;
	
	public void login() { // vai consultar o login no banco baseado nos dados informados pela userposjava
		DaoLogin dao = new DaoLogin();
		try {
			Long userPosJava = dao.buscarLogin(null, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}