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


    @Override
    public ApartmentItemVo getApartmentItemVoById(Long id) {
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectApartmentById(id);

        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByApartmentId(id);

        List<GraphInfo> graphVoList = graphInfoMapper.grapinfoSelectListByItemTypeAndId(ItemType.APARTMENT, id);

        BigDecimal minRent = roomInfoMapper.selectMinRentByApartmentId(id);

        ApartmentItemVo apartmentItemVo = new ApartmentItemVo();

        BeanUtils.copyProperties(apartmentInfo, apartmentItemVo);
        apartmentItemVo.setGraphVoList(graphVoList);
        apartmentItemVo.setLabelInfoList(labelInfoList);
        apartmentItemVo.setMinRent(minRent);

        return apartmentItemVo;
    }

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
        appApartmentDetailVo.setIsDelete(apartmentInfo.getIsDeleted() == 1);
        appApartmentDetailVo.setGraphVoList(graphVoList);
        appApartmentDetailVo.setLabelInfoList(labelInfoList);
        appApartmentDetailVo.setFacilityInfoList(facilityInfoList);
        appApartmentDetailVo.setMinRent(minRent);
        return appApartmentDetailVo;
    }
}




