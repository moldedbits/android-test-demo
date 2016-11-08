package com.moldedbits.android.models.response;

import com.moldedbits.android.models.User;

import lombok.Getter;
import lombok.Setter;

public class LoginResponse extends BaseResponse {

    @Getter
    @Setter
    User user;
}
