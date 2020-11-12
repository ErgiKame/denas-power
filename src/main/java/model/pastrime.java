package model;

import java.sql.Date;
import java.sql.Timestamp;

public class pastrime {

	private int pastrimeid;
	private String vendi, pershkrimi;
	private Date data;
	private Timestamp created_date;
	private perdoruesit userid;
	
	public pastrime() {
		super();
	}

	public pastrime(int pastrimeid, perdoruesit userid, String vendi, String pershkrimi, Date data, Timestamp created_date) {
		super();
		this.pastrimeid = pastrimeid;
		this.userid = userid;
		this.vendi = vendi;
		this.pershkrimi = pershkrimi;
		this.data = data;
		this.created_date = created_date;
	}

	public int getPastrimeid() {
		return pastrimeid;
	}

	public void setPastrimeid(int pastrimeid) {
		this.pastrimeid = pastrimeid;
	}

	public perdoruesit getUserid() {
		return userid;
	}

	public void setUserid(perdoruesit userid) {
		this.userid = userid;
	}

	public String getVendi() {
		return vendi;
	}

	public void setVendi(String vendi) {
		this.vendi = vendi;
	}

	public String getPershkrimi() {
		return pershkrimi;
	}

	public void setPershkrimi(String pershkrimi) {
		this.pershkrimi = pershkrimi;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}
	
	
	
}
