package com.edm.gumall.member.dao;

import com.edm.gumall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author Edmund
 * @email 609031809@qq.com
 * @date 2023-03-30 22:49:31
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
