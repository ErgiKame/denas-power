package dao;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;
import model.karburanti;
import model.perdoruesit;
import utils.Helpers;

public class karburantiDao extends DAO {
	
	public DecimalFormat decimalFormat = new DecimalFormat("#.##");
	
	public karburantiDao() {
		super();
	}
	
	public void addKarburanti(karburanti karburant) throws SQLException {
		
		
		String insert_karburanti = "INSERT INTO denas_power.karburanti " + 
				"(userid, lloji, sasia, vlera,"
				+ "data, created_date, foto_fature) VALUES (?,?,?,?,?,?,?)";
		stm = connector.prepareStatement(insert_karburanti);

		stm.setInt(1, karburant.getUserid().getUserid());
		stm.setString(2, karburant.getLloji());
		stm.setDouble(3, karburant.getSasia());
		stm.setDouble(4, karburant.getCmimi());
		stm.setDate(5, karburant.getData());
		stm.setTimestamp(6, karburant.getCreated_date());
		stm.setString(7, karburant.getFoto_fature());

		stm.executeUpdate();
		stm.close();
		}
	
	public List<karburanti> viewKarburanti(LocalDate date_from, LocalDate date_to) throws SQLException{
		List<karburanti> data = new ArrayList<karburanti>();
		String query = "SELECT k.userid, k.lloji, k.sasia, k.vlera, data, p.username, k.karburantiid, k.foto_fature FROM denas_power.karburanti k "
				+ "join denas_power.perdoruesit p on k.userid = p.userid where data between  '"+date_from+"' and '"+date_to+"' ";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query);
		
		while(rs.next()) {
			karburanti k = new karburanti();
			perdoruesit p = new perdoruesit();
			p.setUserid(rs.getInt(1));
			p.setUsername(rs.getString(6));
			k.setUserid(p);
			k.setLloji(rs.getString(2));
			k.setSasia(rs.getDouble(3));
			k.setVlera(rs.getDouble(4));
			k.setData(rs.getDate(5));
			k.setCmimi(Double.parseDouble(decimalFormat.format(k.getVlera() / k.getSasia())));
			k.setKarburantiid(rs.getInt(7));
			k.setFoto_fature(rs.getString(8));
			
			k.setPhoto_fature(Helpers.setPhoto(new ImageView(), rs.getString(8)));
			
			data.add(k);
		}
		return data;
	}
	
	public void deleteKarburanti(int karburantiid) throws SQLException{
		String query = "DELETE FROM denas_power.karburanti where karburantiid=?";
		stm = connector.prepareStatement(query);
		stm.setInt(1, karburantiid);
		
		stm.execute();
		stm.close();
		
	}
	
	public void updateKarburanti(karburanti k) throws SQLException {
		String update = "UPDATE denas_power.karburanti SET sasia = ?, data = ?,"
				+ "vlera = ?, lloji = ?, foto_fature = ? WHERE karburantiid = ?";
		stm = connector.prepareStatement(update);

		stm.setDouble(1, k.getSasia());
		stm.setDate(2, k.getData());
		stm.setDouble(3, k.getCmimi());
		stm.setString(4, k.getLloji());
		stm.setString(5, k.getFoto_fature());
		stm.setInt(6, k.getKarburantiid());
		
		
		stm.executeUpdate();
		stm.close();
		
	}
 	
}

