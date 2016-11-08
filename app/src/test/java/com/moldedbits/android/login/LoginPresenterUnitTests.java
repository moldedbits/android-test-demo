package com.moldedbits.android.login;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class LoginPresenterUnitTests {

    private LoginPresenter mPresenter;

    @Before
    public void setup() {
        mPresenter = new LoginPresenter(null, null);
    }

    @Test
    public void validationTest() {
        assertThat(mPresenter.isValidUsername(""), is(false));
        assertThat(mPresenter.isValidUsername("abc"), is(false));
        assertThat(mPresenter.isValidUsername("a@b.com"), is(true));
    }
}