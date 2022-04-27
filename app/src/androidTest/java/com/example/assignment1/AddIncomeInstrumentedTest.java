package com.example.assignment1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;

public class AddIncomeInstrumentedTest {
    @Rule
    public ActivityTestRule<AddIncomeActivity> mActivityTestRule = new ActivityTestRule<>(AddIncomeActivity.class);

    @Test
    public void view_isCorrect() {
        onView(withId(R.id.income_spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.editText_income_title)).check(matches(isDisplayed()));
        onView(withId(R.id.editText_income)).check(matches(isDisplayed()));
        onView(withId(R.id.button_add_income)).check(matches(isDisplayed()));
    }
}
