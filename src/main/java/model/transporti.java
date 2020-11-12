package model;

import java.sql.Date;
import java.sql.Timestamp;

import javafx.scene.image.ImageView;

public class transporti {

	private int transportiid, nisja, mberritja, distanca;
	private String foto_nisje, foto_mberritje, koment;
	private ImageView photo_nisje, photo_mberritje;
	private Date data;
	private Timestamp created_date;
	private perdoruesit userid;
	
	
	public transporti() {
		super();
	}



	public transporti(int transportiid, perdoruesit userid, int nisja, int mberritja, int distanca, String foto_nisje,
			String foto_mberritje, String koment, ImageView photo_nisje, ImageView photo_mberritje, Date data,
			Timestamp created_date) {
		super();
		this.transportiid = transportiid;
		this.userid = userid;
		this.nisja = nisja;
		this.mberritja = mberritja;
		this.distanca = distanca;
		this.foto_nisje = foto_nisje;
		this.foto_mberritje = foto_mberritje;
		this.koment = koment;
		this.photo_nisje = photo_nisje;
		this.photo_mberritje = photo_mberritje;
		this.data = data;
		this.created_date = created_date;
	}




	public transporti(int transportiid, perdoruesit userid, int nisja, int mberritja, int distanca, String foto_nisje,
			String foto_mberritje, String koment, Date data, Timestamp created_date) {
		super();
		this.transportiid = transportiid;
		this.userid = userid;
		this.nisja = nisja;
		this.mberritja = mberritja;
		this.foto_nisje = foto_nisje;
		this.foto_mberritje = foto_mberritje;
		this.koment = koment;
		this.data = data;
		this.created_date = created_date;
	}



	public int getTransportiid() {
		return transportiid;
	}



	public void setTransportiid(int transportiid) {
		this.transportiid = transportiid;
	}



	public perdoruesit getUserid() {
		return userid;
	}



	public void setUserid(perdoruesit userid) {
		this.userid = userid;
	}



	public int getNisja() {
		return nisja;
	}



	public void setNisja(int nisja) {
		this.nisja = nisja;
	}



	public int getMberritja() {
		return mberritja;
	}



	public void setMberritja(int mberritja) {
		this.mberritja = mberritja;
	}



	



	public String getFoto_nisje() {
		return foto_nisje;
	}



	public void setFoto_nisje(String foto_nisje) {
		this.foto_nisje = foto_nisje;
	}



	public String getFoto_mberritje() {
		return foto_mberritje;
	}



	public void setFoto_mberritje(String foto_mberritje) {
		this.foto_mberritje = foto_mberritje;
	}



	public String getKoment() {
		return koment;
	}



	public void setKoment(String koment) {
		this.koment = koment;
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




	public ImageView getPhoto_nisje() {
		return photo_nisje;
	}




	public void setPhoto_nisje(ImageView photo_nisje) {
		this.photo_nisje = photo_nisje;
	}




	public ImageView getPhoto_mberritje() {
		return photo_mberritje;
	}




	public void setPhoto_mberritje(ImageView photo_mberritje) {
		this.photo_mberritje = photo_mberritje;
	}




	public int getDistanca() {
		return distanca;
	}


	public void setDistanca(int distanca) {
		this.distanca = distanca;
	}
	
	

}

