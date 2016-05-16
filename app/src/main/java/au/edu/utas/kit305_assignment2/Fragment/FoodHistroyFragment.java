package au.edu.utas.kit305_assignment2.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import au.edu.utas.kit305_assignment2.Adapter.FoodHistoryRecyclerAdapter;
import au.edu.utas.kit305_assignment2.DatabaseHelper;
import au.edu.utas.kit305_assignment2.Listener.EndlessRecyclerOnScrollListener;
import au.edu.utas.kit305_assignment2.R;

/**
 * Created by adarshan on 5/13/16.
 */
public class FoodHistroyFragment extends Fragment
{
    private Button comparison, sevenDays, oneMonth;
    private RecyclerView recyclerView;
    private FoodHistoryRecyclerAdapter foodHistoryRecyclerAdapter;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private int dateRange = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.foodhistory_fragment, container, false);
        dateRange = getArguments().getInt("dateRange");
        Log.i("dateRange",dateRange+"");
        comparison =(Button) view.findViewById(R.id.comparison);
        recyclerView = (RecyclerView) view.findViewById(R.id.past_data);
        sevenDays = (Button) view.findViewById(R.id.seven_days);
        oneMonth = (Button) view.findViewById(R.id.one_month);
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager1);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //get current date time with Date()
        Calendar cal = Calendar.getInstance();
        final String startDate = dateFormat.format(cal.getTime());

        cal.add(Calendar.DATE, -dateRange);
        final String endDate = dateFormat.format(cal.getTime());


        updateList(startDate,endDate);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager1) {
            @Override
            public void onLoadMore(int current_page) {
                int lastFirstVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                ((LinearLayoutManager) recyclerView.getLayoutManager()).scrollToPosition(lastFirstVisiblePosition);
                loadMore(current_page, startDate, endDate);

            }
        });
        comparison.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewComparison();
            }
        });
        sevenDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("dateRange", 7);
                FoodHistroyFragment fragment = new FoodHistroyFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragments, fragment, FoodHistroyFragment.class.getName());
                fragmentTransaction.commit();

            }
        });
        oneMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("dateRange", 30);
                FoodHistroyFragment fragment = new FoodHistroyFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragments, fragment, FoodHistroyFragment.class.getName());
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private void viewComparison()
    {

    }

    private void updateList(String startDate, String endDate)
    {
        DatabaseHelper db = new DatabaseHelper(getActivity());
        foodHistoryRecyclerAdapter = new FoodHistoryRecyclerAdapter(getActivity(), db.getListFoods(1,startDate,endDate));
        recyclerView.setAdapter(foodHistoryRecyclerAdapter);
    }

    private void loadMore(int page,String startDate, String endDate)
    {
        DatabaseHelper db = new DatabaseHelper(getActivity());
        foodHistoryRecyclerAdapter = new FoodHistoryRecyclerAdapter(getActivity(), db.getListFoods(page,startDate,endDate));
        recyclerView.setAdapter(foodHistoryRecyclerAdapter);
    }
}
