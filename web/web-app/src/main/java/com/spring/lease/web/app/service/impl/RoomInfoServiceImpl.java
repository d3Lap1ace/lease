package com.spring.lease.web.app.service.impl;

import com.spring.lease.common.context.LoginUserContext;
import com.spring.lease.model.entity.*;
import com.spring.lease.model.enums.ItemType;
import com.spring.lease.model.enums.LeaseStatus;
import com.spring.lease.web.app.mapper.*;
import com.spring.lease.web.app.service.*;
import com.spring.lease.web.app.vo.apartment.ApartmentItemVo;
import com.spring.lease.web.app.vo.attr.AttrValueVo;
import com.spring.lease.web.app.vo.fee.FeeValueVo;
import com.spring.lease.web.app.vo.graph.GraphVo;
import com.spring.lease.web.app.vo.room.RoomDetailVo;
import com.spring.lease.web.app.vo.room.RoomItemVo;
import com.spring.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
@Slf4j
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

    @Resource
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private BrowsingHistoryService browsingHistoryService;
    @Autowired
    private ApartmentInfoService apartmentInfoService;
    @Resource
    private GraphInfoMapper graphInfoMapper;
    @Resource
    private AttrValueMapper attrValueMapper;
    @Resource
    private FacilityInfoMapper facilityInfoMapper;
    @Resource
    private LabelInfoMapper labelInfoMapper;
    @Resource
    private PaymentTypeMapper paymentTypeMapper;
    @Resource
    private LeaseTermMapper leaseTermMapper;
    @Resource
    private FeeValueMapper feeValueMapper;
    @Resource
    private LeaseAgreementMapper leaseAgreementMapper;





    /**
     * 分页查询房间列表
     * @param page
     * @param queryVo
     * @return
     */
    @Override
    public IPage<RoomItemVo> pageRoomItemByQuery(IPage<RoomItemVo> page, RoomQueryVo queryVo) {
        return roomInfoMapper.pageRoomItemByQuery(page,queryVo);
    }

    /**
     * 根据id详细查询房间详细列表
     * @param id 房间id
     * @return
     */
    @Override
    public RoomDetailVo getDetailByid(Long id) {
        //1.查询RoomInfo
        RoomInfo roomInfo = roomInfoMapper.selectRoomById(id);
        if (roomInfo == null) {
            return null;
        }

        //2.查询所属公寓信息
        ApartmentItemVo apartmentItemVo = apartmentInfoService.getApartmentItemVoById(roomInfo.getApartmentId());

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

        //9.查询费用项目信息
        List<FeeValueVo> feeValueVoList = feeValueMapper.selectListByApartmentId(roomInfo.getApartmentId());

        //10.查询房间入住状态
        LambdaQueryWrapper<LeaseAgreement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LeaseAgreement::getRoomId, roomInfo.getId());
        queryWrapper.in(LeaseAgreement::getStatus, LeaseStatus.SIGNED, LeaseStatus.WITHDRAWING);
        Long singedCount = leaseAgreementMapper.selectCount(queryWrapper);

        RoomDetailVo appRoomDetailVo = new RoomDetailVo();
        BeanUtils.copyProperties(roomInfo, appRoomDetailVo);
        appRoomDetailVo.setIsDelete(roomInfo.getIsDeleted() == 1);
        appRoomDetailVo.setIsCheckIn(singedCount > 0);

        appRoomDetailVo.setApartmentItemVo(apartmentItemVo);
        appRoomDetailVo.setGraphVoList(graphVoList);
        appRoomDetailVo.setAttrValueVoList(attrvalueVoList);
        appRoomDetailVo.setFacilityInfoList(facilityInfoList);
        appRoomDetailVo.setLabelInfoList(labelInfoList);
        appRoomDetailVo.setPaymentTypeList(paymentTypeList);
        appRoomDetailVo.setFeeValueVoList(feeValueVoList);
        appRoomDetailVo.setLeaseTermList(leaseTermList);

        // 添加游览记录
        browsingHistoryService.saveHistory(LoginUserContext.getLoginUser().getUserId(), id);

        return appRoomDetailVo;
    }

    /**
     * 根据公寓id查询房间列表
     * @param page
     * @param id
     * @return
     */
    @Override
    public IPage<RoomItemVo> pageItemByApartmentId(IPage<RoomItemVo> page, Long id) {
        return roomInfoMapper.pageItemByApartmentId(page, id);
    }
}




