package cn.bugstack.xfg.dev.tech.infrastructure.dao;

import cn.bugstack.xfg.dev.tech.infrastructure.dao.po.OauthAccount;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IOauthAccountDao {

    OauthAccount loadUserByUsername(String clientId, String userName);

}
