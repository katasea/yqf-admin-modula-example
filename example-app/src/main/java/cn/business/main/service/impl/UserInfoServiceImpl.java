package cn.business.main.service.impl;

import cn.business.bean.po.UserInfo;
import cn.business.main.mapper.UserInfoMapper;
import cn.business.main.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author katasea
 * 2020/3/30 16:15
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
}
