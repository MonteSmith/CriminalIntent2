package com.example.student.criminalintent;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import java.util.UUID;

import static com.example.student.criminalintent.CrimeFragment.newInstance;

public class CrimeActivity extends SingleFragmentActivity {
    @Override
    public CrimeActivity() {

        UUID crimeId = (UUID)getIntent()
                .getSerializableExtra(CrimeFragment.EXTRA_CRIME_ID);
        return newInstance(crimeId);
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


    @Override
    protected Fragment createFragment() {

    }
}
