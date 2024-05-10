package com.ruoyi.SysControl.mapper;

import java.util.List;
import com.ruoyi.SysControl.domain.OwnerInfo;

/**
 * 主人信息Mapper接口
 * 
 * @author zhu
 * @date 2024-04-10
 */
public interface OwnerInfoMapper 
{
    /**
     * 查询主人信息
     * 
     * @param ownerId 主人信息主键
     * @return 主人信息
     */
    public OwnerInfo selectOwnerInfoByOwnerId(Long ownerId);

    /**
     * 查询主人信息列表
     * 
     * @param ownerInfo 主人信息
     * @return 主人信息集合
     */
    public List<OwnerInfo> selectOwnerInfoList(OwnerInfo ownerInfo);

    /**
     * 新增主人信息
     * 
     * @param ownerInfo 主人信息
     * @return 结果
     */
    public int insertOwnerInfo(OwnerInfo ownerInfo);

    /**
     * 修改主人信息
     * 
     * @param ownerInfo 主人信息
     * @return 结果
     */
    public int updateOwnerInfo(OwnerInfo ownerInfo);

    /**
     * 删除主人信息
     * 
     * @param ownerId 主人信息主键
     * @return 结果
     */
    public int deleteOwnerInfoByOwnerId(Long ownerId);

    /**
     * 批量删除主人信息
     * 
     * @param ownerIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOwnerInfoByOwnerIds(Long[] ownerIds);
}
