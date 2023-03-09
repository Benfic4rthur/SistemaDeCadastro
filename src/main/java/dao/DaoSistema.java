package dao;

import java.sql.Connection;

import conexaoJdbc.SingleConnection;
import model.MinhaUserPosJava;

public class DaoSistema {

    private Connection connection;

    public DaoSistema() {
        connection = SingleConnection.getConnection();
    }

	public void selectLogin(MinhaUserPosJava minhaUserPosJava) {
		// TODO Auto-generated method stub
		
	}
    
   
   
}
