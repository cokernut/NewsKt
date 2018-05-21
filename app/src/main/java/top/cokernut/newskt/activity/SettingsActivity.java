package top.cokernut.newskt.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import top.cokernut.newskt.R;

public class SettingsActivity extends top.cokernut.newskt.base.BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment, new top.cokernut.newskt.fragment.SettingsFragment()).commit();
        setTitle("设置");
    }
}
