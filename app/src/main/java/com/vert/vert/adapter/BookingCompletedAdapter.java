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
import com.vert.vert.activity.CompletedScreenDetail;
import com.vert.vert.model.CompletedBooking_model;
import com.vert.vert.model.OngoingBooking_model;

import java.util.List;

/**
 * Created by rajan on 10/16/18.
 */

public class BookingCompletedAdapter extends RecyclerView.Adapter<BookingCompletedAdapter.CompletedbookingHolder> {

    CompletedBooking_model completedModel;
    private Context context;
    private List<CompletedBooking_model> completedbookingModelList;

    public BookingCompletedAdapter(Context context, List<CompletedBooking_model> completedbookingModelList) {
        this.completedbookingModelList = completedbookingModelList;
        this.context = context;

    }

    @Override
    public BookingCompletedAdapter.CompletedbookingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookings_ongoing_row_list, parent, false);
        BookingCompletedAdapter.CompletedbookingHolder viewHolder = new BookingCompletedAdapter.CompletedbookingHolder(v);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(BookingCompletedAdapter.CompletedbookingHolder holder, int position) {
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
                CompletedBooking_model completedModel_s = completedbookingModelList.get(getAdapterPosition());

                if ((v == itemView)){

                   Intent intent = new Intent(context,CompletedScreenDetail.class);

                    intent.putExtra("bookid", completedModel_s.getBookid());
                    intent.putExtra("flightnumber", completedModel_s.getFlightnumber());
                    intent.putExtra("from", completedModel_s.getFrom());
                    intent.putExtra("to", completedModel_s.getTo());
                    intent.putExtra("flightdate", completedModel_s.getDate());
                    intent.putExtra("flighttime", completedModel_s.getTime());
                    intent.putExtra("name", completedModel_s.getName());
                    intent.putExtra("name2", completedModel_s.getName2());
                    intent.putExtra("name3", completedModel_s.getName3());
                    intent.putExtra("name4", completedModel_s.getName4());
                    intent.putExtra("pound", completedModel_s.getPound());
                    intent.putExtra("pound2", completedModel_s.getPound2());
                    intent.putExtra("pound3", completedModel_s.getPound3());
                    intent.putExtra("pound4", completedModel_s.getPound4());
                    intent.putExtra("price", completedModel_s.getPrice());
                    intent.putExtra("paymentstatus", completedModel_s.getPaymentstatus());
                    intent.putExtra("seat", completedModel_s.getSeat());
                    intent.putExtra("bookdate", completedModel_s.getBookdate());
                    intent.putExtra("contactusername", completedModel_s.getContactusername());
                    intent.putExtra("contactemailid", completedModel_s.getContactemail());
                    intent.putExtra("contactphonenumber", completedModel_s.getPhonenumber());
                    intent.putExtra("bookstatus", completedModel_s.getBookstatus());

                    context.startActivity(intent);
                }


            }

        }
    }
}
