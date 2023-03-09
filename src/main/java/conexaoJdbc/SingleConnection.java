package conexaoJdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {

	// Define as informações da conexão com o banco de dados
	private static String url = "jdbc:postgresql://localhost:5432/Sistema-pessoal";
	private static String user = "postgres";
	private static String password = "admin";

	// Mantém uma única conexão aberta ao longo da execução do programa
	private static Connection conexao = null;

	// Bloco estático que é executado uma única vez quando a classe é carregada
	static {
		conectar();
	}
	// Método responsável por estabelecer a conexão com o banco de dados
	private static void conectar() {
		try {
			// Verifica se a conexão ainda não foi estabelecida
			if (conexao == null) {
				// Registra o driver JDBC para o PostgreSQL
				Class.forName("org.postgresql.Driver");
				// Estabelece a conexão com o banco de dados usando as informações definidas acima
				conexao = DriverManager.getConnection(url, user, password);
				// Define que as transações precisam ser confirmadas manualmente (não são confirmadas automaticamente)
				conexao.setAutoCommit(false);
			}
		} catch (Exception e) {
			// Imprime o stack trace de qualquer exceção que ocorra
			e.printStackTrace();
		}
	}
	// Construtor da classe, chama o método conectar()
	public SingleConnection() {
		conectar();
	}
	// Método que retorna a conexão estabelecida
	public static Connection getConnection() {
		return conexao;
	}
}