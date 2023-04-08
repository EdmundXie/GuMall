package com.edm.gumall.thirdparty;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GumallThirdPartyApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Resource
	OSSClient ossClient;
	@Test
	public void testUpload() throws FileNotFoundException {

		// 填写Bucket名称，例如examplebucket。
		String bucketName = "gumall-bucket";

		// 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
		String objectName = "dish.jpeg";
		// 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
		// 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
		String filePath= "/Users/edmundxie/Downloads/PandaPlateImages/3fabb83a-1c09-4fd9-892b-4ef7457daafa.jpeg";


		// 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		try {
			InputStream inputStream = new FileInputStream(filePath);
			// 创建PutObjectRequest对象。
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, objectName, inputStream);
			// 设置该属性可以返回response。如果不设置，则返回的response为空。
			putObjectRequest.setProcess("true");
			// 创建PutObject请求。
			PutObjectResult result = ossClient.putObject(putObjectRequest);
			// 如果上传成功，则返回200。
			System.out.println(result.getResponse().getStatusCode()+"文件上传成功");
		} catch (OSSException oe) {
			System.out.println("Caught an OSSException, which means your request made it to OSS, "
					+ "but was rejected with an error response for some reason.");
			System.out.println("Error Message:" + oe.getErrorMessage());
			System.out.println("Error Code:" + oe.getErrorCode());
			System.out.println("Request ID:" + oe.getRequestId());
			System.out.println("Host ID:" + oe.getHostId());
		} catch (ClientException ce) {
			System.out.println("Caught an ClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with OSS, "
					+ "such as not being able to access the network.");
			System.out.println("Error Message:" + ce.getMessage());
		} finally {
			if (ossClient != null) {
				ossClient.shutdown();
			}
		}
	}
}
