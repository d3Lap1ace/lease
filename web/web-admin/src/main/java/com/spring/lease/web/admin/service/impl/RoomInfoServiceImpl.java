package com.spring.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.spring.lease.model.entity.*;
import com.spring.lease.model.enums.ItemType;
import com.spring.lease.web.admin.mapper.*;
import com.spring.lease.web.admin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.spring.lease.web.admin.vo.attr.AttrValueVo;
import com.spring.lease.web.admin.vo.graph.GraphVo;
import com.spring.lease.web.admin.vo.room.RoomDetailVo;
import com.spring.lease.web.admin.vo.room.RoomItemVo;
import com.spring.lease.web.admin.vo.room.RoomQueryVo;
import com.spring.lease.web.admin.vo.room.RoomSubmitVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private RoomAttrValueService roomAttrValueService;
    @Autowired
    private RoomFacilityService roomFacilityService;
    @Autowired
    private RoomLabelService roomLabelService;
    @Autowired
    private RoomPaymentTypeService roomPaymentTypeService;
    @Autowired
    private RoomLeaseTermService roomLeaseTermService;
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private AttrValueMapper attrValueMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private PaymentTypeMapper paymentTypeMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private LeaseTermMapper leaseTermMapper;


    @Override
    public void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo) {
        Long id = roomSubmitVo.getId();  // 用于判断添加还是更新   获得room_info的主键id   getId()->rooSubmitVo->RoomInfo->BaseEntity.private Long id
        super.saveOrUpdate(roomSubmitVo); // 调用ServiceImpl.saveOrUpdate  用于CURD操作
        if(id != null){
            // 1. 更新graphInfoList
            List<GraphVo> graphVoList = roomSubmitVo.getGraphVoList(); // 获得图片列表
            if(graphVoList != null && graphVoList.size() > 0){         // 列表不为空
                ArrayList<GraphInfo> graphInfos = new ArrayList<>();   // 一个图片对象信息的列表
                for (GraphInfo graphVo : graphInfos) {                 // 遍历赋值
                    GraphInfo graphInfo = new GraphInfo();             // 创建一个图片对象
                    graphInfo.setItemType(ItemType.ROOM);
                    graphInfo.setItemId(roomSubmitVo.getId());
                    graphInfo.setName(graphVo.getName());
                    graphInfo.setUrl(graphVo.getUrl());
                    graphInfos.add(graphInfo);
                }
                graphInfoService.saveBatch(graphInfos);      // 批量更新
            }
            // 2. 更新roomAttrValueList
            List<Long> attrValueIds = roomSubmitVo.getAttrValueIds();
            if(attrValueIds != null && attrValueIds.size() > 0){
                List<RoomAttrValue> roomAttrValues = new ArrayList<>();
                for (Long attrValueId : attrValueIds) {
                    RoomAttrValue roomAttrValue = RoomAttrValue.builder().roomId(roomSubmitVo.getId()).attrValueId(attrValueId).build();
                    roomAttrValues.add(roomAttrValue);
                }
                roomAttrValueService.saveBatch(roomAttrValues);
            }
            // 3. 更新facilityInfoList
            List<Long> facilityInfoIds = roomSubmitVo.getFacilityInfoIds();
            if(facilityInfoIds != null && facilityInfoIds.size() > 0){
                List<RoomFacility> roomFacilities = new ArrayList<>();
                for (Long facilityInfoId : facilityInfoIds) {
                    RoomFacility roomFacility = RoomFacility.builder().roomId(roomSubmitVo.getId()).facilityId(facilityInfoId).build();
                    roomFacilities.add(roomFacility);
                }
                roomFacilityService.saveBatch(roomFacilities);
            }
            // 4. 更新labelInfoList
            List<Long> labelInfoIds = roomSubmitVo.getLabelInfoIds();
            if (!CollectionUtils.isEmpty(labelInfoIds)) {
                ArrayList<RoomLabel> roomLabelList = new ArrayList<>();
                for (Long labelInfoId : labelInfoIds) {
                    RoomLabel roomLabel = RoomLabel.builder().roomId(roomSubmitVo.getId()).labelId(labelInfoId).build();
                    roomLabelList.add(roomLabel);
                }
                roomLabelService.saveBatch(roomLabelList);
            }
            // 5. 更新paymentTypeList
            List<Long> paymentTypeIds = roomSubmitVo.getPaymentTypeIds();
            if (!CollectionUtils.isEmpty(paymentTypeIds)) {
                ArrayList<RoomPaymentType> roomPaymentTypeList = new ArrayList<>();
                for (Long paymentTypeId : paymentTypeIds) {
                    RoomPaymentType roomPaymentType = RoomPaymentType.builder().roomId(roomSubmitVo.getId()).paymentTypeId(paymentTypeId).build();
                    roomPaymentTypeList.add(roomPaymentType);
                }
                roomPaymentTypeService.saveBatch(roomPaymentTypeList);
            }
            // 6. 更新leaseTermList
            List<Long> leaseTermIds = roomSubmitVo.getLeaseTermIds();
            if (!CollectionUtils.isEmpty(leaseTermIds)) {
                ArrayList<RoomLeaseTerm> roomLeaseTerms = new ArrayList<>();
                for (Long leaseTermId : leaseTermIds) {
                    RoomLeaseTerm roomLeaseTerm = RoomLeaseTerm.builder().roomId(roomSubmitVo.getId()).leaseTermId(leaseTermId).build();
                    roomLeaseTerms.add(roomLeaseTerm);
                }
                roomLeaseTermService.saveBatch(roomLeaseTerms);
            }
        }
        // 1. 删除原有graphInfoList
        LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId,id);
        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType, ItemType.ROOM);
        graphInfoService.remove(graphInfoLambdaQueryWrapper);
        // 2. 删除原有roomAttrValueList
        LambdaQueryWrapper<RoomAttrValue> roomAttrValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomAttrValueLambdaQueryWrapper.eq(RoomAttrValue::getRoomId,id);
        roomAttrValueService.remove(roomAttrValueLambdaQueryWrapper);
        // 3. 删除原有roomfacilityInfoList
        LambdaQueryWrapper<RoomFacility> facilityInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        facilityInfoLambdaQueryWrapper.eq(RoomFacility::getRoomId, id);
        roomFacilityService.remove(facilityInfoLambdaQueryWrapper);
        // 4. 删除原有roomlabelInfoList
        LambdaQueryWrapper<RoomLabel> roomLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomLabelLambdaQueryWrapper.eq(RoomLabel::getRoomId, id);
        roomLabelService.remove(roomLabelLambdaQueryWrapper);
        // 5. 删除原有paymentTypeList
        LambdaQueryWrapper<RoomPaymentType> roomPaymentTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomPaymentTypeLambdaQueryWrapper.eq(RoomPaymentType::getRoomId, id);
        roomPaymentTypeService.remove(roomPaymentTypeLambdaQueryWrapper);
        // 6. 删除原有leaseTermList
        LambdaQueryWrapper<RoomLeaseTerm> roomLeaseTermLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomLeaseTermLambdaQueryWrapper.eq(RoomLeaseTerm::getRoomId, id);
        roomLeaseTermService.remove(roomLeaseTermLambdaQueryWrapper);
    }

    @Override
    public IPage<RoomItemVo> pageRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo) {
        return roomInfoMapper.pageRoomItemByQuery(page, queryVo);
    }

    @Override
    public void removeRoomById(Long id) {
        // 1. 删除原有graphInfoList
        LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId,id);
        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType, ItemType.ROOM);
        graphInfoService.remove(graphInfoLambdaQueryWrapper);
        // 2. 删除原有roomAttrValueList
        LambdaQueryWrapper<RoomAttrValue> roomAttrValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomAttrValueLambdaQueryWrapper.eq(RoomAttrValue::getRoomId,id);
        roomAttrValueService.remove(roomAttrValueLambdaQueryWrapper);
        // 3. 删除原有roomfacilityInfoList
        LambdaQueryWrapper<RoomFacility> facilityInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        facilityInfoLambdaQueryWrapper.eq(RoomFacility::getRoomId, id);
        roomFacilityService.remove(facilityInfoLambdaQueryWrapper);
        // 4. 删除原有roomlabelInfoList
        LambdaQueryWrapper<RoomLabel> roomLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomLabelLambdaQueryWrapper.eq(RoomLabel::getRoomId, id);
        roomLabelService.remove(roomLabelLambdaQueryWrapper);
        // 5. 删除原有paymentTypeList
        LambdaQueryWrapper<RoomPaymentType> roomPaymentTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomPaymentTypeLambdaQueryWrapper.eq(RoomPaymentType::getRoomId, id);
        roomPaymentTypeService.remove(roomPaymentTypeLambdaQueryWrapper);
        // 6. 删除原有leaseTermList
        LambdaQueryWrapper<RoomLeaseTerm> roomLeaseTermLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomLeaseTermLambdaQueryWrapper.eq(RoomLeaseTerm::getRoomId, id);
        roomLeaseTermService.remove(roomLeaseTermLambdaQueryWrapper);
    }

    @Override
    public RoomDetailVo getRoomDetailById(Long id) {
        //1.查询RoomInfo
        RoomInfo roomInfo = roomInfoMapper.selectById(id);

        //2.查询所属公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(roomInfo.getApartmentId());

        //3.查询graphInfoList
        List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM, id);

        //4.查询attrValueList
        List<AttrValueVo> attrvalueVoList = attrValueMapper.selectListByRoomId(id);

        //5.查询facilityInfoList
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectListByRoomId(id);

        //6.查询labelInfoList
        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByRoomId(id);

        //7.查询paymentTypeList
        List<PaymentType> paymentTypeList = paymentTypeMapper.selectListByRoomId(id);

        //8.查询leaseTermList
        List<LeaseTerm> leaseTermList = leaseTermMapper.selectListByRoomId(id);


        RoomDetailVo adminRoomDetailVo = new RoomDetailVo();
        BeanUtils.copyProperties(roomInfo, adminRoomDetailVo);

        adminRoomDetailVo.setApartmentInfo(apartmentInfo);
        adminRoomDetailVo.setGraphVoList(graphVoList);
        adminRoomDetailVo.setAttrValueVoList(attrvalueVoList);
        adminRoomDetailVo.setFacilityInfoList(facilityInfoList);
        adminRoomDetailVo.setLabelInfoList(labelInfoList);
        adminRoomDetailVo.setPaymentTypeList(paymentTypeList);
        adminRoomDetailVo.setLeaseTermList(leaseTermList);

        return adminRoomDetailVo;
    }
}




