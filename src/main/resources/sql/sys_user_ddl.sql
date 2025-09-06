-- 创建用户表
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    nickname VARCHAR(50),
    avatar VARCHAR(200),
    gender INTEGER DEFAULT 0,
    status INTEGER DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted INTEGER DEFAULT 0,
    remark VARCHAR(200),
    CONSTRAINT uk_user_username UNIQUE (username),
    CONSTRAINT uk_user_email UNIQUE (email),
    CONSTRAINT uk_user_phone UNIQUE (phone)
);

-- 添加注释
COMMENT ON TABLE sys_user IS '用户表';
COMMENT ON COLUMN sys_user.id IS '用户ID';
COMMENT ON COLUMN sys_user.username IS '用户名';
COMMENT ON COLUMN sys_user.password IS '密码';
COMMENT ON COLUMN sys_user.email IS '邮箱';
COMMENT ON COLUMN sys_user.phone IS '手机号';
COMMENT ON COLUMN sys_user.nickname IS '昵称';
COMMENT ON COLUMN sys_user.avatar IS '头像URL';
COMMENT ON COLUMN sys_user.gender IS '性别（0：未知，1：男，2：女）';
COMMENT ON COLUMN sys_user.status IS '状态（0：禁用，1：启用）';
COMMENT ON COLUMN sys_user.create_time IS '创建时间';
COMMENT ON COLUMN sys_user.update_time IS '更新时间';
COMMENT ON COLUMN sys_user.deleted IS '删除标志（0：未删除，1：已删除）';
COMMENT ON COLUMN sys_user.remark IS '备注';

-- 创建索引
CREATE INDEX idx_sys_user_username ON sys_user(username);
CREATE INDEX idx_sys_user_email ON sys_user(email);
CREATE INDEX idx_sys_user_phone ON sys_user(phone);
CREATE INDEX idx_sys_user_status ON sys_user(status);
CREATE INDEX idx_sys_user_gender ON sys_user(gender);
CREATE INDEX idx_sys_user_create_time ON sys_user(create_time);
CREATE INDEX idx_sys_user_deleted ON sys_user(deleted);

-- 创建更新时间触发器函数
CREATE OR REPLACE FUNCTION update_update_time_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.update_time = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- 创建更新时间触发器
DROP TRIGGER IF EXISTS update_sys_user_update_time ON sys_user;
CREATE TRIGGER update_sys_user_update_time
    BEFORE UPDATE ON sys_user
    FOR EACH ROW
    EXECUTE FUNCTION update_update_time_column();

-- 插入测试数据
INSERT INTO sys_user (username, password, email, phone, nickname, gender, status, remark) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKITi', 'admin@example.com', '13800138000', '管理员', 1, 1, '系统管理员'),
('user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKITi', 'user1@example.com', '13800138001', '用户1', 0, 1, '普通用户'),
('user2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKITi', 'user2@example.com', '13800138002', '用户2', 1, 1, '普通用户'),
('test', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKITi', 'test@example.com', '13800138003', '测试用户', 0, 0, '已禁用用户');

-- 查询验证
SELECT * FROM sys_user ORDER BY id;