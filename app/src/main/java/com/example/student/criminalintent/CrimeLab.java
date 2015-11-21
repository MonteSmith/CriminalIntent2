package com.example.student.criminalintent;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

public class CrimeLab {

    private static final String TAG = "CrimeLab";
    private static final String FILENAME = "crimes.json";

    private ArrayList<Crime> mCrimes;
    private CriminalIntentJSONSerializer mSerializer;

    private Context mAppContext;
    private static CrimeLab sCrimeLab;

    private CrimeLab(Context appContext) {
        mAppContext = appContext;
        mSerializer = new CriminalIntentJSONSerializer(mAppContext, FILENAME);

        try {
            mCrimes = mSerializer.loadCrimes();
        } catch (Exception e) {
        mCrimes = new ArrayList<Crime>();
            Log.e(TAG, "Error saving crimes: ", e);
        }
    } // End of CrimeLab(context appContext


    private void createCrimes() {
        for (int i = 0; i < 100; i++) {
            Crime c = new Crime();
            c.setTitle("Crime #" + i);
            c.setSolved(i % 2 == 0); // Every other one
            mCrimes.add(c);
        } // End of for (int i = 0; i < 100; i++)
    }
        public void addCrime(Crime c) {
        mCrimes.add(c);
    }
        public void deleteCrime(Crime c) {
            mCrimes.remove(c);
        }

    // End of createCrimes()


    public static CrimeLab get(Context c) {
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(c.getApplicationContext());
        }
        return sCrimeLab;
    } // End of CrimeLab get(Context c)

    public Crime getCrime(UUID crimeId) {
        for (Crime c : mCrimes)
            if (c.getId().equals(crimeId))
                return c;

        return new Crime();
    } // End of getCrime(UUID crimeId)

    public int getCrimePosition(UUID crimeId){
        for(int i = 0; i < mCrimes.size(); ++i)
            if(mCrimes.get(i).getId().equals(crimeId))
                return i;
        return -1;
    } // End getCrimePosition(UUID crimeId)

    public ArrayList<Crime> getCrimes() {
        return mCrimes;
    }

    public Context getAppContext() {
        return mAppContext;
    }

    public void setAppContext(Context appContext) {
        mAppContext = appContext;
    } // End of
} // End of CrimeLab
