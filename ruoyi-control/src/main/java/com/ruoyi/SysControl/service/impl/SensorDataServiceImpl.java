package com.ruoyi.SysControl.service.impl;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.SysControl.mapper.SensorDataMapper;
import com.ruoyi.SysControl.domain.SensorData;
import com.ruoyi.SysControl.service.ISensorDataService;

/**
 * 传感器数据Service业务层处理
 * 
 * @author zhu
 * @date 2024-01-23
 */
@Service
public class SensorDataServiceImpl implements ISensorDataService 
{
    @Autowired
    private SensorDataMapper sensorDataMapper;

    /**
     * 查询传感器数据
     * 
     * @param id 传感器数据主键
     * @return 传感器数据
     */
    @Override
    public SensorData selectSensorDataById(Long id)
    {
        return sensorDataMapper.selectSensorDataById(id);
    }

    /**
     * 查询传感器数据列表
     * 
     * @param sensorData 传感器数据
     * @return 传感器数据
     */
    @Override
    public List<SensorData> selectSensorDataList(SensorData sensorData)
    {
        return sensorDataMapper.selectSensorDataList(sensorData);
    }

    /**
     * 新增传感器数据
     * 
     * @param sensorData 传感器数据
     * @return 结果
     */
    @Override
    public int insertSensorData(SensorData sensorData)
    {
        sensorData.setCreateTime(DateUtils.getNowDate());
        sensorData.setDataTime(DateUtils.getNowDate());
        return sensorDataMapper.insertSensorData(sensorData);
    }

    /**
     * 修改传感器数据
     * 
     * @param sensorData 传感器数据
     * @return 结果
     */
    @Override
    public int updateSensorData(SensorData sensorData)
    {
        sensorData.setUpdateTime(DateUtils.getNowDate());
        return sensorDataMapper.updateSensorData(sensorData);
    }

    /**
     * 批量删除传感器数据
     * 
     * @param ids 需要删除的传感器数据主键
     * @return 结果
     */
    @Override
    public int deleteSensorDataByIds(Long[] ids)
    {
        return sensorDataMapper.deleteSensorDataByIds(ids);
    }

    /**
     * 删除传感器数据信息
     * 
     * @param id 传感器数据主键
     * @return 结果
     */
    @Override
    public int deleteSensorDataById(Long id)
    {
        return sensorDataMapper.deleteSensorDataById(id);
    }

    @Override
    public void saveSensorDataToDatabase(List<SensorData> list) {
        for (SensorData sensorData : list) {
            insertSensorData(sensorData);
        }
    }

    @Override
    public List<SensorData> isSensorDataException(List<SensorData> list) {
        // 剔除传感器正常数据
       list.removeIf(sensorData -> !sensorData.getSensorStatus().equals("异常"));
       return list;
    }

    @Override
    public List<SensorData> getListFromDatabase() {
        SensorData sensorData = new SensorData();
        List<SensorData> list = selectSensorDataList(sensorData);
        return list;
    }

    @Override
    public void switchLED(int status) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("http://192.168.137.61/led?num=" + status);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
//                System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
//                String responseBody = EntityUtils.toString(response.getEntity());
//                System.out.println("Response Body : " + responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
