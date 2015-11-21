package com.example.student.criminalintent;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class CrimeListFragment extends ListFragment {

    private static final String TAG = "CrimeListFragment";
    private ArrayList<Crime> mCrimes;
    private boolean mSubtitleVisible;

    public CrimeListFragment() {
    }// End of CrimeListFragment()

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.crime_title);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        mCrimes = CrimeLab.get(getActivity()).getCrimes();
        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
        setRetainInstance(true);
        mSubtitleVisible = false;
    }// End of onCreate(Bundle)

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        //Get the Crime from the adapter
        Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);

        //Start  of CrimeActivity ****
        Intent i = new Intent(getActivity(), CrimeActivity.class);
        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
        startActivity(i);
    }// End of onListItemClick

    @Override
    public void onResume(){
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }//End of onResume()

    private class CrimeAdapter extends ArrayAdapter<Crime> {
        public CrimeAdapter(ArrayList<Crime> crimes) {
            super(getActivity(), 0, crimes);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If we weren't given a view, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime, null);
            }

            // Configure the view for this crime
            Crime c = getItem(position);
            TextView titleTextView = (TextView) convertView.findViewById(R.id.crime_list_item_titleTextView);
            titleTextView.setText(c.getTitle());
            TextView dateTextView = (TextView) convertView.findViewById(R.id.crime_list_item_dateTextView);
            dateTextView.setText(c.getDate().toString());
            CheckBox solvedCheckBox = (CheckBox) convertView.findViewById(R.id.crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(c.isSolved());

            return convertView;
        }// End of getView(int, View, ViewGroup)

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
        {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.fragment_crime_list, menu);
            MenuItem showSubtitle = menu.findItem(R.id.menu_item_show_subtitle);
            if (mSubtitleVisible && showSubtitle != null)
            {
                showSubtitle.setTitle(R.string.hide_subtitle);
            }
        }

        @TargetApi(11)
        @Override
        public boolean onOptionsItemSelected(MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.menu_item_new_crime:
                    Crime crime = new Crime();
                    CrimeLab.get(getActivity()).addCrime(crime);
                    Intent i = new Intent(getActivity(), CrimePagerActivity.class);
                    i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
                    startActivityForResult(i, 0);
                    return true;
                case R.id.menu_item_show_subtitle:
                    AppCompatActivity activity = (AppCompatActivity) getActivity();
                 if (activity.getSupportActionBar().getSubtitle() == null)
                    {
                        activity.getSupportActionBar().setSubtitle(R.string.subtitle);
                        mSubtitleVisible = true;
                        item.setTitle(R.string.hide_subtitle);
                        getActivity().setTitle(R.string.crimes_title);
                        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
                        activity.getSupportActionBar().setIcon(R.mipmap.ic_launcher);
                    } else
                    {
                        activity.getSupportActionBar().setSubtitle(null);
                        mSubtitleVisible = false;
                        item.setTitle(R.string.show_subtitle);
                        getActivity().setTitle(R.string.crimes_title);
                        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
                        activity.getSupportActionBar().setIcon(R.mipmap.ic_launcher);
                    }

                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        @TargetApi(11)
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
            View v = super.onCreateView(inflater, parent, savedInstanceState);
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            getActivity().setTitle(R.string.crimes_title);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
            activity.getSupportActionBar().setIcon(R.mipmap.ic_launcher);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                if (mSubtitleVisible) {
                    activity.getSupportActionBar().setSubtitle(R.string.subtitle);
                }
            }
            return v;
        }
    }//
}//End of CrimeListFragment