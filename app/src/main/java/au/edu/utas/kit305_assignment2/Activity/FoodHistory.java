package au.edu.utas.kit305_assignment2.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import au.edu.utas.kit305_assignment2.Fragment.FoodHistroyFragment;
import au.edu.utas.kit305_assignment2.R;

/**
 * Created by adarshan on 5/13/16.
 */
public class FoodHistory extends AppCompatActivity
{
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_controller);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if (savedInstanceState == null) savedInstanceState = new Bundle();
        savedInstanceState.putInt("dateRange", 2);
        FoodHistroyFragment fragment = new FoodHistroyFragment();
        fragment.setArguments(savedInstanceState);
        fragmentTransaction.replace(R.id.fragments, fragment, FoodHistroyFragment.class.getName());
        fragmentTransaction.commit();
    }
}
