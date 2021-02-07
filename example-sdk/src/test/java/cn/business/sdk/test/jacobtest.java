package cn.business.sdk.test;


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * 文字转语音测试 jdk bin文件中需要导入jacob-1.18-x64.dll
 *
 * @author zk
 * @date: 2019年6月25日 上午10:05:21
 */
public class jacobtest {

	public static void main(String[] args) {
		jacobtest.textToSpeech("1.登录百度AI开放平台注册一个语音服务应用。在开始编码前我们需新建一个和语音服务有...\n" +
				"2.获取access_token信息。后台使用的接口还需要先获取access_...\n" +
				"3.创建项目并引入Maven依赖包。在完成前期准备工作后我们可以将需要的依赖包引入到项目中,...\n" +
				"4.创建文字语音转换工具类。现在我们可以开始编写具体的实现类了,流程如下");
	}
	/**
	 * 语音转文字并播放
	 *
	 * @param text
	 */
	public static void textToSpeech(String text) {
		ActiveXComponent ax = null;
		try {
			ax = new ActiveXComponent("Sapi.SpVoice");

			// 运行时输出语音内容
			Dispatch spVoice = ax.getObject();
			// 音量 0-100
			ax.setProperty("Volume", new Variant(100));
			// 语音朗读速度 -10 到 +10
			ax.setProperty("Rate", new Variant(0));
			// 执行朗读
			Dispatch.call(spVoice, "Speak", new Variant(text));

			// 下面是构建文件流把生成语音文件

			ax = new ActiveXComponent("Sapi.SpFileStream");
			Dispatch spFileStream = ax.getObject();

			ax = new ActiveXComponent("Sapi.SpAudioFormat");
			Dispatch spAudioFormat = ax.getObject();

			// 设置音频流格式
			Dispatch.put(spAudioFormat, "Type", new Variant(22));
			// 设置文件输出流格式
			Dispatch.putRef(spFileStream, "Format", spAudioFormat);
			// 调用输出 文件流打开方法，创建一个.wav文件
			Dispatch.call(spFileStream, "Open", new Variant("./text.wav"), new Variant(3), new Variant(true));
			// 设置声音对象的音频输出流为输出文件对象
			Dispatch.putRef(spVoice, "AudioOutputStream", spFileStream);
			// 设置音量 0到100
			Dispatch.put(spVoice, "Volume", new Variant(100));
			// 设置朗读速度
			Dispatch.put(spVoice, "Rate", new Variant(0));
			// 开始朗读
			Dispatch.call(spVoice, "Speak", new Variant(text));

			// 关闭输出文件
			Dispatch.call(spFileStream, "Close");
			Dispatch.putRef(spVoice, "AudioOutputStream", null);

			spAudioFormat.safeRelease();
			spFileStream.safeRelease();
			spVoice.safeRelease();
			ax.safeRelease();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}