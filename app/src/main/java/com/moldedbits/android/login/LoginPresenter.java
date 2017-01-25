package com.moldedbits.android.login;

import com.moldedbits.android.api.APIService;
import com.moldedbits.android.models.User;
import com.moldedbits.android.models.request.LoginRequest;
import com.moldedbits.android.models.response.LoginResponse;
import com.moldedbits.android.utils.StaticUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

class LoginPresenter implements Callback<LoginResponse> {

    private LoginView mView;

    private APIService mAPIService;

    LoginPresenter(LoginView view, APIService apiService) {
        mView = view;
        mAPIService = apiService;
    }

    void login() {
        String username = mView.getUsername();
        String password = mView.getPassword();

        login(username, password);
    }

    void login(String username, String password) {
        if (username == null || username.length() == 0) {
            mView.setUsernameError("Required");
            return;
        }

        if (!isValidUsername(username)) {
            mView.setUsernameError("Invalid input");
            return;
        }

        if (password == null || password.length() == 0) {
            mView.setPasswordError("Required");
            return;
        }

        LoginRequest request = new LoginRequest(username, password);
        Call<LoginResponse> call = mAPIService.login(request);
        call.enqueue(this);
    }

    // Checks for valid email address
    boolean isValidUsername(String username) {
        Pattern pattern = Pattern.compile("\\A[^@]+@([^@\\.]+\\.)+[^@\\.]+\\z");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

    @Override
    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
        if (!response.isSuccessful()) {
            mView.onLoginFailure("Invalid credentials");
        } else {
            User user = response.body().getUser();
            mView.onLoginSuccess(user);
        }
    }

    @Override
    public void onFailure(Call<LoginResponse> call, Throwable t) {
        mView.onLoginFailure(t.getMessage());
        Timber.e(t, "Could not login");
    }

    void useStaticCall() {
        int sum = StaticUtils.add(1, 2);
        mView.setUsernameError(String.valueOf(sum));
    }

    void useStaticWithCallback() {
        StaticUtils.CustomCallback callback = new StaticUtils.CustomCallback() {
            @Override
            public void onResponse(String response) {
                mView.setUsernameError(response);
            }
        };
        StaticUtils.staticWithCallback(callback);
    }
}
