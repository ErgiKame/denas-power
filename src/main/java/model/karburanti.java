package model;

import java.sql.Date;
import java.sql.Timestamp;

import javafx.scene.image.ImageView;

public class karburanti {
	private int karburantiid;
	private String lloji, foto_fature;
	private double sasia, cmimi, vlera;
	private ImageView photo_fature;
	private Date data;
	private Timestamp created_date;
	private perdoruesit userid;
	
	public karburanti(int karburantiid, perdoruesit userid, String lloji, String foto_fature, ImageView photo_fature, double sasia, double cmimi, double vlera, Date data,
			Timestamp created_date) {
		super();
		this.karburantiid = karburantiid;
		this.userid = userid;
		this.lloji = lloji;
		this.sasia = sasia;
		this.cmimi = cmimi;
		this.vlera = vlera;
		this.data = data;
		this.created_date = created_date;
		this.foto_fature = foto_fature;
		this.photo_fature = photo_fature;
	}

	public karburanti() {
		super();
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

	public int getKarburantiid() {
		return karburantiid;
	}

	public void setKarburantiid(int karburantiid) {
		this.karburantiid = karburantiid;
	}

	public perdoruesit getUserid() {
		return userid;
	}

	public void setUserid(perdoruesit userid) {
		this.userid = userid;
	}

	public String getLloji() {
		return lloji;
	}

	public void setLloji(String lloji) {
		this.lloji = lloji;
	}

	public double getSasia() {
		return sasia;
	}

	public void setSasia(double sasia) {
		this.sasia = sasia;
	}

	public double getCmimi() {
		return cmimi;
	}

	public void setCmimi(double cmimi) {
		this.cmimi = cmimi;
	}

	public double getVlera() {
		return vlera;
	}

	public void setVlera(double vlera) {
		this.vlera = vlera;
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
