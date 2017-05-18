package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
//класс для создания диологового окна.

public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE =
            "com.bignerdranch.android.criminalintent.date";

    // Контейнер для аргументов фрагмета
    private static final String ARG_DATE = "date";

    private DatePicker mDatePicker;

    //Добавление метода newInstance
    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
      /* AlertDialog.Builder предоставляющий динамичный интерфейс для конструирования экземпляров AlertDialog
    *Сначала мы передаем объект Context конструктору AlertDialog.Builder, который возвращает экземпляр AlertDialog.Builder
     * setPositiveButton(…) получает строковый ресурс и объект, реализующий DialogInterface.OnClickListener
      * Построение диалогового окна завершается вызовом AlertDialog.Builder. create(), который возвращает настроенный экземпляр AlertDialog*/
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Извлечение даты и инициализация DatePicker  (DatePickerFragment.java)
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        //задание года месяца дня
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //Включение DatePicker в AlertDialog
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        //отображение данных в дате.
        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        //Дополнение, которое возвращает выбранную
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int year = mDatePicker.getYear();
                                int month = mDatePicker.getMonth();
                                int day = mDatePicker.getDayOfMonth();
                                Date date = new GregorianCalendar(year, month, day).getTime();
                                sendResult(Activity.RESULT_OK, date);
                            }
                })
                .create();
    }

    // метод для передачи данных целевому фрагменту.
    private void sendResult(int resultCode, Date date) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
