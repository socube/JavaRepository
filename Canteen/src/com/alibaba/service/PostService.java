package com.alibaba.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.entity.Post;

/**
 * @author Bill
 * @title: PostService.java
 * @Package: com.alibaba.service
 * @Description: TODO
 * @date:2018年7月12日 下午3:32:20
 * @version:V1.0
 */
@Service
public interface PostService {

	/**
	 * 方法名：post</br> 
	 * 详述：处理用户发帖</br> 
	 * 开发人员：Bill </br> 
	 * 创建时间：2018年7月12日 </br> 
	 * @param title 
	 * @param content 
	 * @param userId 
	 * @throws
	 */
	void post(String title, String content, int userId);

	/**
	 * 方法名：queryAndOrderPosts</br> 
	 * 详述：将从数据库中取出的帖子按照时间先后顺序排列</br> 
	 * 开发人员：Bill </br>
	 * 创建时间：2018年7月12日 </br> 
	 * @return 
	 * @throws
	 */
	List<Post> queryAndOrderPosts();

	/**
	 * 方法名：commentPost</br> 
	 * 详述：TODO（简单方法可一句话概述）</br> 
	 * 开发人员：Bill </br> 
	 * 创建时间：2018年7月15日 </br> 
	 * @param content 
	 * @param userId 
	 * @param postId 
	 * @throws
	 */
	void commentPost(String content, int userId, int postId);

	/**
	 * 方法名：praise</br> 
	 * 详述：用戶點贊帖子</br> 
	 * 开发人员：Bill </br> 
	 * 创建时间：2018年7月15日 </br> 
	 * @param postId 
	 * @throws
	 */
	boolean praise(int postId,int userId);

}
