package com.spring.lease.web.app.service.impl;

import com.spring.lease.model.entity.*;
import com.spring.lease.model.enums.ItemType;
import com.spring.lease.web.app.mapper.*;
import com.spring.lease.web.app.service.LeaseAgreementService;
import com.spring.lease.web.app.vo.agreement.AgreementDetailVo;
import com.spring.lease.web.app.vo.agreement.AgreementItemVo;
import com.spring.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {

    @Resource
    private LeaseAgreementMapper leaseAgreementMapper;
    @Resource
    ApartmentInfoMapper apartmentInfoMapper;
    @Resource
    RoomInfoMapper roomInfoMapper;
    @Resource
    GraphInfoMapper graphInfoMapper;
    @Resource
    LeaseTermMapper leaseTermMapper;
    @Resource
    PaymentTypeMapper paymentTypeMapper;
    /**
     * 获得个人预约信息
     * @param username 手机号
     * @return
     */
    @Override
    public List<AgreementItemVo> getListAgreementByUserId(String username) {
        return leaseAgreementMapper.getListAgreementByUserId(username);

    }

    /**
     * 根据id获取租约详细信息
     * @param id
     * @return
     */
    @Override
    public AgreementDetailVo getAgreementDetailById(Long id) {
        //1.查询租约信息
        LeaseAgreement leaseAgreement = leaseAgreementMapper.selectById(id);
        if (leaseAgreement == null) {
            return null;
        }

        //2.查询公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectApartmentById(leaseAgreement.getApartmentId());

        //3.查询房间信息

        RoomInfo roomInfo = roomInfoMapper.selectRoomById(leaseAgreement.getRoomId());

        //4.查询公寓图片

        List<GraphVo> apartmentGraphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, leaseAgreement.getApartmentId());

        //5.查询房间图片
        List<GraphVo> roomGraphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM, leaseAgreement.getRoomId());

        //6.查询租期信息
        LeaseTerm leaseTerm = leaseTermMapper.selectLeaseTermById(leaseAgreement.getLeaseTermId());

        //7.查询支付方式
        PaymentType paymentType = paymentTypeMapper.selectPaymentTypeById(leaseAgreement.getPaymentTypeId());

        AgreementDetailVo agreementDetailVo = new AgreementDetailVo();
        BeanUtils.copyProperties(leaseAgreement, agreementDetailVo);
        agreementDetailVo.setApartmentName(apartmentInfo.getName());
        agreementDetailVo.setRoomNumber(roomInfo.getRoomNumber());
        agreementDetailVo.setApartmentGraphVoList(apartmentGraphVoList);
        agreementDetailVo.setRoomGraphVoList(roomGraphVoList);
        agreementDetailVo.setPaymentTypeName(paymentType.getName());
        agreementDetailVo.setLeaseTermMonthCount(leaseTerm.getMonthCount());
        agreementDetailVo.setLeaseTermUnit(leaseTerm.getUnit());

        return agreementDetailVo;
    }
}




