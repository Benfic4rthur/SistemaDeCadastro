package sistema_pessoal.sistema_pessoal_cadastro;

import java.sql.SQLException;

import org.junit.Test;

import dao.DaoSistema;
import model.MinhaUserPosJava;

public class MinhaTesteBancoJdbc {
	private Long id;

	@Test
	public void initBanco() throws SQLException {
		DaoSistema dao = new DaoSistema();
		id = (Long) null;
		MinhaUserPosJava minhaUserPosJava = new MinhaUserPosJava(id, null, null);
		
		dao.selectLogin(minhaUserPosJava);
	}
}