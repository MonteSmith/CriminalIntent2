package com.example.student.criminalintent;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class CrimeLab {
    private static Object sCrimelab;
    private ArrayList<Crime> mCrimes;
    private Context mAppContext;
    private static CrimeLab sCrimeLab;

    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mCrimes = new ArrayList<Crime>();
        createCrimes();
    }

    private void createCrimes() {
        for (int i = 0; i < 100; i++) {
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i % 2 == 0); // Every other one
            mCrimes.add(c);
        }
    }


    public static CrimeLab get(Context c) {
        if (sCrimelab == null){
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    }

    public Crime getCrime(UUID crimeId) {
        for (Crime c : mCrimes)
            if (c.getId().equals(crimeId))
                return c;

        return new Crime();
    }

    public int getCrimePosition(UUID crimeId){
        for(int i = 0; i < mCrimes.size(); ++i)
            if(mCrimes.get(i).getId().equals(crimeId))
                return i;
        return -1;
    }

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context appContext) {
        mAppContext = appContext;
    }
}
