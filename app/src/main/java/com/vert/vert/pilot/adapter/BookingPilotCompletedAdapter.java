package com.vert.vert.pilot.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vert.vert.R;
import com.vert.vert.activity.CompletedScreenDetail;
import com.vert.vert.model.CompletedBooking_model;
import com.vert.vert.pilot.activity.PilotCompletedScreenDetail;
import com.vert.vert.pilot.model.CompletedPilotBooking_model;

import java.util.List;

/**
 * Created by rajan on 10/16/18.
 */

public class BookingPilotCompletedAdapter extends RecyclerView.Adapter<BookingPilotCompletedAdapter.CompletedbookingHolder> {

    CompletedPilotBooking_model completedModel;
    private Context context;
    private List<CompletedPilotBooking_model> completedbookingModelList;

    public BookingPilotCompletedAdapter(Context context, List<CompletedPilotBooking_model> completedbookingModelList) {
        this.completedbookingModelList = completedbookingModelList;
        this.context = context;

    }

    @Override
    public BookingPilotCompletedAdapter.CompletedbookingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookings_ongoing_row_list, parent, false);
        BookingPilotCompletedAdapter.CompletedbookingHolder viewHolder = new BookingPilotCompletedAdapter.CompletedbookingHolder(v);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(BookingPilotCompletedAdapter.CompletedbookingHolder holder, int position) {
        completedModel = completedbookingModelList.get(position);

        Resources resource= context.getResources();

        holder.flighttime.setText("Flight Time :"+completedModel.getTime());
        holder.from.setText("From :"+completedModel.getFrom());
        holder.to.setText("To :"+completedModel.getTo());
        holder.date.setText("Date :"+completedModel.getDate());


    }

    @Override
    public int getItemCount() {
        return completedbookingModelList.size();
    }

    public class CompletedbookingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView flighttime,from,to,date;




        public CompletedbookingHolder(View itemView) {
            super(itemView);


            flighttime = (TextView) itemView.findViewById(R.id.flighttime);
            from = (TextView) itemView.findViewById(R.id.from);
            to = (TextView) itemView.findViewById(R.id.to);
            date = (TextView) itemView.findViewById(R.id.date);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


            if ((v == itemView)){
                CompletedPilotBooking_model completedModel_s = completedbookingModelList.get(getAdapterPosition());

                if ((v == itemView)){

                   Intent intent = new Intent(context,PilotCompletedScreenDetail.class);

                    intent.putExtra("bookid", completedModel_s.getBookid());
                    intent.putExtra("flightnumber", completedModel_s.getFlightnumber());
                    intent.putExtra("from", completedModel_s.getFrom());
                    intent.putExtra("to", completedModel_s.getTo());
                    intent.putExtra("flightdate", completedModel_s.getDate());
                    intent.putExtra("flighttime", completedModel_s.getTime());


                    context.startActivity(intent);
                }


            }

        }
    }
}
