package com.myco.lcreporter;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class FillReportActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_report);

/*        Intent intent = getIntent();
        String leaderName = intent.getStringExtra(MainActivity.EXTRA_LIDER);
        String coleaderName = intent.getStringExtra(MainActivity.EXTRA_COLIDER);

        TextView leaderView = new TextView(this);
        leaderView.setTextSize(40);
        leaderView.setText(leaderName);

        setContentView(leaderView);

        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(coleaderName);

        setContentView(textView);
*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fill_report, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
