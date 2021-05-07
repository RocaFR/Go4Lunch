package fr.ferrerasroca.go4lunch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import fr.ferrerasroca.go4lunch.ui.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}