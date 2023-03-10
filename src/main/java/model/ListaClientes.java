package model;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import MinhaTableModel.MinhaTableModel;
import conexaoJdbc.SingleConnection;
import dao.DaoCliente;
import pacoteDados.Cliente;

public class ListaClientes extends JFrame {
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
	private JTextField buscaClienteTextField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ListaClientes frame = new ListaClientes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Connection connection;

	public ListaClientes() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Listagem de Clientes - Sistema Cadastral");
		setSize(900, 850);
		setExtendedState(JFrame.MAXIMIZED_BOTH ^ JFrame.MAXIMIZED_VERT);
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

		// Criação dos campos de texto e labels para os dados do cliente
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(150, 20, 80, 25);
		lblNome.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblNome);
		nome = new JTextField();
		lblNome.setFont(font);
		nome.setBounds(205, 20, 200, 25);
		contentPane.add(nome);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(470, 20, 80, 25);
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
		telefone.setBounds(550, 20, 200, 25);
		contentPane.add(telefone);

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(490, 70, 80, 25);
		lblEmail.setFont(font);
		lblEmail.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblEmail);

		email = new JTextField();
		email.setBounds(550, 70, 200, 25);
		contentPane.add(email);

		JLabel lblDataNascimento = new JLabel("Data de Nascimento:");
		lblDataNascimento.setBounds(135, 70, 200, 25);
		lblDataNascimento.setForeground(Color.WHITE);
		lblDataNascimento.setFont(font);
		contentPane.add(lblDataNascimento);

		dataNascimento = new JTextField("--/--/----");
		dataNascimento.setBounds(305, 70, 100, 25);
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
					date += "/";
					dataNascimento.setText(date);
				}
			}
		});
		contentPane.add(dataNascimento);

		JLabel lblEndereco = new JLabel("Endereço:");
		lblEndereco.setBounds(467, 120, 80, 25);
		lblEndereco.setFont(font);
		lblEndereco.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblEndereco);

		endereco = new JTextField();
		endereco.setBounds(550, 120, 200, 25);
		contentPane.add(endereco);

		JLabel lblProfissao = new JLabel("Profissão:");
		lblProfissao.setBounds(122, 120, 80, 25);
		lblProfissao.setFont(font);
		lblProfissao.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblProfissao);

		profissao = new JTextField();
		profissao.setBounds(205, 120, 200, 25);
		contentPane.add(profissao);

		// Criação do campo para selecionar se a pessoa é física ou jurídica
		JLabel lblTipoPessoa = new JLabel("Tipo de Pessoa:");
		lblTipoPessoa.setBounds(350, 160, 150, 25);
		lblTipoPessoa.setFont(font);
		lblTipoPessoa.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblTipoPessoa);

		// Criação dos botões de opção para seleção do tipo de pessoa
		pessoaFisica = new JRadioButton("Pessoa Física");
		pessoaFisica.setBounds(480, 160, 150, 25);
		pessoaFisica.setFont(font);
		pessoaFisica.setForeground(Color.WHITE);
		pessoaFisica.setOpaque(false);
		contentPane.add(pessoaFisica);

		pessoaJuridica = new JRadioButton("Pessoa Jurídica");
		pessoaJuridica.setBounds(610, 160, 160, 25);
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
		lblCpfCnpj.setBounds(512, 195, 400, 25);
		lblCpfCnpj.setFont(font);
		lblCpfCnpj.setForeground(Color.WHITE); // Define a cor do texto como branco

		cpfcnpJTextField = new JTextField();
		cpfcnpJTextField.setBounds(600, 195, 150, 25);
		cpfcnpJTextField.setFont(font);
		cpfcnpJTextField.setForeground(Color.BLACK); // Define a cor do texto como preto

		pessoaFisica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Limite de 11 caracteres para CPF
				cpfcnpJTextField.setDocument(new LimitarCaracteres(11));
			}
		});

		pessoaJuridica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Limite de 14 caracteres para CNPJ
				cpfcnpJTextField.setDocument(new LimitarCaracteres(14));
			}
		});

		contentPane.add(lblCpfCnpj);
		contentPane.add(cpfcnpJTextField);

		JLabel lblbuscaCliente = new JLabel("pesquisa:");
		lblbuscaCliente.setBounds(10, 239, 80, 25);
		lblbuscaCliente.setFont(font);
		lblbuscaCliente.setForeground(Color.WHITE); // Define a cor do texto como branco

		buscaClienteTextField = new JTextField();
		buscaClienteTextField.setBounds(90, 240, 320, 25);
		buscaClienteTextField.setFont(font);
		buscaClienteTextField.setForeground(Color.BLACK); // Define a cor do texto como preto
		buscaClienteTextField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		contentPane.add(lblbuscaCliente);
		contentPane.add(buscaClienteTextField);
		
		nome.setEnabled(false);
		email.setEnabled(false);
		telefone.setEnabled(false);
		dataNascimento.setEnabled(false);
		endereco.setEnabled(false);
		profissao.setEnabled(false);
		cpfcnpJTextField.setEnabled(false);
		pessoaFisica.setEnabled(false);
		pessoaJuridica.setEnabled(false);

		// Atribui à variável connection uma instância de Connection obtida através do
		// método getConnection() da classe SingleConnection
		connection = SingleConnection.getConnection();

		JTable table = new JTable();
		DaoCliente dao = new DaoCliente();
		List<Cliente> usuarios = dao.editar();
		MinhaTableModel model = new MinhaTableModel(usuarios);
		table.setModel(model);

		// Define a largura da coluna de ID
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setCellRenderer(new DefaultTableCellRenderer());
		columnModel.getColumn(0).setPreferredWidth(0);
		columnModel.getColumn(0).setMinWidth(30);
		columnModel.getColumn(0).setMaxWidth(200);
		columnModel.getColumn(7).setCellRenderer(new DefaultTableCellRenderer());
		columnModel.getColumn(7).setPreferredWidth(7);
		columnModel.getColumn(7).setMinWidth(50);
		columnModel.getColumn(7).setMaxWidth(200);
		columnModel.getColumn(4).setCellRenderer(new DefaultTableCellRenderer());
		columnModel.getColumn(4).setPreferredWidth(4);
		columnModel.getColumn(4).setMinWidth(120);
		columnModel.getColumn(4).setMaxWidth(200);

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING)); // 0 representa a coluna de ID
		sorter.setSortKeys(sortKeys);
		table.setRowSorter(sorter);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(5, 270, 875, 400);
		contentPane.add(scrollPane);

		// botão editar

		JButton btnEditar = new JButton();
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Selecione um registro para editar.");
					return;
				} else {
					if (selectedRow != -1) {
						// Obtém os valores dos campos de edição
						Object nome = table.getValueAt(selectedRow, 1);
						Object email = table.getValueAt(selectedRow, 2);
						Object telefone = table.getValueAt(selectedRow, 3);
						Object datanascimento = table.getValueAt(selectedRow, 4);
						Object profissaocliente = table.getValueAt(selectedRow, 5);
						Object tipopessoa = table.getValueAt(selectedRow, 7);
						Object documento = table.getValueAt(selectedRow, 6);
						Object endereco = table.getValueAt(selectedRow, 8);

						// Preenche os campos de edição com os valores obtidos
						ListaClientes.this.nome.setText((String) nome);
						ListaClientes.this.email.setText((String) email);
						ListaClientes.this.telefone.setText((String) telefone);
						// Crie um objeto SimpleDateFormat com o formato desejado
						SimpleDateFormat dateFormatOrigem = new SimpleDateFormat("yyyy-MM-dd");

						// Transforme o objeto datanascimento em uma string
						String dataString = datanascimento.toString();

						try {
							// Transforme a string em uma data
							Date data = dateFormatOrigem.parse(dataString);

							// Crie um segundo objeto SimpleDateFormat com o formato "dd/MM/yyyy"
							SimpleDateFormat dateFormatDestino = new SimpleDateFormat("dd/MM/yyyy");

							// Formate a data no formato desejado
							String dataFormatada = dateFormatDestino.format(data);

							// Passe a string formatada para o método setText() do campo de texto
							dataNascimento.setText(dataFormatada);
						} catch (ParseException e1) {
							// Trate o erro de parse da data
							e1.printStackTrace();
						}

						profissao.setText((String) profissaocliente);
						if (tipopessoa.equals("f")) {
							pessoaFisica.setSelected(true);
						} else {
							pessoaJuridica.setSelected(true);
						}
						cpfcnpJTextField.setText((String) documento);
						ListaClientes.this.endereco.setText((String) endereco);
					}
				}
			}
		});
		// Define a posição e o tamanho do botão
		btnEditar.setBounds(65, 680, 50, 32);
		nome.setEnabled(true);
		email.setEnabled(true);
		telefone.setEnabled(true);
		dataNascimento.setEnabled(true);
		endereco.setEnabled(true);
		profissao.setEnabled(true);
		cpfcnpJTextField.setEnabled(true);
		pessoaFisica.setEnabled(true);
		pessoaJuridica.setEnabled(true);
		ImageIcon iconeeditar = new ImageIcon("C:\\workspace-java\\sistema-pessoal-cadastro\\src\\main\\java\\images\\editar.png");
		Image imagem = iconeeditar.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		iconeeditar = new ImageIcon(imagem);
		btnEditar.setIcon(iconeeditar);
		contentPane.add(btnEditar);

		JButton btnSalvar = new JButton();
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Selecione um registro para salvar.");
					return;
				} else {
					try {
						salvaeditado();
						nome.setText("");
						email.setText("");
						telefone.setText("");
						dataNascimento.setText("");
						endereco.setText("");
						profissao.setText("");
						cpfcnpJTextField.setText("");
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// Atualiza a tabela com os dados atualizados
					List<Cliente> usuariosAtualizados = dao.editar();
					model.atualizar(usuariosAtualizados);

					// Simula um clique no botão de limpar para limpar os campos da interface
					// gráfica
				}
			}
		});
		btnSalvar.setBounds(120, 680, 55, 32);
		ImageIcon iconesalvar = new ImageIcon("C:\\workspace-java\\sistema-pessoal-cadastro\\src\\main\\java\\images\\salvar.png");
		Image imagem2 = iconesalvar.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		iconesalvar = new ImageIcon(imagem2);
		btnSalvar.setIcon(iconesalvar);
		contentPane.add(btnSalvar);

		JButton btnExcluir = new JButton();
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Selecione um registro para excluir.");
					return;
				}
				int excluir = JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir o registro?");
				if (excluir == 0) {
					long macacoId = (long) table.getModel().getValueAt(selectedRow, 0);

					DaoCliente minhaDao = new DaoCliente();
					minhaDao.excluir(macacoId);

					// Atualiza a tabela com os dados atualizados
					List<Cliente> usuariosAtualizados = dao.editar();
					model.atualizar(usuariosAtualizados);
					MinhaTableModel model = new MinhaTableModel(usuarios);
					model.setRowCount(0);
					ArrayList<Cliente> editar = new ArrayList<>();
					JOptionPane.showMessageDialog(null, "registro excluido!");

				} else if (excluir == 1 || excluir == 2) {
					return;
				}
			}
		});
		btnExcluir.setBounds(180, 680, 55, 32);
		ImageIcon iconeapagar = new ImageIcon("C:\\workspace-java\\sistema-pessoal-cadastro\\src\\main\\java\\images\\excluir.png");
		Image imagem1 = iconeapagar.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		iconeapagar = new ImageIcon(imagem1);
		btnExcluir.setIcon(iconeapagar);
		contentPane.add(btnExcluir);
		
		JButton btnAdicionar = new JButton();
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						try {
							CadastroClientes cadastroClientes = new CadastroClientes(id);
						    cadastroClientes.setVisible(true); // Abre a janela do sistema gráfico
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// Atualiza a tabela com os dados atualizados
					List<Cliente> usuariosAtualizados = dao.editar();
					model.atualizar(usuariosAtualizados);

					// Simula um clique no botão de limpar para limpar os campos da interface
					// gráfica
				}
			
		});
		btnAdicionar.setBounds(5, 680, 55, 32);
		ImageIcon iconeadicionar = new ImageIcon("C:\\workspace-java\\sistema-pessoal-cadastro\\src\\main\\java\\images\\adicionar.png");
		Image imagem3 = iconeadicionar.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		iconeadicionar = new ImageIcon(imagem3);
		btnAdicionar.setIcon(iconeadicionar);
		contentPane.add(btnAdicionar);
		
		JButton btnAtualizar = new JButton();
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
										// Atualiza a tabela com os dados atualizados
					List<Cliente> usuariosAtualizados = dao.editar();
					model.atualizar(usuariosAtualizados);

					// Simula um clique no botão de limpar para limpar os campos da interface
					// gráfica
				}
			}
		});
		btnAtualizar.setBounds(690, 680, 55, 32);
		ImageIcon iconeatualizar = new ImageIcon("C:\\workspace-java\\sistema-pessoal-cadastro\\src\\main\\java\\images\\atualizar.png");
		Image imagem21 = iconeatualizar.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		iconeatualizar = new ImageIcon(imagem21);
		btnAtualizar.setIcon(iconeatualizar);
		contentPane.add(btnAtualizar);
		
		
		JButton btnRelatorio = new JButton();
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int relatorio = JOptionPane.showConfirmDialog(null, "Deseja gerar o relatorio de clientes?");
				if(relatorio == 0) {
				gerarRelatorioPDF(table);
				}else {
					return;
				}
			}
		});
			btnRelatorio.setBounds(750, 680, 130, 32);
			btnRelatorio.setText("Relatorio em pdf");
			contentPane.add(btnRelatorio);

		// adicionar a imagem de fundo com camada abaixo dos campos de login e senha
		contentPane.add(dataNascimento, new Integer(-1));
		contentPane.add(nome, new Integer(-1));
		contentPane.add(telefone, new Integer(-1));
		contentPane.add(email, new Integer(-1));
		contentPane.add(endereco, new Integer(-1));
		contentPane.add(profissao, new Integer(-1));
		contentPane.add(pessoaFisica, new Integer(-1));
		contentPane.add(pessoaJuridica, new Integer(-1));
		contentPane.add(lblbuscaCliente, new Integer(-1));
		contentPane.add(buscaClienteTextField, new Integer(-1));
		contentPane.add(labelFundo, new Integer(Integer.MIN_VALUE));
	}

	public void salvaeditado() throws IOException, Exception {
		DaoCliente dao = new DaoCliente();
		String buscaUltimoId = dao.buscaId();
		long id = (Integer.parseInt(buscaUltimoId));
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
			documentoCliente = pessoaJuridica.getText();
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
			boolean enviadoComSucesso = cliente.salvaCadastroEditado();
			if (enviadoComSucesso) {
				if (enviadoComSucesso) {
					JOptionPane.showMessageDialog(null, "Você editou o registro com sucesso!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao Salvar o Cadastro.");

			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao Salvar o Cadastro:" + ex.getMessage());
		}

		// Chama o método de atualização do DAO passando o objeto criado como argumento
		dao.update(cliente);
	}
	public void gerarRelatorioPDF(JTable table) {
	    Document document = new Document();

	    try {
	        PdfWriter.getInstance(document, new FileOutputStream("C:\\relatorios sistema\\relatorio.pdf"));

	        document.open();

	        // Define o título do relatório
	        Paragraph title = new Paragraph("Relatório de Clientes \n");
	        title.setAlignment(Element.ALIGN_CENTER);
	        title.setSpacingAfter(20); // Adiciona 20 pontos de espaço após o título
	        document.add(title);

	        // Adiciona a tabela ao documento PDF
	        PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
	        pdfTable.setWidthPercentage(100);

	        // Adiciona os cabeçalhos das colunas da tabela
	        for (int i = 0; i < table.getColumnCount(); i++) {
	            PdfPCell cell = new PdfPCell(new Phrase(table.getColumnName(i)));
	            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	            pdfTable.addCell(cell);
	        }

	        // Adiciona os dados das células da tabela
	        for (int i = 0; i < table.getRowCount(); i++) {
	            for (int j = 0; j < table.getColumnCount(); j++) {
	                PdfPCell cell = new PdfPCell(new Phrase(table.getValueAt(i, j).toString()));
	                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	                pdfTable.addCell(cell);
	            }
	        }
	        // Obtém o arquivo do relatório
		    File relatorio = new File("C:\\relatorios sistema\\relatorio.pdf");

		    // Abre o arquivo com o aplicativo padrão do sistema
		    Desktop.getDesktop().open(relatorio);


	        document.add(pdfTable);
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        document.close();
	    }
	   }
	}