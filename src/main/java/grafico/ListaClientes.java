package grafico;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.MaskFormatter;

import org.omg.PortableInterceptor.ObjectReferenceTemplate;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import MinhaTableModel.MinhaTableModel;
import conexaoJdbc.SingleConnection;
import dao.DaoCliente;
import processamentoDeDados.Cliente;
import processamentoDeDados.LimitarCaracteres;

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
	private JTextField idEditado;
	private ButtonGroup tipomoradia;
	private JTextField cidadeField;
	private JTextField cepField;
	private JRadioButton apartamento;
	private JRadioButton casa;
	private String estado;
	private JTextField tipomoradiaField;
	private JTextField estadobox;
	private JTextField numeroField;

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
		setSize(1000, 830);
		 // Impede a maximiza????o da janela
	      setResizable(false);

	      // Define o estado da janela como "MAXIMIZED_VERT" para impedir a maximiza????o vertical
	      setExtendedState(getExtendedState() | JFrame.MAXIMIZED_VERT);

	      // Define o estado da janela como "MAXIMIZED_HORIZ" para impedir a maximiza????o horizontal
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

		// Cria????o dos campos de texto e labels para os dados do cliente
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(200, 40, 80, 25);
		lblNome.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblNome);
		nome = new JTextField();
		lblNome.setFont(font);
		nome.setBounds(255, 40, 200, 25);
		contentPane.add(nome);

		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setBounds(520, 40, 80, 25);
		lblTelefone.setForeground(Color.WHITE);
		lblTelefone.setFont(font);
		contentPane.add(lblTelefone);

		// Cria uma m??scara de formata????o para o telefone
		MaskFormatter mascaraTelefone = new MaskFormatter();
		try {
			mascaraTelefone.setMask("(##) #####-####");
			mascaraTelefone.setPlaceholderCharacter('_');
		} catch (ParseException ex) {
			System.out.println("Erro ao criar a m??scara de formata????o: " + ex.getMessage());
		}
		telefone = new JFormattedTextField(mascaraTelefone);
		telefone.setBounds(600, 40, 200, 25);
		contentPane.add(telefone);

		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setBounds(540, 90, 80, 25);
		lblEmail.setFont(font);
		lblEmail.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblEmail);

		email = new JTextField();
		email.setBounds(600, 90, 200, 25);
		contentPane.add(email);

		JLabel lblDataNascimento = new JLabel("Data de Nascimento:");
		lblDataNascimento.setBounds(185, 90, 200, 25);
		lblDataNascimento.setForeground(Color.WHITE);
		lblDataNascimento.setFont(font);
		contentPane.add(lblDataNascimento);

		dataNascimento = new JTextField("--/--/----");
		dataNascimento.setBounds(355, 90, 100, 25);
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
		lblrua.setBounds(490, 140, 80, 25);
		lblrua.setFont(font);
		lblrua.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblrua);

		endereco = new JTextField();
		endereco.setBounds(530, 140, 150, 25);
		contentPane.add(endereco);
		
		JLabel lblNumero = new JLabel("N??mero:");
		lblNumero.setBounds(687, 140, 80, 25);
		lblNumero.setFont(font);
		lblNumero.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblNumero);

		numeroField = new JTextField();
		numeroField.setBounds(760, 140, 40, 25);
		contentPane.add(numeroField);	
		
		idEditado = new JTextField();
		idEditado.setBounds(0, 0, 0, 0);
		contentPane.add(idEditado);

		JLabel lblProfissao = new JLabel("Profiss??o:");
		lblProfissao.setBounds(172, 140, 80, 25);
		lblProfissao.setFont(font);
		lblProfissao.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblProfissao);

		profissao = new JTextField();
		profissao.setBounds(255, 140, 200, 25);
		contentPane.add(profissao);
		
		JLabel lblcidade = new JLabel("Cidade:");
		lblcidade.setBounds(152, 190, 60, 25);
		lblcidade.setFont(font);
		lblcidade.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblcidade);

		cidadeField = new JTextField();
		cidadeField.setBounds(215, 190, 150, 25);
		contentPane.add(cidadeField);
		
		
		// Cria o JLabel para exibir "UF:"
		JLabel lbluf = new JLabel("UF:");
		lbluf.setBounds(372, 190, 60, 25);
		lbluf.setFont(font);
		lbluf.setForeground(Color.WHITE);
		contentPane.add(lbluf);

		// Array com todos os estados brasileiros
		String[] estados = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};

		// Cria o JComboBox com os estados
		JComboBox<String> estadosBox = new JComboBox<>(estados);
		estadosBox.setBounds(402, 190, 50, 25);
		estadosBox.addActionListener(e -> {
		    JComboBox<String> cb = (JComboBox<String>) e.getSource();
		    estado = (String) cb.getSelectedItem();
		    estadobox.setText(estado); // Define o valor do estado selecionado no JTextField estadobox
		});

		estadobox = new JTextField();
		contentPane.add(estadobox);
		contentPane.add(estadosBox);
		
		JLabel lblCep = new JLabel("Cep:");
		lblCep.setBounds(490, 190, 60, 25);
		lblCep.setFont(font);
		lblCep.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblCep);

		cepField = new JTextField();
		cepField.setDocument(new LimitarCaracteres(9));
		cepField.setBounds(530, 190, 120, 25);
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

		// Cria????o do grupo de bot??es de op????o para sele????o do tipo de moradia
		apartamento = new JRadioButton("Apto");
		apartamento.setBounds(660, 190, 70, 25);
		apartamento.setFont(font);
		apartamento.setForeground(Color.WHITE);
		apartamento.setOpaque(false);
		apartamento.setSelected(true); // Adicionando a sele????o do bot??o
		contentPane.add(apartamento);

		casa = new JRadioButton("Casa");
		casa.setBounds(720, 190, 100, 25);
		casa.setFont(font);
		casa.setForeground(Color.WHITE);
		casa.setOpaque(false);
		contentPane.add(casa);	
		
		tipomoradia = new ButtonGroup();
		tipomoradia.add(apartamento);
		tipomoradia.add(casa);

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

		// Cria????o do campo para selecionar se a pessoa ?? f??sica ou jur??dica
		JLabel lblTipoPessoa = new JLabel("Tipo de Pessoa:");
		lblTipoPessoa.setBounds(400, 240, 150, 25);
		lblTipoPessoa.setFont(font);
		lblTipoPessoa.setForeground(Color.WHITE); // Define a cor do texto como branco
		contentPane.add(lblTipoPessoa);

		// Cria????o dos bot??es de op????o para sele????o do tipo de pessoa
		pessoaFisica = new JRadioButton("Pessoa F??sica");
		pessoaFisica.setBounds(530, 240, 150, 25);
		pessoaFisica.setFont(font);
		pessoaFisica.setForeground(Color.WHITE);
		pessoaFisica.setOpaque(false);
		contentPane.add(pessoaFisica);

		pessoaJuridica = new JRadioButton("Pessoa Jur??dica");
		pessoaJuridica.setBounds(660, 240, 160, 25);
		pessoaJuridica.setFont(font);
		pessoaJuridica.setForeground(Color.WHITE);
		pessoaJuridica.setOpaque(false);
		contentPane.add(pessoaJuridica);

		tipoPessoa = new ButtonGroup();
		tipoPessoa.add(pessoaFisica);
		tipoPessoa.add(pessoaJuridica);

		// Cria????o dos campos de texto para CPF (caso pessoa f??sica) e CNPJ (caso pessoa jur??dica)
		JLabel lblCpfCnpj = new JLabel("CPF/CNPJ:");
		lblCpfCnpj.setBounds(512, 277, 400, 25);
		lblCpfCnpj.setFont(font);
		lblCpfCnpj.setForeground(Color.WHITE); // Define a cor do texto como branco

		cpfcnpJTextField = new JTextField();
		cpfcnpJTextField.setBounds(600, 275, 200, 25);
		cpfcnpJTextField.setFont(font);
		cpfcnpJTextField.setForeground(Color.BLACK); // Define a cor do texto como preto

		pessoaFisica.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Limite de 14 caracteres para CPF
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
				// Limite de 18 caracteres para CNPJ
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
		
		// Atribui ?? vari??vel connection uma inst??ncia de Connection obtida atrav??s do
		// m??todo getConnection() da classe SingleConnection
		connection = SingleConnection.getConnection();
		
		//tabela com os dados do banco

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
		columnModel.getColumn(7).setMinWidth(35);
		columnModel.getColumn(7).setMaxWidth(200);
		columnModel.getColumn(4).setCellRenderer(new DefaultTableCellRenderer());
		columnModel.getColumn(4).setPreferredWidth(4);
		columnModel.getColumn(4).setMinWidth(80);
		columnModel.getColumn(4).setMaxWidth(200);
		columnModel.getColumn(6).setCellRenderer(new DefaultTableCellRenderer());
		columnModel.getColumn(6).setPreferredWidth(6);
		columnModel.getColumn(6).setMinWidth(115);
		columnModel.getColumn(6).setMaxWidth(200);
		columnModel.getColumn(11).setCellRenderer(new DefaultTableCellRenderer());
		columnModel.getColumn(11).setPreferredWidth(11);
		columnModel.getColumn(11).setMinWidth(30);
		columnModel.getColumn(11).setMaxWidth(200);
		columnModel.getColumn(9).setCellRenderer(new DefaultTableCellRenderer());
		columnModel.getColumn(9).setPreferredWidth(9);
		columnModel.getColumn(9).setMinWidth(55);
		columnModel.getColumn(9).setMaxWidth(200);
		columnModel.getColumn(12).setCellRenderer(new DefaultTableCellRenderer());
		columnModel.getColumn(12).setPreferredWidth(12);
		columnModel.getColumn(12).setMinWidth(65);
		columnModel.getColumn(12).setMaxWidth(200);
		columnModel.getColumn(3).setCellRenderer(new DefaultTableCellRenderer());
		columnModel.getColumn(3).setPreferredWidth(12);
		columnModel.getColumn(3).setMinWidth(100);
		columnModel.getColumn(3).setMaxWidth(200);
	
	

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		List<RowSorter.SortKey> sortKeys = new ArrayList<>();
		sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING)); // 0 representa a coluna de ID
		sorter.setSortKeys(sortKeys);
		table.setRowSorter(sorter);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(5, 370, 975, 360);
		contentPane.add(scrollPane);

		// bot??o de edi????o

		JButton btnEditar = new JButton();
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "Selecione um registro para editar.");
					return;
				} else {
					if (selectedRow != -1) {
						// Obt??m os valores dos campos de edi????o
						Object id = table.getValueAt(selectedRow, 0);
						Object nome = table.getValueAt(selectedRow, 1);
						Object email = table.getValueAt(selectedRow, 2);
						Object telefone = table.getValueAt(selectedRow, 3);
						Object datanascimento = table.getValueAt(selectedRow, 4);
						Object profissaocliente = table.getValueAt(selectedRow, 5);
						Object tipopessoa = table.getValueAt(selectedRow, 7);
						Object documento = table.getValueAt(selectedRow, 6);
						Object endereco = table.getValueAt(selectedRow, 8);
						Object numero = table.getValueAt(selectedRow, 9);
						Object cidade = table.getValueAt(selectedRow, 10);
						Object estado = table.getValueAt(selectedRow, 11);
						Object cep = table.getValueAt(selectedRow, 12);
						Object tipomoradia = table.getValueAt(selectedRow, 13);

						// Preenche os campos de edi????o com os valores obtidos
						String idString = table.getValueAt(selectedRow, 0).toString();
						idEditado.setText(idString);
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

							// Passe a string formatada para o m??todo setText() do campo de texto
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
						numeroField.setText((String) numero);
						cidadeField.setText((String) cidade);
						estadobox.setText(ListaClientes.this.estado);
						cepField.setText((String) cep);
						if (tipomoradia.equals("Casa")) {
							casa.setSelected(true);
						} else {
							apartamento.setSelected(true);
						}
						estadobox.setText((String) estado);
						// Define o valor inicial do JComboBox para o valor no JTextField estadobox
						for (int i = 0; i < estados.length; i++) {
						    if (estados[i].equals(estadobox.getText())) {
						        estadosBox.setSelectedIndex(i);
						        break;
						    }
						}
						
					}
				}
			}
		});
		// Define a posi????o e o tamanho do bot??o
		btnEditar.setBounds(65, 740, 50, 32);
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
		
		// Crie uma nova janela de dica de ferramenta personalizada
		JToolTip tooltip = new JToolTip();
		tooltip.setTipText("Clique para editar");
		tooltip.setBackground(Color.YELLOW);
		tooltip.setForeground(Color.BLACK);
		tooltip.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		tooltip.setOpaque(true);

		// Adicione um listener de mouse ao bot??o
		btnEditar.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        // Exiba a janela de dica de ferramenta
		        Point p = e.getLocationOnScreen();
		        tooltip.setLocation(p.x + 10, p.y + 20);
		        tooltip.setVisible(true);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        // Oculte a janela de dica de ferramenta
		        tooltip.setVisible(false);
		    }
		});

		// Adicione a janela de dica de ferramenta ao bot??o
		btnEditar.setToolTipText("Clique para editar o registro selecionado");
		contentPane.add(btnEditar);
		
		//bot??o que salva a edi????o

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
						cepField.setText("");
						cidadeField.setText("");
						numeroField.setText("");
						
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

					// Simula um clique no bot??o de limpar para limpar os campos da interface
					// gr??fica
				}
			}
		});
		btnSalvar.setBounds(120, 740, 55, 32);
		ImageIcon iconesalvar = new ImageIcon("C:\\workspace-java\\sistema-pessoal-cadastro\\src\\main\\java\\images\\salvar.png");
		Image imagem2 = iconesalvar.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		iconesalvar = new ImageIcon(imagem2);
		btnSalvar.setIcon(iconesalvar);

		// Crie uma nova janela de dica de ferramenta personalizada
		JToolTip tooltip17 = new JToolTip();
		tooltip17.setTipText("Clique para salvar");
		tooltip17.setBackground(Color.YELLOW);
		tooltip17.setForeground(Color.BLACK);
		tooltip17.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		tooltip17.setOpaque(true);

		// Adicione um listener de mouse ao bot??o
		btnSalvar.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        // Exiba a janela de dica de ferramenta
		        Point p = e.getLocationOnScreen();
		        tooltip17.setLocation(p.x + 10, p.y + 20);
		        tooltip17.setVisible(true);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        // Oculte a janela de dica de ferramenta
		        tooltip17.setVisible(false);
		    }
		});

		// Adicione a janela de dica de ferramenta ao bot??o
		btnSalvar.setToolTipText("Clique para salvar o registro editado");
		contentPane.add(btnSalvar);

		
		//bot??o que faz o delete

		JButton btnExcluir = new JButton();
		btnExcluir.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int[] selectedRows = table.getSelectedRows();
		        if (selectedRows.length == 0) {
		            JOptionPane.showMessageDialog(null, "Selecione um registro para excluir.");
		            return;
		        }
		        int excluir = JOptionPane.showConfirmDialog(null, "Deseja mesmo excluir o(s) registro(s)?");
		        if (excluir == 0) {
		            for (int i = selectedRows.length - 1; i >= 0; i--) {
		                long macacoId = (long) table.getModel().getValueAt(selectedRows[i], 0);
		                DaoCliente minhaDao = new DaoCliente();
		                minhaDao.excluir(macacoId);
		            }
		            List<Cliente> usuariosAtualizados = dao.editar();
		            model.atualizar(usuariosAtualizados);
		            JOptionPane.showMessageDialog(null, "registro(s) excluido(s)!");
		        } else if (excluir == 1 || excluir == 2) {
		            return;
		        }
		    }
		});
		btnExcluir.setBounds(180, 740, 55, 32);
		ImageIcon iconeapagar = new ImageIcon("C:\\workspace-java\\sistema-pessoal-cadastro\\src\\main\\java\\images\\excluir.png");
		Image imagem1 = iconeapagar.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		iconeapagar = new ImageIcon(imagem1);
		btnExcluir.setIcon(iconeapagar);


		// Crie uma nova janela de dica de ferramenta personalizada
		JToolTip tooltip15 = new JToolTip();
		tooltip15.setTipText("Clique para excluir");
		tooltip15.setBackground(Color.YELLOW);
		tooltip15.setForeground(Color.BLACK);
		tooltip15.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		tooltip15.setOpaque(true);

		// Adicione um listener de mouse ao bot??o
		btnExcluir.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        // Exiba a janela de dica de ferramenta
		        Point p = e.getLocationOnScreen();
		        tooltip15.setLocation(p.x + 10, p.y + 20);
		        tooltip15.setVisible(true);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        // Oculte a janela de dica de ferramenta
		        tooltip15.setVisible(false);
		    }
		});

		// Adicione a janela de dica de ferramenta ao bot??o
		btnExcluir.setToolTipText("Clique para excluir o registro selecionado");
		contentPane.add(btnExcluir);

		
		//bot??o para um novo cadastro
		
		JButton btnAdicionar = new JButton();
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						try {
							dispose(); // Fecha a janela atual
							CadastroClientes cadastroClientes = new CadastroClientes(id);
						    cadastroClientes.setVisible(true); // Abre a janela do sistema gr??fico
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					// Atualiza a tabela com os dados atualizados
					List<Cliente> usuariosAtualizados = dao.editar();
					model.atualizar(usuariosAtualizados);

					// Simula um clique no bot??o de limpar para limpar os campos da interface
					// gr??fica
				}
			
		});
		btnAdicionar.setBounds(5, 740, 55, 32);
		ImageIcon iconeadicionar = new ImageIcon("C:\\workspace-java\\sistema-pessoal-cadastro\\src\\main\\java\\images\\adicionar.png");
		Image imagem3 = iconeadicionar.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		iconeadicionar = new ImageIcon(imagem3);
		btnAdicionar.setIcon(iconeadicionar);

		// Crie uma nova janela de dica de ferramenta personalizada
		JToolTip tooltip12 = new JToolTip();
		tooltip12.setTipText("Clique para adicionar");
		tooltip12.setBackground(Color.YELLOW);
		tooltip12.setForeground(Color.BLACK);
		tooltip12.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		tooltip12.setOpaque(true);

		// Adicione um listener de mouse ao bot??o
		btnAdicionar.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        // Exiba a janela de dica de ferramenta
		        Point p = e.getLocationOnScreen();
		        tooltip12.setLocation(p.x + 10, p.y + 20);
		        tooltip12.setVisible(true);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        // Oculte a janela de dica de ferramenta
		        tooltip12.setVisible(false);
		    }
		});

		// Adicione a janela de dica de ferramenta ao bot??o
		btnAdicionar.setToolTipText("Clique para adicionar um novo registro");
		contentPane.add(btnAdicionar);

		
		//bot??o que atualiza a lista
		
		JButton btnAtualizar = new JButton();
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					// Atualiza a tabela com os dados atualizados
					List<Cliente> usuariosAtualizados = dao.editar();
					model.atualizar(usuariosAtualizados);
					

					//limpa os campos da interfaca gr??fica
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
					buscaClienteTextField.setText("");
			}
		});
		btnAtualizar.setBounds(800, 740, 75, 32);
		ImageIcon iconeatualizar = new ImageIcon("C:\\workspace-java\\sistema-pessoal-cadastro\\src\\main\\java\\images\\atualizar.png");
		Image imagem21 = iconeatualizar.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		iconeatualizar = new ImageIcon(imagem21);
		btnAtualizar.setIcon(iconeatualizar);

		// Crie uma nova janela de dica de ferramenta personalizada
		JToolTip tooltip1 = new JToolTip();
		tooltip1.setTipText("Clique para atualizar");
		tooltip1.setBackground(Color.YELLOW);
		tooltip1.setForeground(Color.BLACK);
		tooltip1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		tooltip1.setOpaque(true);

		// Adicione um listener de mouse ao bot??o
		btnAtualizar.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        // Exiba a janela de dica de ferramenta
		        Point p = e.getLocationOnScreen();
		        tooltip1.setLocation(p.x + 10, p.y + 20);
		        tooltip1.setVisible(true);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        // Oculte a janela de dica de ferramenta
		        tooltip1.setVisible(false);
		    }
		});

		// Adicione a janela de dica de ferramenta ao bot??o
		btnAtualizar.setToolTipText("Clique para atualizar a tabela");
		contentPane.add(btnAtualizar);

		
		//bot??o de relatorio
		
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
		btnRelatorio.setBounds(880, 740, 100, 32);
		ImageIcon iconerelatorio = new ImageIcon("C:\\workspace-java\\sistema-pessoal-cadastro\\src\\main\\java\\images\\relatorio.png");
		Image imagem211 = iconerelatorio.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		iconerelatorio = new ImageIcon(imagem211);
		btnRelatorio.setIcon(iconerelatorio);

		// Crie uma nova janela de dica de ferramenta personalizada
		JToolTip tooltip11 = new JToolTip();
		tooltip11.setTipText("Clique para gerar um relat??rio");
		tooltip11.setBackground(Color.YELLOW);
		tooltip11.setForeground(Color.BLACK);
		tooltip11.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		tooltip11.setOpaque(true);

		// Adicione um listener de mouse ao bot??o
		btnRelatorio.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		        // Exiba a janela de dica de ferramenta
		        Point p = e.getLocationOnScreen();
		        tooltip11.setLocation(p.x + 10, p.y + 20);
		        tooltip11.setVisible(true);
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		        // Oculte a janela de dica de ferramenta
		        tooltip11.setVisible(false);
		    }
		});

		// Adicione a janela de dica de ferramenta ao bot??o
		btnRelatorio.setToolTipText("Clique para gerar um relat??rio");
		contentPane.add(btnRelatorio);

			
			//campo de pesquisa de clientes

			JLabel lblbuscaCliente = new JLabel("pesquisa:");
			lblbuscaCliente.setBounds(10, 339, 80, 25);
			lblbuscaCliente.setFont(font);
			lblbuscaCliente.setForeground(Color.WHITE); // Define a cor do texto como branco

			buscaClienteTextField = new JTextField();
			buscaClienteTextField.setBounds(90, 339, 320, 25);
			buscaClienteTextField.setFont(font);
			buscaClienteTextField.setForeground(Color.BLACK); // Define a cor do texto como preto
			buscaClienteTextField.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
			buscaClienteTextField.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	DaoCliente dao = new DaoCliente();
			        String textoPesquisa = buscaClienteTextField.getText();

			        // Executa a consulta no banco de dados para buscar todos os clientes
			        List<Cliente> todosClientes = dao.editar();

			        // Filtra os clientes com base no texto de pesquisa digitado pelo usu??rio
			        List<Cliente> clientesFiltrados = new ArrayList<>();
			        for (Cliente cliente : todosClientes) {
			            if (cliente.getNome().toLowerCase().contains(textoPesquisa.toLowerCase())) {
			                clientesFiltrados.add(cliente);
			            }
			        }

			     // Atualiza a tabela com os dados atualizados
					List<Cliente> usuariosAtualizados = dao.editar();
					model.atualizar(clientesFiltrados);
			    }
			});

			contentPane.add(lblbuscaCliente);
			contentPane.add(buscaClienteTextField);
	
			

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

	//metodo que faz o update na base de dados
	
	public void salvaeditado() throws IOException, Exception {
		DaoCliente dao = new DaoCliente();
		String ideditado = idEditado.getText();
		long id = (Integer.parseInt(ideditado));
		String nomeCliente = nome.getText();
		String telefoneCliente = telefone.getText();
		String emailCliente = email.getText();
		String cidade = cidadeField.getText();
		String estado = estadobox.getText();
		String cep = cepField.getText();
		String numero = numeroField.getText();
		String enderecoCliente = endereco.getText();
		String profissaoCliente = profissao.getText();
		SimpleDateFormat formatoEntrada = new SimpleDateFormat("dd/MM/yyyy"); // Define o formato da entrada
		SimpleDateFormat formatoSaida = new SimpleDateFormat("yyyy-MM-dd"); // Define o formato da sa??da

		String dataNascimentoString = dataNascimento.getText(); // Obt??m a string com a data de nascimento informada
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
		String tipomoradiaString = tipomoradia.getSelection().equals(casa.getModel()) ? "Casa" : "Apartamento";
		if (tipomoradiaString.equals("Casa")) {
			tipomoradiaString = tipomoradiaField.getText();
		} else {
			tipomoradiaString = tipomoradiaField.getText();
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
			JOptionPane.showMessageDialog(null, "O campo profiss??o deve ser preenchido.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else if (documentoCliente == null || documentoCliente.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo CPF/CNPJ deve ser preenchido.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		} else if (enderecoCliente == null || enderecoCliente.isEmpty()) {
			JOptionPane.showMessageDialog(null, "O campo endere??o deve ser preenchido.", "Erro",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// se todos os campos estiverem preenchidos, cria o objeto Cliente
		Cliente cliente = new Cliente(id, nomeCliente, emailCliente, telefoneCliente, dataNascimentoFormatada,
				profissaoCliente, documentoCliente, tipoPessoaCliente, enderecoCliente , cep, numero, tipomoradiaString, cidade, estado);
		try {
			boolean enviadoComSucesso = cliente.salvaCadastroEditado();
			if (enviadoComSucesso) {
				if (enviadoComSucesso) {
					JOptionPane.showMessageDialog(null, "Voc?? editou o registro com sucesso!");
				}
			} else {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao Salvar o Cadastro.");

			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao Salvar o Cadastro:" + ex.getMessage());
		}

		// Chama o m??todo de atualiza????o do DAO passando o objeto criado como argumento
		dao.update(cliente);
	}
	
	//metodo que gera o relatorio em pdf
	
	public void gerarRelatorioPDF(JTable table) {
	    Document document = new Document();

	    try {

	    	PdfWriter.getInstance(document, new FileOutputStream("C:\\Sigm4 - Sistema De Gest??o\\relatorios\\relatorio de cadastro de clientes.pdf"));
	    	
	    	// Define as margens do documento
	    	document.setMargins(0, 0, 0, 0);

	    	document.open();
	    	        
	    	// Define o t??tulo do relat??rio
	    	Paragraph title = new Paragraph("Relat??rio de Clientes \n");
	    	title.setAlignment(Element.ALIGN_CENTER);
	    	title.setSpacingAfter(20); // Adiciona 20 pontos de espa??o ap??s o t??tulo
	    	document.add(title);

	    	// Adiciona a tabela ao documento PDF
	    	PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
	    	pdfTable.setWidthPercentage(100);
	    	pdfTable.setWidths(new float[]{4, 15, 25, 12, 11, 15, 12, 5 , 13, 8, 14, 4, 9, 10}); // Definindo as larguras das colunas

	    	// Define as margens internas das c??lulas da tabela
	    	pdfTable.getDefaultCell().setPadding(5);

	    	// Adiciona os cabe??alhos das colunas da tabela
	    	for (int i = 0; i < table.getColumnCount(); i++) {
	    	    PdfPCell cell = new PdfPCell(new Phrase(table.getColumnName(i), FontFactory.getFont(FontFactory.TIMES_ROMAN, 7)));
	    	    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	    pdfTable.addCell(cell);
	    	}

	    	// Adiciona os dados das c??lulas da tabela
	    	for (int i = 0; i < table.getRowCount(); i++) {
	    	    for (int j = 0; j < table.getColumnCount(); j++) {
	    	        PdfPCell cell = new PdfPCell(new Phrase(table.getValueAt(i, j).toString(), FontFactory.getFont(FontFactory.TIMES_ROMAN, 6)));
	    	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	    	        pdfTable.addCell(cell);
	    	    }
	    	}

	    	// Obt??m o arquivo do relat??rio
	    	File relatorio = new File("C:\\Sigm4 - Sistema De Gest??o\\relatorios\\relatorio de cadastro de clientes.pdf");

	    	// Abre o arquivo com o aplicativo padr??o do sistema
	    	Desktop.getDesktop().open(relatorio);

	    	document.add(pdfTable);
	    	

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        document.close();
	    }
	   }
	}