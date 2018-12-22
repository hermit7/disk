package com.ise.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import com.ise.dao.conn.HdfsConn;
import com.ise.pojo.BreadCrumb;
import com.ise.pojo.HdfsFolder;

public class PathUtil {
	static FileSystem fs = HdfsConn.getFileSystem();

	public static void main(String[] args) throws Exception {
		HdfsFolder tree = listFolder("/");
		printTree(tree);
	}

	public static void printTree(HdfsFolder folder) {
		if (folder == null) {
			return;
		}
		System.out.println(folder.getText());
		List<HdfsFolder> list = folder.getChildren();
		if (list != null) {
			for (HdfsFolder f : list) {
				printTree(f);
			}
		}
	}

	public static List<BreadCrumb> getBreads(String path) {
		ArrayList<BreadCrumb> list = new ArrayList<>();
		// path = path.replaceAll("^/[a-z]+/[a-z]+", "");
		path = path.substring(1);
		String[] split = path.split("/");
		StringBuffer sb = new StringBuffer("/" + split[0] + "/" + split[1]);
		for (int i = 2; i < split.length; i++) {
			BreadCrumb crumb = new BreadCrumb();
			sb.append("/");
			sb.append(split[i]);
			crumb.setFolderPath(sb.toString());
			crumb.setFolderName(split[i]);
			list.add(crumb);
		}

		return list;
	}
	
	public static List<BreadCrumb> getBreadsOfAdmin(String path) {
		ArrayList<BreadCrumb> list = new ArrayList<>();
		// path = path.replaceAll("^/[a-z]+/[a-z]+", "");
		path = path.substring(1);
		String[] split = path.split("/");
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < split.length; i++) {
			BreadCrumb crumb = new BreadCrumb();
			sb.append("/");
			sb.append(split[i]);
			crumb.setFolderPath(sb.toString());
			crumb.setFolderName(split[i]);
			list.add(crumb);
		}
		
		return list;
	}

	public static String formatPath(String root, String name) {
		String result = "";
		if ("/".equals(root)) {
			result = root + name;
		} else {
			result = root + "/" + name;
		}
		return result;
	}

	// 找出当前根目录下所有的文件夹
	public static HdfsFolder listFolder(String curRoot) {
		HdfsFolder root = null;
		HashMap<String, String> attr = null;
		try {
			root = new HdfsFolder();

			FileStatus[] listStatus = fs.listStatus(new Path(curRoot));
			attr = new HashMap<>();
			attr.put("url", curRoot);
			root.setText("全部文件");
			root.setChecked(true);
			root.setAttributes(attr);
			ArrayList<HdfsFolder> children = new ArrayList<>();
			HdfsFolder tmp = null;
			for (FileStatus file : listStatus) {
				if (file.isDirectory()) {
					tmp = new HdfsFolder();
					attr = new HashMap<>();
					tmp.setText(file.getPath().getName());
					attr.put("url", file.getPath().toString());
					tmp.setAttributes(attr);
					tmp.setState("closed");
					tmp.setChecked(false);
					listFolderByRecursion(file.getPath().toString(), tmp);
					children.add(tmp);
				}
			}
			root.setChildren(children);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return root;
	}

	private static void listFolderByRecursion(String path, HdfsFolder hdfsFolder) {
		ArrayList<HdfsFolder> children = new ArrayList<>();
		try {
			FileStatus[] listStatus = fs.listStatus(new Path(path));
			HdfsFolder tmp = null;
			HashMap<String, String> attr = null;
			for (FileStatus file : listStatus) {
				if (file.isDirectory()) {
					tmp = new HdfsFolder();
					attr = new HashMap<>();
					tmp.setText(file.getPath().getName());
					attr.put("url", file.getPath().toString());
					tmp.setAttributes(attr);
					tmp.setState("closed");
					tmp.setChecked(false);
					listFolderByRecursion(file.getPath().toString(), tmp);
					children.add(tmp);
				}
			}
			hdfsFolder.setChildren(children);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
