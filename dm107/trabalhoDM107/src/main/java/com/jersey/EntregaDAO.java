package com.jersey;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntregaDAO {
	public void insert(Connection conn, EntregaModel entrega) throws SQLException {
		String sql = "insert into entrega (numPedido, idCliente, nomeRecebedor, cpfRecebedor, dataEntrega) values (?, ?, ?, ?, ?);";
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, entrega.getNumPedido());
		pstm.setInt(2, entrega.getIdCliente());
		pstm.setString(3, entrega.getNomeRecebedor());
		pstm.setString(4, entrega.getCpfRecebedor());
		pstm.setDate(5, entrega.getDataEntrega());
		pstm.execute();
	}

	public EntregaModel listByNumPedido(Connection conn, int idPedido) throws SQLException {
		String sql = "select * from entrega where numPedido = ?";
		PreparedStatement pstm;
		pstm = conn.prepareStatement(sql);
		pstm.setInt(1, idPedido);

		ResultSet rs = pstm.executeQuery();
		EntregaModel entrega = new EntregaModel();
		while (rs.next()) {
			int id = rs.getInt("id");
			int numPedido = rs.getInt("numPedido");
			int idCliente = rs.getInt("idCliente");
			String nomeRecebedor = rs.getString("nomeRecebedor");
			String cpfRecebedor = rs.getString("cpfRecebedor");
			Date dataEntrega = rs.getDate("dataEntrega");
			entrega = new EntregaModel(id, numPedido, idCliente, nomeRecebedor, cpfRecebedor, dataEntrega);
		}
		return entrega;
	}

}
