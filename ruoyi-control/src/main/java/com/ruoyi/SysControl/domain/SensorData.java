package com.ruoyi.SysControl.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 传感器数据对象 sensor_data
 * 
 * @author ruoyi
 * @date 2024-01-23
 */
public class SensorData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 设备ID */
    @Excel(name = "设备ID")
    private String deviceId;

    /** 传感器类型 */
    @Excel(name = "传感器类型")
    private String sensorType;

    /** 传感器数据值 */
    @Excel(name = "传感器数据值")
    private BigDecimal dataValue;

    /** 数据时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    @Excel(name = "数据时间", width = 30, dateFormat = "yyyy-MM-dd HH-mm-ss")
    private Date dataTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDeviceId(String deviceId) 
    {
        this.deviceId = deviceId;
    }

    public String getDeviceId() 
    {
        return deviceId;
    }
    public void setSensorType(String sensorType) 
    {
        this.sensorType = sensorType;
    }

    public String getSensorType() 
    {
        return sensorType;
    }
    public void setDataValue(BigDecimal dataValue) 
    {
        this.dataValue = dataValue;
    }

    public BigDecimal getDataValue() 
    {
        return dataValue;
    }
    public void setDataTime(Date dataTime) 
    {
        this.dataTime = dataTime;
    }

    public Date getDataTime() 
    {
        return dataTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deviceId", getDeviceId())
            .append("sensorType", getSensorType())
            .append("dataValue", getDataValue())
            .append("dataTime", getDataTime())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
