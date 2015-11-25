package com.example.student.criminalintent;


import android.support.v4.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }// End of createFragment()
}// End of CrimeListActivity class