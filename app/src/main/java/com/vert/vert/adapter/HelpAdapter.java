package com.vert.vert.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.vert.vert.R;
import com.vert.vert.Static.Userdetails;
import com.vert.vert.activity.InboxScreenDetail;
import com.vert.vert.model.Help_model;
import com.vert.vert.model.Inbox_model;

import java.util.List;

/**
 * Created by rajan on 10/15/18.
 */

public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.HelpHolder> {

    Help_model helpModel;
    private Context context;
    private List<Help_model> helpModelList;

    public HelpAdapter(Context context, List<Help_model> helpModelList) {
        this.helpModelList = helpModelList;
        this.context = context;

    }

    @Override
    public HelpAdapter.HelpHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.help_row_list, parent, false);
        HelpAdapter.HelpHolder viewHolder = new HelpAdapter.HelpHolder(v);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(HelpAdapter.HelpHolder holder, int position) {
        helpModel = helpModelList.get(position);

        Resources resource= context.getResources();
        holder.help.setText(helpModel.getHelp());
        holder.answer.setText(helpModel.getAnswer());

    }

    @Override
    public int getItemCount() {
        return helpModelList.size();
    }

    public class HelpHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView help,answer;

        Button show;


        public HelpHolder(View itemView) {
            super(itemView);


            help = (TextView) itemView.findViewById(R.id.help);
            answer = (TextView) itemView.findViewById(R.id.answer);
            show = (Button) itemView.findViewById(R.id.show);


            show.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {


                if ((v == show)){


                    if(Userdetails.value.equals("0")) {

                        answer.setVisibility(View.VISIBLE);

                        Userdetails.value = "1";

                        show.setText("Hide");

                    }else {

                        answer.setVisibility(View.GONE);

                        Userdetails.value = "0";

                        show.setText("Show");
                    }


            }

        }
    }
}
