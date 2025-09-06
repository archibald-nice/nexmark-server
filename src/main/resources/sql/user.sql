-- 创建用户表
CREATE TABLE sys_user (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
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
    remark VARCHAR(200)
);

-- 创建索引
CREATE INDEX idx_user_username ON sys_user(username);
CREATE INDEX idx_user_email ON sys_user(email);
CREATE INDEX idx_user_phone ON sys_user(phone);
CREATE INDEX idx_user_status ON sys_user(status);
CREATE INDEX idx_user_create_time ON sys_user(create_time);

-- 插入测试数据
INSERT INTO sys_user (username, password, email, phone, nickname, gender, status) VALUES 
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKITi', 'admin@example.com', '13800138000', '管理员', 1, 1),
('user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKITi', 'user1@example.com', '13800138001', '用户1', 0, 1),
('user2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKITi', 'user2@example.com', '13800138002', '用户2', 0, 1);

-- 更新函数
CREATE OR REPLACE FUNCTION update_modified_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.update_time = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- 创建触发器
CREATE TRIGGER update_user_update_time
    BEFORE UPDATE ON sys_user
    FOR EACH ROW
    EXECUTE FUNCTION update_modified_column();