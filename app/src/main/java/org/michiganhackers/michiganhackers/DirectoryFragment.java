package org.michiganhackers.michiganhackers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

// Todo: it is a good practice when using fragments to check isAdded before getActivity() is called. This helps avoid a null pointer exception when the fragment is detached from the activity. OR getActivity() == null
public class DirectoryFragment extends Fragment {

    private TreeMap<String, Team> teamsByName;
    private DirectoryExpandableListAdapter directoryExpandableListAdapter;
    private HashMap<DatabaseReference, ValueEventListener> valueEventListeners;
    public DirectoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_directory, container, false);

        ExpandableListView expandableListView = layout.findViewById(R.id.directory_expandableListView);
        if(teamsByName == null){
            teamsByName = new TreeMap<>();
        }
        ExecuteOnDataChange executeOnDataChange = new ExecuteOnDataChange() {
            @Override
            public void executeOnDataChange() {
                directoryExpandableListAdapter.notifyDataSetChanged();
            }
        };
        UserDataRepo userDataRepo = new UserDataRepo(teamsByName, executeOnDataChange);
        directoryExpandableListAdapter = new DirectoryExpandableListAdapter(getContext(),teamsByName);
        expandableListView.setAdapter(directoryExpandableListAdapter);

        Button editProfileButton = layout.findViewById(R.id.directroy_editProfileButton);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                getActivity().startActivity(intent);
            }
        });

        return layout;
    }
}
