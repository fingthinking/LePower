package com.lepower.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

@Table(name = "SelfInfo")
public class SelfInfo extends EntityBase{
	
	@Column(name="uId")
	private String uId;
	
	@Column(name="countOfDongtai")
	private String countOfDongtai;
	
	@Column(name="countOfFensi")
	private String countOfFensi;
	
	@Column(name="countOfGuanzhu")
	private String countOfGuanzhu;

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getCountOfDongtai() {
		return countOfDongtai;
	}

	public void setCountOfDongtai(String countOfDongtai) {
		this.countOfDongtai = countOfDongtai;
	}

	public String getCountOfFensi() {
		return countOfFensi;
	}

	public void setCountOfFensi(String countOfFensi) {
		this.countOfFensi = countOfFensi;
	}

	public String getCountOfGuanzhu() {
		return countOfGuanzhu;
	}

	public void setCountOfGuanzhu(String countOfGuanzhu) {
		this.countOfGuanzhu = countOfGuanzhu;
	}

	public SelfInfo(String uId, String countOfDongtai, String countOfFensi,
			String countOfGuanzhu) {
		super();
		this.uId = uId;
		this.countOfDongtai = countOfDongtai;
		this.countOfFensi = countOfFensi;
		this.countOfGuanzhu = countOfGuanzhu;
	}

	public SelfInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "SelfInfo [uId=" + uId + ", countOfDongtai=" + countOfDongtai
				+ ", countOfFensi=" + countOfFensi + ", countOfGuanzhu="
				+ countOfGuanzhu + "]";
	}
	
	
	

}
