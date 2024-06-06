package com.spring.lease.web.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.spring.lease.model.entity.BrowsingHistory;
import com.spring.lease.model.entity.UserInfo;
import com.spring.lease.web.app.mapper.BrowsingHistoryMapper;
import com.spring.lease.web.app.mapper.GraphInfoMapper;
import com.spring.lease.web.app.service.BrowsingHistoryService;
import com.spring.lease.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author liubo
 * @description 针对表【browsing_history(浏览历史)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
@Slf4j
public class BrowsingHistoryServiceImpl extends ServiceImpl<BrowsingHistoryMapper, BrowsingHistory>
        implements BrowsingHistoryService {


    @Resource
    private BrowsingHistoryMapper browsingHistoryMapper;

    @Override
    public IPage<HistoryItemVo> pageHistoryItemByUserId(IPage<HistoryItemVo> page, Long userId) {
        return browsingHistoryMapper.pageHistoryItemByUserId(page, userId);
    }

    /**
     * 实现保存游览记录功能
     * @param userId
     * @param id
     */
    @Override
    public void saveHistory(Long userId, Long id) {
        // 保存当前用户id 房间id  和当前时间
        BrowsingHistory browsingHistory = new BrowsingHistory(userId,id,new Date());

        // 查询是否来过
        LambdaQueryWrapper<BrowsingHistory> browsingHistoryLambdaQueryWrapper = new LambdaQueryWrapper<>();
        browsingHistoryLambdaQueryWrapper.eq(BrowsingHistory::getUserId,userId)
                .eq(BrowsingHistory::getId,id);
        Long count = browsingHistoryMapper.selectCount(browsingHistoryLambdaQueryWrapper);
        if (count > 0){
            LambdaUpdateWrapper<BrowsingHistory> browsingHistoryLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            browsingHistoryLambdaUpdateWrapper.eq(BrowsingHistory::getUserId,userId)
                    .eq(BrowsingHistory::getId,id);
            browsingHistoryMapper.update(browsingHistory,browsingHistoryLambdaUpdateWrapper);
        }else
            this.save(browsingHistory);



    }
}




