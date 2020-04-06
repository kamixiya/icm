-- 插入权限
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'AUTHORITY_CREATE', '创建权限', '权限管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'AUTHORITY_RETRIEVE', '查询权限', '权限管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'AUTHORITY_UPDATE', '修改权限', '权限管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'AUTHORITY_DELETE', '删除权限', '权限管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'USER_CREATE', '创建用户', '用户管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'USER_RETRIEVE', '查询用户', '用户管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'USER_UPDATE', '修改用户', '用户管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'USER_DELETE', '删除用户', '用户管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'ROLE_CREATE', '创建角色', '角色管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'ROLE_RETRIEVE', '查询角色', '角色管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'ROLE_UPDATE', '修改角色', '角色管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'ROLE_DELETE', '删除角色', '角色管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'LOG_RETRIEVE', '查询日志', '日志管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'UNIT_CREATE', '创建公司', '公司管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'UNIT_RETRIEVE', '查询公司', '公司管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'UNIT_UPDATE', '修改公司', '公司管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'UNIT_DELETE', '删除公司', '公司管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'UNIT_LEADER', '公司领导', '公司管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'DEPARTMENT_CREATE', '创建部门', '部门管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'DEPARTMENT_RETRIEVE', '查询部门', '部门管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'DEPARTMENT_UPDATE', '修改部门', '部门管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'DEPARTMENT_DELETE', '删除部门', '部门管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'POSITION_CREATE', '创建岗位', '岗位管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'POSITION_RETRIEVE', '查询岗位', '岗位管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'POSITION_UPDATE', '修改岗位', '岗位管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'POSITION_DELETE', '删除岗位', '岗位管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'EMPLOYEE_CREATE', '创建员工', '员工管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'EMPLOYEE_RETRIEVE', '查询员工', '员工管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'EMPLOYEE_UPDATE', '修改员工', '员工管理' FROM zj_sys_authority;
INSERT INTO zj_sys_authority (`id`, `created_by`, `created_time`, `filter_path`, `last_modified_by`, `last_modified_time`, `code`, `name`, `type`) SELECT IFNULL(MAX(id),0) + 1, '1', SYSDATE(), '', '1', SYSDATE(), 'EMPLOYEE_DELETE', '删除员工', '员工管理' FROM zj_sys_authority;

-- 角色
INSERT INTO zj_sys_role(id, NAME, remark, created_by, created_time, last_modified_by, last_modified_time)
VALUES(1,'系统管理员', '系统基础数据，组织架构及权限管理员', 1, SYSDATE(), 1, SYSDATE());

INSERT INTO zj_sys_role(id, NAME, remark, created_by, created_time, last_modified_by, last_modified_time)
VALUES(2,'系统基础用户', '系统基础数据查询', 1, SYSDATE(), 1, SYSDATE());

-- 授权
INSERT INTO zj_sys_role_authority(role_id, authority_id)
SELECT a.id, b.id FROM zj_sys_role AS a CROSS JOIN zj_sys_authority AS b
WHERE
    a.name = '系统管理员';

INSERT INTO zj_sys_role_authority(role_id, authority_id)
SELECT a.id, b.id FROM zj_sys_role AS a CROSS JOIN zj_sys_authority AS b
WHERE
    a.name = '业务基础用户' AND (
    (b.code not like 'USER_%' or b.code = 'USER_RETRIEVE') and
    b.code not like 'UNIT_%' and
    b.code not like 'DEPARTMENT_%' and
    b.code not like 'POSITION_%' and
    b.code not like 'ORGANIZATION_%'
  );

-- 用户: 初始化密码为!1qAz@2wSx
INSERT INTO zj_sys_user(
  id, account, NAME, PASSWORD, is_enabled, is_local_account, change_pwd_time, password_expire_date, account_expire_date, wrong_pwd_count, last_wrong_pwd_time, remark,
  created_by, created_time, last_modified_by, last_modified_time, filter_path)
VALUES (
         1, 'admin', '系统管理员', '$2a$10$CFt7YlXVdh3PfACTB9GySuDZxQb5FFyNjB8Bcvhfj.y.LyEUuwBte', 1, 1, SYSDATE(), NULL, NULL, 0, NULL, '系统预设的超级管理员账号',
         NULL, SYSDATE(), NULL, SYSDATE(), NULL
       );

INSERT INTO zj_sys_user(
  id, account, NAME, PASSWORD, is_enabled, is_local_account, change_pwd_time, password_expire_date, account_expire_date, wrong_pwd_count, last_wrong_pwd_time, remark,
  created_by, created_time, last_modified_by, last_modified_time, filter_path)
VALUES (
         2, 'susan', '苏三', '$2a$10$j5TDFe5WXMho5ZRh.jsiL.J6imfuaezextxQAfHK.KOJb1106AvSG', 1, 1, SYSDATE(), NULL, NULL, 0, NULL, '系统基础用户',
         NULL, SYSDATE(), NULL, SYSDATE(), NULL
       );

-- 设置角色
INSERT INTO zj_sys_user_role(user_id, role_id)
SELECT a.id, b.id FROM zj_sys_user AS a CROSS JOIN zj_sys_role AS b
WHERE
    a.account = 'admin' AND (b.name='系统管理员');

INSERT INTO zj_sys_user_role(user_id, role_id)
SELECT a.id, b.id FROM zj_sys_user AS a CROSS JOIN zj_sys_role AS b
WHERE
    a.account = 'susan' AND (b.name='系统基础用户');

-- 组织机构
INSERT INTO zj_sys_organization  (
  id , created_by , created_time , filter_path ,
  last_modified_by , last_modified_time , show_order ,
  tree_level , tree_path , is_available , is_interior ,
  NAME , organization_type , short_name , parent_id
) VALUES (
           1, 1, SYSDATE(), '1-',
           1, SYSDATE(), 1,
           1, '1-', TRUE, FALSE,
           '中国国家XX单位', 'UNIT', '中国国家XX单位', NULL
         );

-- 单位
INSERT INTO  zj_sys_unit (
  id,created_by,created_time,filter_path,last_modified_by,last_modified_time,
  is_available,NAME,oa_id,short_name,show_order,organization_id, unit_number
)
SELECT
  o.id, 1, SYSDATE(), o.filter_path , 1, SYSDATE(),
  TRUE AS is_available, o.name, NULL, o.short_name AS short_name , o.show_order,  o.id AS organization_id,
  'XXDW' AS unit_number
FROM
  zj_sys_organization  o WHERE o.organization_type = 'UNIT';
