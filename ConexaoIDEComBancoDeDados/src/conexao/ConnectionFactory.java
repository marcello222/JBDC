package conexao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

// Essas Lib´s fazem a conexao com o dataSource e puxar o pool de conexao 
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionFactory {

	public DataSource dataSource;

	// criando o pool de conexao 
	public ConnectionFactory() {
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();		
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/lojinha?useTimezone=true&serverTimezone=UTC");
		comboPooledDataSource.setUser("root");
		comboPooledDataSource.setPassword("root123");
		
		
		// define quantas conexoes estarão disponiveis 
		comboPooledDataSource.setMaxPoolSize(15);

		this.dataSource = comboPooledDataSource;

	}

	// pegando conexao que esta no pool
	public Connection recuperarConexao() throws SQLException {
		return this.dataSource.getConnection();

	}

}
