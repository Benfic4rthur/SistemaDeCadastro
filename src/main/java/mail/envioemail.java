package mail;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

public class envioemail extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField deField;
	private JTextField telefoneField;
	private JTextField cargoField;
	private JTextField emailToField;
	private JTextField emailSubjectField;
	private JTextArea emailMessageArea;
	private JButton btnAnexar;
	private JTextField anexoField;
	private File anexo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					envioemail frame = new envioemail();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public envioemail() {
		setTitle("Envio de email");
		setBounds(100, 100, 470, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		// Configura o tamanho da janela
		setSize(470, 380);
		// Obtém as dimensões da tela
		Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
		// Calcula a posição da janela no centro da tela
		int x = (tela.width - getWidth()) / 2;
		int y = (tela.height - getHeight()) / 2;
		// Configura a posição da janela
		setLocation(x, y);
		// Configura o comportamento padrão do botão fechar da janela
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		/* Labels */

		JLabel lblde = new JLabel("Nome:");
		lblde.setBounds(10, 10, 66, 14);
		contentPane.add(lblde);

		JLabel lblFone = new JLabel("Telefone:");
		lblFone.setBounds(10, 35, 96, 14);
		contentPane.add(lblFone);

		JLabel lblCargo = new JLabel("Profissão:");
		lblCargo.setBounds(10, 60, 96, 14);
		contentPane.add(lblCargo);

		JLabel lblPara = new JLabel("Para:");
		lblPara.setBounds(10, 85, 46, 14);
		contentPane.add(lblPara);

		JLabel lblAssunto = new JLabel("Assunto:");
		lblAssunto.setBounds(10, 110, 66, 14);
		contentPane.add(lblAssunto);

		JLabel lblMensagem = new JLabel("Mensagem:");
		lblMensagem.setBounds(10, 135, 66, 14);
		contentPane.add(lblMensagem);

		/* Text Fields */

		deField = new JTextField();
		deField.setBounds(106, 7, 335, 20);
		contentPane.add(deField);
		deField.setColumns(10);

		// Cria uma máscara de formatação para o telefone
		MaskFormatter mascaraTelefone = new MaskFormatter();
		try {
			mascaraTelefone.setMask("(##) #####-####");
			mascaraTelefone.setPlaceholderCharacter('_');
		} catch (ParseException ex) {
			System.out.println("Erro ao criar a máscara de formatação: " + ex.getMessage());
		}
		telefoneField = new JFormattedTextField(mascaraTelefone);
		telefoneField.setBounds(106, 32, 335, 20);
		contentPane.add(telefoneField);
		

		cargoField = new JTextField();
		cargoField.setBounds(106, 57, 335, 20);
		contentPane.add(cargoField);
		cargoField.setColumns(10);

		emailToField = new JTextField();
		emailToField.setBounds(106, 82, 335, 20);
		contentPane.add(emailToField);
		emailToField.setColumns(10);

		emailSubjectField = new JTextField();
		emailSubjectField.setBounds(106, 107, 335, 20);
		contentPane.add(emailSubjectField);
		emailSubjectField.setColumns(10);

		/* Mensagem */

		emailMessageArea = new JTextArea();
		emailMessageArea.setBounds(106, 132, 335, 113);
		contentPane.add(emailMessageArea);

		/* Botão para anexar arquivo */

		btnAnexar = new JButton("Anexar");
		btnAnexar.setBounds(10, 265, 89, 23);
		contentPane.add(btnAnexar);

		anexoField = new JTextField();
		anexoField.setEditable(false);
		anexoField.setBounds(106, 266, 335, 20);
		contentPane.add(anexoField);
		anexoField.setColumns(10);
		/* Listener do botão de anexo */

		btnAnexar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(envioemail.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					anexo = chooser.getSelectedFile();
					anexoField.setText(anexo.getName());
				}
			}
		});

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					enviarEmail();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnEnviar.setBounds(130, 300, 89, 23);
		contentPane.add(btnEnviar);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emailSubjectField.setText("");
				emailMessageArea.setText("");
				deField.setText("");
				telefoneField.setText("");
				cargoField.setText("");
				emailToField.setText("");
			}
		});
		btnLimpar.setBounds(230, 300, 89, 23);
		contentPane.add(btnLimpar);
	}

	private void enviarEmail() throws IOException, Exception {
		String telefone = telefoneField.getText();
		telefone = telefone.replaceAll("[^0-9]", ""); // remove todos os caracteres não numéricos
		if (telefone.length() >= 2) {
			telefone = "(" + telefone.substring(0, 2) + ")" + telefone.substring(2);
		}
		if (telefone.length() >= 9) {
			telefone = telefone.substring(0, 8) + "-" + telefone.substring(8);
		}
		telefoneField.setText(telefone);
		String cargo = cargoField.getText();
		String de = deField.getText();
		String para = emailToField.getText();
		String assunto = emailSubjectField.getText();
		String mensagem = emailMessageArea.getText();
		String username = "seu email hotmail.com aqui";
		String password = "mhytboqxsugbwiyo";
		StringBuilder stringBuildermensagemEmail = new StringBuilder();
		stringBuildermensagemEmail.append("<html><body style='background-color:#F5F5F5;'>");
		stringBuildermensagemEmail.append("<div style='background-color:#FFFFFF;padding:20px;'>");
		stringBuildermensagemEmail
				.append("<h1 style='color:#333;font-size:22px;font-weight:bold;margin-top:0;'>" + assunto + "</h1>");
		stringBuildermensagemEmail
				.append("<h1 style='color:#333;font-size:16px;font-weight:bold;margin-top:0;'>" + mensagem + "</h1>");
		stringBuildermensagemEmail.append("<p style='margin-bottom:20px;font-size:14px;'>");
		stringBuildermensagemEmail.append("<br>");
		stringBuildermensagemEmail.append("<span style='font-size:14px;font-weight:bold;'>" + de + "</span><br>");
		stringBuildermensagemEmail.append(cargo + "<br>");
		stringBuildermensagemEmail.append(telefone + "<br>");
		stringBuildermensagemEmail.append("“A grandeza não consiste em receber honras, mas em merecê-las.”</p>");
		stringBuildermensagemEmail.append(
				"<p style='color:#888;'>Obs.: As informações contidas neste e-mail e nos arquivos anexados podem ser informações confidenciais ou privilegiadas.<br>");
		stringBuildermensagemEmail.append(
				"Caso você não seja o destinatário correto, apague o conteúdo desta mensagem e notifique o remetente imediatamente.<br>");
		stringBuildermensagemEmail.append(
				"<span style='color:#008000;'>Antes de imprimir pense em sua responsabilidade e compromisso com o Meio Ambiente!</span></p>");
		stringBuildermensagemEmail.append("</div>");
		stringBuildermensagemEmail.append(
				"<a href='#' style='display:inline-block;background-color:#4CAF50;color:#fff;padding:10px 20px;text-decoration:none;border-radius:5px;'>Responder</a>");
		stringBuildermensagemEmail.append("</body></html>");
		if (de.isEmpty() || para.isEmpty() || assunto.isEmpty() || mensagem.isEmpty() || telefone.isEmpty()
				|| cargo.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
			return;
		}

		if (anexo == null || !anexo.exists()) {
			int anexoemail = JOptionPane.showConfirmDialog(null, "Deseja prosseguir o envio do e-mail sem anexo?");
			if (anexoemail == JOptionPane.YES_OPTION) {
				EnvioJavaMail envioSemAnexo = new EnvioJavaMail(username, password, de, para, assunto,
						stringBuildermensagemEmail.toString());
				try {
					boolean enviadoComSucessoSemAnexo = envioSemAnexo.enviarEmail(true);
					if (enviadoComSucessoSemAnexo) {
						// Criar JProgressBar para mostrar o loading
						JProgressBar progressBar = new JProgressBar(0, 100);
						progressBar.setIndeterminate(true);
						progressBar.setString("Enviando E-mails...");
						progressBar.setStringPainted(true);
						// Criar JScrollPane com a barra de progresso
						JScrollPane scrollPane = new JScrollPane(progressBar);
						scrollPane.setBounds(30, 40, 424, 294);
						// Criar JOptionPane com o JScrollPane
						JOptionPane optionPane = new JOptionPane(scrollPane, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
						JDialog dialog = optionPane.createDialog("Aguarde...");
						// Centralizar o JOptionPane no JFrame pai
						dialog.setLocationRelativeTo(null);
						// Iniciar Timer para fechar o JOptionPane após 3 segundos
						Timer timer = new Timer(3000, (event) -> {
						    dialog.dispose();
						    dialog.setVisible(false);
						    JOptionPane.showMessageDialog(null, "Email enviado com sucesso!");
						});
						timer.setRepeats(false);
						timer.start();
						// Mostrar o JOptionPane com a barra de loading
						dialog.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Ocorreu um erro ao enviar o e-mail.");
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro ao enviar o e-mail: " + ex.getMessage());
				}
			} else {
				JOptionPane.showMessageDialog(null, "Por favor, preencha o anexo.");
				return;
			}
		} else if (anexo != null && anexo.exists()) {
			EnvioJavaMail envio = new EnvioJavaMail(username, password, de, para, assunto,
					stringBuildermensagemEmail.toString(), anexo);
			try {
				boolean enviadoComSucesso = envio.enviarEmailAnexo(true);
				if (enviadoComSucesso) {
					// Criar JProgressBar para mostrar o loading
					JProgressBar progressBar = new JProgressBar(0, 100);
					progressBar.setIndeterminate(true);
					progressBar.setString("Enviando E-mails...");
					progressBar.setStringPainted(true);
					// Criar JScrollPane com a barra de progresso
					JScrollPane scrollPane = new JScrollPane(progressBar);
					scrollPane.setBounds(30, 40, 424, 294);
					// Criar JOptionPane com o JScrollPane
					JOptionPane optionPane = new JOptionPane(scrollPane, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
					JDialog dialog = optionPane.createDialog("Aguarde...");
					// Centralizar o JOptionPane no JFrame pai
					dialog.setLocationRelativeTo(null);
					// Iniciar Timer para fechar o JOptionPane após 3 segundos
					Timer timer = new Timer(3000, (event) -> {
					    dialog.dispose();
					    dialog.setVisible(false);
					    JOptionPane.showMessageDialog(null, "Email enviado com sucesso!");
					});
					timer.setRepeats(false);
					timer.start();
					// Mostrar o JOptionPane com a barra de loading
					dialog.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro ao enviar o e-mail.");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao enviar o e-mail: " + ex.getMessage());
			}
		}

	}
}