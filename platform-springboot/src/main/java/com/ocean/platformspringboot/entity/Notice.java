package com.ocean.platformspringboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 通知公告表
 * </p>
 *
 * @author gjw
 * @since 2022-03-27
 */
@Getter
@Setter
  public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

      /**
     * 公告号
     */
      @TableId(value = "notice_id", type = IdType.AUTO)
      private Integer noticeId;

      /**
     * 公告标题
     */
      private String noticeTitle;

      /**
     * 公告内容
     */
      private String noticeContent;

      /**
     * 公告状态;0正常 1关闭
     */
      private Integer noticeStatus;



}
