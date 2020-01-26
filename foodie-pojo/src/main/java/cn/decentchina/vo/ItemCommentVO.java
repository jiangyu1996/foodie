package cn.decentchina.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 用于展示商品评价的VO
 */
@Data
public class ItemCommentVO {

    private Integer commentLevel;
    private String content;
    private String specName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    private String userFace;
    private String nickname;

}
