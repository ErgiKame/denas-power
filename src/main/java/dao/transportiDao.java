package dao;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.ImageView;
import main.global;
import model.perdoruesit;
import model.transporti;
import utils.Helpers;


public class transportiDao extends DAO{

	public transportiDao() {
		super();
	}

	public void addTransporti(transporti transport) throws SQLException {
		String insert_transporti = "INSERT INTO denas_power.transporti " + 
				"(userid, nisja, mberritja, " 
				+ "koment, data, created_date) VALUES (?,?,?,?,?,?)";
		stm = connector.prepareStatement(insert_transporti);

		stm.setInt(1, transport.getUserid().getUserid());
		stm.setDouble(2, transport.getNisja());
		stm.setDouble(3, transport.getMberritja());
// major update		stm.setString(4, transport.getFoto_nisje());
// major update		stm.setString(5, transport.getFoto_mberritje());
		stm.setString(4, transport.getKoment());
		stm.setDate(5, transport.getData());
		stm.setTimestamp(6, transport.getCreated_date());

		stm.executeUpdate();
		stm.close();
	}

	public void updateTransporti(transporti transport) throws SQLException{
		String update_transporti = "UPDATE denas_power.transporti SET data = ?, nisja = ?, mberritja = ?, koment = ? WHERE transportiid = ?";
		stm = connector.prepareStatement(update_transporti);

		stm.setDate(1, transport.getData());
		stm.setDouble(2, transport.getNisja());
		stm.setDouble(3, transport.getMberritja());
		stm.setString(4, transport.getKoment());
// major update		stm.setString(5, transport.getFoto_nisje());
// major update		stm.setString(6, transport.getFoto_mberritje());
		stm.setInt(5, transport.getTransportiid());

		stm.executeUpdate();
		stm.close();
	}

	public List<transporti> viewTransporti(LocalDate date_from, LocalDate date_to) throws SQLException{
		List<transporti> data = new ArrayList<transporti>();
		String query = "SELECT t.userid, t.nisja, t.mberritja, t.koment, data, p.username, t.transportiid "
				+ "FROM denas_power.transporti t join denas_power.perdoruesit p on t.userid = p.userid where data between  '"+date_from+"' and '"+date_to+"' ";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query);

		while(rs.next()) {
			transporti t = new transporti();
			perdoruesit p = new perdoruesit();
			p.setUserid(rs.getInt(1));
			p.setUsername(rs.getString(6));
			t.setUserid(p);
			t.setNisja(rs.getInt(2));
			t.setMberritja(rs.getInt(3));
// major update			t.setFoto_nisje(rs.getString(4));
// major update			t.setFoto_mberritje(rs.getString(5));
			
// major update			t.setPhoto_nisje(Helpers.setPhoto(new ImageView(), rs.getString(4)));
// major update			t.setPhoto_mberritje(Helpers.setPhoto(new ImageView(), rs.getString(5)));
			
			t.setKoment(rs.getString(4));
			t.setData(rs.getDate(5));
			t.setDistanca(t.getMberritja() - t.getNisja());
			t.setTransportiid(rs.getInt(7));

			data.add(t);
		}
		return data;
	}

	public void deleteTransporti(int transportiid) throws SQLException{
		String query = "DELETE FROM denas_power.transporti where transportiid=?";
		stm = connector.prepareStatement(query);
		stm.setInt(1, transportiid);

		stm.execute();
		stm.close();

	}

}
