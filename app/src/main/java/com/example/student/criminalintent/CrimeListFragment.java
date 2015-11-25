package com.example.student.criminalintent;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class CrimeListFragment extends ListFragment
{
    private static final String TAG = "CrimeListFragment";
    private boolean mSubtitleVisible;
    private ArrayList<Crime> mCrimes;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.crimes_title);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        // Gets the CrimeLab singleton and then gets the list of crimes.
        mCrimes = CrimeLab.get(getActivity()).getCrimes();

        CrimeAdapter adapter = new CrimeAdapter(mCrimes);
        setListAdapter(adapter);
        setRetainInstance(true);
        mSubtitleVisible = false;

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        Crime c = ((CrimeAdapter)getListAdapter()).getItem(position);

        // Start CrimePagerActivity
        Intent i = new Intent(getActivity(), CrimePagerActivity.class);

        i.putExtra(CrimeFragment.EXTRA_CRIME_ID, c.getId());
        startActivity(i);
    }

    private class CrimeAdapter extends ArrayAdapter<Crime>
    {
        public CrimeAdapter(ArrayList<Crime> crimes)
        {
            super(getActivity(), 0, crimes);
        }

        // Returns a view inflated from the custom layout and populated with the correct Crime data.
        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            if (convertView == null)
            {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_crime,
                        null);
            }

            Crime c = getItem(position);

            TextView titleTextView = (TextView)convertView.findViewById(R.id
                    .crime_list_item_titleTextView);
            titleTextView.setText(c.getTitle());
            TextView dateTextView = (TextView)convertView.findViewById(R.id
                    .crime_list_item_dateTextView);
            dateTextView.setText(c.getDate().toString());
            CheckBox solvedCheckBox = (CheckBox)convertView.findViewById(R.id
                    .crime_list_item_solvedCheckBox);
            solvedCheckBox.setChecked(c.isSolved());

            // Returns the view object to the ListView.
            return convertView;
        }
    }

    // Updates the fragment's view by reloading the list.
    @Override
    public void onResume()
    {
        super.onResume();
        ((CrimeAdapter)getListAdapter()).notifyDataSetChanged();
    }

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

    // Responds to the user menu item selection.
    @TargetApi(11)
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            // When the user presses the "add" icon on the action bar.
            case R.id.menu_item_new_crime:
                // Create a new Crime.
                Crime crime = new Crime();
                // Calls addCrime from CrimeLab to add the crime to the ArrayList.
                CrimeLab.get(getActivity()).addCrime(crime);
                // Creates an Intent that tells CrimeListFragment which activity to start.
                Intent i = new Intent(getActivity(), CrimePagerActivity.class);
                // Passes the key-value pair to the new activity.
                i.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
                startActivityForResult(i, 0);
                // Indicates no further processing is needed.
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
                    //activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            // Calls the superclass implementation if the menu item id is not in the implementation.
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item
                .getMenuInfo();
        int position = info.position;
        CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
        Crime crime = adapter.getItem(position);

        switch (item.getItemId())
        {
            case R.id.menu_item_delete_crime:
                CrimeLab.get(getActivity()).deleteCrime(crime);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }


    // Implements onCreateContextMenu to inflate the menu resource and use it to populate the
    // context menu.
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context, menu);
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = super.onCreateView(inflater, parent, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity)getActivity();
        getActivity().setTitle(R.string.crimes_title);
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        activity.getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            if (mSubtitleVisible)
            {
                activity.getSupportActionBar().setSubtitle(R.string.subtitle);
            }
        }

        ListView listView = (ListView)v.findViewById(android.R.id.list);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB)
        {
            // Use floating context menus on Froyo and Gingerbread.
            registerForContextMenu(listView);
        } else
        {
            // Use contextual action bar on Honeycomb and higher.
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener()
            {
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id,
                                                      boolean checked)
                {
                }

                // ActionMode.Callback methods.
                public boolean onCreateActionMode(ActionMode mode, Menu menu)
                {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.crime_list_item_context, menu);
                    return true;
                }

                public boolean onPrepareActionMode(ActionMode mode, Menu menu)
                {
                    return false;
                }

                public boolean onActionItemClicked(ActionMode mode, MenuItem item)
                {
                    switch (item.getItemId())
                    {
                        case R.id.menu_item_delete_crime:
                            CrimeAdapter adapter = (CrimeAdapter)getListAdapter();
                            CrimeLab crimeLab = CrimeLab.get(getActivity());
                            for (int i = adapter.getCount() - 1; i >= 0; i--)
                            {
                                if (getListView().isItemChecked(i))
                                {
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

                public void onDestroyActionMode(ActionMode actionMode)
                {
                }
            });
        }

        return v;
    }
} // End class CrimeListFragment