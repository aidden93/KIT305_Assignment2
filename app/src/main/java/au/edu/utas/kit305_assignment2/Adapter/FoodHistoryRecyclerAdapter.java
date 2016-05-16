package au.edu.utas.kit305_assignment2.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import au.edu.utas.kit305_assignment2.Pojo.PastData;
import au.edu.utas.kit305_assignment2.R;

/**
 * Created by adarshan on 5/13/16.
 */
public class FoodHistoryRecyclerAdapter  extends RecyclerView.Adapter<FoodHistoryRecyclerAdapter.ListHolderView>
{
    private List<PastData> list;
    private Context context;

    public FoodHistoryRecyclerAdapter(Context context, List<PastData> list)

    {
        this.context = context;
        this.list = list;
    }


    @Override
    public ListHolderView onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_recycler_items, null);
        ListHolderView holder = new ListHolderView(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ListHolderView holder, int position)
    {
        PastData details = list.get(position);
        holder.foodGroup.setText(details.getFoodGroup());
        holder.foodType.setText(details.getFoodType());
        holder.serving.setText(details.getServing());
        holder.date.setText(details.getDate());
        holder.meal_time.setText(details.getMealTime());
    }

    public void clearAdapter()
    {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ListHolderView extends RecyclerView.ViewHolder
    {
        protected TextView foodGroup, foodType, serving, quantity, date, meal_time;

        public ListHolderView(View itemView)
        {
            super(itemView);
            this.foodGroup = (TextView) itemView.findViewById(R.id.foodgr);
            this.foodType = (TextView) itemView.findViewById(R.id.foodtr);
            this.serving = (TextView) itemView.findViewById(R.id.servingr);
            this.date = (TextView) itemView.findViewById(R.id.dater);
            this.meal_time = (TextView) itemView.findViewById(R.id.timer);
        }
    }
}