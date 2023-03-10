package model;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import dao.DaoLogin;
import mail.EnvioJavaMail;
import mail.envioemail;

public class IndexSistema extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Long id;

	public IndexSistema(Long id) {
		this.id = id;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Index - Sistema Cadastral");
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();

		// carregar imagem de fundo
		ImageIcon imagemFundo = new ImageIcon(
				"C:\\workspace-java\\sistema-pessoal-cadastro\\src\\main\\java\\images\\background.jpg");

		// criar JLabel com a imagem de fundo
		JLabel labelFundo = new JLabel(imagemFundo);
		labelFundo.setBounds(0, 0, width, height);
		contentPane.add(labelFundo, new Integer(Integer.MIN_VALUE));

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnCadastro = new JMenu("Clientes");
		menuBar.add(mnCadastro);

		JMenuItem mntmCadastroCliente = new JMenuItem("Cadastro de cliente");
		mntmCadastroCliente.addActionListener(e -> {
		    
		    CadastroClientes cadastroClientes = new CadastroClientes(id);
		    cadastroClientes.setVisible(true); // Abre a janela do sistema gráfico
		});

		JMenuItem mnListagemCliente = new JMenuItem("Listagem de clientes");
		mnListagemCliente.addActionListener(e -> {
		    ListaClientes listagemClientes = new ListaClientes();
		    listagemClientes.setVisible(true); // Abre a janela do sistema gráfico
		});
		
		

		// carrega a imagem que será usada como ícone
		ImageIcon iconeSair = new ImageIcon("C:\\workspace-java\\sistema-pessoal-cadastro\\src\\main\\java\\images\\logout.png");

		// cria o JMenuItem e define o ícone
		JMenuItem mnSair = new JMenuItem();
		mnSair.setIcon(iconeSair);

		// define o tamanho máximo da imagem
		Dimension maxIconSize = new Dimension(30, 30);
		mnSair.setMaximumSize(maxIconSize);
		mnSair.addActionListener(e -> {
		    dispose(); // Fecha a janela atual
		    LoginSistema login = new LoginSistema();
		    login.setVisible(true); // Abre a janela do sistema gráfico de login
		});
		
		JMenu mnMail = new JMenu("E-mail");
		menuBar.add(mnMail);

		JMenuItem mnEnviomail = new JMenuItem("Envio de E-mail");
		mnEnviomail.addActionListener(e -> {
		    
			  envioemail email = new envioemail();
			  email.setVisible(true); // Abre a janela do sistema gráfico
		});
		
		menuBar.add(mnCadastro);
		menuBar.add(Box.createHorizontalGlue()); // Adiciona espaço horizontal entre os itens de menu
		// Cria um Box.Filler para preencher o espaço restante na barra de menu
		Dimension fillerSize = new Dimension(0, 0); // tamanho inicial
		Dimension maxSize = new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE); // tamanho máximo
		JComponent filler = new Box.Filler(fillerSize, fillerSize, maxSize);
		menuBar.add(filler);
		menuBar.add(mnMail);
		menuBar.add(mnSair);
		mnMail.add(mnEnviomail);
		mnCadastro.add(mntmCadastroCliente);
		mnCadastro.add(mnListagemCliente);
		mnCadastro.add(new JSeparator()); // Adiciona um separador vertical

	}

}
