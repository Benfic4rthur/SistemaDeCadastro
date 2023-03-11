package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import dao.DaoCliente;
import pacoteDados.Cliente;

public class CadastroClientes extends JFrame {

	/*
	 * public static void main(String[] args) { CadastroClientes frame = new
	 * CadastroClientes(null); frame.setVisible(true);
	 * 
	 * }
	 */

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Long id;
	private JTextField nome;
	private JTextField dataNascimento;
	private JTextField telefone;
	private JTextField email;
	private JTextField endereco;
	private JTextField profissao;
	private JRadioButton pessoaFisica;
	private JRadioButton pessoaJuridica;
	private ButtonGroup tipoPessoa;
	private JTextField cpfcnpJTextField;

	public CadastroClientes(Long id) {
		this.id = id;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Cadastro de clientes - Sistema Cadastral");
		setSize(800, 700);
		 // Impede a maximização da janela
	      setResizable(false);

	      // Define o estado da janela como "MAXIMIZED_VERT" para impedir a maximização vertical
	      setExtendedState(getExtendedState() | JFrame.MAXIMIZED_VERT);

	      // Define o estado da janela como "MAXIMIZED_HORIZ" para impedir a maximização horizontal
	      setExtendedState(getExtendedState() | JFrame.MAXIMIZED_HORIZ);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// Centralize a janela na tela
		setLocationRelativeTo(null);

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

		Font font = new Font("Tahoma", Font.PLAIN, 18);
		int fieldWidth = 180;
		int fieldHeight = 30;

		// Criação dos campos de texto e labels para os dados do cliente
		
		Font fonte = new Font("Tahoma", Font.PLAIN, 30);
		JLabel lblTitulo = new JLabel("Cadastro de clientes");
		lblTitulo.setBounds(270, 100, 300, 25);
		lblTitulo.setForeground(Color.WHITE); // Define a cor do texto como branco
		lblTitulo.setFont(fonte);
		contentPane.add(lblTitulo);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(100, 200, 80, 25);
		lblNome.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblNome);
		nome = new JTextField();
		lblNome.setFont(font);
		nome.setBounds(155, 200, 200, 25);
		contentPane.add(nome);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(420, 200, 80, 25);
		lblTelefone.setForeground(Color.WHITE);
		lblTelefone.setFont(font);
		contentPane.add(lblTelefone);

		// Cria uma máscara de formatação para o telefone
		MaskFormatter mascaraTelefone = new MaskFormatter();
		try {
			mascaraTelefone.setMask("(##) #####-####");
			mascaraTelefone.setPlaceholderCharacter('_');
		} catch (ParseException ex) {
			System.out.println("Erro ao criar a máscara de formatação: " + ex.getMessage());
		}
		telefone = new JFormattedTextField(mascaraTelefone);
		telefone.setBounds(500, 200, 200, 25);
		contentPane.add(telefone);

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(440, 250, 80, 25);
		lblEmail.setFont(font);
		lblEmail.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblEmail);

		email = new JTextField();
		email.setBounds(500, 250, 200, 25);
		contentPane.add(email);

		JLabel lblDataNascimento = new JLabel("Data de Nascimento:");
		lblDataNascimento.setBounds(85, 250, 200, 25);
		lblDataNascimento.setForeground(Color.WHITE);
		lblDataNascimento.setFont(font);
		contentPane.add(lblDataNascimento);

		dataNascimento = new JTextField("--/--/----");
		dataNascimento.setBounds(255, 250, 100, 25);
		dataNascimento.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (dataNascimento.getText().equals("--/--/----")) {
					dataNascimento.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				String data = dataNascimento.getText();
				if (data.equals("")) {
					dataNascimento.setText("dd/mm/aaaa");
				}
			}
		});
		dataNascimento.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
					return;
				}

				String date = dataNascimento.getText();
				int length = date.length();
				if (length == 2 || length == 5) {
				    LimitarCaracteres limitador = new LimitarCaracteres(10); // Limita a 10 caracteres
				    dataNascimento.setDocument(limitador);
				    date += "/";
				    dataNascimento.setText(date);
				}

			}
		});
		contentPane.add(dataNascimento);

		JLabel lblEndereco = new JLabel("Endereço:");
		lblEndereco.setBounds(417, 300, 80, 25);
		lblEndereco.setFont(font);
		lblEndereco.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblEndereco);

		endereco = new JTextField();
		endereco.setBounds(500, 300, 200, 25);
		contentPane.add(endereco);

		JLabel lblProfissao = new JLabel("Profissão:");
		lblProfissao.setBounds(72, 300, 80, 25);
		lblProfissao.setFont(font);
		lblProfissao.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblProfissao);

		profissao = new JTextField();
		profissao.setBounds(155, 300, 200, 25);
		contentPane.add(profissao);

		// Criação do campo para selecionar se a pessoa é física ou jurídica
		JLabel lblTipoPessoa = new JLabel("Tipo de Pessoa:");
		lblTipoPessoa.setBounds(280, 350, 150, 25);
		lblTipoPessoa.setFont(font);
		lblTipoPessoa.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblTipoPessoa);

		// Criação dos botões de opção para seleção do tipo de pessoa
		pessoaFisica = new JRadioButton("Pessoa Física");
		pessoaFisica.setBounds(410, 350, 150, 25);
		pessoaFisica.setFont(font);
		pessoaFisica.setForeground(Color.WHITE);
		pessoaFisica.setOpaque(false);
		pessoaFisica.setSelected(true); // Adicionando a seleção do botão
		contentPane.add(pessoaFisica);

		pessoaJuridica = new JRadioButton("Pessoa Jurídica");
		pessoaJuridica.setBounds(550, 350, 160, 25);
		pessoaJuridica.setFont(font);
		pessoaJuridica.setForeground(Color.WHITE);
		pessoaJuridica.setOpaque(false);
		contentPane.add(pessoaJuridica);

		tipoPessoa = new ButtonGroup();
		tipoPessoa.add(pessoaFisica);
		tipoPessoa.add(pessoaJuridica);

		// Criação dos campos de texto para CPF (caso pessoa física) e CNPJ (caso pessoa
		// jurídica)
		JLabel lblCpfCnpj = new JLabel("CPF/CNPJ:");
		lblCpfCnpj.setBounds(410, 400, 400, 25);
		lblCpfCnpj.setFont(font);
		lblCpfCnpj.setForeground(Color.WHITE); // Define a cor do texto como branco

		cpfcnpJTextField = new JTextField();
		cpfcnpJTextField.setBounds(500, 400, 200, 25);
		cpfcnpJTextField.setFont(font);
		cpfcnpJTextField.setForeground(Color.BLACK);// Define a cor do texto como preto
		cpfcnpJTextField.setDocument(new LimitarCaracteres(14));

		pessoaFisica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Limite de 11 caracteres para CPF
				cpfcnpJTextField.setDocument(new LimitarCaracteres(14));
				cpfcnpJTextField.addKeyListener(new KeyAdapter() {
				    @Override
				    public void keyTyped(KeyEvent e) {
				        String text = cpfcnpJTextField.getText();
				        int length = text.length();

				        if (length == 3 || length == 7) {
				            text += ".";
				            cpfcnpJTextField.setText(text);
				        } else if (length == 11) {
				            text += "-";
				            cpfcnpJTextField.setText(text);
				        
				        }
				    }
				});
			}
		});
		pessoaJuridica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Limite de 14 caracteres para CNPJ
				cpfcnpJTextField.setDocument(new LimitarCaracteres(18));
				cpfcnpJTextField.addKeyListener(new KeyAdapter() {
				    @Override
				    public void keyTyped(KeyEvent e) {
				        String text = cpfcnpJTextField.getText();
				        int length = text.length();

				        if (length == 2 || length == 6) {
				            text += ".";
				            cpfcnpJTextField.setText(text);
				        } else if (length == 10) {
				            text += "/";
				            cpfcnpJTextField.setText(text);
				        } else if (length == 15) {
				            text += "-";
				            cpfcnpJTextField.setText(text);
				        }
				    }
				});
			}
		});

		contentPane.add(lblCpfCnpj);
		contentPane.add(cpfcnpJTextField);

		// Criação do botão de salvar
		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					salvarNovoCliente();
					nome.setText("");
					email.setText("");
					telefone.setText("");
					dataNascimento.setText("");
					endereco.setText("");
					profissao.setText("");
					cpfcnpJTextField.setText("");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSalvar.setBounds(340, 450, 120, 23);
		contentPane.add(btnSalvar);

		// adicionar a imagem de fundo com camada abaixo dos campos de login e senha
		contentPane.add(dataNascimento, new Integer(-1));
		contentPane.add(nome, new Integer(-1));
		contentPane.add(telefone, new Integer(-1));
		contentPane.add(email, new Integer(-1));
		contentPane.add(endereco, new Integer(-1));
		contentPane.add(profissao, new Integer(-1));
		contentPane.add(pessoaFisica, new Integer(-1));
		contentPane.add(pessoaJuridica, new Integer(-1));
		contentPane.add(labelFundo, new Integer(Integer.MIN_VALUE));

		// Criação do botão de salvar
		JButton chamaListagem = new JButton("Lista de clientes");
		chamaListagem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					dispose(); // Fecha a janela atual
					ListaClientes listagemClientes = new ListaClientes();
					listagemClientes.setVisible(true); // Abre a janela do sistema gráfico
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		chamaListagem.setBounds(50, 600, 130, 23);
		contentPane.add(chamaListagem);

		// adicionar a imagem de fundo com camada abaixo dos campos de login e senha
		contentPane.add(dataNascimento, new Integer(-1));
		contentPane.add(nome, new Integer(-1));
		contentPane.add(telefone, new Integer(-1));
		contentPane.add(email, new Integer(-1));
		contentPane.add(endereco, new Integer(-1));
		contentPane.add(profissao, new Integer(-1));
		contentPane.add(pessoaFisica, new Integer(-1));
		contentPane.add(pessoaJuridica, new Integer(-1));
		contentPane.add(labelFundo, new Integer(Integer.MIN_VALUE));
	}

	// Método para salvar novo cliente
	public void salvarNovoCliente() {
		DaoCliente dao = new DaoCliente();
		String buscaUltimoId = dao.buscaId();
		long id = (Integer.parseInt(buscaUltimoId) + 1);
		String nomeCliente = nome.getText();
		String telefoneCliente = telefone.getText();
		String emailCliente = email.getText();
		String enderecoCliente = endereco.getText();
		String profissaoCliente = profissao.getText();
		SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy"); // Define o formato da entrada
		SimpleDateFormat formatoSaida = new SimpleDateFormat("yyyy-MM-dd"); // Define o formato da saída

		String dataNascimentoString = dataNascimento.getText(); // Obtém a string com a data de nascimento informada
		String dataNascimentoFormatada = "";
		try {
			Date dataNascimento = formatoEntrada.parse(dataNascimentoString); // Faz o parsing da data no formato de
																				// entrada
			dataNascimentoFormatada = formatoSaida.format(dataNascimento); // Formata a data para o formato desejado
		} catch (ParseException ex) {
			// Se houver um erro de parsing, trate-o aqui
		}
		String tipoPessoaCliente = tipoPessoa.getSelection().equals(pessoaFisica.getModel()) ? "F" : "J";
		String documentoCliente = "";
		if (tipoPessoaCliente.equals("F")) {
			documentoCliente = cpfcnpJTextField.getText();
			tipoPessoaCliente = "f";
		} else {
			documentoCliente = cpfcnpJTextField.getText();
			tipoPessoaCliente = "j";
		}

		if (nomeCliente == null || nomeCliente.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo nome deve ser preenchido.", "Erro", JOptionPane.ERROR_MESSAGE);
			return;
		} else if (emailCliente == null || emailCliente.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo e-mail deve ser preenchido.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else if (telefoneCliente == null || telefoneCliente.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo telefone deve ser preenchido.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else if (dataNascimentoFormatada == null || dataNascimentoFormatada.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo data de nascimento deve ser preenchido.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else if (profissaoCliente == null || profissaoCliente.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo profissão deve ser preenchido.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else if (documentoCliente == null || documentoCliente.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo CPF/CNPJ deve ser preenchido.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else if (enderecoCliente == null || enderecoCliente.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo endereço deve ser preenchido.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// se todos os campos estiverem preenchidos, cria o objeto Cliente
		Cliente cliente = new Cliente(id, nomeCliente, emailCliente, telefoneCliente, dataNascimentoFormatada,
				profissaoCliente, documentoCliente, tipoPessoaCliente, enderecoCliente);
		try {
			boolean enviadoComSucesso = false;
			try {
				enviadoComSucesso = cliente.salvaCadastro();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (enviadoComSucesso) {
				if (enviadoComSucesso) {
					JOptionPane.showMessageDialog(null, "Você cadastrou " + nomeCliente + " com sucesso!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao Salvar o Cadastro.");

			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao Salvar o Cadastro:" + ex.getMessage());
		}
	}
}