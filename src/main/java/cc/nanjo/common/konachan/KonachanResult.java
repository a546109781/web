package cc.nanjo.common.konachan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xw
 * @date 2020/3/16/016 17:45
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class KonachanResult {

    private Boolean success;
    private String msg;
    private String console;
    private String pointer;

}
