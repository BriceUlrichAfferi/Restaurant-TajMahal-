package com.openclassrooms.tajmahal;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;

import org.junit.Before;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.CoreMatchers.endsWith;

import com.openclassrooms.tajmahal.ui.MainActivity;
import com.openclassrooms.tajmahal.ui.restaurant.ReviewFragment;

public class ReviewFragmentTest3 {

    @Before
    public void setUp() {
        ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void validateAndAddReview_AddReview() {
        // Delay for 2 seconds to allow the view to be created
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        // Perform action on the view
        onView(withText(endsWith("example_username"))).check(matches(isDisplayed()));

        onView(withId(R.id.rating17)).perform(ViewActions.click());
        onView(withText("Test user")).check(matches(isDisplayed()));
        onView(withText("4")).check(matches(isDisplayed()));
    }
}
