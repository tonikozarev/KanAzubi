package com.example.techgeek.kanazubi;

import android.support.test.espresso.action.ViewActions;
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

public class RegistrationTest {

    @Rule
    public ActivityTestRule<Register> mRegTestRule = new ActivityTestRule<>(Register.class);
    private Register regActivity = null;

    @Before
    public void setUp() throws Exception {
        regActivity = mRegTestRule.getActivity();
    }

    @Test
    public void registerAndMore() {

        //check if all EditTexts or Buttons are not null
        View view1 = regActivity.findViewById(R.id.addEmail);
        assertNotNull(view1);
        View view2 = regActivity.findViewById(R.id.addName);
        assertNotNull(view2);
        View view3 = regActivity.findViewById(R.id.addPassword);
        assertNotNull(view3);
        View view4 = regActivity.findViewById(R.id.confirmPassword);
        assertNotNull(view4);
        View view5 = regActivity.findViewById(R.id.registerBtn);
        assertNotNull(view5);

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.addEmail)).perform(typeText("test@dataport.de"));
        closeSoftKeyboard();
        onView(withId(R.id.addName)).perform(typeText("Testing Registration"));
        closeSoftKeyboard();
        onView(withId(R.id.addPassword)).perform(typeText("9a$$w0rD"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPassword)).perform(typeText("9a$$w0rD"));
        closeSoftKeyboard();
        onView(withId(R.id.registerBtn)).perform(ViewActions.click());
        onView(withText(R.string.successfulRegister)).inRoot(withDecorView(not(is(regActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));

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
        onView(withId(R.id.addEmail)).perform(typeText("test@dataport.de"));
        onView(withId(R.id.addEmail)).perform(clearText());
    }

    @Test
    public void registerInvalidEmail() {

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.addEmail)).perform(typeText("adsaefsa"));
        closeSoftKeyboard();
        onView(withId(R.id.addName)).perform(typeText("Testing Registration"));
        closeSoftKeyboard();
        onView(withId(R.id.addPassword)).perform(typeText("9a$$w0rD"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPassword)).perform(typeText("9a$$w0rD"));
        closeSoftKeyboard();
        onView(withId(R.id.registerBtn)).perform(ViewActions.click());
        onView(withText(R.string.registerInvalidEmail)).inRoot(withDecorView(not(is(regActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void registerEmailExists() {

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.addEmail)).perform(typeText("test@dataport.de"));
        closeSoftKeyboard();
        onView(withId(R.id.addName)).perform(typeText("Testing Registration"));
        closeSoftKeyboard();
        onView(withId(R.id.addPassword)).perform(typeText("9a$$w0rD"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPassword)).perform(typeText("9a$$w0rD"));
        closeSoftKeyboard();
        onView(withId(R.id.registerBtn)).perform(ViewActions.click());
        onView(withText(R.string.registerEmailExists)).inRoot(withDecorView(not(is(regActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void registerPasswordsNotMatching() {

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.addEmail)).perform(typeText("testing@dataport.de"));
        closeSoftKeyboard();
        onView(withId(R.id.addName)).perform(typeText("Testing Registration"));
        closeSoftKeyboard();
        onView(withId(R.id.addPassword)).perform(typeText("9a$$w0rD_&2"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPassword)).perform(typeText("9a$$w0rD"));
        closeSoftKeyboard();
        onView(withId(R.id.registerBtn)).perform(ViewActions.click());
        onView(withText(R.string.registerNotMatchingPasswords)).inRoot(withDecorView(not(is(regActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void registerEmptyFields() {

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.addEmail)).perform(typeText("testing@dataport.de"));
        closeSoftKeyboard();
        onView(withId(R.id.addName)).perform(typeText(""));
        closeSoftKeyboard();
        onView(withId(R.id.addPassword)).perform(typeText("9a$$w0rD"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPassword)).perform(typeText("9a$$w0rD"));
        closeSoftKeyboard();
        onView(withId(R.id.registerBtn)).perform(ViewActions.click());
        onView(withText(R.string.registerEmptyFields)).inRoot(withDecorView(not(is(regActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @Test
    public void registerPasswordLessThan6Chars() {

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.addEmail)).perform(typeText("testing@dataport.de"));
        closeSoftKeyboard();
        onView(withId(R.id.addName)).perform(typeText("Testing Registration"));
        closeSoftKeyboard();
        onView(withId(R.id.addPassword)).perform(typeText("9a$$w"));
        closeSoftKeyboard();
        onView(withId(R.id.confirmPassword)).perform(typeText("9a$$w"));
        closeSoftKeyboard();
        onView(withId(R.id.registerBtn)).perform(ViewActions.click());
        onView(withText(R.string.registerTooShortPassword)).inRoot(withDecorView(not(is(regActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        regActivity = null;
    }
}