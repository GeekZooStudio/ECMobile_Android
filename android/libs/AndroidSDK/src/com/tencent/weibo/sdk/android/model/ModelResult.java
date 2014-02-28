package com.tencent.weibo.sdk.android.model;
import java.util.List;

public class ModelResult {
	private boolean success = true;
	private String error_message = "success";;
	private List<BaseVO> list;
	private Object obj; //如数据格式无法按照BaseVO拼装则该为map
	private int total;
	private int p;
	private int ps;
	private String lon;//经度
	private String lat;//纬度
	private boolean isLastPage ; //是否最后一页
	private boolean isExpires = false;
	
	public boolean isExpires() {
		return isExpires;
	}
	public void setExpires(boolean isExpires) {
		this.isExpires = isExpires;
	}
	public boolean isLastPage() {
		return isLastPage;
	} 
	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public void add(BaseVO vo) {
		list.add(vo);
	}
	public BaseVO get() {
		return list.get(0);
	}
	
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getP() {
		return p;
	}
	public void setP(int p) {
		this.p = p;
	}
	public int getPs() {
		return ps;
	}
	public void setPs(int ps) {
		this.ps = ps;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getError_message() {
		return error_message;
	}
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	public List<BaseVO> getList() {
		return list;
	}
	public void setList(List<BaseVO> list) {
		this.list = list;
	}
}
