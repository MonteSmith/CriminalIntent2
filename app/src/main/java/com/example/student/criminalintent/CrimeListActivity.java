package com.example.student.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

@SuppressWarnings("ALL")
public class CrimeAdapter extends ArrayAdapter<Crime> {

    public CrimeAdapter(ArrayList<Crime> crimes) {
        super(getActivity(), 0, crimes);
    }


    public CrimeAdapter(int crimes) {
        super();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // If we weren't given a view, inflate one
        if (convertView == null) {
            convertView = getActivity().getLayoutInflater()
                    .inflate(R.layout.list_item_crime, null);
        }

        // Configure the view for this Crime
        Crime c = getItem(position);

        TextView titleTextView =
                TextView convertView.findViewById(R.id.crime_list_item_titleTextView);
        titleTextView.setText(c.getTitle());
        TextView.dateTextView =
                (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
        TextView dateTextView = null;
        dateTextView.setText(c.getDate().toString());
        CheckBox solvedCheckBox =
                (CheckBox) convertView.findViewById(R.id.crime_list_item_item_solvedCheckBox);
        solvedCheckBox.setChecked(c.isSolved());

        return convertView;
    }


    private static final String TAG = "CrimeListFragment";

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Crime c = (Crime) (getListAdapter()).getItem(position);
        Log.d(TAG, c.getTitle() + " was clicked");
    }


    protected CrimeListFragment createFragment() {
        return new CrimeListFragment();
    }

    private int mCrimes;

    CrimeAdapter adapter = new CrimeAdapter(mCrimes);




    private Context getActivity() {
        return null;
    }

    {
        public void onListItemClick (ListView 1, View v,int position, long id){
        {
            Crime c = ((CrimeAdapter) getListAdapter()).getItem(position);
            // Start Crime Activity
            Intent i = new Intent(getActivity(), CrimeActivity.class);
            i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
        }


    }
    }


    @Override
    public void onResume() {
        adapter.onResume();
            ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
}




}





