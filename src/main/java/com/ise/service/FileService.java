package com.ise.service;

import java.io.InputStream;
import java.util.List;

import com.ise.pojo.HFile;
import com.ise.pojo.HdfsFolder;

public interface FileService {
	public List<HFile> listFiles(String path);
	
	public boolean uploadFile(InputStream in,String path,String name);

	public boolean modifyFile(String curPath, String originName, String destName);

	public boolean downloadFile(String path);

	public boolean deleteFile(String path);

	public boolean makeDir(String curPath, String folder);

	public HdfsFolder showDirTree(String username);

	public boolean moveFile(String src, String dst, String motion);
}
