package dao;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.nafta_lart;
import model.perdoruesit;

public class nafta_lartDao extends DAO{

	public nafta_lartDao() {

	}

	public void daljeNafta_Lart(nafta_lart nafta_lart) throws SQLException {


		String insert_nafta = "INSERT INTO denas_power.nafta_lart " + 
				"(userid, data, sasia_dalje, sasia_hyrje, lloji_makines, pershkrimi) VALUES (?,?,?,?,?,?)";
		stm = connector.prepareStatement(insert_nafta);

		stm.setInt(1, nafta_lart.getUserid().getUserid());
		stm.setDate(2, nafta_lart.getData());
		stm.setDouble(3, nafta_lart.getSasia_dalje());
		stm.setDouble(4, nafta_lart.getSasia_hyrje());
		stm.setString(5, nafta_lart.getLloji_makines());
		stm.setString(6, nafta_lart.getPershkrimi());

		stm.executeUpdate();
		stm.close();
	}

	public void deleteNafta_lart(int nafta_lartid) throws SQLException{
		String query = "DELETE FROM denas_power.nafta_lart WHERE nafta_lartid=?";
		stm = connector.prepareStatement(query);
		stm.setInt(1, nafta_lartid);

		stm.execute();
		stm.close();

	}

	public List<nafta_lart> viewNafta_lart(LocalDate date_from, LocalDate date_to) throws SQLException{
		List<nafta_lart> data = new ArrayList<nafta_lart>();
		String query = "SELECT na.userid, na.data, na.sasia_dalje, na.lloji_makines, na.pershkrimi, p.username, na.nafta_lartid, na.sasia_hyrje FROM denas_power.nafta_lart na "
				+ "join denas_power.perdoruesit p on na.userid = p.userid where data between  '"+date_from+"' and '"+date_to+"' AND sasia_dalje != 0 ";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query);

		while(rs.next()) {
			nafta_lart na = new nafta_lart();
			perdoruesit p = new perdoruesit();
			p.setUserid(rs.getInt(1));
			p.setUsername(rs.getString(6));
			na.setUserid(p);
			na.setData(rs.getDate(2));
			na.setSasia_dalje(rs.getDouble(3));
			na.setLloji_makines(rs.getString(4));
			na.setPershkrimi(rs.getString(5));
			na.setNafta_larid(rs.getInt(7));
			na.setSasia_hyrje(rs.getDouble(8));

			data.add(na);
		}

		System.out.println(data.size());
		return data;
	}

	public String getGjendja() throws SQLException {

		double gjendja = 0.0;
		String query = "select sum(sasia_hyrje) - sum(sasia_dalje) from denas_power.nafta_lart; ";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query);

		while(rs.next()) {
			gjendja = rs.getDouble(1);
		}
		DecimalFormat df = new DecimalFormat("#.#"); 
		df.format(gjendja);
		return df.format(gjendja);
	}

	public String getLlogaritje(String lloji, LocalDate dateFrom, LocalDate dateTo) throws SQLException {

		double llogaritja = 0.0;
		String query = "select sum(sasia_dalje) from denas_power.nafta_lart WHERE lloji_makines = '"+lloji+"' and data between '"+dateFrom+"' and '"+dateTo+"'; " ;
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query);

		while(rs.next()) {
			llogaritja = rs.getDouble(1);
		}
		DecimalFormat df = new DecimalFormat("#.#"); 
		df.format(llogaritja);
		return df.format(llogaritja);
	}

	public List<nafta_lart> getSasiaSipasLlojit(LocalDate date_from, LocalDate date_to) throws SQLException {
		List<nafta_lart> data = new ArrayList<nafta_lart>();
		String query = "select lloji_makines, sum(sasia_dalje)  from denas_power.nafta_lart where data between  '"+date_from+"' and '"+date_to+"' AND sasia_dalje != 0 group by lloji_makines ";
		stm = connector.prepareStatement(query);
		rs = stm.executeQuery(query);

		while(rs.next()) {
			nafta_lart na = new nafta_lart();
			na.setLloji_makines(rs.getString(1));
			na.setSasia_dalje(rs.getDouble(2));

			data.add(na);
		}
		return data;
	}


}
