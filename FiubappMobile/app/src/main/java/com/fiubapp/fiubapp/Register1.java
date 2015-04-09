package com.fiubapp.fiubapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register1 extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set View to register.xml
        setContentView(R.layout.register1);

        final EditText edit_padron = (EditText)findViewById(R.id.reg_padron);
        final TextView text_padron = (TextView)findViewById(R.id.text_padron);

        final Button button = (Button) findViewById(R.id.btnRegister);
        final EditText edit_pass = (EditText)findViewById(R.id.reg_password);
        edit_pass.setTypeface(Typeface.SANS_SERIF);
        final EditText edit_pass_repeat = (EditText)findViewById(R.id.reg_repeatpassword);
        edit_pass_repeat.setTypeface(Typeface.SANS_SERIF);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox_intercambio);
        checkBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    edit_padron.setHint("Pasaporte");
                    text_padron.setText("Pasaporte");
                }else {
                    edit_padron.setHint("Padron");
                    text_padron.setText("Padrón");
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String password = edit_pass.getText().toString();
                String repeat_password = edit_pass_repeat.getText().toString();
                String username = edit_padron.getText().toString();

                if (!username.equals("") && !password.equals("") && !repeat_password.equals("")){
                    if (password.equals(repeat_password)){
                        if (password.length() >= 8) {
                            Intent i = new Intent(getBaseContext(), Register2.class);
                            i.putExtra("username",username);
                            i.putExtra("password", password);
                            if (((CheckBox)findViewById(R.id.checkBox_intercambio)).isChecked())
                                i.putExtra("isExchange",true);
                            else
                                i.putExtra("isExchange",false);

                            startActivity(i);

                        }else{
                            Toast.makeText(Register1.this, "La contraseña debe tener 8 o más caracteres", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(Register1.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(Register1.this, edit_padron.getHint() +
                            " y/o contraseña vacíos", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
