package com.homenas.netdrive;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.provider.DocumentFile;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.homenas.netdrive.Constants.KEY_LAYOUT_MANAGER;
import static com.homenas.netdrive.Constants.LayoutManagerType;
import static com.homenas.netdrive.Constants.LocalRoot;
import static com.homenas.netdrive.Constants.SPAN_COUNT;
import static com.homenas.netdrive.R.id.recyclerView;


/**
 * Created by engss on 24/10/2017.
 */

public class RecyclerViewFragment extends Fragment implements CustomAdapter.CustomAdapterListener, MainActivity.OnBackPressedListener, NavigationView.OnNavigationItemSelectedListener {

    private final String TAG = getClass().getSimpleName();
    private CustomAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private LayoutManagerType mCurrentLayoutManagerType;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<FilesData> mDataset = new ArrayList<>();
    public Boolean viewGrid = true;
    public DocumentFile curFiles;
    private DocumentFile curRoot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new CustomAdapter(getActivity(), mDataset, this);
        ((MainActivity) getActivity()).setOnBackPressedListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recyclerview_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(recyclerView);
        mRecyclerView.hasFixedSize();
        DividerItemDecoration hDividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL);
        DividerItemDecoration lDividerItemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(hDividerItemDecoration);
        mRecyclerView.addItemDecoration(lDividerItemDecoration);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
        NavigationView navigationView = (NavigationView) ((AppCompatActivity)getActivity()).findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        initItemList();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        updateTitle(item.getTitle().toString());

        if (id == R.id.nav_audio) {
            // Handle the camera action
        } else if (id == R.id.nav_image) {

        } else if (id == R.id.nav_video) {

        } else if (id == R.id.nav_download) {

        } else if (id == R.id.nav_local) {

        } else if (id == R.id.nav_sdcard) {

        } else if (id == R.id.nav_network) {

        } else if (id == R.id.nav_setting) {

        }

        DrawerLayout drawer = (DrawerLayout) ((AppCompatActivity)getActivity()).findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initItemList() {
        curRoot = LocalRoot;
        updateData(LocalRoot);
        mAdapter.notifyDataSetChanged();
    }

    public void setRecyclerViewLayoutManager(Constants.LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }
        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                mAdapter.setView(viewGrid);
                mRecyclerView.setAdapter(mAdapter);
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                mAdapter.setView(!viewGrid);
                mRecyclerView.setAdapter(mAdapter);
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                mAdapter.setView(!viewGrid);
                mRecyclerView.setAdapter(mAdapter);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onItemClick(int position){
//        Toast.makeText(getActivity(), "click at " + mDataset.get(position), Toast.LENGTH_SHORT).show();
        updateData(mDataset.get(position).file);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void doBack() {
        Log.i(TAG,curRoot.getUri().toString());
        if(curFiles.getUri().toString().equals(curRoot.getUri().toString())) {
            Toast.makeText(getActivity(), "At root ", Toast.LENGTH_SHORT).show();
        }else{
            updateData(curFiles.getParentFile());
            mAdapter.notifyDataSetChanged();
        }
    }

    private void updateData(DocumentFile files) {
        if(files.isDirectory()) {
            mDataset.clear();
            curFiles = files;
            for(DocumentFile file : files.listFiles()) {
                FilesData data = new FilesData();
                data.file = file;
                data.fileName = file.getName();
                mDataset.add(data);
            }
        }
    }

    private void updateTitle(String title) {
        if(((AppCompatActivity)getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        }
    }
}
