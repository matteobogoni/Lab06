package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}
			rs.close();
			st.close();
			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		
		final String sql = "SELECT localita, umidita, data "
				+ "FROM situazione "
				+ "WHERE MONTH(DATA) = ? and localita = ? "
				+ "GROUP BY localita, umidita, data";
		
		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			st.setString(2, localita);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
				
		}
			rs.close();
			st.close();
			conn.close();
			return rilevamenti;
		
	}catch (SQLException e) {

		e.printStackTrace();
		throw new RuntimeException(e);
		}
	}
	
public List<Double> getAvgRilevamentiLocalitaMese(int mese) {
		
		final String sql = "SELECT localita, AVG(umidita) AS umiditaMedia "
				+ "FROM situazione "
				+ "WHERE MONTH(DATA) = ? "
				+ "GROUP BY localita";
		
		List<Double>  umidita= new ArrayList<Double>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, mese);
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				
				double media = rs.getDouble("umiditaMedia");
				umidita.add(media);
		}
			rs.close();
			st.close();
			conn.close();
			return umidita;
		
	}catch (SQLException e) {

		e.printStackTrace();
		throw new RuntimeException(e);
	}

	}
	
}
