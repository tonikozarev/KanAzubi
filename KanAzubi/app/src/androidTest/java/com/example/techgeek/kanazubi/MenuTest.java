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

public class MenuTest {

    @Rule
    public ActivityTestRule<Login> mLoginTestRule = new ActivityTestRule<>(Login.class);
    private Login loginActivity = null;

    @Before
    public void setUp() throws Exception {
        loginActivity = mLoginTestRule.getActivity();

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
    }

    @Test
    public void menuSpinnerAndListResultTest() {

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.spinnerTeam)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is("Software"))).perform(ViewActions.click());

        onView(withId(R.id.spinnerStatus)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is("In Bearbeitung"))).perform(ViewActions.click());

        onView(withId(R.id.searchBtn)).perform(ViewActions.click());

        onData(anything()).inAdapterView(withId(R.id.listResult)).atPosition(0).check(matches(withText(startsWith("Test"))));
        onData(anything()).inAdapterView(withId(R.id.listResult)).atPosition(0).perform(ViewActions.click());
        onView(withId(R.id.taskReleased)).check(matches(withText("Veröffentlicht seit: 23.08.2014")));

        onView(isRoot()).perform(ViewActions.pressBack());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.logoutBtn)).perform(ViewActions.click());
    }

    @Test
    public void joinTask_AcceptReadyDone_Test() {

        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.spinnerTeam)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is("Marketing"))).perform(ViewActions.click());
        onView(withId(R.id.spinnerStatus)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is("Offen"))).perform(ViewActions.click());
        onView(withId(R.id.searchBtn)).perform(ViewActions.click());

        onData(anything()).inAdapterView(withId(R.id.listResult)).atPosition(1).check(matches(withText(startsWith("Test2"))));
        onData(anything()).inAdapterView(withId(R.id.listResult)).atPosition(1).perform(ViewActions.click());
        onView(withId(R.id.taskTitle)).check(matches(withText("Test2")));
        onView(withId(R.id.joinTaskBtn)).perform(ViewActions.click());

        onView(withId(R.id.spinnerTeam)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is("Marketing"))).perform(ViewActions.click());
        onView(withId(R.id.spinnerStatus)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is("In Bearbeitung"))).perform(ViewActions.click());
        onView(withId(R.id.searchBtn)).perform(ViewActions.click());

        onData(anything()).inAdapterView(withId(R.id.listResult)).atPosition(0).check(matches(withText(startsWith("Test2"))));
        onData(anything()).inAdapterView(withId(R.id.listResult)).atPosition(0).perform(ViewActions.click());
        onView(withId(R.id.taskDescr)).check(matches(withText("Test In Bearbeitung")));

        onView(withId(R.id.joinTaskBtn)).perform(ViewActions.click());

        onView(withId(R.id.spinnerTeam)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is("Marketing"))).perform(ViewActions.click());
        onView(withId(R.id.spinnerStatus)).perform(ViewActions.click());
        onData(allOf(is(instanceOf(String.class)), is("Erledigt"))).perform(ViewActions.click());
        onView(withId(R.id.searchBtn)).perform(ViewActions.click());

        onData(anything()).inAdapterView(withId(R.id.listResult)).atPosition(0).check(matches(withText(startsWith("Test2"))));
        onData(anything()).inAdapterView(withId(R.id.listResult)).atPosition(0).perform(ViewActions.click());
        onView(withId(R.id.taskReleased)).check(matches(withText("Veröffentlicht seit: 14.02.2016")));

        onView(isRoot()).perform(ViewActions.pressBack());
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.logoutBtn)).perform(ViewActions.click());
    }

    @After
    public void tearDown() throws Exception {
        loginActivity = null;
    }
}