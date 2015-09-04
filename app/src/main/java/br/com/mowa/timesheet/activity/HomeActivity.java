package br.com.mowa.timesheet.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.mowa.timesheet.timesheet.R;

public class HomeActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private ActionBarDrawerToggle drawerToggle;
    private int year, month, day, hour, minute;
    private Button btDate;
    private Button btHorainicio;
    private Button btHoraFim;
    private Button btUpdateButtonHoras;
    private Button btEnviar;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Navigation drawer + toolbar
        setUpToolbar();
        setUpNavigationDrawer(R.id.activity_home_drawer_layout, R.id.activity_home_menu_lateral);




        // Components (DATA e HORA)
        this.btDate = (Button) findViewById(R.id.activity_home_button_data);
        this.btDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarioPickerDialog();
            }
        });

        this.btHorainicio = (Button) findViewById(R.id.activity_home_button_horas_inicio);
        this.btHorainicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btUpdateButtonHoras = btHorainicio;
                relogioPickerDialog();
            }
        });

        this.btHoraFim = (Button) findViewById(R.id.activity_home_button_horas_fim);
        this.btHoraFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btUpdateButtonHoras = btHoraFim;
                relogioPickerDialog();
            }
        });
        loadDateCurrent();


        //Components (Spinner)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getListSpinner());
        this.spinner = (Spinner) findViewById(R.id.activity_home_spinner_projeto);
        this.spinner.setAdapter(adapter);


        //Components (Button enviar)
        this.btEnviar = (Button) findViewById(R.id.activity_home_button_enviar);
        this.btEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast("Enviando");
            }
        });

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void calendarioPickerDialog() {
        Calendar calendarDefaul = Calendar.getInstance();
        calendarDefaul.set(this.year, this.month, this.day);


        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                this,
                calendarDefaul.get(Calendar.YEAR),
                calendarDefaul.get(Calendar.MONTH),
                calendarDefaul.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show(getActivity().getFragmentManager(), "Calendario");
        datePickerDialog.setAccentColor(getResources().getColor(R.color.primary));
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void relogioPickerDialog() {
        Calendar relogioDefaul = Calendar.getInstance();
        relogioDefaul.set(this.year, this.month, this.day, this.hour, this.minute);

        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                this,
                relogioDefaul.get(Calendar.HOUR_OF_DAY),
                relogioDefaul.get(Calendar.MINUTE),
                true
        );

        timePickerDialog.show(getActivity().getFragmentManager(), "Relogio");
        timePickerDialog.setAccentColor(getActivity().getResources().getColor(R.color.primary));
    }

    private void loadDateCurrent() {
        if (year == 0) {
            Calendar c = Calendar.getInstance();
            this.year = c.get(Calendar.YEAR);
            this.month = c.get(Calendar.MONTH);
            this.day = c.get(Calendar.DAY_OF_MONTH);
            this.hour = c.get(Calendar.HOUR_OF_DAY);
            this.minute = c.get(Calendar.MINUTE);

            this.btDate.setText(day + "/" + month + "/" + year);
            this.btHorainicio.setText(this.hour + ":" + this.minute);
            this.btHoraFim.setText(this.hour + ":" + this.minute);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar tDefaul = Calendar.getInstance();
        tDefaul.set(year, month, day, hour, minute);
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;

        this.btDate.setText(day + "/" + (month + 1) + "/" + year);
    }

    @Override
    public void onTimeSet(RadialPickerLayout radialPickerLayout, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minute = minute;

        this.btUpdateButtonHoras.setText(this.hour + ":" + this.minute);
        this.btUpdateButtonHoras = null;
    }
    public List<String> getListSpinner() {
        List<String> list = new ArrayList<String>();
        list.add("Projeto 1");
        list.add("Projeto 2");
        list.add("Projeto 3");
        list.add("Projeto 4");
        return list;
    }

}
