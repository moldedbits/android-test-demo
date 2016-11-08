package com.moldedbits.android.login;

import com.moldedbits.android.api.APIService;
import com.moldedbits.android.models.User;
import com.moldedbits.android.models.request.LoginRequest;
import com.moldedbits.android.models.response.LoginResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterMockApiTest {

    @Mock
    LoginView mLoginView;

    @Mock
    APIService mAPIService;

    @Mock
    Call<LoginResponse> mCall;

    @Captor
    ArgumentCaptor<Callback<LoginResponse>> mCallback;

    private LoginPresenter mPresenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mPresenter = new LoginPresenter(mLoginView, mAPIService);

        when(mAPIService.login(any(LoginRequest.class)))
                .thenReturn(mCall);
    }

    @Test
    public void loginSuccess() {
        mPresenter.login("a@b.c", "abc");

        verify(mCall).enqueue(mCallback.capture());
        mCallback.getValue().onResponse(mCall, getMockSuccessResponse());

        verify(mLoginView, times(1)).onLoginSuccess(any(User.class));
    }

    @Test
    public void loginFailure() {
        mPresenter.login("a@b.c", "abc");

        verify(mCall).enqueue(mCallback.capture());
        mCallback.getValue().onResponse(mCall, getMockFailureResponse());

        verify(mLoginView, times(1)).onLoginFailure("Invalid credentials");
    }

    private Response<LoginResponse> getMockSuccessResponse() {
        LoginResponse successResponse = new LoginResponse();
        successResponse.setUser(getMockUser());
        return Response.success(successResponse);
    }

    private Response<LoginResponse> getMockFailureResponse() {
        String errorMessage = "{" +
                "  \"status\": \"failure\"," +
                "  \"error\": \"Invalid credentials\"" +
                "}";
        ResponseBody responseBody = ResponseBody.create(MediaType.parse("string/json"),
                errorMessage.getBytes());
        return Response.error(401, responseBody);
    }

    private User getMockUser() {
        return new User(1, "Mock User");
    }
}
