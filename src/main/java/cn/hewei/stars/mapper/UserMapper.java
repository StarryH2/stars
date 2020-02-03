package cn.hewei.stars.mapper;

import cn.hewei.stars.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;



/**
 * @Author 何为
 * @Daet 2020-02-02 17:43
 * @Description
 */
@Mapper
public interface UserMapper {

    @Insert("insert into USER (ACCOUNT_ID,NAME,TOKEN,GMT_CREATE,GMT_MODIFIED) VALUES (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

}
