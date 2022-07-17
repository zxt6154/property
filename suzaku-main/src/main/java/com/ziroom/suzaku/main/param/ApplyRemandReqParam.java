package com.ziroom.suzaku.main.param;

import com.ziroom.suzaku.main.model.vo.SuzakuRemandItemVo;
import com.ziroom.suzaku.main.model.vo.SuzakuRemandVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

/**
 * 退库单添加请求参数
 * @author xuzeyu
 */
@Getter
@Setter
@NoArgsConstructor
public class ApplyRemandReqParam  extends SuzakuRemandVo implements Serializable {

    private List<SuzakuRemandItemVo> suzakuRemandItemVos;

}
