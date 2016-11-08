package com.moldedbits.android.login;

import com.moldedbits.android.models.User;

interface LoginView {

    String getUsername();

    String getPassword();

    void setUsernameError(String message);

    void setPasswordError(String message);

    void onLoginSuccess(User user);

    void onLoginFailure(String message);
}
