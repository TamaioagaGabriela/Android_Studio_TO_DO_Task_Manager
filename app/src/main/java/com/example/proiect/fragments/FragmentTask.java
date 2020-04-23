package com.example.proiect.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.proiect.activities.ActivityYourTasks;
import com.example.proiect.clickListener.ClickJson;
import com.example.proiect.clickListener.ClickListener;
import com.example.proiect.clickListener.ClickListenerSecond;
import com.example.proiect.modelTasks.ExampleTaskToDo;
import com.example.proiect.R;
import com.example.proiect.adapters.ToDoAdapter;
import com.example.proiect.database.ApplicationController;
import com.example.proiect.database.OnUserRepositoryActionListener;
import com.example.proiect.database.User;
import com.example.proiect.database.UserRepository;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class FragmentTask extends Fragment implements ClickListener, ClickJson {
    private ArrayList<ExampleTaskToDo> list = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ToDoAdapter toDoAdapter;
    private ArrayList<User> users = new ArrayList<>();
    private String title;
    private String desc;
    private ClickListenerSecond clickListenerSecond;
    private RequestQueue mRequestQueue;

    public FragmentTask(ClickListenerSecond clickListenerSecond) {
        this.clickListenerSecond = clickListenerSecond;
        // Required empty public constructor
    }

    public FragmentTask(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_task_to_do, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((ActivityYourTasks) getActivity()).setActivityListener(FragmentTask.this);

        recyclerView = view.findViewById(R.id.recycler_view_to_do);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        toDoAdapter = new ToDoAdapter(list, this);
        recyclerView.setAdapter(toDoAdapter);

        mRequestQueue = Volley.newRequestQueue(getActivity());

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.proiect", Context.MODE_PRIVATE);
        title = sharedPreferences.getString("title", null);
        desc = sharedPreferences.getString("description", null);

        if(title != null && desc !=null) {
            User user = new User(getName(FirebaseAuth.getInstance().getCurrentUser().getEmail()), title, desc, 0);
            new UserRepository(getContext()).insertTask(user, new OnUserRepositoryActionListener() {
                @Override
                public void actionSucces() {}

                @Override
                public void actionFailed() {}
            });
        }

        String name = getName(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Log.i(TAG, "nume"+name+"sfarsit");

        if (name.equals("mara")){
            Log.i("mesaj", "mesaj");
            parseJSON();
        }

        sharedPreferences.edit().remove("title").commit();
        sharedPreferences.edit().remove("description").commit();

        setData();
        for(int i=0; i<users.size(); i++){
            if(users.get(i).position == 0) {
                ExampleTaskToDo todo = new ExampleTaskToDo(users.get(i).title, users.get(i).description);
                list.add(todo);
            }
        }
        toDoAdapter.notifyDataSetChanged();

    }

    public void setData(){
        if(users != null){
            users.clear();
            users.addAll(new ApplicationController().getAppDatabase().userDao().getAll());
        }

    }

    public String getName(String mail){
        String name = "";
        for(int i=0; i<mail.length(); i++){
            if(mail.charAt(i) != '@'){
                name += mail.charAt(i);
            }else{
                return name;
            }
        }
        return "";
    }

    private void parseJSON(){

        String url = "https://my-json-server.typicode.com/TamaioagaGabriela/repo/tasks";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONArray jsonArray = response;

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject userJSON = jsonArray.getJSONObject(i);

                                String titleJSON = userJSON.getString("title");
                                String descJSON = userJSON.getString("description");
                                User user = new User(getName(FirebaseAuth.getInstance().getCurrentUser().getEmail()), titleJSON, descJSON, 0);
                                new UserRepository(getContext()).insertTask(user, new OnUserRepositoryActionListener() {
                                    @Override
                                    public void actionSucces() { }

                                    @Override
                                    public void actionFailed() { }
                                });

                                list.add(new ExampleTaskToDo(titleJSON, descJSON));
                            }

                            toDoAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }


    @Override
    public void onClickListener(View view, int position) {
        String str1 = list.get(position).getTitle();
        String str2 = list.get(position).getDescription();

        User user = new UserRepository(getContext()).getUserByTitleString(str1);
        user.position = 1;
        new UserRepository(getContext()).update(user, new OnUserRepositoryActionListener() {
            @Override
            public void actionSucces() {
                Toast.makeText(getContext(), "Updated!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void actionFailed() {

            }
        });
        Toast.makeText(getContext(), "Updated!", Toast.LENGTH_SHORT).show();
        list.remove(position);
        toDoAdapter.notifyDataSetChanged();
        clickListenerSecond.onClickListenerForSecond(str1, str2);
    }

    @Override
    public void onClickJson() {
        parseJSON();
    }
}
