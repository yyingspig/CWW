package com.ruoyi.SysControl.mapper;

import java.util.List;
import com.ruoyi.SysControl.domain.PetInfo;

/**
 * 宠物信息管理Mapper接口
 * 
 * @author zhu
 * @date 2024-04-10
 */
public interface PetInfoMapper 
{
    /**
     * 查询宠物信息管理
     * 
     * @param petId 宠物信息管理主键
     * @return 宠物信息管理
     */
    public PetInfo selectPetInfoByPetId(Long petId);

    /**
     * 查询宠物信息管理列表
     * 
     * @param petInfo 宠物信息管理
     * @return 宠物信息管理集合
     */
    public List<PetInfo> selectPetInfoList(PetInfo petInfo);

    /**
     * 新增宠物信息管理
     * 
     * @param petInfo 宠物信息管理
     * @return 结果
     */
    public int insertPetInfo(PetInfo petInfo);

    /**
     * 修改宠物信息管理
     * 
     * @param petInfo 宠物信息管理
     * @return 结果
     */
    public int updatePetInfo(PetInfo petInfo);

    /**
     * 删除宠物信息管理
     * 
     * @param petId 宠物信息管理主键
     * @return 结果
     */
    public int deletePetInfoByPetId(Long petId);

    /**
     * 批量删除宠物信息管理
     * 
     * @param petIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePetInfoByPetIds(Long[] petIds);
}
