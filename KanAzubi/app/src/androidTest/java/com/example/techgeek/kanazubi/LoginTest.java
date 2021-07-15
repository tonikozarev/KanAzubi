package com.example.techgeek.kanazubi;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertNotNull;

public class LoginTest {

    @Rule
    public ActivityTestRule<Login> mLoginTestRule = new ActivityTestRule<>(Login.class);
    private Login loginActivity = null;

    @Before
    public void setUp() throws Exception {
        loginActivity = mLoginTestRule.getActivity();
    }

    @Test
    public void loginAndMore() {

        //check if all EditTexts or Buttons are not null
        View view1 = loginActivity.findViewById(R.id.et_email);
        assertNotNull(view1);
        View view2 = loginActivity.findViewById(R.id.et_password);
        assertNotNull(view2);
        View view3 = loginActivity.findViewById(R.id.loginBtn);
        assertNotNull(view3);
        View view4 = loginActivity.findViewById(R.id.goToRegisterBtn);
        assertNotNull(view4);


        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.et_email)).perform(typeText("admin@dataport.de"));
        closeSoftKeyboard();
        onView(withId(R.id.et_password)).perform(typeText("123456"));
        closeSoftKeyboard();
        onView(withId(R.id.loginBtn)).perform(ViewActions.click());
        onView(withText(R.string.successfulLogin)).inRoot(withDecorView(not(is(loginActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        onView(withId(R.id.logoutBtn)).perform(ViewActions.click());
    }

    @Test
    public void enterIncorrectEmail() {

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.et_email)).perform(typeText("adeqeacdde"));
        closeSoftKeyboard();
        onView(withId(R.id.et_password)).perform(typeText("123456"));
        closeSoftKeyboard();
        onView(withId(R.id.loginBtn)).perform(ViewActions.click());
        onView(withText(R.string.incorrectEmail)).inRoot(withDecorView(not(is(loginActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void enterIncorrectPassword() {

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.et_email)).perform(typeText("admin@dataport.de"));
        closeSoftKeyboard();
        onView(withId(R.id.et_password)).perform(typeText("12345"));
        closeSoftKeyboard();
        onView(withId(R.id.loginBtn)).perform(ViewActions.click());
        onView(withText(R.string.incorrectPassword)).inRoot(withDecorView(not(is(loginActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void enterEmptyEmailOrPassword() {

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.et_email)).perform(typeText("admin@dataport.de"));
        closeSoftKeyboard();
        onView(withId(R.id.et_password)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.loginBtn)).perform(ViewActions.click());
        onView(withText(R.string.emptyEmailPassword)).inRoot(withDecorView(not(is(loginActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        onView(withId(R.id.et_email)).perform(clearText());

        onView(withId(R.id.et_email)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.et_password)).perform(typeText("123456"));
        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.loginBtn)).perform(ViewActions.click());
        onView(withText(R.string.incorrectEmail)).inRoot(withDecorView(not(is(loginActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
        onView(withId(R.id.et_password)).perform(clearText());

        onView(withId(R.id.et_email)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.et_password)).perform(typeText(""));
        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.loginBtn)).perform(ViewActions.click());
        onView(withText(R.string.incorrectEmail)).inRoot(withDecorView(not(is(loginActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void goToRegistrationAndBack() {
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.goToRegisterBtn)).perform(ViewActions.click());

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.goToLoginBtn)).perform(ViewActions.click());

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.loginBtn)).perform(ViewActions.click());
        onView(withText(R.string.incorrectEmail)).inRoot(withDecorView(not(is(loginActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        loginActivity = null;
    }
}