package au.edu.utas.kit305_assignment2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import au.edu.utas.kit305_assignment2.R;

public class MainActivity extends AppCompatActivity
{
    private Button logFood, history, feedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        logFood = (Button) findViewById(R.id.logButton);
        history = (Button) findViewById(R.id.historyButton);
        feedback = (Button) findViewById(R.id.feedbackButton);
        final Intent intent = new Intent(this, LogFoodActivity.class);
        logFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        history.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, FoodHistory.class));
            }
        });
        feedback.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, Feedback.class));
            }
        });
    }
}
