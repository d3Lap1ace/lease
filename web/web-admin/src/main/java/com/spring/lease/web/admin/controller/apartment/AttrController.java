package com.spring.lease.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.lease.common.result.Result;
import com.spring.lease.model.entity.AttrKey;
import com.spring.lease.model.entity.AttrValue;
import com.spring.lease.web.admin.service.AttrKeyService;
import com.spring.lease.web.admin.service.AttrValueService;
import com.spring.lease.web.admin.vo.attr.AttrKeyVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "房间属性管理")
@RestController
@RequestMapping("/admin/attr")
public class AttrController {
    @Autowired
    private AttrKeyService attrKeyService;
    @Autowired
    private AttrValueService attrValueService;

    @Operation(summary = "新增或更新属性名称")
    @PostMapping("key/saveOrUpdate")
    public Result saveOrUpdateAttrKey(@RequestBody AttrKey attrKey) {
        attrKeyService.saveOrUpdate(attrKey);
        return Result.ok();
    }

    @Operation(summary = "新增或更新属性值")
    @PostMapping("value/saveOrUpdate")
    public Result saveOrUpdateAttrValue(@RequestBody AttrValue attrValue) {
        attrValueService.saveOrUpdate(attrValue);
        return Result.ok();
    }


    @Operation(summary = "查询全部属性名称和属性值列表")
    @GetMapping("list")
    public Result<List<AttrKeyVo>> listAttrInfo() {
        // 调用AttrkeyService中查询所有属性名并带偶属性值的方法
        List<AttrKeyVo> list = attrKeyService.getAttrKeyVoList();
        return Result.ok(list);
    }

    @Operation(summary = "根据id删除属性名称")
    @DeleteMapping("key/deleteById")
    public Result removeAttrKeyById(@RequestParam Long attrKeyId) {
        //删除attrKey
        attrKeyService.removeById(attrKeyId);
        QueryWrapper<AttrValue> attrKeyVoQueryWrapper = new QueryWrapper<>();
        attrKeyVoQueryWrapper.eq("attr_key_id",attrKeyId);
        attrValueService.remove(attrKeyVoQueryWrapper);
        return Result.ok();
    }

    @Operation(summary = "根据id删除属性值")
    @DeleteMapping("value/deleteById")
    public Result removeAttrValueById(@RequestParam Long id) {
        attrValueService.removeById(id);
        return Result.ok();
    }

}
