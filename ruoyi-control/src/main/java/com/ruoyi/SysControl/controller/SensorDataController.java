package com.ruoyi.SysControl.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.SysControl.domain.SensorData;
import com.ruoyi.SysControl.service.ISensorDataService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 传感器数据Controller
 *
 * @author zhu
 * @date 2024-01-23
 */
@RestController
@RequestMapping("/SysControl/data")
public class SensorDataController extends BaseController {
    @Autowired
    private ISensorDataService sensorDataService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询传感器数据列表
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:list')")
    @GetMapping("/list")
    public TableDataInfo list(SensorData sensorData) {
        startPage();
        List<SensorData> list = sensorDataService.selectSensorDataList(sensorData);
        return getDataTable(list);
    }

    /**
     * 导出传感器数据列表
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:export')")
    @Log(title = "传感器数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SensorData sensorData) {
        List<SensorData> list = sensorDataService.selectSensorDataList(sensorData);
        ExcelUtil<SensorData> util = new ExcelUtil<SensorData>(SensorData.class);
        util.exportExcel(response, list, "传感器数据数据");
    }

    /**
     * 获取传感器数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sensorDataService.selectSensorDataById(id));
    }

    /**
     * 新增传感器数据
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:add')")
    @Log(title = "传感器数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SensorData sensorData) {
        return toAjax(sensorDataService.insertSensorData(sensorData));
    }

    /**
     * 修改传感器数据
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:edit')")
    @Log(title = "传感器数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SensorData sensorData) {
        return toAjax(sensorDataService.updateSensorData(sensorData));
    }

    /**
     * 删除传感器数据
     */
    @PreAuthorize("@ss.hasPermi('SysControl:data:remove')")
    @Log(title = "传感器数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sensorDataService.deleteSensorDataByIds(ids));
    }

    /**
     * 获取传感器数据
     *
     * @param json
     * @return
     */
    @PostMapping("/test/get")
    public AjaxResult getData(@RequestBody JSONObject json) {
        SensorData sensorData = new SensorData();
        BigDecimal temperature = new BigDecimal(100);
        String sensorType = json.getString("sensorType");
        if (sensorType.equals("servo")) {
            sensorData.setDeviceId("HX711");
            sensorData.setSensorType(sensorType);
            sensorData.setDataValue(json.getBigDecimal("data"));
            if (json.getString("status").isEmpty()) {
                sensorData.setSensorStatus("正常");
            } else {
                sensorData.setSensorStatus(json.getString("status"));
            }
            redisCache.setCacheObject("servo", sensorData, 5, TimeUnit.MINUTES);
        } else {
            temperature = json.getBigDecimal("temperature");
            sensorData.setDeviceId("DHT11");
            sensorData.setSensorType("temperature");
            sensorData.setDataValue(temperature);
            if (json.getString("status").isEmpty()) {
                sensorData.setSensorStatus("正常");
            } else {
                sensorData.setSensorStatus(json.getString("status"));
            }
            redisCache.setCacheObject("temperature", sensorData, 5, TimeUnit.MINUTES);
            sensorData.setSensorType("humidity");
            sensorData.setDataValue(json.getBigDecimal("humidity"));
            redisCache.setCacheObject("humidity", sensorData, 5, TimeUnit.MINUTES);
        }

        // 判断是否为整点数据或异常数据
        LocalDateTime now = LocalDateTime.now();
        int minute = now.getMinute();
        if (minute == 0) { // 整点数据
            sensorDataService.saveSensorDataToDatabase(sensorData, temperature);
        } else {
            if (sensorDataService.isSensorDataException(temperature,sensorData.getDataValue())) { // 异常数据
                sensorData.setSensorStatus("异常");
                sensorDataService.saveSensorDataToDatabase(sensorData, temperature);
            }
        }

        return AjaxResult.success();
    }

    @GetMapping("/test/list")
    public AjaxResult chartList() {
        List<SensorData> sensorDataList = sensorDataService.getListFromDatabase(); // 从数据库中获取数据

        List<Map<String, Object>> chartData = new ArrayList<>();

        for (SensorData data : sensorDataList) {
            Map<String, Object> chartItem = new HashMap<>();
            chartItem.put("time", data.getDataTime()); // 假设数据中有时间字段
            chartItem.put(data.getSensorType(), data.getDataValue());
            chartData.add(chartItem);
        }

        return AjaxResult.success(chartData);
    }

    @GetMapping("/test/switchLED")  // TODO 添加具体地址
    public AjaxResult switchLED() {
        String channel = "__keyspace@0__:servo";
        Jedis jedis = new Jedis("localhost");

        jedis.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                if (message.equals("set")) {
                    BigDecimal value = new BigDecimal(jedis.get("servo"));
                    if (value.compareTo(new BigDecimal("100")) > 0) {
                        // 触发事件
                        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                            HttpGet request = new HttpGet("http://your-event-url");

                            try (CloseableHttpResponse response = httpClient.execute(request)) {
                                System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
                                String responseBody = EntityUtils.toString(response.getEntity());
                                System.out.println("Response Body : " + responseBody);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, channel);
        return AjaxResult.success();
    }

    @GetMapping("/test/duoji")
    public AjaxResult duoji() {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet("http://url");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
                String responseBody = EntityUtils.toString(response.getEntity());
                System.out.println("Response Body : " + responseBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success();
    }
}
