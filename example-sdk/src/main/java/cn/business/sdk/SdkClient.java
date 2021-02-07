package cn.business.sdk;

import cn.business.bean.vo.RequestVO;
import cn.business.bean.vo.ResponseVO;
import cn.business.exception.PayBusinessException;
import cn.business.myenum.EncryptType;
import cn.business.myenum.SignType;
import cn.business.sdk.service.SdkSrv;

import java.util.Map;

/**
 * @author katasea
 * @date 2019/5/23 10:57
 */
public class SdkClient {
	/**
	 * 应用id
	 */
	private String appId;

	/**
	 * 应用安全key
	 */
	private String appSecret;

	/**
	 * 签名类型
	 */
	private String signType;

	/**
	 * 加密类型
	 */
	private String encryptType;

	/**
	 * 处理服务
	 */
	private SdkSrv sdkSrv;

	//=============================构造方法==========================//

	public SdkClient(String AioCloudSrv, String appId, String appSecret) {
		super();
		this.appId = appId;
		this.appSecret = appSecret;
		this.signType = SignType.MD5.toString();
		this.encryptType = EncryptType.DES.toString();
		sdkSrv = new SdkSrv(AioCloudSrv, appId, appSecret, signType, encryptType);
	}

	public SdkClient(String AioCloudSrv, String appId, String appSecret, String signType, String encryptType) {
		super();
		this.appId = appId;
		this.appSecret = appSecret;
		this.signType = signType;
		this.encryptType = encryptType;
		sdkSrv = new SdkSrv(AioCloudSrv, appId, appSecret, signType, encryptType);
	}

	public SdkClient(String appId, String appSecret, String signType, String encryptType) {
		super();
		this.appId = appId;
		this.appSecret = appSecret;
		this.signType = signType;
		this.encryptType = encryptType;
		sdkSrv = new SdkSrv(appId, appSecret, signType, encryptType);
	}

	public String getAppId() {
		return appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public String getSignType() {
		return signType;
	}

	public String getEncryptType() {
		return encryptType;
	}

	//==================================对外服务===============================//

	/**
	 * 统一服务调用入口
	 * @param method 方法名
	 * @param param 业务入参
	 * @return
	 * @throws PayBusinessException
	 */
	public ResponseVO unifyCall(String method,String version,Map<String,Object> param) throws PayBusinessException {
		return sdkSrv.unifyCall(method,version,param);
	}


	/**
	 * 支付下单
	 */
//	public ResponseVO<ChargeResponse> charge(ChargeRequest chargeRequest) throws PayBusinessException {
//		return aioCloudSrv.charge(chargeRequest);
//	}

}
