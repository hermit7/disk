package com.ise.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ise.pojo.BreadCrumb;
import com.ise.pojo.HFile;
import com.ise.pojo.HdfsFolder;
import com.ise.pojo.User;
import com.ise.service.FileService;
import com.ise.util.PathUtil;

@Controller
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;

	@RequestMapping("/list")
	public String listFiles(HttpSession session, @RequestParam(value = "path", defaultValue = "/") String path,
			Model model) {
		try {
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		User user = (User) session.getAttribute("user");
		List<BreadCrumb> breads = null;
		if ("0".equals(user.getType())) {
			breads = PathUtil.getBreadsOfAdmin(path);
		} else {
			if(!path.contains("/disk/")) {
				path = "/disk/" + user.getUsername();
			}
			breads = PathUtil.getBreads(path);
		}
		List<HFile> files = fileService.listFiles(path);
		model.addAttribute("fileList", files);
		model.addAttribute("breadlist", breads);
		model.addAttribute("currentPath", path);
		// System.out.println("当前路径:"+path);
		return "/jsp/filelist.jsp";
	}

	@RequestMapping(value = "/enterFoder")
	public String enterFoder(HttpSession session, String path, Model model) {
		try {
			path = URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		User user = (User) session.getAttribute("user");
		List<BreadCrumb> breads = null;
		if ("0".equals(user.getType())) {
			breads = PathUtil.getBreadsOfAdmin(path);
		} else {
			if(!path.contains("/disk/")) {
				path = "/disk/" + user.getUsername();
			}
			breads = PathUtil.getBreads(path);
		}
		List<HFile> files = fileService.listFiles(path);
		model.addAttribute("fileList", files);
		model.addAttribute("breadlist", breads);
		model.addAttribute("currentPath", path);
		return "/jsp/receive.jsp";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public boolean uploadFile(@RequestParam(value = "filename") MultipartFile file,
			@RequestParam(value = "curPath") String curPath, Model model) {
		boolean flag = false;
		if (file == null) {
			System.out.println("file is empty");
		}
		// System.out.println("上传路径:"+ curPath);
		long size = file.getSize() / 65536;
		String name = file.getOriginalFilename();
		// System.out.println("文件名:"+name);
		try {
			curPath = URLDecoder.decode(curPath, "UTF-8");
			flag = fileService.uploadFile(file.getInputStream(), curPath, name, size);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile2(HttpServletResponse response, @RequestParam(value = "filename") CommonsMultipartFile file,
			@RequestParam(value = "curPath") String curPath, Model model) {
		boolean flag = false;
		PrintWriter out = null;
		if (file == null) {
			System.out.println("file is empty");
		}
		// System.out.println("上传路径:"+ curPath);
		long size = file.getSize() / 65536;
		String name = file.getOriginalFilename();
		// System.out.println("文件名:"+name);
		try {
			curPath = URLDecoder.decode(curPath, "UTF-8");
			flag = fileService.uploadFile(file.getInputStream(), curPath, name, size);
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (out != null) {
			if (flag == true) {
				out.print("1");
			} else {
				out.print("2");
			}
		}
	}

	@RequestMapping(value = "/modify")
	@ResponseBody
	public boolean modifyFile(String curPath, String originName, String destName) {
		return fileService.modifyFile(curPath, originName, destName);
	}

	@RequestMapping(value = "/delete")
	@ResponseBody
	public boolean deleteFile(String path) {
		return fileService.deleteFile(path);
	}

	@RequestMapping(value = "/mkdir")
	@ResponseBody
	public boolean makeDir(String curPath, String folder) {
		return fileService.makeDir(curPath, folder);
	}

	@RequestMapping(value = "/download")
	@ResponseBody
	public boolean downloadFile(String path) {
		return fileService.downloadFile(path);
	}

	/***
	 * 
	 * @param model
	 * @param originName 文件名
	 * @param curPath    curPath/originName 原路径
	 * @param motion     要执行的操作 移动还是辅助
	 * @return
	 */
	@RequestMapping(value = "/dirTree")
	public String showDirTreeUI(Model model, String originName, String curPath, String motion) {
		try {
			originName = URLDecoder.decode(originName, "UTF-8");
			curPath = URLDecoder.decode(curPath, "UTF-8");
			model.addAttribute("originName", originName);
			model.addAttribute("curPath", curPath);
			model.addAttribute("motion", motion);
			// System.out.println(originName + " " + curPath + " " + motion);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "/jsp/dirtree.jsp";
	}

	@RequestMapping(value = "/showDirTree")
	@ResponseBody
	public List<HdfsFolder> showDirTree(HttpSession session) {
		List<HdfsFolder> list = new ArrayList<>();
		User user = (User) session.getAttribute("user");
		list.add(fileService.showDirTree(user.getUsername()));
		return list;
	}

	@RequestMapping(value = "/moveFile")
	@ResponseBody
	public boolean moveFile(String originName, String curPath, @RequestParam(defaultValue = "/") String dst,
			String motion) {
		// System.out.println("移动文件" + originName + " " + curPath + " " + motion);
		String src = PathUtil.formatPath(curPath, originName);
		return fileService.moveFile(src, dst, motion);
	}

}
