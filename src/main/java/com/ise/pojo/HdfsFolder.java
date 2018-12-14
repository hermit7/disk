package com.ise.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * Ŀ¼������
 * @author 6
 *
 */
public class HdfsFolder implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String text; //Ŀ¼�ڵ������
	private String state; //�ڵ�״̬
	private boolean checked; //�ڵ��Ƿ�ѡ��
	private Map<String,String> attributes;  //�Զ������� ����Ŀ¼��·��
	private List<HdfsFolder> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public List<HdfsFolder> getChildren() {
		return children;
	}
	public void setChildren(List<HdfsFolder> children) {
		this.children = children;
	}
	
}
