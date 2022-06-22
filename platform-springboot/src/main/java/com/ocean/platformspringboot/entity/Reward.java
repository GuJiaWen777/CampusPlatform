package com.ocean.platformspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 奖励品表
 * </p>
 *
 * @author gjw
 * @since 2022-03-18
 */
@Getter
@Setter
  public class Reward implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 奖励品号
     */
      @TableId(value = "reward_id", type = IdType.AUTO)
      private Integer rewardId;

      /**
     * 奖励品名称
     */
      private String rewardName;

      /**
     * 奖励品库存
     */
      private Integer rewardNum;

      /**
     * 是否为空;0为不可兑换，1为可兑换
     */
      private Integer rewardOver;

      /**
     * 奖励品所需积分
     */
      private Integer rewardPoint;

     /**
      * 奖励图片地址
      */
      private String picture;

      @TableField(exist = false)
      private Integer exchangeNum;



}
