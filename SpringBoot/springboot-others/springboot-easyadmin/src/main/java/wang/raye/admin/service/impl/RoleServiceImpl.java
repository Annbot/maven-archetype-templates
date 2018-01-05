package wang.raye.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wang.raye.admin.model.Role;
import wang.raye.admin.model.RoleCriteria;
import wang.raye.admin.model.mapper.RoleMapper;
import wang.raye.admin.model.mapper.RoleMenuMapper;
import wang.raye.admin.model.mapper.UserRoleMapper;
import wang.raye.admin.service.RoleService;
import wang.raye.admin.utils.StringUtils;

/**
 * 角色相关业务接口实现类 Created by Raye on 2017/3/18.
 */
@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper mapper;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private RoleMenuMapper roleMenuMapper;

	@Override
	public List<Role> select(int page, int pageSize, String query) {
		return mapper
				.select((page - 1) * pageSize, pageSize, "%" + query + "%");
	}

	@Override
	public int selectCount(String query) {
		RoleCriteria criteria = new RoleCriteria();
		criteria.createCriteria().andNameLike("%" + query + "%");
		return mapper.countByExample(criteria);
	}

	@Override
	public Role selectById(int id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean insert(Role role) {
		return mapper.insertSelective(role) > 0;
	}

	@Override
	public boolean update(Role role) {
		return mapper.updateByPrimaryKeySelective(role) > 0;
	}

	@Transactional
	@Override
	public boolean delete(String id) {
		String[] ids = id.split(",");
		for (String temp : ids) {
			if (StringUtils.isEmpty(temp)) {
				continue;
			}
			mapper.deleteByRoleid(temp);
			roleMenuMapper.deleteByRoleid(temp);
			userRoleMapper.deleteByRoleid(temp);
		}
		return true;
	}
}
