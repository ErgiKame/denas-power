package dao;


public class ControlDAO {

	private static ControlDAO dao = new ControlDAO();
	private perdoruesit_dao perdoruesitDao = new perdoruesit_dao();
	private login_dao loginDao = new login_dao();
	private transportiDao transportiDao = new transportiDao();
	private karburantiDao karburantiDao = new karburantiDao();
	private pastrimeDao pastrimeDao = new pastrimeDao();
	private furnizimeDao furnizimeDao = new furnizimeDao();
	private aktivitetiDao aktivitetiDao = new aktivitetiDao();
	private nafta_lartDao nafta_lartDao = new nafta_lartDao();
	
	public static ControlDAO getControlDao() {
		return dao;
	}

	public perdoruesit_dao getPerdoruesitDao() {
		return perdoruesitDao;
	}
	
	public login_dao getLoginDao() {
		return loginDao;
	}
	
	public transportiDao getTransportiDao() {
		return transportiDao;
	}
	
	public karburantiDao getKarburantiDao() {
		return karburantiDao;
	}
	
	public pastrimeDao getPastrimeDao() {
		return pastrimeDao;
	}
	
	public furnizimeDao getFurnizimeDao() {
		return furnizimeDao;
	}
	
	public aktivitetiDao getAktivitetiDao() {
		return aktivitetiDao;
	}
	
	public nafta_lartDao getNafta_lartDao() {
		return nafta_lartDao;
	}
}
