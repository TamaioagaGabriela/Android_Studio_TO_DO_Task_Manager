package com.example.proiect.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proiect.clickListener.ClickListener;
import com.example.proiect.clickListener.ClickListenerThird;
import com.example.proiect.adapters.DoingAdapter;
import com.example.proiect.modelTasks.ExampleTaskToDo;
import com.example.proiect.R;
import com.example.proiect.database.ApplicationController;
import com.example.proiect.database.OnUserRepositoryActionListener;
import com.example.proiect.database.User;
import com.example.proiect.database.UserRepository;

import java.util.ArrayList;


public class FragmentTaskDoing extends Fragment implements ClickListener {

    private ArrayList<ExampleTaskToDo> list = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private DoingAdapter toDoAdapter;
    private String title;
    private String description;
    private ArrayList<User> users = new ArrayList<>();
    private ClickListenerThird clickListenerThird;

    public FragmentTaskDoing(){}

    public FragmentTaskDoing(ClickListenerThird clickListenerThird) {
        this.clickListenerThird = clickListenerThird;
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_doing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_doing);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        toDoAdapter = new DoingAdapter(list, this);
        recyclerView.setAdapter(toDoAdapter);

        setData();

        if (getArguments() != null)
            title = getArguments().getString("title");
        if (getArguments() != null)
            description = getArguments().getString("description");


        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).position == 1) {
                ExampleTaskToDo todo = new ExampleTaskToDo(users.get(i).title, users.get(i).description);
                list.add(todo);
            }
        }

        //ExampleTaskToDo todo = new ExampleTaskToDo(title, description);
        //list.add(todo);

        toDoAdapter.notifyDataSetChanged();

    }


    public void setData() {
        if (users != null) {
            users.clear();
            users.addAll(new ApplicationController().getAppDatabase().userDao().getAll());
        }

    }

    @Override
    public void onClickListener(View view, int position) {
        String str1 = list.get(position).getTitle();
        String str2 = list.get(position).getDescription();

        User user = new UserRepository(getContext()).getUserByTitleString(str1);
        user.position = 2;
        new UserRepository(getContext()).update(user, new OnUserRepositoryActionListener() {
            @Override
            public void actionSucces() {
                Toast.makeText(getContext(), "Updated!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void actionFailed() {

            }
        });
        list.remove(position);
        toDoAdapter.notifyDataSetChanged();
        clickListenerThird.onClickListenerForThird(str1, str2);
    }
}

