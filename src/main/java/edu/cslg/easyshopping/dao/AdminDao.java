package edu.cslg.easyshopping.dao;

import edu.cslg.easyshopping.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminDao {
    /**
     * 通过登录验证管理员身份
     * @param loginId 登录id
     * @param pwd 密码
     * @return 符合的管理员
     */
    Admin getAdminByLogin(@Param(value = "loginId") String loginId, @Param(value = "pwd") String pwd);
}
