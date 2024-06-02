package com.spring.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.spring.lease.common.exception.LeaseException;
import com.spring.lease.common.result.ResultCodeEnum;
import com.spring.lease.model.entity.*;
import com.spring.lease.model.enums.ItemType;
import com.spring.lease.web.admin.mapper.*;
import com.spring.lease.web.admin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.spring.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.spring.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.spring.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.spring.lease.web.admin.vo.fee.FeeValueVo;
import com.spring.lease.web.admin.vo.graph.GraphVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Autowired
    private ApartmentFacilityService apartmentFacilityService;
    @Autowired
    private ApartmentLabelService apartmentLabelService;
    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;
    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private RoomInfoService roomInfoService;


    @Override
    public void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo) {
        // 保存公寓信息
        super.saveOrUpdate(apartmentSubmitVo);
        // 判断是在添加还是更新
        Long id = apartmentSubmitVo.getId();
        if(id != null){
            // 更新公寓信息
            // 1. 根据apartment_id删除配套
            LambdaQueryWrapper<ApartmentFacility> apartmentFacilityLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 封装条件
            apartmentFacilityLambdaQueryWrapper.eq(ApartmentFacility::getApartmentId,id);
            // 根据条件删除
            apartmentFacilityService.remove(apartmentFacilityLambdaQueryWrapper);

            // 2. 根据apartment_id删除标签
            LambdaQueryWrapper<ApartmentLabel> apartmentLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 封装条件
            apartmentLabelLambdaQueryWrapper.eq(ApartmentLabel::getApartmentId,id);
            // 根据条件删除
            apartmentLabelService.remove(apartmentLabelLambdaQueryWrapper);



            // 3. 根据apartment_id删除杂费
            LambdaQueryWrapper<ApartmentFeeValue> apartmentFeeValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 封装条件
            apartmentFeeValueLambdaQueryWrapper.eq(ApartmentFeeValue::getApartmentId,id);
            // 根据条件删除
            apartmentFeeValueService.remove(apartmentFeeValueLambdaQueryWrapper);


            // 4.根据item_id和item_type删除图片
            LambdaQueryWrapper<GraphInfo> graphVoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 封装条件
            graphVoLambdaQueryWrapper.eq(GraphInfo::getItemType,ItemType.APARTMENT);
            graphVoLambdaQueryWrapper.eq(GraphInfo::getItemId,id);
            // 根据条件删除
            graphInfoService.remove(graphVoLambdaQueryWrapper);



        }
        // 1. 保存配套信息
        // 获取所有的配套id
        List<Long> facilityInfoIds = apartmentSubmitVo.getFacilityInfoIds();
        if(!CollectionUtils.isEmpty(facilityInfoIds)){
            // 创建一个保存ApartmentFacility对象的集合
            ArrayList<ApartmentFacility> apartmentFacilityList = new ArrayList<>();
            // 遍历所有的配套id
            for (Long facilityInfoId : facilityInfoIds) {
                // 创建ApartmentFacility对象
                ApartmentFacility apartmentFacility = new ApartmentFacility();
                // 设置公寓id
                apartmentFacility.setApartmentId(id);
                apartmentFacility.setFacilityId(facilityInfoId);
                // 将apartmentFacility 添加到集合中
                apartmentFacilityList.add(apartmentFacility);
            }
            // 批量插入
            apartmentFacilityService.saveBatch(apartmentFacilityList);
        }
        // 2. 保存标签信息
        //  获得标签合集
        List<Long> labelIds = apartmentSubmitVo.getLabelIds();
        if(!CollectionUtils.isEmpty(labelIds)){
            ArrayList<ApartmentLabel> apartmentLabelArrayList = new ArrayList<>();
            for (Long labelId : labelIds) {
                ApartmentLabel apartmentLabel = new ApartmentLabel();
                apartmentLabel.setApartmentId(id);
                apartmentLabel.setLabelId(labelId);
                apartmentLabelArrayList.add(apartmentLabel);
            }
            apartmentLabelService.saveBatch(apartmentLabelArrayList);
        }
        // 3. 保存杂费信息
        List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();
        if (!CollectionUtils.isEmpty(feeValueIds)) {
            ArrayList<ApartmentFeeValue> apartmentFeeValueList = new ArrayList<>();
            for (Long feeValueId : feeValueIds) {
                ApartmentFeeValue apartmentFeeValue = new ApartmentFeeValue();
                apartmentFeeValue.setApartmentId(apartmentSubmitVo.getId());
                apartmentFeeValue.setFeeValueId(feeValueId);
                apartmentFeeValueList.add(apartmentFeeValue);
            }
            apartmentFeeValueService.saveBatch(apartmentFeeValueList);
        }
        // 4. 保存公寓图片
        List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();
        if (!CollectionUtils.isEmpty(graphVoList)){
            ArrayList<GraphInfo> graphInfoList = new ArrayList<>();
            for (GraphVo graphVo : graphVoList) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemType(ItemType.APARTMENT);
                graphInfo.setItemId(apartmentSubmitVo.getId());
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfoList.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfoList);
        }
    }

    @Override
    public void deleteApartmentById(Long id) {
        // 判断当前公寓下是否有房子,如果有不允许删除
        LambdaQueryWrapper<RoomInfo> roomInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomInfoLambdaQueryWrapper.eq(RoomInfo::getApartmentId,id);
        long count = roomInfoService.count(roomInfoLambdaQueryWrapper);
        // 如果有则抛出异常
        if(count > 0){
            throw new LeaseException(ResultCodeEnum.DELETE_ERROR);
        }
        // 保存公寓信息
        super.removeById(id);
        if(id != null) {
            // 更新公寓信息
            // 1. 根据apartment_id删除配套
            LambdaQueryWrapper<ApartmentFacility> apartmentFacilityLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 封装条件
            apartmentFacilityLambdaQueryWrapper.eq(ApartmentFacility::getApartmentId, id);
            // 根据条件删除
            apartmentFacilityService.remove(apartmentFacilityLambdaQueryWrapper);

            // 2. 根据apartment_id删除标签
            LambdaQueryWrapper<ApartmentLabel> apartmentLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 封装条件
            apartmentLabelLambdaQueryWrapper.eq(ApartmentLabel::getApartmentId, id);
            // 根据条件删除
            apartmentLabelService.remove(apartmentLabelLambdaQueryWrapper);

            // 3. 根据apartment_id删除杂费
            LambdaQueryWrapper<ApartmentFeeValue> apartmentFeeValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 封装条件
            apartmentFeeValueLambdaQueryWrapper.eq(ApartmentFeeValue::getApartmentId, id);
            // 根据条件删除
            apartmentFeeValueService.remove(apartmentFeeValueLambdaQueryWrapper);

            // 4. 根据apartment_id删除标签
            LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 封装条件
            graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId, id);
            // 根据条件删除
            graphInfoService.remove(graphInfoLambdaQueryWrapper);

        }
    }

    @Override
    public ApartmentDetailVo getApartmentDetilByid(Long id) {
        // 查询apartmentInfo
        ApartmentInfo apartmentInfo = this.getById(id);
        if(apartmentInfo == null){return null;}

        // 查询LabelInfo
        List<LabelInfo> labelInfoList = LabelInfoMapper.selectListByApartmentId(id);

        // 查询GraphInfo
        List<GraphVo> graphVoList = GraphInfoMapper.selectListByItemTypeAndId(id);

        // 查询FacilInfo
        List<FacilityInfo> facilityInfoList = FacilityInfoMapper.selectListByApartmentId(id);

        // 查询FeeValue
        List<FeeValueVo> feeValueVoList = FeeValueMapper.selectListByApartmentId(id);

        // 生成公寓详细信息对象
        ApartmentDetailVo adminApartmentDetailVo = new ApartmentDetailVo();

        //将adminPartmentDetailVo的属性复制给apartmentInfo
        BeanUtils.copyProperties(apartmentInfo, adminApartmentDetailVo);

        adminApartmentDetailVo.setGraphVoList(graphVoList);
        adminApartmentDetailVo.setLabelInfoList(labelInfoList);
        adminApartmentDetailVo.setFacilityInfoList(facilityInfoList);
        adminApartmentDetailVo.setFeeValueVoList(feeValueVoList);

        return adminApartmentDetailVo;
    }

    @Override
    public IPage<ApartmentItemVo> pageApartmentItemByQuery(IPage<ApartmentItemVo> page, ApartmentQueryVo queryVo) {

        return ApartmentInfoMapper.pageApartmentItemByQuery(page,queryVo);
    }
}




