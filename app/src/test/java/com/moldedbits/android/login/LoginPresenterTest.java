package com.moldedbits.android.login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    LoginView mLoginView;

    private LoginPresenter mPresenter;

    @Before
    public void setup() {
        mPresenter = new LoginPresenter(mLoginView, null);
    }

    @Test
    public void loginInvalidUsername() {
        mPresenter.login("abc", "abc");
        verify(mLoginView, times(1)).setUsernameError("Invalid input");
    }

    @Test
    public void loginEmptyUsername() {
        mPresenter.login("", "abc");
        verify(mLoginView, times(1)).setUsernameError("Required");
    }

    @Test
    public void loginEmptyPassword() {
        mPresenter.login("a@b.c", "");
        verify(mLoginView, times(1)).setPasswordError("Required");
    }
}
