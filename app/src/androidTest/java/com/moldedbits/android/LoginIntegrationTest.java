package com.moldedbits.android;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.SmallTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.jakewharton.espresso.OkHttp3IdlingResource;
import com.moldedbits.android.api.APIProvider;
import com.moldedbits.android.login.LoginActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class LoginIntegrationTest {

    private String mUsername, mPassword;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(
            LoginActivity.class);

    @Before
    public void setup() {

    }

    @Test
    public void loginSuccess() {
        initValidCredentials();

        onView(withId(R.id.input_username))
                .perform(typeText(mUsername), closeSoftKeyboard());

        onView(withId(R.id.input_password))
                .perform(typeText(mPassword), closeSoftKeyboard());

        onView(withId(R.id.button_submit))
                .perform(click());


        IdlingResource resource = OkHttp3IdlingResource.create("OkHttp",
                APIProvider.getClient());
        Espresso.registerIdlingResources(resource);

        onView(allOf(withId(android.support.design.R.id.snackbar_text),
                withText("Logged in as test@moldedbits.com")))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        Espresso.unregisterIdlingResources(resource);
    }

    @Test
    public void loginFailure() {
        initInvalidCredentials();

        onView(withId(R.id.input_username))
                .perform(typeText(mUsername), closeSoftKeyboard());

        onView(withId(R.id.input_password))
                .perform(typeText(mPassword), closeSoftKeyboard());

        onView(withId(R.id.button_submit))
                .perform(click());


        IdlingResource resource = OkHttp3IdlingResource.create("OkHttp", APIProvider.getClient());
        Espresso.registerIdlingResources(resource);

        onView(allOf(withId(android.support.design.R.id.snackbar_text),
                withText("Invalid credentials")))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

        Espresso.unregisterIdlingResources(resource);
    }

    private void initValidCredentials() {
        mUsername = "test@moldedbits.com";
        mPassword = "foobarfoo";
    }

    private void initInvalidCredentials() {
        mUsername = "test@moldedbits.com";
        mPassword = "wrong";
    }
}
