package dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.pastrime;
import model.perdoruesit;

public class pastrimeDao extends DAO {
	
	public pastrimeDao() {
		super();
	}
	
public void addPastrime(pastrime pastrim) throws SQLException {
		
		
		String insert_pastrime = "INSERT INTO denas_power.pastrime " + 
				"(userid, vendi, pershkrimi, data, created_date) VALUES (?,?,?,?,?)";
		stm = connector.prepareStatement(insert_pastrime);

		stm.setInt(1, pastrim.getUserid().getUserid());
		stm.setString(2, pastrim.getVendi());
		stm.setString(3, pastrim.getPershkrimi());
		stm.setDate(4, pastrim.getData());
		stm.setTimestamp(5, pastrim.getCreated_date());

		stm.executeUpdate();
		stm.close();
		}
	
	public List<pastrime> viewPastrime(LocalDate date_from, LocalDate date_to) throws SQLException{
		List<pastrime> data = new ArrayList<pastrime>();
		String query = "SELECT pas.userid, pas.vendi, pas.pershkrimi, data, p.username, pas.pastrimeid FROM denas_power.pastrime pas "
				+ "join denas_power.perdoruesit p on pas.userid = p.userid where data between  '"+date_from+"' and '"+date_to+"' ";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query);
		
		while(rs.next()) {
			pastrime pas = new pastrime();
			perdoruesit p = new perdoruesit();
			p.setUserid(rs.getInt(1));
			p.setUsername(rs.getString(5));
			pas.setUserid(p);
			pas.setVendi(rs.getString(2));
			pas.setPershkrimi(rs.getString(3));
			pas.setData(rs.getDate(4));
			pas.setPastrimeid(rs.getInt(6));
			data.add(pas);
		}
		return data;
	}
	
	public void deletePastrime(int pastrimeid) throws SQLException{
		String query = "DELETE FROM denas_power.pastrime where pastrimeid=?";
		stm = connector.prepareStatement(query);
		stm.setInt(1, pastrimeid);
		
		stm.execute();
		stm.close();
		
	}

	public void updatePastrime(pastrime pa) throws SQLException {
		String update = "UPDATE denas_power.pastrime SET vendi = ?, data = ?,"
				+ "pershkrimi = ? WHERE pastrimeid = ?";
		stm = connector.prepareStatement(update);

		stm.setString(1, pa.getVendi());
		stm.setDate(2, pa.getData());
		stm.setString(3, pa.getPershkrimi());
		stm.setInt(4, pa.getPastrimeid());
		
		stm.executeUpdate();
		stm.close();
		
	}
}
