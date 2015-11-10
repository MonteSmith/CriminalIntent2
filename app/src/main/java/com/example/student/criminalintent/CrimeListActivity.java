package com.example.student.criminalintent;

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected CrimeFragment createFragment() {
        return new CrimeListFragment();
    }// End of createFragment()
}// End of CrimeListActivity class