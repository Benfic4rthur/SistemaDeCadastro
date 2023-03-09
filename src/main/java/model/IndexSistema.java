package model;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import conexaoJdbc.SingleConnection;

public class IndexSistema extends JFrame{
	private Connection connection;
    private JPanel contentPane;
    private JTextField loginfield;
    private JTextField senhafield;

    public static void main(String[] args) {
    	IndexSistema frame = new IndexSistema();
        frame.setVisible(true);
    }


	public IndexSistema() {
		connection = SingleConnection.getConnection();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Sistema Cadastral");

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JLabel lblde = new JLabel("Login:");
        lblde.setBounds(870, 450, 80, 14);
        contentPane.add(lblde);

        JLabel lblPara = new JLabel("Senha:");
        lblPara.setBounds(870, 480, 80, 14);
        contentPane.add(lblPara);

        loginfield = new JTextField();
        loginfield.setBounds(920, 448, 120, 20);
        contentPane.add(loginfield);
        loginfield.setColumns(10);
        
        senhafield = new JPasswordField();
        senhafield.setBounds(920, 477, 120, 20);
        contentPane.add(senhafield);


        JButton btnLogar = new JButton("Logar");
        btnLogar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	try {
					logar();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        btnLogar.setBounds(935, 510, 80, 23);
        contentPane.add(btnLogar);

        // Configura o comportamento padrão do botão fechar da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Centraliza a janela na tela
        setLocationRelativeTo(null);

        // Define o tamanho da janela como maximizado
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
	
	private void logar() throws IOException, Exception {
	    String login = loginfield.getText();
	    String senha = senhafield.getText();

	    if (login.isEmpty() || senha.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
	        return;
	    }
	    
	    // Consulta o banco de dados para verificar se as credenciais são válidas
	    MinhaUserPosJava user = selectLogin(login, senha);

	    if (user != null) {
	        JOptionPane.showMessageDialog(contentPane, "Logou");
	    } else {
	        JOptionPane.showMessageDialog(contentPane, "Credenciais inválidas");
	    }
	}

	private MinhaUserPosJava selectLogin(String login, String senha) throws SQLException {
	    String sql = "SELECT * FROM login WHERE login = ? AND senha = ?";
	    PreparedStatement select = connection.prepareStatement(sql);
	    select.setString(1, login);
	    select.setString(2, senha);
	    ResultSet rs = select.executeQuery();
	    if (rs.next()) {
	        return new MinhaUserPosJava(
	                rs.getLong("id"),
	                rs.getString("login"),
	                rs.getString("senha")
	        );
	    } else {
	        return null;
	    }
}
}