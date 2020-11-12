package model;

import java.sql.Date;
import java.sql.Timestamp;

import javafx.scene.image.ImageView;

public class furnizime {

	private int furnizimeid;
	private double vlera;
	private String pershkrimi, foto_fature, gjendja;
	private ImageView photo_fature;
	private Date data;
	private Timestamp created_date;
	private perdoruesit userid;
	
	public furnizime() {
		super();
	}
	
	public furnizime(int furnizimeid, perdoruesit userid, double vlera, String pershkrimi, String foto_fature, Date data, ImageView photo_fature, Timestamp created_date, String gjendja) {
		super();
		this.furnizimeid = furnizimeid;
		this.userid = userid;
		this.vlera = vlera;
		this.pershkrimi = pershkrimi;
		this.data = data;
		this.created_date = created_date;
		this.foto_fature = foto_fature;
		this.photo_fature = photo_fature;
		this.gjendja = gjendja;
	}


	public String getGjendja() {
		return gjendja;
	}

	public void setGjendja(String gjendja) {
		this.gjendja = gjendja;
	}

	public String getFoto_fature() {
		return foto_fature;
	}

	public void setFoto_fature(String foto_fature) {
		this.foto_fature = foto_fature;
	}

	public ImageView getPhoto_fature() {
		return photo_fature;
	}

	public void setPhoto_fature(ImageView photo_fature) {
		this.photo_fature = photo_fature;
	}

	public int getFurnizimeid() {
		return furnizimeid;
	}


	public void setFurnizimeid(int furnizimeid) {
		this.furnizimeid = furnizimeid;
	}


	public perdoruesit getUserid() {
		return userid;
	}


	public void setUserid(perdoruesit userid) {
		this.userid = userid;
	}


	public double getVlera() {
		return vlera;
	}


	public void setVlera(double vlera) {
		this.vlera = vlera;
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
