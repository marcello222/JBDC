import java.sql.SQLException;

import conexao.ConnectionFactory;

public class TestaPoolConexoes {

	public static void main(String[] args) throws SQLException {

		ConnectionFactory connectionFactory = new ConnectionFactory();

		
		// quando tem mais conexoes do que o limite definido a proxima conexao tem que esperar fechar as abertas
		for (int i = 0; i < 20; i++) {
			connectionFactory.recuperarConexao();
			System.out.println("Conexao de número: " + i);

		}

	}

}
