package conexao;

import java.sql.Connection;
import java.sql.DriverManager;

public class conexao {
	private static String banco = "jdbc:postgresql://localhost:5432/gerenciamento?autoReconnect=true";
	private static String user = "postgres";
	private static String senha = "MySQL";
	private static Connection connection = null;

	public static Connection getConnection() {
		return connection;
	}

	static {
		conectar();
	}

	public conexao() {
		// TODO Auto-generated constructor stub
		conectar();
	}

	private static void conectar() {
		try {
			if (connection == null) {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(banco, user, senha);
				connection.setAutoCommit(false);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
