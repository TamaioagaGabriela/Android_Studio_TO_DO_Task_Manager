package com.example.proiect.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proiect.modelTasks.ExampleTaskToDo;
import com.example.proiect.R;
import com.example.proiect.clickListener.ClickListener;

import java.util.List;

public class DoneAdapter extends RecyclerView.Adapter<DoneAdapter.DoneViewHolder> {

    private Context context;
    private List<ExampleTaskToDo> list;
    private ClickListener clickListener;

    @NonNull
    @Override
    public DoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.todo_layout, parent, false);
        return new DoneViewHolder(view);
    }

    public DoneAdapter(List<ExampleTaskToDo> list, ClickListener clickListener){
        this.list = list;
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull DoneViewHolder holder, int position) {
        ExampleTaskToDo todo = list.get(position);
        holder.mTextViewTitle.setText(todo.getTitle());
        holder.mTextViewDescription.setText(todo.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DoneViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mTextViewTitle;
        public TextView mTextViewDescription;

        public DoneViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewTitle = itemView.findViewById(R.id.titleToDoTextView);
            mTextViewDescription = itemView.findViewById(R.id.descriptionToDoTextView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            clickListener.onClickListener(v, getAdapterPosition());
        }
    }
}