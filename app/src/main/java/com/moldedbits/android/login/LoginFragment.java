package com.moldedbits.android.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.moldedbits.android.BaseFragment;
import com.moldedbits.android.R;
import com.moldedbits.android.api.APIProvider;
import com.moldedbits.android.api.APIService;
import com.moldedbits.android.models.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginFragment extends BaseFragment implements LoginView {

    private LoginPresenter mPresenter;

    @Bind(R.id.input_username)
    EditText mUsernameInput;

    @Bind(R.id.input_password)
    EditText mPasswordInput;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APIService apiService = APIProvider.getService();
        mPresenter = new LoginPresenter(this, apiService);
    }

    @OnClick(R.id.button_submit)
    public void onLoginClicked() {
        mPresenter.login();
    }

    @Override
    public String getUsername() {
        return mUsernameInput.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordInput.getText().toString();
    }

    @Override
    public void setUsernameError(String message) {
        mUsernameInput.setError(message);
        mUsernameInput.requestFocus();
    }

    @Override
    public void setPasswordError(String message) {
        mPasswordInput.setError(message);
        mPasswordInput.requestFocus();
    }

    @Override
    public void onLoginSuccess(User user) {
        Snackbar.make(mUsernameInput, "Logged in as " + user.getEmail(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onLoginFailure(String message) {
        Snackbar.make(mUsernameInput, message, Snackbar.LENGTH_LONG).show();
    }
}
