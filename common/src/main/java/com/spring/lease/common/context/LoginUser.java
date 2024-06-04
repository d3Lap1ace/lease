package com.spring.lease.common.context;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @classname lease
 * @Auther d3Lap1ace
 * @Time 4/6/2024 18:38 周二
 * @description
 * @Version 1.0
 * From the Laplace Demon
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser {
    private Long userId;
    private String username;
}
