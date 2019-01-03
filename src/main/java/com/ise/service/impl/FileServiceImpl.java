package com.ise.service.impl;

import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ise.dao.HdfsDao;
import com.ise.mapper.UserMapper;
import com.ise.pojo.HFile;
import com.ise.pojo.HdfsFolder;
import com.ise.service.FileService;

@Service("fileService")
public class FileServiceImpl implements FileService {

	@Autowired
	private HdfsDao hdfsDao;

	@Autowired
	private UserMapper userMapper;

	@Override
	public List<HFile> listFiles(String path) {
		List<HFile> list = hdfsDao.listFiles(path);
		Collections.sort(list, new Comparator<HFile>() {

			@Override
			public int compare(HFile f1, HFile f2) {
				return f1.getType().charAt(0) - f2.getType().charAt(0);
			}
		});
		return list;
	}

	@Override
	public boolean uploadFile(InputStream in, String path, String name, String userId, long usedSpace) {
		// 更新usedSpace
		userMapper.increaseUsedSpace(userId, usedSpace);
		return hdfsDao.uploadFile(in, path, name);
	}
	
	@Override
	public boolean uploadFileForAdmin(InputStream in, String path, String name) {
		return hdfsDao.uploadFile(in, path, name);
	}
	
	@Override
	public boolean modifyFile(String curPath, String originName, String destName) {
		return hdfsDao.modifyFile(curPath, originName, destName);
	}

	@Override
	public long deleteFile(String userId,  String path) {
		long size = hdfsDao.getPathSize(path) / 1024; 
		userMapper.decreaseUsedSpace(userId, size);
		hdfsDao.deleteFile(path);
		return size;
	}

	@Override
	public boolean downloadFile(String path) {
		return hdfsDao.downloadFile(path);
	}

	@Override
	public boolean makeDir(String curPath, String folder) {
		return hdfsDao.makeDir(curPath, folder);
	}

	@Override
	public HdfsFolder showDirTree(String username) {
		HdfsFolder root = hdfsDao.listTree(username);
		changeState(root);
		return root;
	}

	private void changeState(HdfsFolder root) {
		if (root == null)
			return;
		if (root.getChildren().isEmpty()) {
			root.setState("open");
		}
		List<HdfsFolder> list = root.getChildren();
		for (HdfsFolder folder : list) {
			changeState(folder);
		}
	}

	@Override
	public boolean moveFile(String src, String dst, String motion) {
		boolean flag = false;
		if ("copy".equals(motion)) {
			flag = hdfsDao.copyFile(src, dst);
		} else if ("move".equals(motion)) {
			flag = hdfsDao.moveFile(src, dst);
		}
		return flag;
	}

}
