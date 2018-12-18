package com.ise.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * 目录树对象
 * @author 6
 *
 */
public class HdfsFolder implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String text; //目录节点的名字
	private String state; //节点状态
	private boolean checked; //节点是否选中
	private Map<String,String> attributes;  //自定义属性 保存目录的路径
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
