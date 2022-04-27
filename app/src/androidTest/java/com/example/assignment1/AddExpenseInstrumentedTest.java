package com.example.assignment1;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.rule.ActivityTestRule;
import org.junit.Rule;
import org.junit.Test;

public class AddExpenseInstrumentedTest {
    @Rule
    public ActivityTestRule<AddExpenseActivity> mActivityTestRule = new ActivityTestRule<>(AddExpenseActivity.class);

    @Test
    public void view_isCorrect() {
        onView(withId(R.id.expense_spinner)).check(matches(isDisplayed()));
        onView(withId(R.id.editText_expense_title)).check(matches(isDisplayed()));
        onView(withId(R.id.editText_expense)).check(matches(isDisplayed()));
        onView(withId(R.id.button_add_expense)).check(matches(isDisplayed()));
    }
}
