package au.edu.utas.kit305_assignment2.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import au.edu.utas.kit305_assignment2.Activity.LogFoodActivity;
import au.edu.utas.kit305_assignment2.Adapter.FoodHistoryRecyclerAdapter;
import au.edu.utas.kit305_assignment2.DatabaseHelper;
import au.edu.utas.kit305_assignment2.Listener.EndlessRecyclerOnScrollListener;
import au.edu.utas.kit305_assignment2.Listener.RecyclerItemClickListener;
import au.edu.utas.kit305_assignment2.R;

/**
 * Created by adarshan on 5/13/16.
 */
public class FoodHistroyFragment extends Fragment
{
    private Button comparison, sevenDays, oneMonth, allTime;
    private RecyclerView recyclerView;
    private FoodHistoryRecyclerAdapter foodHistoryRecyclerAdapter;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private DatabaseHelper db;
    private Calendar cal;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int dateRange = 0;
    private int page = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.foodhistory_fragment, container, false);
        db = new DatabaseHelper(getActivity());
        dateRange = getArguments().getInt("dateRange");
        comparison =(Button) view.findViewById(R.id.comparison);
        recyclerView = (RecyclerView) view.findViewById(R.id.past_data);
        sevenDays = (Button) view.findViewById(R.id.seven_days);
        oneMonth = (Button) view.findViewById(R.id.one_month);
        allTime = (Button) view.findViewById(R.id.all_time);
        final LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager1);
        //get current date time with Date()
        cal =  Calendar.getInstance();
        final String startDate = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, -dateRange);
        final String endDate = dateFormat.format(cal.getTime());


        updateList(page, startDate,endDate);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(layoutManager1) {
            @Override
            public void onLoadMore(int current_page) {
                if (page > 0) page = current_page;
                int lastFirstVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                recyclerView.getLayoutManager().scrollToPosition(lastFirstVisiblePosition);
                updateList(current_page, startDate, endDate);

            }
        });
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(view.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(view.getContext(), LogFoodActivity.class);
                        intent.putExtra("row", position);
                        startActivity(intent);
                    }
                })
        );

        final ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                //Remove swiped item from list and notify the RecyclerView
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Delete Entry")
                        .setMessage("Do you really want to remove this entry from your history?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                if (db.removeEntry(viewHolder.getAdapterPosition()+1))
                                    Toast.makeText(view.getContext(), "Entry removed.", Toast.LENGTH_SHORT).show();
                                else {
                                    updateList(page, startDate, endDate);
                                    Toast.makeText(view.getContext(), "Failed to remove the entry.", Toast.LENGTH_SHORT).show();
                                }
                            }})
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                updateList(page, startDate, endDate);
                                recyclerView.invalidate();
                                Toast.makeText(view.getContext(), "Entry readded.", Toast.LENGTH_SHORT).show();
                            }}).show();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


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
        allTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putInt("dateRange", Integer.MAX_VALUE);
                FoodHistroyFragment fragment = new FoodHistroyFragment();
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragments, fragment, FoodHistroyFragment.class.getName());
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateList(page, dateFormat.format(cal.getTime()), dateFormat.format(cal.getTime()));
    }

    private void viewComparison()
    {

    }

    private void updateList(int page, String startDate, String endDate)
    {
        foodHistoryRecyclerAdapter = new FoodHistoryRecyclerAdapter(getActivity(), db.getListFoods(page,startDate,endDate));
        recyclerView.setAdapter(foodHistoryRecyclerAdapter);
    }
}
