package com.spring.lease.web.app.service;

import com.spring.lease.model.entity.BrowsingHistory;
import com.spring.lease.web.app.vo.history.HistoryItemVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【browsing_history(浏览历史)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface BrowsingHistoryService extends IService<BrowsingHistory> {
    /**
     * 分页查询游览记录
     * @param page
     * @param userId
     * @return
     */
    IPage<HistoryItemVo> pageHistoryItemByUserId(IPage<HistoryItemVo> page, Long userId);

    /**
     * 保存游览记录
     * @param userId
     * @param id
     */
    void saveHistory(Long userId, Long id);
}
