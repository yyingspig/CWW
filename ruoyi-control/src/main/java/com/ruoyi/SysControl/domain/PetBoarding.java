package com.ruoyi.SysControl.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 入住信息对象 pet_boarding
 * 
 * @author zhu
 * @date 2024-04-10
 */
public class PetBoarding extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 入住id */
    private Long boardingId;

    /** 宠物id */
    @Excel(name = "宠物id")
    private Long petId;

    /** 宠物屋id */
    @Excel(name = "宠物屋id")
    private String boardingHouseNumber;

    /** 入住时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入住时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkInDate;

    /** 入住天数 */
    @Excel(name = "入住天数")
    private Long stayDays;

    /** 离开时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "离开时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkOutDate;

    /** 主人id */
    @Excel(name = "主人id")
    private Long ownerId;

    public void setBoardingId(Long boardingId) 
    {
        this.boardingId = boardingId;
    }

    public Long getBoardingId() 
    {
        return boardingId;
    }
    public void setPetId(Long petId) 
    {
        this.petId = petId;
    }

    public Long getPetId() 
    {
        return petId;
    }
    public void setBoardingHouseNumber(String boardingHouseNumber) 
    {
        this.boardingHouseNumber = boardingHouseNumber;
    }

    public String getBoardingHouseNumber() 
    {
        return boardingHouseNumber;
    }
    public void setCheckInDate(Date checkInDate) 
    {
        this.checkInDate = checkInDate;
    }

    public Date getCheckInDate() 
    {
        return checkInDate;
    }
    public void setStayDays(Long stayDays) 
    {
        this.stayDays = stayDays;
    }

    public Long getStayDays() 
    {
        return stayDays;
    }
    public void setCheckOutDate(Date checkOutDate) 
    {
        this.checkOutDate = checkOutDate;
    }

    public Date getCheckOutDate() 
    {
        return checkOutDate;
    }
    public void setOwnerId(Long ownerId) 
    {
        this.ownerId = ownerId;
    }

    public Long getOwnerId() 
    {
        return ownerId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("boardingId", getBoardingId())
            .append("petId", getPetId())
            .append("boardingHouseNumber", getBoardingHouseNumber())
            .append("checkInDate", getCheckInDate())
            .append("stayDays", getStayDays())
            .append("checkOutDate", getCheckOutDate())
            .append("ownerId", getOwnerId())
            .toString();
    }
}
