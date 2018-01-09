package cn.e3mall.fastdfs;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import cn.e3mall.common.utils.FastDFSClient;

public class TestFastDFS {
	@Test
	public void testUpload() throws  Exception{
		// 1.创建一个配置文件，配置TrackerServer的IP地址及端口号
		// 2.加载配置文件
		ClientGlobal.init("D:/Java就业班/e3/e3-manager-web/src/main/resources/conf/client.conf");
		// 3.创建一个TrackerClient对象 直接new
		TrackerClient trackerClient = new TrackerClient();
		// 4.使用TrackerClient获得一个TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		// 5.创建一个StorageClient对象 需要连个参数。 TrackerServer 对象 StorageServer 对象
		StorageServer storageServer = null;
		StorageClient storageClient =new StorageClient(trackerServer, storageServer);
		// 6.StorageClient对象上传对象
		//参数一.文件路径及文件名    参数二  不带点的文件扩展名   参数三  元数据(文件介绍信息  不设置 null
		String[] strings = storageClient.upload_file("F:/壁纸/新建文件夹 (2)/2.jpg", "jpg", null);
		for (String string : strings) {
			System.out.println(string);
		}
	}
	
	@Test
	public void testUploadUtil() throws Exception{
		FastDFSClient client = 
				new FastDFSClient("D:/Java就业班/e3/e3-manager-web/src/main/resources/conf/client.conf");
		String uploadFile = 
				client.uploadFile("F:/壁纸/新建文件夹 (2)//138-140Q2134K4.jpg");
		System.out.println(uploadFile);
		
		
	}
	
	
}
