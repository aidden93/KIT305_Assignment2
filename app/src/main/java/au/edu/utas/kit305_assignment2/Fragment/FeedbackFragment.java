package au.edu.utas.kit305_assignment2.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import au.edu.utas.kit305_assignment2.R;

/**
 * Created by adarshan on 5/13/16.
 */
public class FeedbackFragment extends Fragment
{
    private Button tfid, suggestion, moreInfromation;
    private TextView tfidDeatils, suggestionDetails, moreInfromationDetails;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.feedback_fragment, container, false);

        tfid = (Button) view.findViewById(R.id.tfid);
        suggestion = (Button) view.findViewById(R.id.suggestion);
        moreInfromation = (Button) view.findViewById(R.id.more_information);

        tfidDeatils = (TextView) view.findViewById(R.id.tfid_details);
        suggestionDetails = (TextView) view.findViewById(R.id.suggestion_details);
        moreInfromationDetails = (TextView) view.findViewById(R.id.more_information_details);

        tfid.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDetails(1);
            }
        });

        suggestion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDetails(2);
            }
        });

        moreInfromation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDetails(3);
            }
        });
        return view;
    }

    private void showDetails(int i)
    {
        if(i == 1) {
            tfidDeatils.setVisibility(View.VISIBLE);
            tfidDeatils.setText("1.Follow the Australian Dietary Guideline properly."+"\n"+"2.Eat plenty of vegetables and fruits as given in the guideline."+"\n"+"3.When eating away from home avoid the food that have lots of fats");
            suggestionDetails.setVisibility(View.GONE);
            moreInfromationDetails.setVisibility(View.GONE);
        } else if(i == 2) {
            tfidDeatils.setVisibility(View.GONE);
            suggestionDetails.setVisibility(View.VISIBLE);
            suggestionDetails.setText("1.Don't give up eating healthy foods."+"\n"+"2.Eat with other people not TV, eating with other people helps you maintain your meal intake from five food groups."+"\n"+"3.Eat regularly dont'skip meals.");
            moreInfromationDetails.setVisibility(View.GONE);
        } else {
            tfidDeatils.setVisibility(View.GONE);
            suggestionDetails.setVisibility(View.GONE);
            moreInfromationDetails.setVisibility(View.VISIBLE);
            moreInfromationDetails.setText("https://www.eatforhealth.gov.au/guidelines/about-australian-dietary-guidelines");
        }
    }
}
