package com.example.proiect.fragments;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.proiect.activities.ActivityYourTasks;
import com.example.proiect.alarm.AlarmReceiver;
import com.example.proiect.R;

import java.util.Calendar;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class FragmentAddTask extends Fragment {


    TimePickerDialog time_picker;
    EditText edit_time;
    Button btn_time_picker;
    TextView text_view_time_picker;


    DatePickerDialog data_picker;
    EditText edit_data;
    Button btn_data_picker;
    TextView text_view_data_picker;

    Button btn_set_alarm;
    Button btn_add_task;
    EditText edt_title, edt_description;

    int alarmHour, alarmMinutes, alarmDay, alarmMonth, alarmYear;
    int pick_time = 0;
    int pick_date = 0;

    public static String EXTRA_TITLE_TASK = "title_task";
    public static String EXTRA_DESCRIPTION_TASK = "description_task";



    public FragmentAddTask() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add_task, container, false);

        edt_title = (EditText) view.findViewById(R.id.edit_text_title);
        edt_description = (EditText) view.findViewById(R.id.edit_text_description);

        text_view_time_picker = (TextView) view.findViewById(R.id.text_view_time_picker);
        edit_time = (EditText) view.findViewById(R.id.edit_text_time);
        edit_time.setInputType(InputType.TYPE_NULL);
        edit_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
                // time picker dialog
                time_picker = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                edit_time.setText(sHour + ":" + sMinute);
                                alarmHour = sHour;
                                alarmMinutes = sMinute;
                                Log.i(TAG, "ssmin - " + sMinute);
                                Log.i(TAG, "sshour - " + sHour);
                                pick_time = 1;
                            }
                        }, hour, minutes, true);;
                time_picker.show();

            }
        });
        btn_time_picker = (Button) view.findViewById(R.id.btn_time);
        btn_time_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_view_time_picker.setText("Selected Time: "+ edit_time.getText());
            }
        });


        text_view_data_picker = (TextView) view.findViewById(R.id.text_view_data_picker);
        edit_data = (EditText) view.findViewById(R.id.edit_text_data);
        edit_data.setInputType(InputType.TYPE_NULL);
        edit_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                data_picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                edit_data.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                //Log.i(TAG, "an - " + year);
                                //Log.i(TAG, "luna - " + (monthOfYear+1));
                                //Log.i(TAG, "ziua - " + dayOfMonth);
                                alarmDay = dayOfMonth;
                                alarmMonth = monthOfYear;
                                alarmYear = year;
                                pick_date = 1;
                            }
                        }, year, month, day);
                data_picker.show();
            }
        });

        btn_data_picker = (Button) view.findViewById(R.id.btn_data);
        btn_data_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_view_data_picker.setText("Selected Date: "+ edit_data.getText());
            }
        });


        btn_set_alarm = (Button) view.findViewById(R.id.btn_alarm);
        btn_set_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, alarmYear);
                calendar.set(Calendar.MONTH, alarmMonth);
                calendar.set(Calendar.DAY_OF_MONTH, alarmDay);
                calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
                calendar.set(Calendar.MINUTE, alarmMinutes);
                calendar.set(Calendar.SECOND, 0);

                //Log.i(TAG, "time " + calendar.getTime());

                if (pick_date == 1 && pick_date == 1) {
                    startAlarm(calendar);
                    Toast.makeText(getContext(), "Notification successfully created!", Toast.LENGTH_SHORT).show();
                    //closeFragment();
                }
                else Toast.makeText(getContext(), "Alarm is not set!", Toast.LENGTH_SHORT).show();
            }
        });


        btn_add_task = (Button) view.findViewById(R.id.btn_add_task);
        btn_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "titlu - " + edt_title.getText().toString());
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.proiect", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("title", edt_title.getText().toString()).commit();
                sharedPreferences.edit().putString("description", edt_description.getText().toString()).commit();
                Intent intent = new Intent(getContext(), ActivityYourTasks.class);
                startActivity(intent);
                EXTRA_TITLE_TASK = edt_title.getText().toString();
                EXTRA_DESCRIPTION_TASK = edt_description.getText().toString();
                getActivity().finish();
            }
        });

        return view;
    }


    private void startAlarm(Calendar c){
        EXTRA_TITLE_TASK = edt_title.getText().toString();
        EXTRA_DESCRIPTION_TASK = edt_description.getText().toString();
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.example.proiect", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("title", edt_title.getText().toString()).commit();
        sharedPreferences.edit().putString("description", edt_description.getText().toString()).commit();

        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 1, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void closeFragment() {
        getFragmentManager().beginTransaction().remove(this).commit();
    }
}
