package com.tencent.weibo.sdk.android.network;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class HttpService {
	private final int TAG_RUNNING = 1;//请求线程真正运行
	private final int TAG_WAITTING = 0;//请求线程出错
	private List<HttpReq> mWaitReqList = null; //等待线程存储容器
	private List<HttpReq> mRunningReqList = null;//正在运行线程存储容器
	
	private static HttpService instance = null;
	private int mThreadNum = 4;  //自大运行线程数量
	/**
	 * 获取可用发送请求对象实例方法
	 * @return 返回可用HttpService对象
	 * 
	 * */
	public static HttpService getInstance()
	{
		if(instance == null)
		{
			instance = new HttpService();
		}
		return instance;
	} 
	/**
	 * 构造方法
	 * */
	private HttpService()
	{
		mWaitReqList = new LinkedList<HttpReq>();
		mRunningReqList = new LinkedList<HttpReq>();
	}
	/**
	 * 添加请求对象设置请求级别
	 * @param req 请求对象
	 * */
	public void addImmediateReq(HttpReq req)
	{
		req.setServiceTag(TAG_RUNNING);
		mRunningReqList.add(req);
		req.execute();
	}
	/**
	 * 添加请求对象设置请求级别
	 * @param req 请求对象
	 * */
	public void addNormalReq(HttpReq req){
	
		if(mRunningReqList.size() < mThreadNum)
		{
			req.setServiceTag(TAG_RUNNING);
			mRunningReqList.add(req);
			req.execute();
		}
		else
		{
			req.setServiceTag(TAG_WAITTING);
			mWaitReqList.add(req);
		}
	}
	/**
	 * 清除错误请求
	 * @param req 请求对象
	 * */
	public void cancelReq(HttpReq req)
	{
		if(req.getServiceTag() == TAG_RUNNING)
		{
			for( Iterator<HttpReq> it = mRunningReqList.iterator(); it.hasNext(); )
			{
				HttpReq oneReq = it.next();
				if(oneReq == req)
				{
					req.cancel(true);
					mRunningReqList.remove(req);
				}
			}
		}
		else if(req.getServiceTag() == TAG_WAITTING)
		{
			//TODO: 应该调用cancel(true)呢，还是直接从waittinglist中删除
			for(Iterator<HttpReq> it = mWaitReqList.iterator();it.hasNext();){
				if(req==it.next()){
					mWaitReqList.remove(req);
				}
			}
		}
	}
	/**
	 * 
	 * 清除所有请求
	 * 
	 */
	public void cancelAllReq()
	{
		for( Iterator<HttpReq> it = mRunningReqList.iterator(); it.hasNext(); )
		{
			HttpReq oneReq = (HttpReq)it.next();
			oneReq.cancel(true);
		}
		mWaitReqList.clear();
	}
	
	public void SetAsynchronousTaskNum(int n)
	{
		
	}
	/**
	 *判断请求是否完成
	 * @param req 请求对象
	 * */
	public void onReqFinish(HttpReq req)
	{
		boolean successDelete = false;
		for( Iterator<HttpReq> it = mRunningReqList.iterator(); it.hasNext(); )
		{
			HttpReq oneReq = it.next();
			if(req == oneReq)
			{
				it.remove();
				successDelete = true;
				break;
			}
		}
		
		if(successDelete == false)
		{
			//TODO:不应该进入
		}
		
		if(mWaitReqList.size() > 0 && mRunningReqList.size() < mThreadNum)
		{
			Iterator<HttpReq> it = mWaitReqList.iterator();
			HttpReq oneReq = it.next();
			it.remove();
			oneReq.setServiceTag(TAG_RUNNING);
			mRunningReqList.add(oneReq);
			oneReq.execute();
		}
	}
}
