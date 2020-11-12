package model;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class aktiviteti {

	private int aktivitetiid;
	private String emri_aktiviteti,moduli;
	private perdoruesit userid;
	private Date data;
	private Time ora;
	
	SimpleDateFormat ft =  new SimpleDateFormat ("dd/MM/yyyy");
	SimpleDateFormat rt =  new SimpleDateFormat ("HH:mm");
	
	public aktiviteti(int aktivitetiid, String emri_aktiviteti, String moduli, perdoruesit userid, Date data, Time ora,
			SimpleDateFormat ft, SimpleDateFormat rt) {
		super();
		this.aktivitetiid = aktivitetiid;
		this.emri_aktiviteti = emri_aktiviteti;
		this.moduli = moduli;
		this.userid = userid;
		this.data = data;
		this.ora = ora;
		this.ft = ft;
		this.rt = rt;
	}
	
	public aktiviteti(String emri_aktiviteti, String moduli, perdoruesit userid, Date data, Time ora,
			SimpleDateFormat ft, SimpleDateFormat rt) {
		this.emri_aktiviteti = emri_aktiviteti;
		this.moduli = moduli;
		this.userid = userid;
		this.data = data;
		this.ora = ora;
		this.ft = ft;
		this.rt = rt;
	}
	
	public aktiviteti() {
		super();
	}

	public int getAktivitetiid() {
		return aktivitetiid;
	}

	public void setAktivitetiid(int aktivitetiid) {
		this.aktivitetiid = aktivitetiid;
	}

	public String getEmri_aktiviteti() {
		return emri_aktiviteti;
	}

	public void setEmri_aktiviteti(String emri_aktiviteti) {
		this.emri_aktiviteti = emri_aktiviteti;
	}
	
	
	public String getModuli() {
		return moduli;
	}

	public void setModuli(String moduli) {
		this.moduli = moduli;
	}

	public perdoruesit getUserid() {
		return userid;
	}

	public void setUserid(perdoruesit userid) {
		this.userid = userid;
	}

	public String getData() {
		return ft.format(data);
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getOra() {
		return rt.format(ora);
	}

	public void setOra(Time ora) {
		this.ora = ora;
	}
	
}
