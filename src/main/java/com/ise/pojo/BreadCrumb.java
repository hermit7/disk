package com.ise.pojo;

import java.io.Serializable;
/**
 * 面包屑导航对象
 * @author 6
 *
 */
public class BreadCrumb implements Serializable{ 

	private static final long serialVersionUID = 1L;
	
	private String folderName;  //各级文件夹的名字
	private String folderPath;	//该文件夹路径

	
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public String getFolderPath() {
		return folderPath;
	}
	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}
}
