package com.example.moaathalrajab.filesandsharedpreferencesdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.basgeekball.awesomevalidation.utility.custom.SimpleCustomValidation;
import com.google.common.collect.Range;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AwesomeValidation awesomeValidation;

    private EditText mETemail;
    private EditText mETDoB;
    private EditText mETName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        mETemail    =   (EditText)findViewById(R.id.editText);
        mETDoB      =   (EditText)findViewById(R.id.editText2);
        mETName     =   (EditText)findViewById(R.id.editText3);

        addValidationToViews();
    }
    private void addValidationToViews() {
        awesomeValidation.addValidation(this, R.id.editText, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
        awesomeValidation.addValidation(this, R.id.editText2,  new SimpleCustomValidation() {
            @Override
            public boolean compare(String input) {
                // check if the age is >= 18
                try {
                    Calendar calendarBirthday = Calendar.getInstance();
                    Calendar calendarToday = Calendar.getInstance();
                    calendarBirthday.setTime(new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(input));
                    int yearOfToday = calendarToday.get(Calendar.YEAR);
                    int yearOfBirthday = calendarBirthday.get(Calendar.YEAR);
                    if (yearOfToday - yearOfBirthday > 18) {
                        return true;
                    } else if (yearOfToday - yearOfBirthday == 18) {
                        int monthOfToday = calendarToday.get(Calendar.MONTH);
                        int monthOfBirthday = calendarBirthday.get(Calendar.MONTH);
                        if (monthOfToday > monthOfBirthday) {
                            return true;
                        } else if (monthOfToday == monthOfBirthday) {
                            if (calendarToday.get(Calendar.DAY_OF_MONTH) >= calendarBirthday.get(Calendar.DAY_OF_MONTH)) {
                                return true;
                            }
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }, R.string.invalid_date);
        awesomeValidation.addValidation(this, R.id.editText3, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
    }

    private void submitForm() {
        // Validate the form...
        if (awesomeValidation.validate()) {
            Toast.makeText(this, "Form Validated Successfully", Toast.LENGTH_LONG).show();
        }
    }


    public void doAction(View view) {
        submitForm();
    }

    public void moveToUI2(View view) {
        Intent intent=new Intent(this, Main2Activity.class);
        startActivity(intent);
    }
}
