package com.spring.lease.web.app.vo.apartment;


import com.spring.lease.model.entity.ApartmentInfo;
import com.spring.lease.model.entity.GraphInfo;
import com.spring.lease.model.entity.LabelInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "App端公寓信息")
public class ApartmentItemVo extends ApartmentInfo {

    private List<LabelInfo> labelInfoList;

    private List<GraphInfo> graphVoList;

    private BigDecimal minRent;
}
