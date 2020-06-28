package it.polito.tdp.newufosightings.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.newufosightings.model.Adiacenza;
import it.polito.tdp.newufosightings.model.Sighting;
import it.polito.tdp.newufosightings.model.State;

public class NewUfoSightingsDAO {

	public Map<String, State> loadAllStates(Map<String, State> states) {
		String sql = "SELECT DISTINCT * FROM state";

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				State state = new State(rs.getString("id"), rs.getString("Name"), rs.getString("Capital"),
						rs.getDouble("Lat"), rs.getDouble("Lng"), rs.getInt("Area"), rs.getInt("Population"),
						rs.getString("Neighbors"));
				states.put(rs.getString("id"), state);
			}

			conn.close();
			return states;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<String> getShapes(Integer anno) {
		String sql = "SELECT DISTINCT shape " + 
				"FROM sighting AS s " + 
				"WHERE year(s.datetime) = ?";
		
		List<String> result = new ArrayList<String>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(rs.getString("shape"));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Adiacenza> getAdiacenze(Integer anno, String shape, Map<String, State> states) {
		String sql = "SELECT n.state1, n.state2, COUNT(DISTINCT s1.id)+COUNT(DISTINCT s2.id) AS peso " + 
				"FROM neighbor AS n, sighting AS s1, sighting AS s2 " + 
				"WHERE YEAR(s1.datetime) = ? AND YEAR(s2.datetime) = ? AND s1.shape = ? AND s2.shape = ? " + 
				"AND n.state1 = s1.state AND n.state2 = s2.state " + 
				"GROUP BY n.state1, n.state2";
		
		List<Adiacenza> result = new ArrayList<Adiacenza>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);
			st.setInt(2, anno);
			st.setString(3, shape);
			st.setString(4, shape);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Adiacenza(states.get(rs.getString("state1")), states.get(rs.getString("state2")), rs.getInt("peso")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}
	
	public List<Sighting> getAllSighting(Integer anno, String shape) {
		String sql = "SELECT id, datetime, state, shape " + 
				"FROM sighting AS s " + 
				"WHERE s.shape = ? AND YEAR(s.datetime) = ?";
		
		List<Sighting> result = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, shape);
			st.setInt(2, anno);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				result.add(new Sighting(rs.getInt("id"), rs.getTimestamp("datetime").toLocalDateTime(), rs.getString("state"), rs.getString("shape")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore connessione al database");
			throw new RuntimeException("Error Connection Database");
		}
	}

}
