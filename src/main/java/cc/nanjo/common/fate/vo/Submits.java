package cc.nanjo.common.fate.vo;

import lombok.Data;

/**
 * @Author: xw
 * 2019-4-8 17:49
 */
@Data
public class Submits {
    private Long id;
    private String preInput;
    private String nowInput;

    public Submits(Long id, String preInput, String nowInput) {
        this.id = id;
        this.preInput = preInput;
        this.nowInput = nowInput;
    }
}