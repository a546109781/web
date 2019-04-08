package cc.nanjo.common.fate.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author: xw
 * 2019-4-8 17:42
 */
@Data
public class SubmitReq {

    private List<Submits> submits;
    private String isShow;

}
