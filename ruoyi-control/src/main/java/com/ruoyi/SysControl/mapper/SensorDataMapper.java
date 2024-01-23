package com.ruoyi.SysControl.mapper;

import java.util.List;
import com.ruoyi.SysControl.domain.SensorData;

/**
 * 传感器数据Mapper接口
 * 
 * @author ruoyi
 * @date 2024-01-23
 */
public interface SensorDataMapper 
{
    /**
     * 查询传感器数据
     * 
     * @param id 传感器数据主键
     * @return 传感器数据
     */
    public SensorData selectSensorDataById(Long id);

    /**
     * 查询传感器数据列表
     * 
     * @param sensorData 传感器数据
     * @return 传感器数据集合
     */
    public List<SensorData> selectSensorDataList(SensorData sensorData);

    /**
     * 新增传感器数据
     * 
     * @param sensorData 传感器数据
     * @return 结果
     */
    public int insertSensorData(SensorData sensorData);

    /**
     * 修改传感器数据
     * 
     * @param sensorData 传感器数据
     * @return 结果
     */
    public int updateSensorData(SensorData sensorData);

    /**
     * 删除传感器数据
     * 
     * @param id 传感器数据主键
     * @return 结果
     */
    public int deleteSensorDataById(Long id);

    /**
     * 批量删除传感器数据
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSensorDataByIds(Long[] ids);
}
