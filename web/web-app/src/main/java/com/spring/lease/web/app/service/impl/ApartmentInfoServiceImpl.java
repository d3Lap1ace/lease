package com.spring.lease.web.app.service.impl;

import com.spring.lease.model.entity.ApartmentInfo;
import com.spring.lease.model.entity.FacilityInfo;
import com.spring.lease.model.entity.LabelInfo;
import com.spring.lease.model.enums.ItemType;
import com.spring.lease.web.app.mapper.*;
import com.spring.lease.web.app.service.ApartmentInfoService;
import com.spring.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.spring.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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


}




