package com.moldedbits.android.login;

import com.moldedbits.android.utils.StaticUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
@PrepareForTest( { StaticUtils.class })
public class LoginPresenterStaticTest {
    @Mock
    LoginView mLoginView;

    private LoginPresenter mPresenter;

    @Captor
    ArgumentCaptor<StaticUtils.CustomCallback> mCustomCallback;

    @Before
    public void setup() {
        mPresenter = new LoginPresenter(mLoginView, null);
    }

    @Test
    public void testStaticMethod() {
        PowerMockito.mockStatic(StaticUtils.class);
        PowerMockito.when(StaticUtils.add(anyInt(), anyInt())).thenReturn(1);
        mPresenter.useStaticCall();
        verify(mLoginView, times(1)).setUsernameError("1");
    }

    @Test
    public void testStaticMethodWithCallback() {
        PowerMockito.mockStatic(StaticUtils.class);
        mPresenter.useStaticWithCallback();
        PowerMockito.verifyStatic();
        StaticUtils.staticWithCallback(mCustomCallback.capture());
        mCustomCallback.getValue().onResponse("custom response");
        verify(mLoginView, times(1)).setUsernameError("custom response");
    }
}
