package model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
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
		setTitle("Listagem de MinhaUserPosJavas - Sistema Cadastral");
		setSize(800, 1050);
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
		int fieldWidth = 180;
		int fieldHeight = 30;

		// Criação dos campos de texto e labels para os dados do cliente
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(100, 50, 80, 25);
		lblNome.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblNome);
		nome = new JTextField();
		lblNome.setFont(font);
		nome.setBounds(155, 50, 200, 25);
		contentPane.add(nome);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(420, 50, 80, 25);
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
		telefone.setBounds(500, 50, 200, 25);
		contentPane.add(telefone);

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(440, 100, 80, 25);
		lblEmail.setFont(font);
		lblEmail.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblEmail);

		email = new JTextField();
		email.setBounds(500, 100, 200, 25);
		contentPane.add(email);

		JLabel lblDataNascimento = new JLabel("Data de Nascimento:");
		lblDataNascimento.setBounds(85, 100, 200, 25);
		lblDataNascimento.setForeground(Color.WHITE);
		lblDataNascimento.setFont(font);
		contentPane.add(lblDataNascimento);

		dataNascimento = new JTextField("--/--/----");
		dataNascimento.setBounds(255, 100, 100, 25);
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
		lblEndereco.setBounds(417, 150, 80, 25);
		lblEndereco.setFont(font);
		lblEndereco.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblEndereco);

		endereco = new JTextField();
		endereco.setBounds(500, 150, 200, 25);
		contentPane.add(endereco);

		JLabel lblProfissao = new JLabel("Profissão:");
		lblProfissao.setBounds(72, 150, 80, 25);
		lblProfissao.setFont(font);
		lblProfissao.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblProfissao);

		profissao = new JTextField();
		profissao.setBounds(155, 150, 200, 25);
		contentPane.add(profissao);

		// Criação do campo para selecionar se a pessoa é física ou jurídica
		JLabel lblTipoPessoa = new JLabel("Tipo de Pessoa:");
		lblTipoPessoa.setBounds(280, 200, 150, 25);
		lblTipoPessoa.setFont(font);
		lblTipoPessoa.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblTipoPessoa);

		// Criação dos botões de opção para seleção do tipo de pessoa
		pessoaFisica = new JRadioButton("Pessoa Física");
		pessoaFisica.setBounds(410, 200, 150, 25);
		pessoaFisica.setFont(font);
		pessoaFisica.setForeground(Color.WHITE);
		pessoaFisica.setOpaque(false);
		pessoaFisica.setSelected(true); // Adicionando a seleção do botão
		contentPane.add(pessoaFisica);

		pessoaJuridica = new JRadioButton("Pessoa Jurídica");
		pessoaJuridica.setBounds(550, 200, 160, 25);
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
		lblCpfCnpj.setBounds(462, 250, 400, 25);
		lblCpfCnpj.setFont(font);
		lblCpfCnpj.setForeground(Color.WHITE); // Define a cor do texto como branco

		cpfcnpJTextField = new JTextField();
		cpfcnpJTextField.setBounds(550, 250, 150, 25);
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

		
		
		// Atribui à variável connection uma instância de Connection obtida através do
		// método getConnection() da classe SingleConnection
		connection = SingleConnection.getConnection();

		
		JTable table = new JTable();
		DaoCliente dao = new DaoCliente();
		List<Cliente> clientes = dao.editar();
		MinhaTableModel model = new MinhaTableModel(clientes);
		table.setModel(model);

		// Define a largura da coluna de ID
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.getColumn(0).setCellRenderer(new DefaultTableCellRenderer());
		columnModel.getColumn(0).setPreferredWidth(0);
		columnModel.getColumn(0).setMinWidth(50);
		columnModel.getColumn(0).setMaxWidth(200);

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING)); // 0 representa a coluna de ID
		sorter.setSortKeys(sortKeys);
		table.setRowSorter(sorter);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(10, 200, 490, 250);
		contentPane.add(scrollPane);

/*
		 botão edita
		JButton btnEditar = new JButton();
		btnEditar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow != -1) {
		            // Obtém os valores dos campos de edição
		            String nome = nome.getText();
		            String email = email.getText();
		            String telefone = telefone.getText();
		            String datanascimento = dataNascimento.getText();
		            String profissao = profissao.getText();
		            String documento = cpfcnpJTextField.getText();
		            String tipopessoa = tipoPessoaField.getText();
		            String endereco = endereco.getText();
		            Long id = (Long) table.getValueAt(selectedRow, 0);

		            // Cria um objeto Cliente com os valores obtidos
		            Cliente usuario = new Cliente(id, nome, email, telefone, datanascimento, profissao, documento, tipopessoa, endereco);

		            // Chama o método de atualização do DAO passando o objeto criado como argumento
		            dao.update(usuario);

		            // Atualiza a tabela com os dados atualizados
		            List<Cliente> usuariosAtualizados = dao.editar();
		            model.atualizar(usuariosAtualizados);
		        }
		    }
		});
		
		btnEditar.setBounds(40, 470, 130, 23);
		contentPane.add(btnEditar);

		// botão salva macaco editado

		JButton btnSalvamacaco = new JButton("Salvar edição");
		btnSalvamacaco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Selecione um registro para salvar.");
					return;
				} else {
					// Obtém os valores dos campos de edição
					String nome = deField.getText();
					String email = emailToField.getText();
					Long id = (Long) table.getValueAt(selectedRow, 0);

					// Cria um objeto MinhaUserPosJava com os valores obtidos
					Cliente usuario = new Cliente(id, nome, email);

					// Chama o método de atualização do DAO passando o objeto criado como argumento
					dao.update(usuario);

					// Atualiza a tabela com os dados atualizados
					List<Cliente> usuariosAtualizados = dao.editar();
					model.atualizar(usuariosAtualizados);

					// Simula um clique no botão de limpar para limpar os campos da interface
					// gráfica
				}
			}
		});

		btnSalvamacaco.setBounds(180, 470, 130, 23);
		contentPane.add(btnSalvamacaco);

		int selectedRow = table.getSelectedRow();
		if (selectedRow != -1) {
		    // Obtém os valores dos campos de edição
		    String nome = deField.getText();
		    String email = emailToField.getText();
		    Long id = (Long) table.getValueAt(selectedRow, 0);
		    String telefone = telefoneField.getText();
		    String datanascimento = dataNascimentoField.getText();
		    String profissao = profissaoField.getText();
		    String documento = documentoField.getText();
		    String tipopessoa = tipoPessoaField.getText();
		    String endereco = enderecoField.getText();

		    // Cria um objeto Cliente com os valores obtidos
		    Cliente usuario = new Cliente(id, nome, email, telefone, datanascimento, profissao, documento, tipopessoa, endereco);

		    // Chama o método de atualização do DAO passando o objeto criado como argumento
		    dao.update(usuario);

		    // Atualiza a tabela com os dados atualizados
		    List<Cliente> usuariosAtualizados = dao.editar();
		    model.atualizar(usuariosAtualizados);
		}


		// botão excluir macaco

		JButton btnExcluir = new JButton("Excluir Registro");
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

					if (editar != null) {
						for (Cliente user : editar) {
							model.addRow(new Object[] { user.getId(), user.getNome(), user.getEmail() });

						}
					}
				} else if (excluir == 1 || excluir == 2) {
					JOptionPane.showMessageDialog(null, "ok!");
					return;
				}
			}
		});
		btnExcluir.setBounds(320, 470, 150, 23);
		contentPane.add(btnExcluir);
*/
	}
}