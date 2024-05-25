package com.openclassrooms.tajmahal.ui;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RatingBar;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.openclassrooms.tajmahal.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)

//  this is a large test
public class ReviewFragmentTest {

    /*
     This marks the ActivityScenarioRule
     which launches the specified activity (MainActivity)
     before each test and closes it after each test
     */
    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    /*
    This is the specific method to be testesd by the testing framewor
     */

    @Test
    public void reviewFrTest2() {

        //Click performed on the TextView "Laisser un avis"
        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.laisserUnAvis), withText("Laisser un avis"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                12)));
        materialTextView.perform(scrollTo(), click());


        /* Scendario 1:
        No text ins entered nor a rate selected and click action is performed on the chip
        */
        ViewInteraction chip = onView(
                allOf(withId(R.id.valider), withText("Valider"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                4),
                        isDisplayed()));
        chip.perform(click());


        /* Scendario 2:
        Replaces the current text with the specified text ("Good Restaurant)
        and closes the soft keyboard after entering the text*/
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.textField),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.comment),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Good Restaurant"), closeSoftKeyboard());
//
        //Finds the RatingBar with the specified ID and sets its rating to 4
        onView(withId(R.id.rating17)).perform(setRating(4));

        ViewInteraction chip2 = onView(
                allOf(withId(R.id.valider), withText("Valider"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.core.widget.NestedScrollView")),
                                        0),
                                4),
                        isDisplayed()));
        chip2.perform(click());


        /* Finds the navigation button (the button named  "up" in the toolbar)
        and clicks it         to navigate to previous screen*/
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.RelativeLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());
    }

    //Helps locate a child view at a given position within a parent view
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    /*Defines a custom ViewAction to set the rating of a RatingBar
    the reason is that the built-in actions in Espresso
    do not cover this specific interaction
     */
    private static ViewAction setRating(final int rating) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(RatingBar.class);
            }

            @Override
            public String getDescription() {
                return "Set rating on a RatingBar";
            }

            @Override
            public void perform(UiController uiController, View view) {
                RatingBar ratingBar = (RatingBar) view;
                ratingBar.setRating(rating);
            }
        };
    }
}
