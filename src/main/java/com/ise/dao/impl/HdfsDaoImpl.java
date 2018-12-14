package com.ise.dao.impl;

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

import com.ise.constant.Constants;
import com.ise.dao.HdfsDao;
import com.ise.dao.conn.HdfsConn;
import com.ise.pojo.HFile;
import com.ise.pojo.HdfsFolder;
import com.ise.util.MyFileUtil;
import com.ise.util.PathUtil;

@Repository("hdfsDao")
public class HdfsDaoImpl implements HdfsDao {

	private static FileSystem fs = HdfsConn.getFileSystem();

	public static void main(String[] args) throws Exception {
		Path path = new Path("/disk/lzy/Git-2.18.0-64-bit.exe");
		boolean b = fs.exists(path);
		System.out.println(path.toString());
		if (b) {
			FileStatus listXAttrs = fs.getFileStatus(path);
			System.out.println(listXAttrs);
		} else {
			System.out.println("123124");
		}
	}
	
	public HFile getFileStatus(String path) {
		HFile file = new HFile();
		Path p = new Path(path);
		try {
			FileStatus status = fs.getFileStatus(p);
			String name = p.getName();
			file.setName(name);
			file.setSize(MyFileUtil.fileSizeFormat(status.getLen()));
			file.setPath(path.toString());
			if (status.isDirectory()) {
				file.setType("d");
			} else {
				file.setType(MyFileUtil.getFileType(name));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	
	public boolean isPathExist(String path) {
		try {
			return fs.exists(new Path(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 展示该目录下所有文件
	 * 
	 * @return
	 */
	public List<HFile> listFiles(String path) {
		Path userRoot = new Path(path);
		ArrayList<HFile> list = new ArrayList<>();
		HFile file = null;

		try {
			FileStatus[] listStatus = fs.listStatus(userRoot);
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

	/**
	 * 用户新建文件夹
	 */
	@Override
	public boolean makeDir(String curPath, String folder) {
		boolean flag = true;
		String path = PathUtil.formatPath(curPath, folder);
		try {
			flag = fs.mkdirs(new Path(path));
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 新建用户根目录
	 */
	public void makeDir(String root) {
		Path path = new Path(Constants.NETPAN_ROOT + root);
		try {
			boolean exists = fs.exists(path);
			if (!exists) {
				fs.mkdirs(path);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public HdfsFolder listTree(String username) {
		String userRoot = Constants.NETPAN_ROOT + username;
		return PathUtil.listFolder(userRoot);
	}

	@Override
	public boolean copyFile(String src, String dst) {
		boolean flag = false;
		try {
			flag = FileUtil.copy(HdfsConn.getFileSystem(), new Path(src), HdfsConn.getFileSystem(), new Path(dst),
					false, HdfsConn.getConfiguration());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean moveFile(String src, String dst) {
		boolean flag = false;
		try {
			flag = FileUtil.copy(HdfsConn.getFileSystem(), new Path(src), HdfsConn.getFileSystem(), new Path(dst), true,
					HdfsConn.getConfiguration());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
