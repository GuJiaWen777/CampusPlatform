package com.ocean.platformspringboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 成就记录表
 * </p>
 *
 * @author gjw
 * @since 2022-03-27
 */
@Getter
@Setter
@AllArgsConstructor
@TableName("achv_record")
public class AchvRecord implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 用户号
     */
        private Integer userId;

      /**
     * 成就号
     */
        private Integer achvId;

      /**
     * 获得成就时间
     */
      private LocalDateTime achvTime;


}
