package com.rolecall;

import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rolecall.R;

import java.util.ArrayList;
import java.util.List;

public class ListRoleDialog extends AppCompatDialogFragment {

    private List<Pair<String, String>> roles;

    public static ListRoleDialog newInstance(ArrayList<Pair<String, String>> roles) {
        ListRoleDialog fragment = new ListRoleDialog();
        Bundle args = new Bundle();
        args.putSerializable("roles", roles);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_role, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.roles_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            roles = (List<Pair<String, String>>) getArguments().getSerializable("roles");
        } else {
            roles = new ArrayList<>();
        }

        ListAdapter adapter = new ListAdapter(roles);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
