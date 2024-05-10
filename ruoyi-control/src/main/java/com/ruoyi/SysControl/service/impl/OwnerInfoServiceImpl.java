package com.ruoyi.SysControl.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.SysControl.mapper.OwnerInfoMapper;
import com.ruoyi.SysControl.domain.OwnerInfo;
import com.ruoyi.SysControl.service.IOwnerInfoService;

/**
 * 主人信息Service业务层处理
 * 
 * @author zhu
 * @date 2024-04-10
 */
@Service
public class OwnerInfoServiceImpl implements IOwnerInfoService 
{
    @Autowired
    private OwnerInfoMapper ownerInfoMapper;

    /**
     * 查询主人信息
     * 
     * @param ownerId 主人信息主键
     * @return 主人信息
     */
    @Override
    public OwnerInfo selectOwnerInfoByOwnerId(Long ownerId)
    {
        return ownerInfoMapper.selectOwnerInfoByOwnerId(ownerId);
    }

    /**
     * 查询主人信息列表
     * 
     * @param ownerInfo 主人信息
     * @return 主人信息
     */
    @Override
    public List<OwnerInfo> selectOwnerInfoList(OwnerInfo ownerInfo)
    {
        return ownerInfoMapper.selectOwnerInfoList(ownerInfo);
    }

    /**
     * 新增主人信息
     * 
     * @param ownerInfo 主人信息
     * @return 结果
     */
    @Override
    public int insertOwnerInfo(OwnerInfo ownerInfo)
    {
        return ownerInfoMapper.insertOwnerInfo(ownerInfo);
    }

    /**
     * 修改主人信息
     * 
     * @param ownerInfo 主人信息
     * @return 结果
     */
    @Override
    public int updateOwnerInfo(OwnerInfo ownerInfo)
    {
        return ownerInfoMapper.updateOwnerInfo(ownerInfo);
    }

    /**
     * 批量删除主人信息
     * 
     * @param ownerIds 需要删除的主人信息主键
     * @return 结果
     */
    @Override
    public int deleteOwnerInfoByOwnerIds(Long[] ownerIds)
    {
        return ownerInfoMapper.deleteOwnerInfoByOwnerIds(ownerIds);
    }

    /**
     * 删除主人信息信息
     * 
     * @param ownerId 主人信息主键
     * @return 结果
     */
    @Override
    public int deleteOwnerInfoByOwnerId(Long ownerId)
    {
        return ownerInfoMapper.deleteOwnerInfoByOwnerId(ownerId);
    }
}
