package com.vert.vert.adapter;

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

import java.util.List;

/**
 * Created by rajan on 10/15/18.
 */

public class BookingOngoingAdapter extends RecyclerView.Adapter<BookingOngoingAdapter.OngoingbookingHolder> {

    OngoingBooking_model ongoingModel;
    private Context context;
    private List<OngoingBooking_model> ongoingbookingModelList;

    public BookingOngoingAdapter(Context context, List<OngoingBooking_model> ongoingbookingModelList) {
        this.ongoingbookingModelList = ongoingbookingModelList;
        this.context = context;

    }

    @Override
    public BookingOngoingAdapter.OngoingbookingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookings_ongoing_row_list, parent, false);
        BookingOngoingAdapter.OngoingbookingHolder viewHolder = new BookingOngoingAdapter.OngoingbookingHolder(v);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(BookingOngoingAdapter.OngoingbookingHolder holder, int position) {
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

    public class OngoingbookingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView flighttime,from,to,date;




        public OngoingbookingHolder(View itemView) {
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
                OngoingBooking_model allongingModel_s = ongoingbookingModelList.get(getAdapterPosition());

                if ((v == itemView)){

                    Intent intent = new Intent(context,OngoingScreenDetail.class);

                    intent.putExtra("bookid", allongingModel_s.getBookid());
                    intent.putExtra("flightnumber", allongingModel_s.getFlightnumber());
                    intent.putExtra("flightname", allongingModel_s.getFlightname());
                    intent.putExtra("from", allongingModel_s.getFrom());
                    intent.putExtra("to", allongingModel_s.getTo());
                    intent.putExtra("flightdate", allongingModel_s.getDate());
                    intent.putExtra("flighttime", allongingModel_s.getTime());
                    intent.putExtra("flightstatus", allongingModel_s.getFlightstatus());
                    intent.putExtra("name", allongingModel_s.getName());
                    intent.putExtra("name2", allongingModel_s.getName2());
                    intent.putExtra("name3", allongingModel_s.getName3());
                    intent.putExtra("name4", allongingModel_s.getName4());
                    intent.putExtra("pound", allongingModel_s.getPound());
                    intent.putExtra("pound2", allongingModel_s.getPound2());
                    intent.putExtra("pound3", allongingModel_s.getPound3());
                    intent.putExtra("pound4", allongingModel_s.getPound4());
                    intent.putExtra("price", allongingModel_s.getPrice());
                    intent.putExtra("paymentstatus", allongingModel_s.getPaymentstatus());
                    intent.putExtra("seat", allongingModel_s.getSeat());
                    intent.putExtra("bookdate", allongingModel_s.getBookdate());
                    intent.putExtra("contactusername", allongingModel_s.getContactusername());
                    intent.putExtra("contactemailid", allongingModel_s.getContactemail());
                    intent.putExtra("contactphonenumber", allongingModel_s.getPhonenumber());
                    intent.putExtra("bookstatus", allongingModel_s.getBookstatus());

                    context.startActivity(intent);
                }


            }

        }
    }
}
