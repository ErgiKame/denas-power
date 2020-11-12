package model;

import java.sql.Date;

public class nafta_lart {

	private int nafta_larid;
	private Date data;
	private double sasia_dalje, sasia_hyrje, gjendja;
	private String lloji_makines, pershkrimi;
	private perdoruesit userid;

	public nafta_lart(){
		super();
	}

	
	
	public nafta_lart(int nafta_larid, Date data, double sasia_dalje, double sasia_hyrje, double gjendja,
			String lloji_makines, String pershkrimi, perdoruesit userid) {
		super();
		this.nafta_larid = nafta_larid;
		this.data = data;
		this.sasia_dalje = sasia_dalje;
		this.sasia_hyrje = sasia_hyrje;
		this.gjendja = gjendja;
		this.lloji_makines = lloji_makines;
		this.pershkrimi = pershkrimi;
		this.userid = userid;
	}



	public int getNafta_larid() {
		return nafta_larid;
	}

	public void setNafta_larid(int nafta_larid) {
		this.nafta_larid = nafta_larid;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getSasia_dalje() {
		return sasia_dalje;
	}

	public void setSasia_dalje(double sasia_dalje) {
		this.sasia_dalje = sasia_dalje;
	}

	public double getSasia_hyrje() {
		return sasia_hyrje;
	}

	public void setSasia_hyrje(double sasia_hyrje) {
		this.sasia_hyrje = sasia_hyrje;
	}

	public double getGjendja() {
		return gjendja;
	}

	public void setGjendja(double gjendja) {
		this.gjendja = gjendja;
	}

	public String getLloji_makines() {
		return lloji_makines;
	}

	public void setLloji_makines(String lloji_makines) {
		this.lloji_makines = lloji_makines;
	}

	public String getPershkrimi() {
		return pershkrimi;
	}

	public void setPershkrimi(String pershkrimi) {
		this.pershkrimi = pershkrimi;
	}

	public perdoruesit getUserid() {
		return userid;
	}

	public void setUserid(perdoruesit userid) {
		this.userid = userid;
	}




}
