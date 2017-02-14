package com.example.sergei.innandsnislvalidation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText innEditText;
    EditText snilsEditText;
    TextView innCorrection;
    TextView snilsCorrection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        innEditText = (EditText)findViewById(R.id.inn_edit_text);
        snilsEditText = (EditText)findViewById(R.id.snils_edit_text);

        innCorrection = (TextView)findViewById(R.id.inn_correction);
        snilsCorrection = (TextView)findViewById(R.id.snils_correction);

        innEditText.getText().replace(0, innEditText.getText().length(), "500100732259", 0, 12);
        snilsEditText.getText().replace(0, snilsEditText.getText().length(), "11223344595", 0, 11);
    }

    public void onClick(View view) {

        validateSnils();
        validateInn();

    }

    private void validateInn() {

        String inn = innEditText.getText().toString();
        Boolean isInnValid = false;



       if(inn.length() == 10){

           isInnValid = checkInnStepControlNumber(inn, 2);
           innCorrection.setText(isInnValid ? "ОК 10 символов" : "Не ок, 10 символов");

       }
       else if(inn.length() == 12){

           isInnValid = checkInnStepControlNumber(inn, 1) && checkInnStepControlNumber(inn, 2);
           innCorrection.setText(isInnValid  ? "ОК 12 символов" : "Не ок, 12 символов");

       }
       else{
           innCorrection.setText("Инн должен быть 10 или 12 символов");
       }

    }

    final int innCheckArray[] = {3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8};

    private boolean checkInnStepControlNumber(String inn, int step){

        int sum = 0;

        for(int i = 0; i < inn.length() - (3 - step); i++){
            sum += (inn.charAt(i) - '0') * innCheckArray[i + (14 - inn.length() - step)];
        }

        return (sum % 11) % 10 == inn.charAt(inn.length() - (3 - step)) - '0';

    }

    private void validateSnils() {


        String snils = snilsEditText.getText().toString();

        if(snils.length() == 11){

            int sum = 0;
            int checkSum = Integer.parseInt("" + snils.charAt(snils.length() - 2) + snils.charAt(snils.length() - 1));

               if(Integer.parseInt(snils.substring(0, 9)) <= Integer.parseInt("001001998") ) {
                   snilsCorrection.setText("СНИЛС не проверяется");
               }
               else{
                   for (int i = 0; i < snils.length() - 2; i++) {
                       sum += (snils.charAt(i) - '0') * ((snils.length() - 2) - i);
                   }

                   if (sum < 100 && checkSum == sum) {
                       snilsCorrection.setText("ОК 11 символов");
                   } else if ((sum == 100 || sum == 101) && checkSum == 0) {
                       snilsCorrection.setText("ОК 11 символов");
                   } else if (sum > 101 && (sum % 101 == checkSum || (sum == 100 && checkSum == 0))) {
                       snilsCorrection.setText("ОК 11 символов");
                   } else {
                       snilsCorrection.setText("Контрольная сумма не верна");
                   }
               }

        }
        else{
            snilsCorrection.setText("СНИЛС должен быть 11 символов");
        }
    }
}
