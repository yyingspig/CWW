CREATE TABLE IF NOT EXISTS device_info (
    id INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    device_id VARCHAR(50) NOT NULL COMMENT '设备ID',
    device_name VARCHAR(100) NOT NULL COMMENT '设备名称',
    device_type VARCHAR(50) NOT NULL COMMENT '设备类型',
    connection_status TINYINT(1) NOT NULL COMMENT '连接状态：0-未连接，1-已连接',
    network_status TINYINT(1) NOT NULL COMMENT '网络状态：0-离线，1-在线',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设备信息表';


CREATE TABLE IF NOT EXISTS sensor_data (
    id INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    device_id VARCHAR(50) NOT NULL COMMENT '设备ID',
    sensor_type VARCHAR(50) NOT NULL COMMENT '传感器类型',
    data_value FLOAT(10, 2) NOT NULL COMMENT '传感器数据值',
    data_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '数据时间',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='传感器数据表';


CREATE TABLE IF NOT EXISTS component_status (
    id INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    device_id VARCHAR(50) NOT NULL COMMENT '设备ID',
    component_type VARCHAR(50) NOT NULL COMMENT '元件类型',
    component_status INT(11) NOT NULL COMMENT '元件状态',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='元件状态表';


CREATE TABLE IF NOT EXISTS operation_log (
    id INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    device_id VARCHAR(50) NOT NULL COMMENT '设备ID',
    log_type VARCHAR(50) NOT NULL COMMENT '日志类型',
    log_content TEXT NOT NULL COMMENT '日志内容',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';