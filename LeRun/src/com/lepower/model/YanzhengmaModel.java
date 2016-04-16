package com.lepower.model;

public class YanzhengmaModel {
	
	 private int code;
	    private String msg;
	    private String smsid;

	    public int getCode() {
	        return code;
	    }

	    public void setCode(int code) {
	        this.code = code;
	    }

	    public String getMsg() {
	        return msg;
	    }

	    public void setMsg(String msg) {
	        this.msg = msg;
	}

	    public String getSmsid() {
	        return smsid;
	    }

	    public void setSmsid(String smsid) {
	        this.smsid = smsid;
	    }

		@Override
		public String toString() {
			return "YanzhengmaModel [code=" + code + ", msg=" + msg
					+ ", smsid=" + smsid + "]";
		}
	    

}
