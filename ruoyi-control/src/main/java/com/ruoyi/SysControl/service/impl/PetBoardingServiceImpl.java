package com.ruoyi.SysControl.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.SysControl.mapper.PetBoardingMapper;
import com.ruoyi.SysControl.domain.PetBoarding;
import com.ruoyi.SysControl.service.IPetBoardingService;

/**
 * 入住信息Service业务层处理
 * 
 * @author zhu
 * @date 2024-04-10
 */
@Service
public class PetBoardingServiceImpl implements IPetBoardingService 
{
    @Autowired
    private PetBoardingMapper petBoardingMapper;

    /**
     * 查询入住信息
     * 
     * @param boardingId 入住信息主键
     * @return 入住信息
     */
    @Override
    public PetBoarding selectPetBoardingByBoardingId(Long boardingId)
    {
        return petBoardingMapper.selectPetBoardingByBoardingId(boardingId);
    }

    /**
     * 查询入住信息列表
     * 
     * @param petBoarding 入住信息
     * @return 入住信息
     */
    @Override
    public List<PetBoarding> selectPetBoardingList(PetBoarding petBoarding)
    {
        return petBoardingMapper.selectPetBoardingList(petBoarding);
    }

    /**
     * 新增入住信息
     * 
     * @param petBoarding 入住信息
     * @return 结果
     */
    @Override
    public int insertPetBoarding(PetBoarding petBoarding)
    {
        return petBoardingMapper.insertPetBoarding(petBoarding);
    }

    /**
     * 修改入住信息
     * 
     * @param petBoarding 入住信息
     * @return 结果
     */
    @Override
    public int updatePetBoarding(PetBoarding petBoarding)
    {
        return petBoardingMapper.updatePetBoarding(petBoarding);
    }

    /**
     * 批量删除入住信息
     * 
     * @param boardingIds 需要删除的入住信息主键
     * @return 结果
     */
    @Override
    public int deletePetBoardingByBoardingIds(Long[] boardingIds)
    {
        return petBoardingMapper.deletePetBoardingByBoardingIds(boardingIds);
    }

    /**
     * 删除入住信息信息
     * 
     * @param boardingId 入住信息主键
     * @return 结果
     */
    @Override
    public int deletePetBoardingByBoardingId(Long boardingId)
    {
        return petBoardingMapper.deletePetBoardingByBoardingId(boardingId);
    }

    @Override
    public int updatePetBoardingById(Long boardingId) {
        return petBoardingMapper.updatePetBoardingById(boardingId);
    }

    @Override
    public int updatePetBoardingById2(Long boardingId) {
        return petBoardingMapper.updatePetBoardingById2(boardingId);
    }
}
