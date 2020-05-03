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
import com.vert.vert.activity.CancelledScreenDetail;
import com.vert.vert.activity.CompletedScreenDetail;
import com.vert.vert.model.CancelledBooking_model;
import com.vert.vert.model.CompletedBooking_model;

import java.util.List;

/**
 * Created by rajan on 10/16/18.
 */

public class BookingCancelledAdapter extends RecyclerView.Adapter<BookingCancelledAdapter.CancelledbookingHolder> {

    CancelledBooking_model cancelleddModel;
    private Context context;
    private List<CancelledBooking_model> cancelledbookingModelList;

    public BookingCancelledAdapter(Context context, List<CancelledBooking_model> cancelledbookingModelList) {
        this.cancelledbookingModelList = cancelledbookingModelList;
        this.context = context;

    }

    @Override
    public BookingCancelledAdapter.CancelledbookingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookings_canceled_list, parent, false);
        BookingCancelledAdapter.CancelledbookingHolder viewHolder = new BookingCancelledAdapter.CancelledbookingHolder(v);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(BookingCancelledAdapter.CancelledbookingHolder holder, int position) {
        cancelleddModel = cancelledbookingModelList.get(position);

        Resources resource= context.getResources();
        holder.bookid.setText("Booking Id :"+cancelleddModel.getBookid());
        holder.flightnumber.setText("Flight Number :"+cancelleddModel.getFlightnumber());
        holder.flighttime.setText("Flight Time :"+cancelleddModel.getTime());
        holder.from.setText("From :"+cancelleddModel.getFrom());
        holder.to.setText("To :"+cancelleddModel.getTo());
        holder.date.setText("Date :"+cancelleddModel.getDate());



    }

    @Override
    public int getItemCount() {
        return cancelledbookingModelList.size();
    }

    public class CancelledbookingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView bookid,flightnumber,flighttime,from,to,date;




        public CancelledbookingHolder(View itemView) {
            super(itemView);


            bookid = (TextView) itemView.findViewById(R.id.bookid);
            flightnumber = (TextView) itemView.findViewById(R.id.flightnumber);
            flighttime = (TextView) itemView.findViewById(R.id.flighttime);
            from = (TextView) itemView.findViewById(R.id.from);
            to = (TextView) itemView.findViewById(R.id.to);
            date = (TextView) itemView.findViewById(R.id.date);



            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


            if ((v == itemView)){
                CancelledBooking_model cancelledModel_s = cancelledbookingModelList.get(getAdapterPosition());

                if ((v == itemView)){

                   Intent intent = new Intent(context,CancelledScreenDetail.class);

                    intent.putExtra("bookid", cancelledModel_s.getBookid());
                    intent.putExtra("flightnumber", cancelledModel_s.getFlightnumber());
                    intent.putExtra("flightname", cancelledModel_s.getFlightname());
                    intent.putExtra("from", cancelledModel_s.getFrom());
                    intent.putExtra("to", cancelledModel_s.getTo());
                    intent.putExtra("flightdate", cancelledModel_s.getDate());
                    intent.putExtra("flighttime", cancelledModel_s.getTime());
                    intent.putExtra("flightstatus", cancelledModel_s.getFlightstatus());
                    intent.putExtra("name", cancelledModel_s.getName());
                    intent.putExtra("name2", cancelledModel_s.getName2());
                    intent.putExtra("name3", cancelledModel_s.getName3());
                    intent.putExtra("name4", cancelledModel_s.getName4());
                    intent.putExtra("pound", cancelledModel_s.getPound());
                    intent.putExtra("pound2", cancelledModel_s.getPound2());
                    intent.putExtra("pound3", cancelledModel_s.getPound3());
                    intent.putExtra("pound4", cancelledModel_s.getPound4());
                    intent.putExtra("price", cancelledModel_s.getPrice());
                    intent.putExtra("paymentstatus", cancelledModel_s.getPaymentstatus());
                    intent.putExtra("seat", cancelledModel_s.getSeat());
                    intent.putExtra("bookdate", cancelledModel_s.getBookdate());
                    intent.putExtra("contactusername", cancelledModel_s.getContactusername());
                    intent.putExtra("contactemailid", cancelledModel_s.getContactemail());
                    intent.putExtra("contactphonenumber", cancelledModel_s.getPhonenumber());
                    intent.putExtra("bookstatus", cancelledModel_s.getBookstatus());
                    intent.putExtra("canceldate", cancelledModel_s.getCanceldate());

                    context.startActivity(intent);
                }


            }

        }
    }
}
