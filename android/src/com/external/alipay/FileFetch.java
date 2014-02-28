/**
 * Alipay.com Inc.
 * Copyright (c) 2005-2009 All Rights Reserved.
 */
package com.external.alipay;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * 
 * @author jianmin.jiang
 * 
 * @version $Id: FileFetch.java, v 0.1 2012-2-14 下午8:55:40 jianmin.jiang Exp $
 */
final class FileFetch implements Runnable {

	private String fileUrl;
	private String savePath;
	private FileDownloader downloader;
	private boolean stop = false;
	private long fileStart;
	private long fileEnd;

	public FileFetch(String fileUrl, String savePath, FileDownloader downloader) {
		this.fileUrl = fileUrl;
		this.savePath = savePath;
		this.downloader = downloader;
	}

	public void run() {
		if (downloader.showProgress()) {
			if (fileEnd <= 0 || fileStart >= fileEnd) {
				this.stop = true;
				return;
			}
		}
		boolean canStop = false;
		FileAccess fileAccess = new FileAccess();
		while (!this.stop) {
			InputStream input = null;
			int responseCode = 0;
			try {
				try {
					HttpGet httpGet = new HttpGet(fileUrl);
					// 取得HttpClient
					HttpClient httpClient = new DefaultHttpClient();
					if (downloader.showProgress()) {
						String property = "bytes=" + fileStart + "-" + fileEnd;
						// conn.setRequestProperty("RANGE", property);
						httpGet.addHeader("Range", property);
					}
					// 请求HttpClient，获得HttpResponce
					HttpResponse response = httpClient.execute(httpGet);
					// 请求成功
					responseCode = response.getStatusLine().getStatusCode();
					switch (responseCode) {
					case HttpStatus.SC_OK:
					case HttpStatus.SC_CREATED:
					case HttpStatus.SC_ACCEPTED:
					case HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION:
					case HttpStatus.SC_NO_CONTENT:
					case HttpStatus.SC_RESET_CONTENT:
					case HttpStatus.SC_PARTIAL_CONTENT:
					case HttpStatus.SC_MULTI_STATUS:
						input = response.getEntity().getContent();
						break;
					default:
						this.stop = true;
						break;
					}
					if (this.stop) {
						break;// 返回失败结果，不需要再下载文件
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (input == null) {
					continue;
				}
				int size;
				byte[] buffer = new byte[1024];
				do {
					size = input.read(buffer, 0, buffer.length);
					if (size != -1) {
						this.fileStart += fileAccess.write(buffer, 0, size);
						this.downloader.writeTempFile();
					}
					canStop = downloader.showProgress() ? fileStart < fileEnd
							: true;
					canStop = !stop && canStop;
				} while (size > -1 && canStop);
				this.stop = true;
			} catch (SocketTimeoutException e) {
				if (responseCode == 0) {
					this.stop = true;
				}
			} catch (IOException e) {
				this.stop = true;
			} catch (Exception e) {
				this.stop = true;
			} finally {
				if (input != null)
					try {
						input.close();
					} catch (Exception e) {
					}
			}
		}
		fileAccess.close();
	}

	public final long getFileStart() {
		return fileStart;
	}

	public final void setFileStart(long fileStart) {
		this.fileStart = fileStart;
	}

	public final long getFileEnd() {
		return fileEnd;
	}

	public final void setFileEnd(long fileEnd) {
		this.fileEnd = fileEnd;
	}

	public final boolean isStop() {
		return this.stop;
	}

	public final void stop() {
		stop = true;
	}

	final class FileAccess {

		private FileOutputStream outStream;

		public FileAccess() {
			try {
				/**
				 * 只能保存在程序的files目录下。如果放在files的子文件夹下，会出现读取权限的问题
				 * 第二个参数设置为true，表示可以追加数据，实现断点续传。
				 * 
				 * 在此处写入的文件是不可读的，会在下载完成后改成可读。
				 */
				outStream = new FileOutputStream(savePath, true);
			} catch (FileNotFoundException e) {
				// 在以上bean.createFile()中已创建文件，不会产生这个例外
				e.printStackTrace();
			}
		}

		public synchronized int write(byte[] b, int start, int len)
				throws IOException {
			outStream.write(b, start, len);
			return len;
		}

		public void close() {
			try {
				outStream.close();
			} catch (Exception e) {
			}
		}
	}
}
