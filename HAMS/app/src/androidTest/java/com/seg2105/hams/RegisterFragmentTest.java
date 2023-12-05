package com.seg2105.hams;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.annotation.UiThreadTest;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.seg2105.hams.UI.MainActivity;
import com.seg2105.hams.UI.RegisterFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest
public class RegisterFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void launchFragment() {
        // Launch the RegisterFragment
        ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.btn_register)).perform(click());
    }

    @Test
    @UiThreadTest
    public void checkFirstName() throws Exception {
        // Launch the RegisterFragment
        FragmentScenario.launchInContainer(RegisterFragment.class);

        // Scroll to the btn_register button before interacting with it
        onView(withId(R.id.btn_register)).perform(ViewActions.scrollTo());

        // Now perform the interactions
        onView(withId(R.id.doctor)).perform(click());
        onView(withId(R.id.first_name)).perform(replaceText("user1"));
        onView(withId(R.id.btn_register)).perform(click());

        // Validate your assertions here
        // ...
    }

    // Additional test cases
    @Test
    @UiThreadTest
    public void additionalTestCase1() throws Exception {
        // Similar setup as above
        FragmentScenario.launchInContainer(RegisterFragment.class);

        // Scroll to the btn_register button before interacting with it
        onView(withId(R.id.btn_register)).perform(ViewActions.scrollTo());

        // Perform interactions and assertions for this test case
        // ...
    }

    @Test
    @UiThreadTest
    public void additionalTestCase2() throws Exception {
        // Similar setup as above
        FragmentScenario.launchInContainer(RegisterFragment.class);

        // Scroll to the btn_register button before interacting with it
        onView(withId(R.id.btn_register)).perform(ViewActions.scrollTo());

        // Perform interactions and assertions for this test case
        // ...
    }

    @Test
    @UiThreadTest
    public void additionalTestCase3() throws Exception {
        // Similar setup as above
        FragmentScenario.launchInContainer(RegisterFragment.class);

        // Scroll to the btn_register button before interacting with it
        onView(withId(R.id.btn_register)).perform(ViewActions.scrollTo());

        // Perform interactions and assertions for this test case
        // ...
    }
}