package com.ocean.platformspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 奖励品兑换表
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
@Getter
@Setter
@TableName("reward_exchange")
public class RewardExchange implements Serializable {

      private static final long serialVersionUID = 1L;

      /**
     * 兑换号
     */
      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      /**
     * 用户号
     */
      private Integer userId;

      /**
     * 奖励号
     */
      private Integer rewardId;

      /**
     * 兑换数量
     */
      private Integer rewardNum;

      /**
       * 是否交付;0为未交付，1未已交付
       */
      private Integer status;

      /**
     * 获取奖励时间
     */
      private LocalDateTime ingestTime;


      @TableField(exist = false)
      private String userName;
      @TableField(exist = false)
      private String rewardName;
      @TableField(exist = false)
      private Integer rewardPoint;


}
