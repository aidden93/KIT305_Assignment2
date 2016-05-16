package au.edu.utas.kit305_assignment2.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import au.edu.utas.kit305_assignment2.DatabaseHelper;
import au.edu.utas.kit305_assignment2.R;

public class LogFoodActivity extends AppCompatActivity {

    private EditText date;
    private SimpleDateFormat dateFormatter;
    private Button log;
    private Calendar calendar;
    private Spinner foodGroup,foodType,servingsType;
    private EditText quantity;
    private Spinner mealTime;
    private String[] foodGroupItems = {"Choose Food Group", "Vegetables and legumes/beans", "Fruit", "Grain (cereal) foods"
    , "Lean meats and poultry, fish, eggs, tofu, nuts and seeds and legumes/beans", "Milk, yoghurt cheese and/or alternatives"};
    private String[] vegTypes = {"Dark green or cruciferous/brassica", "Root/tubular/bulb vegetables", "Legumes/beans", "Other vegetables"};
    private String[] fruitTypes = {"Pome fruits", "Citrus fruits", "Stone fruits", "Tropical fruits", "Berries", "Other fruits"};
    private String[] grainTypes = {"Breads", "Breakfast Cereals", "Grains", "Other products"};
    private String[] meatTypes = {"Lean meats", "Poultry", "Fish and seafood", "Eggs", "Nuts and seeds", "Legumes/beans"};
    private String[] milkTypes = {"Milks","Yoghurt", "Cheese"};
    private String[] servingType = {"Servings", "Grams", "Millilitres"};
    private String[] mealTimes = {"Breakfast", "Morning snack", "Lunch", "Afternoon snack", "Dinner", "Evening snack"};
   private DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        setContentView(R.layout.activity_log_food);
        init();
    }

    private void init()
    {
        final String time = new SimpleDateFormat("hh:mm").format(new Date());
        quantity = (EditText) findViewById(R.id.quantity);
        foodGroup = (Spinner) findViewById(R.id.foodGroup);
        foodType = (Spinner) findViewById(R.id.foodType);
        servingsType = (Spinner) findViewById(R.id.servingsType);
        log = (Button) findViewById(R.id.logEntryButton);
        date = (EditText) findViewById(R.id.date);
        calendar = Calendar.getInstance();
        mealTime = (Spinner) findViewById(R.id.timePeriod);
        final ArrayAdapter<String> adapterservings = new ArrayAdapter<>(LogFoodActivity.this,
                android.R.layout.simple_spinner_dropdown_item, servingType);

        date.setText(dateFormatter.format(calendar.getTime()));
        servingsType.setAdapter(adapterservings);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(LogFoodActivity.this,
                android.R.layout.simple_spinner_dropdown_item, foodGroupItems);
        foodGroup.setAdapter(adapter);
        foodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(foodGroup.getSelectedItem().equals("Vegetables and legumes/beans")) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(LogFoodActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, vegTypes);
                    foodType.setAdapter(adapter);
                }
                else if (foodGroup.getSelectedItem().equals("Fruit")) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(LogFoodActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, fruitTypes);
                    foodType.setAdapter(adapter);
                }
                else if (foodGroup.getSelectedItem().equals("Grain (cereal) foods")) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(LogFoodActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, grainTypes);
                    foodType.setAdapter(adapter);
                }
                else if (foodGroup.getSelectedItem().equals("Lean meats and poultry, fish, eggs, tofu, nuts and seeds and legumes/beans")) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(LogFoodActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, meatTypes);
                    foodType.setAdapter(adapter);
                }
                else if (foodGroup.getSelectedItem().equals("Milk, yoghurt cheese and/or alternatives")) {
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(LogFoodActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, milkTypes);
                    foodType.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        ArrayAdapter<String> timeadapter = new ArrayAdapter<>(LogFoodActivity.this,
                android.R.layout.simple_spinner_dropdown_item, mealTimes);
        mealTime.setAdapter(timeadapter);

        date.setInputType(InputType.TYPE_NULL);

        final DatePickerDialog pickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener()
        {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                calendar.set(year, monthOfYear, dayOfMonth);
                date.setText(dateFormatter.format(calendar.getTime()));
            }

        },calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        //I chose focus instead of click for a reason. Click wasn't working if you didn't already have the edittext focused
        // so you'd have to click twice. This way you only click once
        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) pickerDialog.show();
            }
        });
        log.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if (foodGroup.getSelectedItem().equals("Choose Food Group")) {
                    Toast.makeText(getApplicationContext(), "Please select a food group", Toast.LENGTH_SHORT).show();
                } else if (quantity.getText().toString().length() == 0 || quantity.getText().equals("0")) {
                    Toast.makeText(getApplicationContext(), "Please enter a quantity greater than 0", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseHelper = new DatabaseHelper(LogFoodActivity.this);
                    if (databaseHelper.insert(foodGroup.getSelectedItem().toString(), foodType.getSelectedItem().toString(), quantity.getText().toString() + " " + servingsType.getSelectedItem().toString()
                            , date.getText().toString(), mealTime.getSelectedItem().toString())) {
                        Toast.makeText(getApplicationContext(), "Entry successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Entry unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });
    }
}
