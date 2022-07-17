package com.ziroom.suzaku.main.client.ehr.handler;

import com.ziroom.suzaku.main.client.ehr.model.EhrBaseRespModel;
import com.ziroom.tech.exception.HttpBusinessException;
import com.ziroom.tech.exception.HttpException;
import com.ziroom.tech.http.HttpRespHandler;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author xuzeyu
 */
@Component
public class EhrErrorRespHandler implements HttpRespHandler<EhrBaseRespModel<?>> {

    @Override
    public void handle(EhrBaseRespModel<?> respModel) {
        if(Objects.isNull(respModel)) {
            throw new HttpException("-1", "ehr interface result is null");
        }
        if (respModel.getErrorCode()!=20000) {
            throw new HttpBusinessException(respModel.getErrorCode().toString(), respModel.getMessage());
        }

    }
}
