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

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    private Context context;
    private List<ExampleTaskToDo> list;
    private ClickListener clickListener;

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.todo_layout, parent, false);
        return new ToDoViewHolder(view);
    }

    public ToDoAdapter(List<ExampleTaskToDo> list, ClickListener clickListener){
        this.list = list;
        this.clickListener = clickListener;
    }

    public ToDoAdapter(Context context, List<ExampleTaskToDo> list){
        this.list = list;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoAdapter.ToDoViewHolder holder, int position) {
        ExampleTaskToDo todo = list.get(position);
        holder.mTextViewTitle.setText(todo.getTitle());
        holder.mTextViewDescription.setText(todo.getDescription());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ToDoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mTextViewTitle;
        public TextView mTextViewDescription;

        public ToDoViewHolder(@NonNull View itemView) {
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
