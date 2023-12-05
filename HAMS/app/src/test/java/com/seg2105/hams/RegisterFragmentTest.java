package com.seg2105.hams;

import com.seg2105.hams.UI.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4ClassRunner.class)
public class RegisterFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkFirstName() throws Exception {
        ActivityScenario.launch(RegisterFragment.class);
        onView(withId(R.id.doctor)).perform(click());
        onView(withId(R.id.first_name)).perform(replaceText("user1"));
        onView(withId(R.id.btn_register)).perform(click());

        // Validate your assertions here
        // For example:
        // onView(withId(R.id.some_view_id)).check(matches(isDisplayed()));
        // assertNotNull(mActivity.findViewById(R.id.some_other_view_id));
        // Perform assertions based on the behavior of your app
    }

    // Add more test methods as needed for other functionality
}
