package com.ise.pojo;

import java.io.Serializable;
/**
 * ���м��������
 * @author 6
 *
 */
public class BreadCrumb implements Serializable{ 

	private static final long serialVersionUID = 1L;
	
	private String folderName;  //�����ļ��е�����
	private String folderPath;	//���ļ���·��

	
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
