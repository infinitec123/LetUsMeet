package infiniteloop.letusmeet.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import infiniteloop.letusmeet.R;


/**
 * @author Sharath Pandeshwar
 * @since 25/08/15.
 * <p/>
 * Custom widget to show a learning card to the user
 */
public class FirstTimeInstructionWidget extends LinearLayout {

    private TextView mTitleTextView;
    private TextView mDescriptionTextView;
    private Button mConfirmationButton;

    private String mTitle;
    private String mDescription;
    private String mButtonText;

    /**
     * Reference to my host
     */
    private FirstTimeInstructionWidgetListener mFirstTimeInstructionWidgetListener;

    public FirstTimeInstructionWidget(Context context) {
        super(context);
        initialise();
    }


    public FirstTimeInstructionWidget(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CompoundLayout);
        mTitle = typedArray.getString(R.styleable.CompoundLayout_com_titleText);
        mDescription = typedArray.getString(R.styleable.CompoundLayout_com_descriptionText);
        mButtonText = typedArray.getString(R.styleable.CompoundLayout_com_buttonText);
        typedArray.recycle();
        initialise();
    }

    private void initialise() {
        /* Inflate the XML */
        String inflatorservice = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li;
        li = (LayoutInflater) getContext().getSystemService(inflatorservice);
        li.inflate(R.layout.layout_first_time_instruction_widget, this, true);

        mTitleTextView = (TextView) findViewById(R.id.id_ftue_title);
        mDescriptionTextView = (TextView) findViewById(R.id.id_ftue_description);
        mConfirmationButton = (Button) findViewById(R.id.id_confirmation_button);

        mConfirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final View v = FirstTimeInstructionWidget.this;
                ViewCompat.animate(v)
                        .translationX((float) v.getWidth())
                        .setInterpolator(new FastOutSlowInInterpolator())
                        .setDuration(500L)
                        .setListener(new ViewPropertyAnimatorListenerAdapter() {
                            public void onAnimationEnd(View view) {
                                setVisibility(View.GONE);
                                if (mFirstTimeInstructionWidgetListener != null) {
                                    mFirstTimeInstructionWidgetListener.onCardDismissed();
                                }
                            }
                        })
                        .start();
            }
        });

        if (mTitle != null) {
            mTitleTextView.setText(mTitle);
        }

        if (mDescription != null) {
            mDescriptionTextView.setText(mDescription);
        }

        if (mButtonText != null) {
            mConfirmationButton.setText(mButtonText);
        }
    }

    //*********************************************************************
    // APIs
    //*********************************************************************

    /**
     * Use this function to set data to this view
     */
    public void bindModel(String title, String description) {
        mTitle = title;
        mDescription = description;
        mTitleTextView.setText(title);
        mDescriptionTextView.setText(description);
    }

    /**
     * Use this function to attach a listener
     *
     * @param firstTimeInstructionWidgetListener
     */
    public void setListener(FirstTimeInstructionWidgetListener firstTimeInstructionWidgetListener) {
        mFirstTimeInstructionWidgetListener = firstTimeInstructionWidgetListener;
    }

    //*********************************************************************
    // Private classes
    //*********************************************************************

    /**
     * Interface my hosts need to implement to get necessary callbacks.
     */
    public interface FirstTimeInstructionWidgetListener {

        /**
         * Called when user hits 'Got It' button
         */
        void onCardDismissed();
    }

    //*********************************************************************
    // Utility functions
    //*********************************************************************


    //*********************************************************************
    // End of class
    //*********************************************************************

}
