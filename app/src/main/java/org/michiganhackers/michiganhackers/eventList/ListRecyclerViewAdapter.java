package org.michiganhackers.michiganhackers.eventList;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.api.client.util.DateTime;

import org.michiganhackers.michiganhackers.R;

import java.util.ArrayList;

public class ListRecyclerViewAdapter extends RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final ArrayList<CalendarEvent> dataSet;
    private onItemClickListener clickListener;
    ListRecyclerViewAdapter(Context context, ArrayList<CalendarEvent> dataSet){
        inflater = LayoutInflater.from(context);
        this.dataSet = dataSet;
    }
    public interface onItemClickListener{
        void onItemClick(int position, View imageView);
    }
    public void setOnItemClickListener(onItemClickListener listener){
        clickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvEventName.setText(dataSet.get(position).getSummary());
        DateTime start = dataSet.get(position).getStart().getDateTime();
        if (start == null) {
            // All-day events don't have start times, so just use
            // the start date.
            start = dataSet.get(position).getStart().getDate();
        }
        //holder.eventTime.setText(start.toString());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void updateData (ArrayList<CalendarEvent> data) {
        if (data != null && data.size() > 0) {
            dataSet.clear();
            dataSet.addAll(data);
            notifyDataSetChanged();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        final TextView tvEventName;

        ViewHolder(final View itemView) {
            super(itemView);
            tvEventName = itemView.findViewById(R.id.tv_event_name);
            //eventTime = itemView.findViewById(R.id.event_time);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(clickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            clickListener.onItemClick(position, v.findViewById(R.id.image_event_bkgnd));
                        }
                    }
                }
            });
        }
    }
}
