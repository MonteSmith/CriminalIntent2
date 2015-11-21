package com.example.student.criminalintent;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
        getActivity().setTitle(R.string.crime_title);
        mCrimes = CrimeLab.get(getActivity()).getCrimes();
        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
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

    private class CrimeAdapter extends com.example.student.criminalintent.CrimeAdapter {
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


        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
        }// End of onCreateContextMenu


        @Override
        public boolean onContextItemSelected(MenuItem item) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
            int position = info.position;
            CrimeAdapter adapter = (CrimeAdapter) getListAdapter();
            Crime crime = adapter.getItem(position);

            switch (item.getItemId()) {
                case R.id.menu_item_delete_crime:
                    CrimeLab.get(getActivity()).deleteCrime(crime);
                    adapter.notifyDataSetChanged();
                    return true;
            }
            return super.onContextItemSelected(item);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                                 Bundle savedInstanceState) {
            View v = super.onCreateView(inflater, parent, savedInstanceState);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                // Use floating context menus on Froyo and Gingerbread
                registerForContextMenu(listView);
            } else {
                // Use contextual action bar on Honeycomb and higher
                listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
                listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {

                    public void onItemCheckedStateChanged(ActionMode mode, int position,
                                                          long id, boolean checked) {
                        // Required, but not used in this implementation
                    }


                    // ActionMode.Callback methods
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        MenuInflater inflater = mode.getMenuInflater();
                        inflater.inflate(R.menu.crime_list_item_context, menu);
                        return true;
                    }

                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false;
                        // Required, but not used in this implementation
                    }

                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_item_delete_crime:
                                CrimeAdapter adapter = (CrimeAdapter) getListAdapter();
                                CrimeLab crimeLab = CrimeLab.get(getActivity());
                                for (int i = adapter.getCount() - 1; i >= 0; i--) {
                                    if (getListView().isItemChecked(i)) {
                                        crimeLab.deleteCrime(adapter.getItem(i));
                                    }
                                }
                                mode.finish();
                                adapter.notifyDataSetChanged();
                                return true;
                            default:
                                return false;
                        }
                    }

                    public void onDestroyActionMode(ActionMode mode) {
                        // Required, but not used in this implementation
                    }
                });
            }
            return v;
        }

        private void notifyDataSetChanged() {
        }


    }// End of CrimeAdapter

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
        }

}//End of CrimeListFragment