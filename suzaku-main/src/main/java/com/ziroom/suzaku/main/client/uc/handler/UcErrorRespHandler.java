package com.ziroom.suzaku.main.client.uc.handler;

import com.ziroom.suzaku.main.client.uc.model.UcBaseRespModel;
import com.ziroom.tech.exception.HttpBusinessException;
import com.ziroom.tech.exception.HttpException;
import com.ziroom.tech.http.HttpRespHandler;
import org.springframework.stereotype.Component;
import java.util.Objects;

/**
 * @author xuzeyu
 */
@Component
public class UcErrorRespHandler implements HttpRespHandler<UcBaseRespModel<?>> {

    @Override
    public void handle(UcBaseRespModel<?> respModel) {
        if(Objects.isNull(respModel)) {
            throw new HttpException("-1", "uc interface result is null");
        }
        if (!"200".equals(respModel.getCode())) {
            throw new HttpBusinessException(respModel.getCode(), respModel.getMessage());
        }

    }
}
