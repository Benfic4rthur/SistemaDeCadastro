package model;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.Connection;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import conexaoJdbc.SingleConnection;
import dao.DaoLogin;

public class LoginSistema extends JFrame {
	private Connection connection;
	private JPanel contentPane;
	private JTextField loginfield;
	private JTextField senhafield;
	private Long id;
	private JCheckBox lembrarSenhaCheckbox;

	public static void main(String[] args) {
		// Verifica se as informações de login e senha já foram salvas anteriormente
		Preferences prefs = Preferences.userRoot().node("LoginSistema");
		String loginSalvo = prefs.get("login", null);
		String senhaSalva = prefs.get("senha", null);
		if (loginSalvo != null && senhaSalva != null) {
			// Se as informações já foram salvas, preenche os campos de login e senha da
			// janela de login
			LoginSistema loginSistema = new LoginSistema();
			loginSistema.loginfield.setText(loginSalvo);
			loginSistema.senhafield.setText(senhaSalva);
			loginSistema.setVisible(true);
		} else {
			// Se as informações ainda não foram salvas, cria a janela de login vazia
			new LoginSistema().setVisible(true);
		}
	}

	public LoginSistema() {
		connection = SingleConnection.getConnection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Login - Sistema Cadastral");

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Font font = new Font("Tahoma", Font.PLAIN, 16);
		int fieldWidth = 180;
		int fieldHeight = 30;

		JLabel lblde = new JLabel("Login:");
		contentPane.add(lblde);
		lblde.setFont(font);
		lblde.setBounds(800, 400, 80, 30);
		contentPane.add(lblde);

		JLabel lblPara = new JLabel("Senha:");
		contentPane.add(lblPara);
		lblPara.setFont(font);
		lblPara.setBounds(800, 450, 80, 30);
		contentPane.add(lblPara);

		loginfield = new JTextField();
		loginfield.setBounds(920, 398, 120, 20);
		contentPane.add(loginfield);
		loginfield.setColumns(10);
		loginfield.setFont(font);
		loginfield.setBounds(860, 398, fieldWidth, fieldHeight);
		contentPane.add(loginfield);

		JButton btnLogar = new JButton("Logar");
		btnLogar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            logar();
		        } catch (Exception e1) {
		            e1.printStackTrace();
		        }
		    }
		});
		btnLogar.setFont(font);
		btnLogar.setBounds(960, 500, 80, 30);
		contentPane.add(btnLogar);

		senhafield = new JPasswordField();
		senhafield.setBounds(920, 417, 120, 20);
		contentPane.add(senhafield);
		senhafield.setFont(font);
		senhafield.setBounds(860, 450, fieldWidth, fieldHeight);
		contentPane.add(senhafield);
		senhafield.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyPressed(KeyEvent e) {
		        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
		            try {
		                logar();
		            } catch (Exception e1) {
		                e1.printStackTrace();
		            }
		        }
		    }
		});
		contentPane.add(senhafield);


		// Configura o comportamento padrão do botão fechar da janela
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Centraliza a janela na tela
		setLocationRelativeTo(null);

		// Define o tamanho da janela como maximizado
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		lembrarSenhaCheckbox = new JCheckBox("Lembrar login/senha");
		lembrarSenhaCheckbox.setBounds(795, 500, 150, 30);
		contentPane.add(lembrarSenhaCheckbox);
	}

	private void salvarInformacoesLogin(String login, String senha) {
		// Implemente aqui a lógica para salvar as informações de login e senha do
		// usuário
		// em um arquivo ou banco de dados local, por exemplo:
		// - Criar um arquivo de configurações e escrever as informações nele
		// - Salvar as informações em um banco de dados local como SQLite ou H2
		// - Utilizar as APIs Preferences ou Properties do Java

		// Exemplo de implementação utilizando a API Preferences do Java:
		Preferences prefs = Preferences.userRoot().node("LoginSistema");
		prefs.put("login", login);
		prefs.put("senha", senha);
	}

	private void removerInformacoesLogin() {
		// Implemente aqui a lógica para remover as informações de login e senha do
		// usuário
		// do arquivo ou banco de dados local onde foram armazenados, por exemplo:
		// - Deletar o arquivo de configurações
		// - Deletar as informações do banco de dados local
		// - Utilizar as APIs Preferences ou Properties do Java

		// Exemplo de implementação utilizando a API Preferences do Java:
		Preferences prefs = Preferences.userRoot().node("LoginSistema");
		prefs.remove("login");
		prefs.remove("senha");

	}
	private void logar() throws IOException, Exception {
		DaoLogin dao = new DaoLogin();
		String login = loginfield.getText();
		String senha = senhafield.getText();

		if (login.isEmpty() || senha.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
			return;
		}

		Long id = dao.buscarLogin(login, senha);

		if (id != null) {
			// Armazena o valor do id na variável id na classe IndexSistema
			this.id = id;
			dispose(); // Fecha a janela atual
			IndexSistema sistemaGrafico = new IndexSistema(id);
			sistemaGrafico.setVisible(true); // Abre a janela do sistema gráfico
		} else {
			JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos.");
		}

		// Fecha a conexão com o banco de dados
		dao.fecharConexao();
	}
}
