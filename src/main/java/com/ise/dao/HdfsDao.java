package com.ise.dao;

import java.io.InputStream;
import java.util.List;

import com.ise.pojo.HFile;
import com.ise.pojo.HdfsFolder;

public interface HdfsDao {
	public List<HFile> listFiles(String path);

	public boolean uploadFile(InputStream in, String path, String name);

	public boolean modifyFile(String curPath, String originName, String destName);

	public boolean downloadFile(String path);

	public boolean deleteFile(String path);

	public boolean makeDir(String curPath, String folder);

	public void makeDir(String root);

	public HdfsFolder listTree(String username);

	public boolean copyFile(String src, String dest);

	public boolean moveFile(String src, String dest);
	
	public boolean isPathExist(String path);
	
	public HFile getFileStatus(String path);
	
	public long getPathSize(String path);
}
