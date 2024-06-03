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
import jakarta.annotation.Resource;
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
    @Resource
    private RoomInfoMapper roomInfoMapper;
    @Resource
    private AttrValueMapper attrValueMapper;
    @Resource
    private LabelInfoMapper labelInfoMapper;
    @Resource
    private PaymentTypeMapper paymentTypeMapper;
    @Resource
    private FacilityInfoMapper facilityInfoMapper;
    @Resource
    private GraphInfoMapper graphInfoMapper;
    @Resource
    private ApartmentInfoMapper apartmentInfoMapper;
    @Resource
    private LeaseTermMapper leaseTermMapper;


    @Override
    public void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo) {
        Long id = roomSubmitVo.getId();  // 用于判断添加还是更新   获得room_info的主键id   getId()->rooSubmitVo->RoomInfo->BaseEntity.private Long id
        super.saveOrUpdate(roomSubmitVo); // 调用ServiceImpl.saveOrUpdate  用于CURD操作
        if(id != null){
            // 1. 删除原有graphInfoList
            LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 封装对象
            graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId,id);
            graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType, ItemType.ROOM);
            graphInfoService.remove(graphInfoLambdaQueryWrapper);
            // 2. 删除原有roomAttrValueList
            LambdaQueryWrapper<RoomAttrValue> roomAttrValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 封装对象
            roomAttrValueLambdaQueryWrapper.eq(RoomAttrValue::getRoomId,id);
            roomAttrValueService.remove(roomAttrValueLambdaQueryWrapper);
            // 3. 删除原有roomFacilityInfoList
            LambdaQueryWrapper<RoomFacility> facilityInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 封装对象
            facilityInfoLambdaQueryWrapper.eq(RoomFacility::getRoomId, id);
            roomFacilityService.remove(facilityInfoLambdaQueryWrapper);
            // 4. 删除原有roomLabelInfoList
            LambdaQueryWrapper<RoomLabel> roomLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 封装对象
            roomLabelLambdaQueryWrapper.eq(RoomLabel::getRoomId, id);
            roomLabelService.remove(roomLabelLambdaQueryWrapper);
            // 5. 删除原有roomPaymentTypeList
            LambdaQueryWrapper<RoomPaymentType> roomPaymentTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 封装对象
            roomPaymentTypeLambdaQueryWrapper.eq(RoomPaymentType::getRoomId, id);
            roomPaymentTypeService.remove(roomPaymentTypeLambdaQueryWrapper);
            // 6. 删除原有roomLeaseTermList
            LambdaQueryWrapper<RoomLeaseTerm> roomLeaseTermLambdaQueryWrapper = new LambdaQueryWrapper<>();
            // 封装对象
            roomLeaseTermLambdaQueryWrapper.eq(RoomLeaseTerm::getRoomId, id);
            roomLeaseTermService.remove(roomLeaseTermLambdaQueryWrapper);
        }
        // 1. 更新graphInfoList
        List<GraphVo> graphVoList = roomSubmitVo.getGraphVoList(); // 获得图片列表
        if(graphVoList != null && graphVoList.size() > 0){         // 列表不为空
            ArrayList<GraphInfo> graphInfos = new ArrayList<>();   // 一个图片对象信息的列表
            for (GraphInfo graphVo : graphInfos) {                 // 遍历赋值
                GraphInfo graphInfo = new GraphInfo();             // 创建一个图片对象
                graphInfo.setItemType(ItemType.ROOM);
                graphInfo.setItemId(id);
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                graphInfos.add(graphInfo);                          // 加入列表
            }
            graphInfoService.saveBatch(graphInfos);      // 批量更新
        }
        // 2. 更新roomAttrValueList
        List<Long> attrValueIds = roomSubmitVo.getAttrValueIds();  // 获得属性列表
        if(attrValueIds != null && attrValueIds.size() > 0){        // 列表不为空
            List<RoomAttrValue> roomAttrValues = new ArrayList<>(); // 创建房间属性对象列表
            for (Long attrValueId : attrValueIds) {                // 遍历赋值
                RoomAttrValue roomAttrValue = RoomAttrValue.builder()
                        .roomId(id)
                        .attrValueId(attrValueId)
                        .build();
                roomAttrValues.add(roomAttrValue);             // 加入列表
            }
            roomAttrValueService.saveBatch(roomAttrValues); // 批量更新
        }
        // 3. 更新facilityInfoList
        List<Long> facilityInfoIds = roomSubmitVo.getFacilityInfoIds(); // 获得配套信息
        if(facilityInfoIds != null && facilityInfoIds.size() > 0){
            List<RoomFacility> roomFacilities = new ArrayList<>();      // 创建 房间配套 对象列表
            for (Long facilityInfoId : facilityInfoIds) {               // 遍历赋值
                RoomFacility roomFacility = RoomFacility.builder()
                        .roomId(id)
                        .facilityId(facilityInfoId)
                        .build();
                roomFacilities.add(roomFacility);
            }
            roomFacilityService.saveBatch(roomFacilities);              // 批量更新
        }
        // 4. 更新labelInfoList
        List<Long> labelInfoIds = roomSubmitVo.getLabelInfoIds();  // 获得标签信息
        if (!CollectionUtils.isEmpty(labelInfoIds)) {
            ArrayList<RoomLabel> roomLabelList = new ArrayList<>();  // 创建 房间标签 对象列表
            for (Long labelInfoId : labelInfoIds) {                 // 遍历赋值
                RoomLabel roomLabel = RoomLabel.builder()
                        .roomId(id)
                        .labelId(labelInfoId)
                        .build();
                roomLabelList.add(roomLabel);                    // 添加roomLable到列表
            }
            roomLabelService.saveBatch(roomLabelList);          // 批量更新
        }
        // 5. 更新paymentTypeList
        List<Long> paymentTypeIds = roomSubmitVo.getPaymentTypeIds();   // 获得支付信息列表
        if (!CollectionUtils.isEmpty(paymentTypeIds)) {
            ArrayList<RoomPaymentType> roomPaymentTypeList = new ArrayList<>(); // 创建 房间支付 对象列表
            for (Long paymentTypeId : paymentTypeIds) {                         // 遍历赋值
                RoomPaymentType roomPaymentType = RoomPaymentType.builder()
                        .roomId(id)
                        .paymentTypeId(paymentTypeId)
                        .build();
                roomPaymentTypeList.add(roomPaymentType);                        // 添加roomPaymentType到列表
            }
            roomPaymentTypeService.saveBatch(roomPaymentTypeList);              // 批量更新
        }
        // 6. 更新leaseTermList
        List<Long> leaseTermIds = roomSubmitVo.getLeaseTermIds();           // 获得租期信息列表
        if (!CollectionUtils.isEmpty(leaseTermIds)) {
            ArrayList<RoomLeaseTerm> roomLeaseTerms = new ArrayList<>();     // 创建 房间租期 对象列表
            for (Long leaseTermId : leaseTermIds) {                         // 遍历赋值
                RoomLeaseTerm roomLeaseTerm = RoomLeaseTerm.builder()
                        .roomId(id)
                        .leaseTermId(leaseTermId)
                        .build();
                roomLeaseTerms.add(roomLeaseTerm);                          // 添加roomLeaseTerm到列表
            }
            roomLeaseTermService.saveBatch(roomLeaseTerms);                 // 批量更新
        }
    }


    @Override
    public IPage<RoomItemVo> pageRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo) {
        return baseMapper.pageRoomItemByQuery(page, queryVo);
    }

    @Override
    public void removeRoomById(Long id) {
        //1.删除RoomInfo
        baseMapper.deleteById(id);

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
        // 5. 删除原有roompaymentTypeList
        LambdaQueryWrapper<RoomPaymentType> roomPaymentTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomPaymentTypeLambdaQueryWrapper.eq(RoomPaymentType::getRoomId, id);
        roomPaymentTypeService.remove(roomPaymentTypeLambdaQueryWrapper);
        // 6. 删除原有roomleaseTermList
        LambdaQueryWrapper<RoomLeaseTerm> roomLeaseTermLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomLeaseTermLambdaQueryWrapper.eq(RoomLeaseTerm::getRoomId, id);
        roomLeaseTermService.remove(roomLeaseTermLambdaQueryWrapper);
    }

    @Override
    public RoomDetailVo getRoomDetailById(Long id) {

        //1.查询RoomInfo
        RoomInfo roomInfo = baseMapper.selectById(id);

        //2.查询所属公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(roomInfo.getApartmentId());

        //3.查询graphInfoList
        List<GraphVo> graphVoList = graphInfoMapper.selectRoomListByItemTypeAndId(ItemType.ROOM,id);

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




