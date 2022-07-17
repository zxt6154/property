package com.ziroom.suzaku.main.service.impl;

import com.ziroom.suzaku.main.dao.SuzakuClassifyCustomValueMapper;
import com.ziroom.suzaku.main.service.SuzakuClassifyCustomValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 资产分类扩展属性值
 * @author xuzeyu
 */
@Service
public class SuzakuClassifyCustomValueServiceImpl  implements SuzakuClassifyCustomValueService {

    @Autowired
    private SuzakuClassifyCustomValueMapper classifyCustomValueMapper;


}
