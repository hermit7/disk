package com.ise.dao.impl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;
import org.springframework.stereotype.Repository;

import com.ise.dao.HdfsDao;
import com.ise.dao.conn.HdfsConn;
import com.ise.pojo.HFile;
import com.ise.pojo.HdfsFolder;
import com.ise.util.MyFileUtil;
import com.ise.util.PathUtil;

@Repository("hdfsDao")
public class HdfsDaoImpl implements HdfsDao {

	private static FileSystem fs = HdfsConn.getFileSystem();

	public static void main(String[] args) throws FileNotFoundException {
		HdfsDao dao = new HdfsDaoImpl();

		List<HFile> list = dao.listFiles("/");
		for (HFile file : list) {
			System.out.println(file.toString());
		}
	}

	/**
	 * 展示该目录下所有文件
	 * 
	 * @return
	 */
	public List<HFile> listFiles(String path) {
		ArrayList<HFile> list = new ArrayList<>();
		HFile file = null;

		try {
			FileStatus[] listStatus = fs.listStatus(new Path(path));
			for (FileStatus status : listStatus) {
				file = new HFile();
				Path p = status.getPath();
				String name = p.getName();
				file.setName(name);
				file.setPath(p.toString().replaceAll("^.+://.+:[0-9]+/", "/"));
				file.setTime(MyFileUtil.fileTimeFormat(status.getModificationTime()));
				file.setSize(MyFileUtil.fileSizeFormat(status.getLen()));
				if (status.isDirectory()) {
					file.setType("d");
				} else {
					file.setType(MyFileUtil.getFileType(name));
				}
				list.add(file);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 新建文件夹
	 */
	public void mkdir() {

	}

	/**
	 * 上传文件
	 */
	@Override
	public boolean uploadFile(InputStream in, String path, String name) {
		String dest = PathUtil.formatPath(path, name);
		Path dst = new Path(dest);
		boolean flag = true;
		try {
			OutputStream os = fs.create(dst, new Progressable() {
				@Override
				public void progress() {

				}
			});
			// len = IOUtils.copy(in, os);
			IOUtils.copyBytes(in, os, 2048, true);
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean modifyFile(String curPath, String originName, String destName) {
		boolean flag = false;
		Path src = new Path(PathUtil.formatPath(curPath, originName));
		Path dst = new Path(PathUtil.formatPath(curPath, destName));
		try {
			flag = fs.rename(src, dst);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean downloadFile(String path) {
		boolean flag = true;
		try {
			fs.copyToLocalFile(new Path(path), new Path("d://tmp/"));
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean deleteFile(String path) {
		boolean flag = true;
		Path p = new Path(path);
		try {
			if (fs.exists(p)) {
				flag = fs.delete(p, true);
			}
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean makeDir(String curPath, String folder) {
		boolean flag = true;
		String path = PathUtil.formatPath(curPath, folder);
		try {
			flag =  fs.mkdirs(new Path(path));
		}  catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public HdfsFolder listTree() {
		return PathUtil.listFolder();
	}

	@Override
	public boolean copyFile(String src, String dst) {
		boolean flag = false;
		try {
			flag = FileUtil.copy(HdfsConn.getFileSystem(), new Path(src), 
					HdfsConn.getFileSystem(), new Path(dst), false, HdfsConn.getConfiguration());
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean moveFile(String src, String dst) {
		boolean flag = false;
		try {
			flag = FileUtil.copy(HdfsConn.getFileSystem(), new Path(src), 
					HdfsConn.getFileSystem(), new Path(dst), true, HdfsConn.getConfiguration());
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
