package com.example.calculatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;

public class MainActivity extends AppCompatActivity {

    // crée les variables qui vont contenir les composants
    private TextView TX_resultat;
    private TextView TX_calcule;
    private Button BT_clear;
    private Button BT_remove;
    private Button BT_1;
    private Button BT_2;
    private Button BT_3;
    private Button BT_4;
    private Button BT_5;
    private Button BT_6;
    private Button BT_7;
    private Button BT_8;
    private Button BT_9;
    private Button BT_0;
    private Button BT_virgule;
    private Button BT_div;
    private Button BT_multi;
    private Button BT_moin;
    private Button BT_plus;
    private Button BT_egale;
    private ArrayList<Double> num = new ArrayList<>();
    private ArrayList<String> operation = new ArrayList<>();
    private Boolean operationAfter;
    private String number = "";
    private String last = "0";
    private Boolean egaleEnable = false;
    private Button BT_JS1;
    private Button BT_JS2;
    private Boolean virguleUse = false;
    private Boolean operationNow = false;
    private Boolean egaleUse = true;
    private Boolean numberAfterVirgule = false;
    private Integer numC = 0;
    private Boolean cAfter = false;
    private Boolean egalePressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // déclare les composants
        TX_calcule = findViewById(R.id.main_textView_calcul);
        TX_resultat = findViewById(R.id.main_textView_resulta);
        BT_0 = findViewById(R.id.main_button_0);
        BT_1 = findViewById(R.id.main_button_1);
        BT_2 = findViewById(R.id.main_button_2);
        BT_3 = findViewById(R.id.main_button_3);
        BT_4 = findViewById(R.id.main_button_4);
        BT_5 = findViewById(R.id.main_button_5);
        BT_6 = findViewById(R.id.main_button_6);
        BT_7 = findViewById(R.id.main_button_7);
        BT_8 = findViewById(R.id.main_button_8);
        BT_9 = findViewById(R.id.main_button_9);
        BT_clear = findViewById(R.id.main_button_Clear);
        BT_div = findViewById(R.id.main_button_div);
        BT_moin = findViewById(R.id.main_button_moin);
        BT_remove = findViewById(R.id.main_button_remove);
        BT_plus = findViewById(R.id.main_button_plus);
        BT_multi = findViewById(R.id.main_button_multi);
        BT_egale = findViewById(R.id.main_button_egale);
        BT_JS1 = findViewById(R.id.main_button_jsp);
        BT_JS2 = findViewById(R.id.main_button_jps2);
        BT_virgule = findViewById(R.id.main_button_virgule);
        operationAfter = false;
        operationNow = false;
        egaleUse = true;
        numberAfterVirgule = false;
    }


    private void resetField(){
        TX_resultat.setText("");
        TX_calcule.setText("");

    }

    private void afterEgale(String add){
        if (!operationNow) {
            egaleUse = false;
            virguleUse = false;
            operationAfter = true;
            if (egaleEnable) {
                egaleEnable = false;
                resetField();
                num.clear();
                operation.clear();
                actual(add);
            } else {
                actual(add);
            }
        }
        numC = 0;
        operationNow = true;
    }

    private void displayActual(String add){
        String actual = TX_calcule.getText().toString();
        String newCalcule = actual + add;
        TX_calcule.setText(newCalcule);
    }

    private void listeActual(String add) {
        if (operationAfter) {
            numberAfterVirgule = false;
            operationAfter = false;

            if (number.equals("")) {
                number = last;
            }

            num.add(Double.parseDouble(number));
            number = "";
            operation.add(add);
        } else {

            number = number + add;
            TX_resultat.setText(number);
        }
    }

    private void result() {
        String resultat = "";
        double calcule = num.get(0);
        int nombreDeOperation = operation.size();
        int nombreDeNombre = num.size();
        int count = 0;
        int changeOp = 0;

        for (int i = 0; i < nombreDeNombre; i++) {
            //Initialise les 2 chiffres
            double num1 = calcule;
            double num2 = num.get(i);

            //Commence avec les 2 chiffres
            if (calcule == num.get(0)) {
                num2 = num.get(i + 1);
                count+= 1;
                i++;
            }

            //Effectue l'opération correspondante
            switch(operation.get(changeOp)) {
                case " + ": calcule = num1 + num2;
                    break;
                case " - ": calcule = num1 - num2;
                    break;
                case " * ": calcule = num1 * num2;
                    break;
                case " / ":
                    if (num2 == 0) {
                        resultat = "Error";
                } else {
                        calcule = num1 / num2;
                    }
                    break;
                default: System.out.print("Error! Enter correct operator");
                    return;
            }

            //Vérifie si on doit changer d'opération
            count += 1;
            if (count == 2) {
                count = 0;
                changeOp += 1;

            }
        }
        
        if (resultat.equals("")) {
            resultat = Double.toString(calcule);

        }
        last = resultat;
        TX_resultat.setText(resultat);
    }

    private void actual(String add) {
        if (egalePressed) {
            egalePressed = false;
            resetField();
        }
        if (egaleEnable) {
            egaleEnable = false;
            resetField();
        }
        numC = 0;
        operationNow = false;
        numberAfterVirgule = true;
        displayActual(add);
        listeActual(add);
    }

    @Override
    protected void onStart() {
        super.onStart();

        BT_virgule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numberAfterVirgule) {
                    if (!virguleUse) {
                        actual(".");
                    }
                    virguleUse = true;
                }
            }
        });

        BT_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numC == 1) {
                    resetField();
                    num.clear();
                    operation.clear();
                    egaleEnable = false;
                    number = "0";
                    numC = 0;
                    cAfter = true;
                } else {
                    resetField();
                    num.clear();
                    operation.clear();
                    egaleEnable = false;
                    number = "";
                    numC += 1;
                }
            }
        });
        BT_JS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code ici
                testArray();
            }
        });
        BT_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code ici

                actual("1");
            }
        });
        BT_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code ici
                actual("2");
            }
        });
        BT_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code ici
                actual("3");
            }
        });
        BT_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code ici
                actual("4");
            }
        });
        BT_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code ici
                actual("5");
            }
        });
        BT_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code ici
                actual("6");
            }
        });
        BT_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code ici
                actual("7");
            }
        });
        BT_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code ici
                actual("8");
            }
        });
        BT_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code ici
                actual("9");
            }
        });
        BT_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code ici
                actual("0");
            }
        });
        BT_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code ici
                afterEgale(" + ");
            }
        });
        BT_moin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code ici
                afterEgale(" - ");
            }
        });
        BT_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code ici
                afterEgale(" * ");
            }
        });
        BT_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //code ici
                afterEgale(" / ");
            }
        });
        BT_egale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                egalePressed = true;
                if (operationNow && cAfter) {
                    egaleEnable = true;
                    actual("0");
                    TX_calcule.setText("0");
                }
                    if (!egaleUse) {
                        cAfter = true;
                        operationAfter = true;
                        egaleEnable = true;
                        listeActual("");
                        result();
                        number = "";
                        egaleUse = true;
                    }
                num.clear();
                operation.clear();
                egaleEnable = false;
                number = "";
            }
        });
    }
    private void testArray() {
        StringBuilder message = new StringBuilder();
        for (Double s : num) {
            message.append(s);
        }
        Snackbar snack = Snackbar.make(findViewById(R.id.main_activity_layout),message, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snack.show();
    }

}