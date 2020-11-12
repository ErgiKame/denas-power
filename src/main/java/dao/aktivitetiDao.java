package dao;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import model.aktiviteti;
import model.perdoruesit;

public class aktivitetiDao extends DAO {

	public aktivitetiDao() {
		super();
	}
	
	public List<aktiviteti> getAllActivitetet() throws SQLException{
		List<aktiviteti> data = new ArrayList<aktiviteti>();
		String query = "select a.userid, emri_aktiviteti, data, ora, p.userid, p.username, moduli from denas_power.aktiviteti a join denas_power.perdoruesit p on a.userid = p.userid "
				+ "order by data, ora";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query); 
		
		while(rs.next()) {
			perdoruesit p = new perdoruesit();
			p.setUserid(rs.getInt(5));
			p.setUsername(rs.getString(6));
			
			aktiviteti a = new aktiviteti();
			a.setAktivitetiid(rs.getInt(1));
			a.setEmri_aktiviteti(rs.getString(2));
			a.setData(rs.getDate(3));
			a.setOra(rs.getTime(4));
			a.setUserid(p);
			a.setModuli(rs.getString(7));
			
			data.add(a);
		}
		return data;
	}
	
	public void insertAktivitet(aktiviteti aktiviteti) throws SQLException {

		String insert = "insert into denas_power.aktiviteti(emri_aktiviteti,userid,data,ora,moduli) values(?,?,?,?,?)";
		stm = connector.prepareStatement(insert);

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
	   
	    
		stm.setString(1, aktiviteti.getEmri_aktiviteti());
		stm.setInt(2, aktiviteti.getUserid().getUserid());
		stm.setString(3, dateFormat.format(date));
		stm.setString(4, sdf.format(cal.getTime()));
		stm.setString(5, aktiviteti.getModuli());
		
		stm.executeUpdate();
		stm.close();
	}
	
}
