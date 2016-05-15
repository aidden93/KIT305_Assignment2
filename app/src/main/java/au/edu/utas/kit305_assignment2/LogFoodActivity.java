package au.edu.utas.kit305_assignment2;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class LogFoodActivity extends AppCompatActivity {

    private EditText date;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        setContentView(R.layout.activity_log_food);
        init();
    }

    private void init() {
        final Calendar calendar = Calendar.getInstance();
        final Button log = (Button) findViewById(R.id.logEntryButton);
        final Spinner foodGroup = (Spinner) findViewById(R.id.foodGroup);
        final Spinner foodType = (Spinner) findViewById(R.id.foodType);
        final EditText amount = (EditText) findViewById(R.id.amount);
        final Spinner servingType = (Spinner) findViewById(R.id.servingsType);
        final Spinner time = (Spinner) findViewById(R.id.timePeriod);

        date = (EditText) findViewById(R.id.date);
        date.setText(dateFormatter.format(calendar.getTime()));
        date.setInputType(InputType.TYPE_NULL);
        final DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) pickerDialog.show();
            }
        });

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
