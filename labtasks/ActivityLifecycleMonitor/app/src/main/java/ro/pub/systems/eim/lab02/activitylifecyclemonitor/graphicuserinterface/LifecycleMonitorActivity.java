package ro.pub.systems.eim.lab02.activitylifecyclemonitor.graphicuserinterface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;

import ro.pub.systems.eim.lab02.activitylifecyclemonitor.R;
import ro.pub.systems.eim.lab02.activitylifecyclemonitor.general.Constants;
import ro.pub.systems.eim.lab02.activitylifecyclemonitor.general.Utilities;

public class LifecycleMonitorActivity extends AppCompatActivity {

    private ButtonClickListener buttonClickListener = new ButtonClickListener();

    private class ButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            EditText usernameEditText = (EditText)findViewById(R.id.username_edit_text);
            EditText passwordEditText = (EditText)findViewById(R.id.password_edit_text);
            if (((Button)view).getText().toString().equals(getResources().getString(R.string.ok_button_content))) {
                LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                View popupContent;
                if (Utilities.allowAccess(getApplicationContext(), username, password)) {
                    popupContent = layoutInflater.inflate(R.layout.popup_window_authentication_success, null);
                } else {
                    popupContent = layoutInflater.inflate(R.layout.popup_window_authentication_fail, null);
                }
                final PopupWindow popupWindow = new PopupWindow(popupContent, android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT);
                Button dismissButton = (Button)popupContent.findViewById(R.id.dismiss_button);
                dismissButton.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
            if (((Button)view).getText().toString().equals(getResources().getString(R.string.cancel_button_content))) {
                usernameEditText.setText(getResources().getText(R.string.empty));
                passwordEditText.setText(getResources().getText(R.string.empty));
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle_monitor);

        // click pe butonul de OK => actiune
        Button okButton = (Button) findViewById(R.id.ok_button);
        okButton.setOnClickListener(buttonClickListener);

        // click pe butonul de CANCEL => actiune
        Button cancelButton = (Button) findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(buttonClickListener);

        // daca nu este salvata deja o stare => e prima oara cand onCreat() este invocata
        if (savedInstanceState == null) {
            Log.d(Constants.TAG, "onCreate() method was invoked without a previous state");
        } else {
            Log.d(Constants.TAG, "onCreate() method was invoked with a previous state");

            // ce fac astea?????
            if (savedInstanceState.containsKey(Constants.USERNAME_EDIT_TEXT)) {
                EditText usernameEditText = (EditText)findViewById(R.id.username_edit_text);
                usernameEditText.setText(savedInstanceState.getString(Constants.USERNAME_EDIT_TEXT));
            }
            if (savedInstanceState.containsKey(Constants.PASSWORD_EDIT_TEXT)) {
                EditText passwordEditText = (EditText)findViewById(R.id.password_edit_text);
                passwordEditText.setText(savedInstanceState.getString(Constants.PASSWORD_EDIT_TEXT));
            }
            if (savedInstanceState.containsKey(Constants.REMEMBER_ME_CHECKBOX)) {
                CheckBox rememberMeCheckbox = (CheckBox)findViewById(R.id.remember_me_checkbox);
                rememberMeCheckbox.setChecked(savedInstanceState.getBoolean(Constants.REMEMBER_ME_CHECKBOX));
            }
        }
    }

    // metoda onRestart()
    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(Constants.TAG, "onRestart() method was invoked");
    }

    // metoda onStart()
    @Override
    public void onStart() {
        super.onStart();
        Log.d(Constants.TAG, "onStart() method was invoked");
    }

    // metoda onResume()
    @Override
    public void onResume() {
        super.onResume();
        Log.d(Constants.TAG, "onResume() method was invoked");
    }

    // metoda onPause()
    @Override
    public void onPause() {
        super.onPause();
        Log.d(Constants.TAG, "onPause() method was invoked");
    }

    // metoda onStop()
    @Override
    public void onStop() {
        super.onStop();
        Log.d(Constants.TAG, "onStop() method was invoked");
    }

    // metoda onDestory()
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(Constants.TAG, "onDestroy() method was invoked");
    }

    // metoda care salveaza starea butonului CheckBox
    // Cand este apasat CheckBox-ul de "Remember me" sunt retinute numele si parola user-ului
    // Este retinut statusul butonului CheckBox => apasat / checked
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        EditText usernameEditText = (EditText)findViewById(R.id.username_edit_text);
        EditText passwordEditText = (EditText)findViewById(R.id.password_edit_text);
        CheckBox rememberMeCheckBox = (CheckBox)findViewById(R.id.remember_me_checkbox);
        if (rememberMeCheckBox.isChecked()) {
            savedInstanceState.putString(Constants.USERNAME_EDIT_TEXT, usernameEditText.getText().toString());
            savedInstanceState.putString(Constants.PASSWORD_EDIT_TEXT, passwordEditText.getText().toString());
            savedInstanceState.putBoolean(Constants.REMEMBER_ME_CHECKBOX, rememberMeCheckBox.isChecked());
        }
    }

    // metoda reseteaza valorile campurilor salvate de onSaveInstanceState()
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // resetare username => campul text pentru username va fi gol
        if (savedInstanceState.containsKey(Constants.USERNAME_EDIT_TEXT)) {
            EditText usernameEditText = (EditText)findViewById(R.id.username_edit_text);
            usernameEditText.setText(savedInstanceState.getString(Constants.USERNAME_EDIT_TEXT));
        }

        //resetare parola => campul text pentru parola va fi gol
        if (savedInstanceState.containsKey(Constants.PASSWORD_EDIT_TEXT)) {
            EditText passwordEditText = (EditText)findViewById(R.id.password_edit_text);
            passwordEditText.setText(savedInstanceState.getString(Constants.PASSWORD_EDIT_TEXT));
        }

        // resetare stare CheckBox => CheckBox-ul nu va fi apasat / clicked
        if (savedInstanceState.containsKey(Constants.REMEMBER_ME_CHECKBOX)) {
            CheckBox rememberMeCheckBox = (CheckBox) findViewById(R.id.remember_me_checkbox);
            rememberMeCheckBox.setChecked(savedInstanceState.getBoolean(Constants.REMEMBER_ME_CHECKBOX));
        }
    }
}
