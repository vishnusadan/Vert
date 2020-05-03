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
import com.vert.vert.activity.OngoingScreenDetail;
import com.vert.vert.model.OngoingBooking_model;
import com.vert.vert.pilot.activity.PilotOngoingScreenDetail;
import com.vert.vert.pilot.model.OngoingPilotBooking_model;

import java.util.List;

/**
 * Created by rajan on 10/15/18.
 */

public class BookingPilotOngoingAdapter extends RecyclerView.Adapter<BookingPilotOngoingAdapter.OngoingpilotbookingHolder> {

    OngoingPilotBooking_model ongoingModel;
    private Context context;
    private List<OngoingPilotBooking_model> ongoingbookingModelList;

    public BookingPilotOngoingAdapter(Context context, List<OngoingPilotBooking_model> ongoingbookingModelList) {
        this.ongoingbookingModelList = ongoingbookingModelList;
        this.context = context;

    }

    @Override
    public BookingPilotOngoingAdapter.OngoingpilotbookingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookings_ongoing_row_list, parent, false);
        BookingPilotOngoingAdapter.OngoingpilotbookingHolder viewHolder = new BookingPilotOngoingAdapter.OngoingpilotbookingHolder(v);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(BookingPilotOngoingAdapter.OngoingpilotbookingHolder holder, int position) {
        ongoingModel = ongoingbookingModelList.get(position);

        Resources resource= context.getResources();

        holder.flighttime.setText("Flight Time :"+ongoingModel.getTime());
        holder.from.setText("From :"+ongoingModel.getFrom());
        holder.to.setText("To :"+ongoingModel.getTo());
        holder.date.setText("Date :"+ongoingModel.getDate());




    }

    @Override
    public int getItemCount() {
        return ongoingbookingModelList.size();
    }

    public class OngoingpilotbookingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView flighttime,from,to,date;




        public OngoingpilotbookingHolder(View itemView) {
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
                OngoingPilotBooking_model allongingModel_s = ongoingbookingModelList.get(getAdapterPosition());

                if ((v == itemView)){

                    Intent intent = new Intent(context, PilotOngoingScreenDetail.class);

                    intent.putExtra("bookid", allongingModel_s.getBookid());
                    intent.putExtra("flightnumber", allongingModel_s.getFlightnumber());
                    intent.putExtra("from", allongingModel_s.getFrom());
                    intent.putExtra("to", allongingModel_s.getTo());
                    intent.putExtra("flightdate", allongingModel_s.getDate());
                    intent.putExtra("flighttime", allongingModel_s.getTime());

                    context.startActivity(intent);
                }

            }

        }
    }
}
