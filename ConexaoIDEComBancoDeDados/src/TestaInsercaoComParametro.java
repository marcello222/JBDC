import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import conexao.ConnectionFactory;

public class TestaInsercaoComParametro {

	public static void main(String[] args) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		try (Connection connection = factory.recuperarConexao()) {

			// eu vou controlar o momento do commit da minha (transação), usamos
			// 'AutoCommit'
			connection.setAutoCommit(false);

			
			// Com o try com recursos (try-with-resources) não é mais necessário explicitar o close
			// para fechar o statements (ResultSet, Connection, PreparedStatement). Pelo fato dos statements estenderem AutoCloseable
			try (PreparedStatement stm = connection.prepareStatement(
					"INSERT INTO PRODUTO (nome, descricao) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);) {
				adcionarVariavel("SmartTV", "45 polegadas", stm);
				adcionarVariavel("Radio", "Radio de bateria", stm);
				
				
				// é necessário explicitar o commit e o rollback, para ter o controle total das
				// transações para mostrar todos produtos adicionados ou nada.
				connection.commit();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("ROLLBACK EXECUTADO");
				connection.rollback();
			}
		}

	}

	private static void adcionarVariavel(String nome, String descricao, PreparedStatement stm) throws SQLException {
		stm.setString(1, nome);
		stm.setString(2, descricao);

		stm.execute();

		try (ResultSet rst = stm.getGeneratedKeys()) {

			while (rst.next()) {
				Integer id = rst.getInt(1);
				System.out.println("O id criado foi: " + id);
			}

		}
	}

}
