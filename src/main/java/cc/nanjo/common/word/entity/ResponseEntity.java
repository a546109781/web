package cc.nanjo.common.word.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xw
 * @date 2019/8/8 10:49
 */
@Data
@AllArgsConstructor
public class ResponseEntity {

    private String code;
    private String msg;
    private String data;

    public static ResponseEntity success(String data) {
        return new ResponseEntity("0", "成功", data);
    }

    public static ResponseEntity fail(String msg) {
        return new ResponseEntity("-1", msg, null);
    }

}
