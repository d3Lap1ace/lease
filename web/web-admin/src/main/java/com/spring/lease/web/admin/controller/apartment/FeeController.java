package com.spring.lease.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.spring.lease.common.result.Result;
import com.spring.lease.model.entity.FeeKey;
import com.spring.lease.model.entity.FeeValue;
import com.spring.lease.web.admin.service.FeeKeyService;
import com.spring.lease.web.admin.service.FeeValueService;
import com.spring.lease.web.admin.vo.fee.FeeKeyVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "房间杂费管理")
@RestController
@RequestMapping("/admin/fee")
public class FeeController {

    @Autowired
    private FeeKeyService feeKeyService;

    @Autowired
    private FeeValueService feeValueService;

    @Operation(summary = "保存或更新杂费名称")
    @PostMapping("key/saveOrUpdate")
    public Result saveOrUpdateFeeKey(@RequestBody FeeKey feeKey) {
        feeKeyService.saveOrUpdate(feeKey);
        return Result.ok();
    }

    @Operation(summary = "保存或更新杂费值")
    @PostMapping("value/saveOrUpdate")
    public Result saveOrUpdateFeeValue(@RequestBody FeeValue feeValue) {
        feeValueService.saveOrUpdate(feeValue);
        return Result.ok();
    }


    @Operation(summary = "查询全部杂费名称和杂费值列表")
    @GetMapping("list")
    public Result<List<FeeKeyVo>> feeInfoList() {
        List<FeeKeyVo>list = feeKeyService.listFeeInfo();
        return Result.ok(list);
    }

    @Operation(summary = "根据id删除杂费名称")
    @DeleteMapping("key/deleteById")
    public Result deleteFeeKeyById(@RequestParam Long feeKeyId) {
        // 删除名称
        feeKeyService.removeById(feeKeyId);
        // 根据feekeyid删除属性对应的属性值  创建lambdaQueryWrapper对象
        LambdaQueryWrapper<FeeValue> feeValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 封装条件
        feeValueLambdaQueryWrapper.eq(FeeValue::getFeeKeyId,feeKeyId);
        // 根据属性名id删除对应的属性值
        feeValueService.remove(feeValueLambdaQueryWrapper);
        return Result.ok();
    }

    @Operation(summary = "根据id删除杂费值")
    @DeleteMapping("value/deleteById")
    public Result deleteFeeValueById(@RequestParam Long id) {
        feeValueService.removeById(id);
        return Result.ok();
    }
}
