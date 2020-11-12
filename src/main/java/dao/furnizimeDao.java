package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;
import model.furnizime;
import model.perdoruesit;
import utils.Helpers;

public class furnizimeDao extends DAO {

	public furnizimeDao() {
		super();
	}
	
public void addFurnizime(furnizime furnizime) throws SQLException {
		
		
		String insert_furnizime = "INSERT INTO denas_power.furnizime " + 
				"(userid, data, pershkrimi, vlera, created_date, foto_fature, gjendja) VALUES (?,?,?,?,?,?,?)";
		stm = connector.prepareStatement(insert_furnizime);

		stm.setInt(1, furnizime.getUserid().getUserid());
		stm.setDate(2, (Date) furnizime.getData());
		stm.setString(3, furnizime.getPershkrimi());
		stm.setDouble(4, furnizime.getVlera());
		stm.setTimestamp(5, furnizime.getCreated_date());
		stm.setString(6, furnizime.getFoto_fature());
		stm.setString(7, "Jo");

		stm.executeUpdate();
		stm.close();
		}
	
	public List<furnizime> viewFurnizime(LocalDate date_from, LocalDate date_to) throws SQLException{
		List<furnizime> data = new ArrayList<furnizime>();
		String query = "SELECT f.userid, f.data, f.pershkrimi, f.vlera, p.username, f.furnizimeid, f.foto_fature, f.gjendja FROM denas_power.furnizime f "
				+ "join denas_power.perdoruesit p on f.userid = p.userid where f.data between  '"+date_from+"' and '"+date_to+"' ";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query);
		
		while(rs.next()) {
			furnizime f = new furnizime();
			perdoruesit p = new perdoruesit();
			p.setUserid(rs.getInt(1));
			p.setUsername(rs.getString(5));
			f.setUserid(p);
			f.setData(rs.getDate(2));
			f.setPershkrimi(rs.getString(3));
			f.setVlera(rs.getDouble(4));
			f.setFurnizimeid(rs.getInt(6));
			f.setFoto_fature(rs.getString(7));
			f.setPhoto_fature(Helpers.setPhoto(new ImageView(), rs.getString(7)));
			f.setGjendja(rs.getString(8));
			
			data.add(f);
		}
		return data;
	}
	
	public void deleteFurnizime(int furnizimeid) throws SQLException{
		String query = "DELETE FROM denas_power.furnizime where furnizimeid=?";
		stm = connector.prepareStatement(query);
		stm.setInt(1, furnizimeid);
		
		stm.execute();
		stm.close();
		
	}

	public void updateFurnizime(furnizime f) throws SQLException {
		String update = "UPDATE denas_power.furnizime SET  data = ?,"
				+ "pershkrimi = ?, vlera = ?, foto_fature = ? WHERE furnizimeid = ?";
		stm = connector.prepareStatement(update);

		stm.setDate(1, (Date) f.getData());
		stm.setString(2, f.getPershkrimi());
		stm.setDouble(3, f.getVlera());
		stm.setString(4, f.getFoto_fature());
		stm.setInt(5, f.getFurnizimeid());
		
		
		stm.executeUpdate();
		stm.close();
		
	}
	
	public void updateGjendja(furnizime f) throws SQLException{
		String update = "UPDATE denas_power.furnizime SET  gjendja = ? WHERE furnizimeid = ?";
		stm = connector.prepareStatement(update);

		stm.setString(1, f.getGjendja());
		stm.setInt(2, f.getFurnizimeid());
		
		
		
		stm.executeUpdate();
		stm.close();
	}
	
}
