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
import com.vert.vert.activity.InboxScreenDetail;
import com.vert.vert.activity.OngoingScreenDetail;
import com.vert.vert.model.Inbox_model;
import com.vert.vert.model.OngoingBooking_model;

import java.util.List;

/**
 * Created by rajan on 10/15/18.
 */

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.InboxHolder> {

    Inbox_model inboxModel;
    private Context context;
    private List<Inbox_model> inboxModelList;

    public InboxAdapter(Context context, List<Inbox_model> inboxModelList) {
        this.inboxModelList = inboxModelList;
        this.context = context;

    }

    @Override
    public InboxAdapter.InboxHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inbox_row_list, parent, false);
        InboxAdapter.InboxHolder viewHolder = new InboxAdapter.InboxHolder(v);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(InboxAdapter.InboxHolder holder, int position) {
        inboxModel = inboxModelList.get(position);

        Resources resource= context.getResources();
        holder.message.setText(inboxModel.getMessage());





    }

    @Override
    public int getItemCount() {
        return inboxModelList.size();
    }

    public class InboxHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView message;




        public InboxHolder(View itemView) {
            super(itemView);


            message = (TextView) itemView.findViewById(R.id.message);


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


            if ((v == itemView)){
                Inbox_model inboxModel_s = inboxModelList.get(getAdapterPosition());

                if ((v == itemView)){

                    Intent intent = new Intent(context,InboxScreenDetail.class);

                    intent.putExtra("bookid", inboxModel_s.getBookid());
                    intent.putExtra("from", inboxModel_s.getFrom());
                    intent.putExtra("to", inboxModel_s.getTo());
                    intent.putExtra("flightdate", inboxModel_s.getDate());
                    intent.putExtra("flighttime", inboxModel_s.getTime());
                    intent.putExtra("bookdate", inboxModel_s.getBookdate());
                    intent.putExtra("message", inboxModel_s.getMessage());
                    intent.putExtra("contactusername", inboxModel_s.getContactusername());

                    context.startActivity(intent);
                }


            }

        }
    }
}
