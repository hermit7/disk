package com.ise.pojo;

import java.io.Serializable;
/**
 * �ļ�����
 * @author 6
 *
 */
public class HFile implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private String time;
	private String type; // ���ļ������� �ļ��� ѹ���ļ� ͼƬ��
	private String path; // ���ļ���·��
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private String size;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "File [name=" + name + ", time=" + time + ", type=" + type + ", path=" + path + ", size=" + size + "]";
	}
	
}
