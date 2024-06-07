package com.spring.lease.web.app.service.impl;

import com.spring.lease.model.entity.ApartmentInfo;
import com.spring.lease.model.entity.FacilityInfo;
import com.spring.lease.model.entity.GraphInfo;
import com.spring.lease.model.entity.LabelInfo;
import com.spring.lease.model.enums.ItemType;
import com.spring.lease.web.app.mapper.*;
import com.spring.lease.web.app.service.ApartmentInfoService;
import com.spring.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.spring.lease.web.app.vo.apartment.ApartmentItemVo;
import com.spring.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Resource
    private ApartmentInfoMapper apartmentInfoMapper;
    @Resource
    private LabelInfoMapper labelInfoMapper;
    @Resource
    private GraphInfoMapper graphInfoMapper;
    @Resource
    private RoomInfoMapper roomInfoMapper;
    @Resource
    private FacilityInfoMapper facilityInfoMapper;


    /**
     * 根据id查询所属公寓信息
     * @param id 公寓id
     * @return
     */
    @Override
    public ApartmentItemVo getApartmentItemVoById(Long id) {
        // 根据公寓id查询公寓实体信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectApartmentById(id);
        // 根据公寓id查询标签信息
        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByApartmentId(id);
        // 根据公寓id查询图片信息
        List<GraphInfo> graphVoList = graphInfoMapper.grapinfoSelectListByItemTypeAndId(ItemType.APARTMENT, id);
        // 根据公寓id查询最小租金信息
        BigDecimal minRent = roomInfoMapper.selectMinRentByApartmentId(id);
        // 声明公寓对象
        ApartmentItemVo apartmentItemVo = new ApartmentItemVo();
        // 将apartmentInfo赋值给apartmentItemVo
        BeanUtils.copyProperties(apartmentInfo, apartmentItemVo);
        // 获得图片列表
        apartmentItemVo.setGraphVoList(graphVoList);
        // 获得标签列表
        apartmentItemVo.setLabelInfoList(labelInfoList);
        // 获得最小租金房间
        apartmentItemVo.setMinRent(minRent);

        return apartmentItemVo;
    }

    /**
     * 根据id查询公寓详细信息
     * @param id
     * @return
     */
    @Override
    public ApartmentDetailVo getApartmentDetailById(Long id) {
        //1.查询ApartmentInfo
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectApartmentById(id);
        if (apartmentInfo == null) {
            return null;
        }

        //2.查询GraphInfo
        List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, id);

        //3.查询LabelInfo
        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByApartmentId(id);

        //4.查询FacilityInfo
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectListByApartmentId(id);

        //5.查询公寓最低房租
        BigDecimal minRent = roomInfoMapper.selectMinRentByApartmentId(id);

        ApartmentDetailVo appApartmentDetailVo = new ApartmentDetailVo();

        BeanUtils.copyProperties(apartmentInfo, appApartmentDetailVo);

        appApartmentDetailVo.setGraphVoList(graphVoList);
        appApartmentDetailVo.setLabelInfoList(labelInfoList);
        appApartmentDetailVo.setFacilityInfoList(facilityInfoList);
        appApartmentDetailVo.setMinRent(minRent);

        if(apartmentInfo.getIsDeleted() == null){
            appApartmentDetailVo.setIsDelete(true);
        }else{
            appApartmentDetailVo.setIsDelete(false);
        }

        return appApartmentDetailVo;
    }
}




