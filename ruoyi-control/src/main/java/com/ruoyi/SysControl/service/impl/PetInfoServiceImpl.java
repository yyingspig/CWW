package com.ruoyi.SysControl.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.SysControl.mapper.PetInfoMapper;
import com.ruoyi.SysControl.domain.PetInfo;
import com.ruoyi.SysControl.service.IPetInfoService;

/**
 * 宠物信息管理Service业务层处理
 * 
 * @author zhu
 * @date 2024-04-10
 */
@Service
public class PetInfoServiceImpl implements IPetInfoService 
{
    @Autowired
    private PetInfoMapper petInfoMapper;

    /**
     * 查询宠物信息管理
     * 
     * @param petId 宠物信息管理主键
     * @return 宠物信息管理
     */
    @Override
    public PetInfo selectPetInfoByPetId(Long petId)
    {
        return petInfoMapper.selectPetInfoByPetId(petId);
    }

    /**
     * 查询宠物信息管理列表
     * 
     * @param petInfo 宠物信息管理
     * @return 宠物信息管理
     */
    @Override
    public List<PetInfo> selectPetInfoList(PetInfo petInfo)
    {
        return petInfoMapper.selectPetInfoList(petInfo);
    }

    /**
     * 新增宠物信息管理
     * 
     * @param petInfo 宠物信息管理
     * @return 结果
     */
    @Override
    public int insertPetInfo(PetInfo petInfo)
    {
        return petInfoMapper.insertPetInfo(petInfo);
    }

    /**
     * 修改宠物信息管理
     * 
     * @param petInfo 宠物信息管理
     * @return 结果
     */
    @Override
    public int updatePetInfo(PetInfo petInfo)
    {
        return petInfoMapper.updatePetInfo(petInfo);
    }

    /**
     * 批量删除宠物信息管理
     * 
     * @param petIds 需要删除的宠物信息管理主键
     * @return 结果
     */
    @Override
    public int deletePetInfoByPetIds(Long[] petIds)
    {
        return petInfoMapper.deletePetInfoByPetIds(petIds);
    }

    /**
     * 删除宠物信息管理信息
     * 
     * @param petId 宠物信息管理主键
     * @return 结果
     */
    @Override
    public int deletePetInfoByPetId(Long petId)
    {
        return petInfoMapper.deletePetInfoByPetId(petId);
    }
}
