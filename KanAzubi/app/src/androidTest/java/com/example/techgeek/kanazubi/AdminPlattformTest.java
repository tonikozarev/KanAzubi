package com.example.techgeek.kanazubi;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.StringStartsWith.startsWith;

public class AdminPlattformTest {

    @Rule
    public ActivityTestRule<Login> mLoginTestRule = new ActivityTestRule<>(Login.class);
    private Login loginActivity = null;

    @Before
    public void setUp() throws Exception {
        loginActivity = mLoginTestRule.getActivity();
    }

    @Test
    public void adminPlattformTest() {
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

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.searchBtn)).perform(ViewActions.click());

        onView(withId(R.id.et_taskTitle)).perform(clearText());
        onView(withId(R.id.et_taskTitle)).perform(replaceText("Test Titel hinzufügen"));
        closeSoftKeyboard();
        onView(withId(R.id.et_taskDescr)).perform(clearText());
        onView(withId(R.id.et_taskDescr)).perform(replaceText("Test ist sehr wichtig!"));
        closeSoftKeyboard();
        onView(withId(R.id.et_taskReleased)).perform(clearText());
        onView(withId(R.id.et_taskReleased)).perform(replaceText("01.01.2004"));
        closeSoftKeyboard();
        onView(withId(R.id.et_taskTeam)).perform(clearText());
        onView(withId(R.id.et_taskTeam)).perform(replaceText("Software"));
        closeSoftKeyboard();

        onView(withId(R.id.addTaskBtn)).perform(ViewActions.click());
        onView(withId(R.id.logoutBtn)).perform(ViewActions.click());

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.et_email)).perform(typeText("tony@gmail.com"));
        closeSoftKeyboard();
        onView(withId(R.id.et_password)).perform(typeText("qwerty"));
        closeSoftKeyboard();
        onView(withId(R.id.loginBtn)).perform(ViewActions.click());
        onView(withText(R.string.successfulLogin)).inRoot(withDecorView(not(is(loginActivity.getWindow().getDecorView())))).check(matches(isDisplayed()));

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.spinnerTeam)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is("Software"))).perform(ViewActions.click());
        onView(withId(R.id.spinnerStatus)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is("Offen"))).perform(ViewActions.click());
        onView(withId(R.id.searchBtn)).perform(ViewActions.click());

        onData(anything()).inAdapterView(withId(R.id.listResult)).atPosition(1).check(matches(withText(startsWith("Test Titel hinzufügen"))));
        onData(anything()).inAdapterView(withId(R.id.listResult)).atPosition(1).perform(ViewActions.click());
        onView(withId(R.id.taskDescr)).check(matches(withText("Test ist sehr wichtig!")));
        onView(withId(R.id.taskReleased)).check(matches(withText("Veröffentlicht seit: 01.01.2004")));
        onView(withId(R.id.joinTaskBtn)).perform(ViewActions.click());

        onView(withId(R.id.spinnerTeam)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is("Software"))).perform(ViewActions.click());
        onView(withId(R.id.spinnerStatus)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is("In Bearbeitung"))).perform(ViewActions.click());
        onView(withId(R.id.searchBtn)).perform(ViewActions.click());

        onData(anything()).inAdapterView(withId(R.id.listResult)).atPosition(1).check(matches(withText(startsWith("Test Titel hinzufügen"))));
        onData(anything()).inAdapterView(withId(R.id.listResult)).atPosition(1).perform(ViewActions.click());

        onView(isRoot()).perform(ViewActions.pressBack());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.logoutBtn)).perform(ViewActions.click());
    }

    @After
    public void tearDown() throws Exception {
        loginActivity = null;
    }

}