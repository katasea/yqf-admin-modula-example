package cn.business.bean.po;

import cn.business.auto.Column;
import cn.business.auto.Table;

import java.io.Serializable;

/**
 * application information record
 *
 * @author katasea
 */
@Table(name = "MCM_APPLICATION")
public class Application implements Serializable{

	private static final long serialVersionUID = -3659005483062971583L;

	@Column(flag = "primary",type = "varchar2(32)")
	private String appId;
	@Column(type = "varchar2(200)")
	private String appName;
	@Column(type = "varchar2(64)")
	private String appSecret;
	@Column
	private String createTime;
	@Column(type = "varchar2(32)")
	private String createUserId;
	@Column
	private String updateTime;
	@Column
	private String appType;
	@Column(type = "int")
	private String enable;
	@Column(type = "varchar2(600)")
	private String extra;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}


	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}
}
