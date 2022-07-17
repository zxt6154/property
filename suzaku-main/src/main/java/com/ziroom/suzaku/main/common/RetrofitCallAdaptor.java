package com.ziroom.suzaku.main.common;

import com.ziroom.suzaku.main.constant.ErrorCode;
import com.ziroom.suzaku.main.exception.SuzakuBussinesException;
import lombok.experimental.UtilityClass;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@UtilityClass
public class RetrofitCallAdaptor {

    public static <T> T execute(Call<T> retrofitCall) {
        try {
            Response<T> response = retrofitCall.execute();
            if (response.isSuccessful()) {
                return response.body();
            }
            ResponseBody errorBody = response.errorBody();
            throw new SuzakuBussinesException(errorBody == null ? "请求失败" : errorBody.string(), ErrorCode.NET_REQUEST_SUSPEND.getErrMessage());
        } catch (IOException e) {
            throw new SuzakuBussinesException(e.getMessage(), ErrorCode.NET_REQUEST_SUSPEND.getErrMessage());
        }
    }
}
