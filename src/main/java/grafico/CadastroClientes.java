package grafico;

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
import javax.swing.JComboBox;
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

import com.itextpdf.text.xml.simpleparser.NewLineHandler;

import dao.DaoCliente;
import processamentoDeDados.Cliente;
import processamentoDeDados.LimitarCaracteres;

public class CadastroClientes extends JFrame {

	
	 public static void main(String[] args) { CadastroClientes frame = new
	 CadastroClientes(null); frame.setVisible(true);
	 
	 }
	 

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
	private ButtonGroup tipomoradia;
	private JTextField cpfcnpJTextField;
	private JTextField cidadeField;
	private JTextField cepField;
	private JRadioButton apartamento;
	private JRadioButton casa;
	private String estado;
	private JTextField tipomoradiaField;
	private JTextField estadobox;
	private JTextField numeroField;

	public CadastroClientes(Long id) {
		this.id = id;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Cadastro de clientes - Sistema Cadastral");
		setSize(800, 600);
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
		lblTitulo.setBounds(270, 80, 300, 25);
		lblTitulo.setForeground(Color.WHITE); // Define a cor do texto como branco
		lblTitulo.setFont(fonte);
		contentPane.add(lblTitulo);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(100, 180, 80, 25);
		lblNome.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblNome);
		nome = new JTextField();
		lblNome.setFont(font);
		nome.setBounds(155, 180, 200, 25);
		contentPane.add(nome);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(420, 180, 80, 25);
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
		telefone.setBounds(500, 180, 200, 25);
		contentPane.add(telefone);

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(440, 230, 80, 25);
		lblEmail.setFont(font);
		lblEmail.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblEmail);

		email = new JTextField();
		email.setBounds(500, 230, 200, 25);
		contentPane.add(email);

		JLabel lblDataNascimento = new JLabel("Data de Nascimento:");
		lblDataNascimento.setBounds(85, 230, 200, 25);
		lblDataNascimento.setForeground(Color.WHITE);
		lblDataNascimento.setFont(font);
		contentPane.add(lblDataNascimento);

		dataNascimento = new JTextField("--/--/----");
		dataNascimento.setBounds(255, 230, 100, 25);
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

		JLabel lblrua = new JLabel("Rua:");
		lblrua.setBounds(390, 280, 60, 25);
		lblrua.setFont(font);
		lblrua.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblrua);

		endereco = new JTextField();
		endereco.setBounds(430, 280, 150, 25);
		contentPane.add(endereco);
		
		JLabel lblNumero = new JLabel("Número:");
		lblNumero.setBounds(587, 280, 80, 25);
		lblNumero.setFont(font);
		lblNumero.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblNumero);

		numeroField = new JTextField();
		numeroField.setBounds(660, 280, 40, 25);
		contentPane.add(numeroField);

		JLabel lblProfissao = new JLabel("Profissão:");
		lblProfissao.setBounds(72, 280, 80, 25);
		lblProfissao.setFont(font);
		lblProfissao.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblProfissao);

		profissao = new JTextField();
		profissao.setBounds(155, 280, 200, 25);
		contentPane.add(profissao);
		
		JLabel lblcidade = new JLabel("Cidade:");
		lblcidade.setBounds(72, 330, 60, 25);
		lblcidade.setFont(font);
		lblcidade.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblcidade);

		cidadeField = new JTextField();
		cidadeField.setBounds(135, 330, 150, 25);
		contentPane.add(cidadeField);
		
		
		// Cria o JLabel para exibir "UF:"
		JLabel lbluf = new JLabel("UF:");
		lbluf.setBounds(292, 330, 60, 25);
		lbluf.setFont(font);
		lbluf.setForeground(Color.WHITE);
		contentPane.add(lbluf);

		// Array com todos os estados brasileiros
		String[] estados = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};

		// Cria o JComboBox com os estados
		JComboBox<String> estadosBox = new JComboBox<>(estados);
		estadosBox.setBounds(325, 330, 50, 25);
		estadosBox.addActionListener(e -> {
		    JComboBox<String> cb = (JComboBox<String>) e.getSource();
		    estado = (String) cb.getSelectedItem();
		    estadobox.setText(estado); // Define o valor do estado selecionado no JTextField estadobox
		});

		estadobox = new JTextField();
		contentPane.add(estadobox);
		contentPane.add(estadosBox);
		
		JLabel lblCep = new JLabel("Cep:");
		lblCep.setBounds(385, 330, 60, 25);
		lblCep.setFont(font);
		lblCep.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblCep);

		cepField = new JTextField();
		cepField.setDocument(new LimitarCaracteres(9));
		cepField.setBounds(425, 330, 120, 25);
		cepField.addKeyListener(new KeyAdapter() {
		    @Override
		    public void keyTyped(KeyEvent e) {
		        String text = cepField.getText();
		        int length = text.length();

		        if (length == 5) {
		            text += "-";
		            cepField.setText(text);
		        }
		    }
		});
		contentPane.add(cepField);
		
		JLabel lbltipomoradia = new JLabel("Tipo de moradia:");
		lbltipomoradia.setFont(font);
		lbltipomoradia.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lbltipomoradia);
		lbltipomoradia.setVisible(false);


		// Criação do grupo de botões de opção para seleção do tipo de moradia
		ButtonGroup tipomoradia = new ButtonGroup();

		apartamento = new JRadioButton("Apto");
		apartamento.setBounds(560, 330, 70, 25);
		apartamento.setFont(font);
		apartamento.setForeground(Color.WHITE);
		apartamento.setOpaque(false);
		apartamento.setSelected(true); // Adicionando a seleção do botão
		tipomoradia.add(apartamento);

		casa = new JRadioButton("Casa");
		casa.setBounds(620, 330, 100, 25);
		casa.setFont(font);
		casa.setForeground(Color.WHITE);
		casa.setOpaque(false);
		tipomoradia.add(casa);

		contentPane.add(apartamento);
		contentPane.add(casa);

		tipomoradiaField = new JTextField();
		contentPane.add(tipomoradiaField);

		apartamento.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        tipomoradiaField.setText("Apartamento");
		    }
		});
		casa.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        tipomoradiaField.setText("Casa");
		    }
		});

		// Criação do campo para selecionar se a pessoa é física ou jurídica
		JLabel lblTipoPessoa = new JLabel("Tipo de Pessoa:");
		lblTipoPessoa.setBounds(280, 380, 150, 25);
		lblTipoPessoa.setFont(font);
		lblTipoPessoa.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblTipoPessoa);

		// Criação dos botões de opção para seleção do tipo de pessoa
		pessoaFisica = new JRadioButton("Pessoa Física");
		pessoaFisica.setBounds(415, 380, 150, 25);
		pessoaFisica.setFont(font);
		pessoaFisica.setForeground(Color.WHITE);
		pessoaFisica.setOpaque(false);
		pessoaFisica.setSelected(true); // Adicionando a seleção do botão
		contentPane.add(pessoaFisica);

		pessoaJuridica = new JRadioButton("Pessoa Jurídica");
		pessoaJuridica.setBounds(550, 380, 160, 25);
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
		lblCpfCnpj.setBounds(410, 430, 400, 25);
		lblCpfCnpj.setFont(font);
		lblCpfCnpj.setForeground(Color.WHITE); // Define a cor do texto como branco

		cpfcnpJTextField = new JTextField();
		cpfcnpJTextField.setBounds(500, 430, 200, 25);
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
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnSalvar.setBounds(160, 520, 120, 30);
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
		chamaListagem.setBounds(20, 520, 130, 30);
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
		contentPane.add(estadosBox, new Integer(-1));
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
		String cidade = cidadeField.getText();
		String estado = estadobox.getText();
		String cep = cepField.getText();
		String numero = numeroField.getText();
		String tipomoradia = tipomoradiaField.getText();
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
		} else if (cidade == null || cidade.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo cidade deve ser preenchido.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else if (estado == null || estado.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo UF deve ser preenchido.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else if (cep == null || cep.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo CEP deve ser preenchido.", "Erro",
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
				profissaoCliente, documentoCliente, tipoPessoaCliente, enderecoCliente , cep, numero, tipomoradia, cidade, estado);
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
		
		nome.setText("");
		email.setText("");
		telefone.setText("");
		dataNascimento.setText("");
		endereco.setText("");
		cidadeField.setText("");
		cepField.setText("");
		profissao.setText("");
		cpfcnpJTextField.setText("");
		numeroField.setText("");
	}
}