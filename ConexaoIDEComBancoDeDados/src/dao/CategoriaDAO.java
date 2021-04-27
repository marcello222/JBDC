package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.Categoria;
import conexao.Produto;

public class CategoriaDAO {

	private Connection connection;

	public CategoriaDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Categoria> listar() throws SQLException {
		List<Categoria> categorias = new ArrayList<>();

		String sql = "SELECT ID, NOME FROM CATEGORIA";

		try (PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.execute();

			try (ResultSet rst = pstm.getResultSet()) {
				while (rst.next()) {
					Categoria categoria = new Categoria(rst.getInt(1), rst.getString(2));

					categorias.add(categoria);

				}

			}

		}
		return categorias;
	}

	public List<Categoria> listarComProdutos() throws SQLException {
		Categoria ultima = null;
		List<Categoria> categorias = new ArrayList<>();

		String sql = "SELECT C.ID, C.NOME, P.ID, P.NOME, P.DESCRICAO FROM CATEGORIA C INNER JOIN "
				+ " PRODUTO P ON C.ID = P.CATEGORIA_ID";

		try (PreparedStatement pstm = connection.prepareStatement(sql)) {
			pstm.execute();

			try (ResultSet rst = pstm.getResultSet()) {
				while (rst.next()) {

					// se o nome da ultima Categoria for diferente do que vem do BancoDados,
					// significa que é outra Categoria, fazendo com que não liste mais de uma vez as
					// Categorias ja listadas.
					if (ultima == null || ultima.getNome().equals(rst.getString(2))) {
						Categoria categoria = new Categoria(rst.getInt(1), rst.getString(2));
						ultima = categoria;
						categorias.add(categoria);

					}

					// ordem da query, ID, nome categoria, id, nome produto e descricao 5 categorias
					// de produto...
					Produto produto = new Produto(rst.getInt(3), rst.getString(4), rst.getString(5));
					ultima.adicionar(produto);

				}

			}
			return categorias;
		}

	}
}