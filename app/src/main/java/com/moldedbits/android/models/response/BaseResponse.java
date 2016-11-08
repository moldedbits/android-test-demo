package com.moldedbits.android.models.response;

import lombok.Getter;
import lombok.Setter;

public class BaseResponse {

    private static final String FAILURE = "failure";

    @Getter
    @Setter
    String status;

    @Getter
    @Setter
    String error;

    public boolean isError() {
        return status != null && FAILURE.equalsIgnoreCase(status);
    }
}
