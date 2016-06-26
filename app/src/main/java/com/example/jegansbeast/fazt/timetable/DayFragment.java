package com.example.jegansbeast.fazt.timetable;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.jegansbeast.fazt.Database;
import com.example.jegansbeast.fazt.MainActivity;
import com.example.jegansbeast.fazt.R;
import com.example.jegansbeast.fazt.T;
import com.example.jegansbeast.fazt.communication.SubjectItemMonitor;
import com.example.jegansbeast.fazt.subject.Subject;
import com.example.jegansbeast.fazt.timetable.inputdialog.ItemSelectorAdapter;
import com.example.jegansbeast.fazt.timetable.inputdialog.ListItemDecoration;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;


public class DayFragment extends Fragment implements ItemSelectorAdapter.ItemSelectionInteraction
        , ClickListener {

    private Database.DAYS day;
    SubjectItemMonitor monitor = SubjectItemMonitor.getInstance();
    RecyclerView list;
    FloatingActionButton add;
    Dialog dialog = null;
    DayFragment dayFragment = this;
    DaySubjectListAdapter adapter;
    ActionMode actionMode;
    ActionModeCallback callback = new ActionModeCallback();
    AlertDialog.Builder builder;

    public DayFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        list = (RecyclerView) view.findViewById(R.id.day_list);
        add = (FloatingActionButton) view.findViewById(R.id.day_add);

        adapter = new DaySubjectListAdapter(getActivity(), day, this);
        list.setAdapter(adapter);


        list.addItemDecoration(new ListItemDecoration(getContext(), R.drawable.subjects_divider));

        list.setLayoutManager(new LinearLayoutManager(getContext()));

        list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 5)
                    add.hide();
                else if (dy <= 5)
                    add.show();
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter.getSelectedItemCount()>0) {
                    adapter.clearSelection();
                    actionMode.finish();
                }
                setupBuilder(1);//insert mode
                dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }

    protected void setupBuilder(int mode) {
        builder = new AlertDialog.Builder(getContext());

        View input = LayoutInflater.from(getContext()).inflate(R.layout.day_subject_input, null);
        ExpandableListView expandableListView = (ExpandableListView) input.findViewById(R.id.input_day_subject_list);
        ItemSelectorAdapter itemSelectorAdapter = new ItemSelectorAdapter(getContext(), dayFragment,mode);

        expandableListView.setAdapter(itemSelectorAdapter);

        expandableListView.setOnChildClickListener(itemSelectorAdapter);

        builder.setView(input);
        builder.setTitle("Select Item");

        builder.setCancelable(true);
    }

    public Database.DAYS getDay() {
        return day;
    }

    public void setDay(Database.DAYS day) {
        this.day = day;

    }

    @Override
    public void selected(PositionComparable object, int mode) {
        if (dialog != null) {
            if (object != null) {
                switch (mode) {
                    case 1:
                        //insert
                        Database database = SubjectItemMonitor.getInstance().getDb();
                        if (object instanceof Subject)
                            database.insertSubjectWithDay(day, (Subject) object, adapter.getItemCount() + 1);
                        else if (object instanceof OtherItem) {
                            database.insertOtherItemWithDay(day, (OtherItem) object, adapter.getItemCount() + 1);
                        }
                        adapter.refreshList();
                        dialog.cancel();
                        break;
                    case 2:
                        //update
                        if(adapter.getSelectedItemCount()<2) {
                            int selectedpos = (int) adapter.getSelectedItems().get(0);
                            monitor.updateItem(adapter.itemList.get(selectedpos),object,day);
                            adapter.refreshList();
                            adapter.clearSelection();
                            actionMode.finish();
                            dialog.cancel();

                        }
                        break;
                }
            }

            dialog = null;
        }
    }

    private void toggleSelection(int position) {
        adapter.toggleSelection(position);

        int count = adapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }


    @Override
    public void onItemClicked(int position) {
        if (actionMode != null) {
            toggleSelection(position);
        }

    }

    @Override
    public boolean onItemLongClicked(int position) {
        if (actionMode == null) {
            actionMode = ((MainActivity) getActivity()).startSupportActionMode(callback);
        }

        toggleSelection(position);

        return true;
    }


    public class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.timetable_select, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

            MenuItem edititem = menu.findItem(R.id.edit);
            edititem.setIcon(new IconicsDrawable(getContext()).icon(FontAwesome.Icon.faw_pencil).color(Color.WHITE).sizeDp(25));
            MenuItem deleteitem = menu.findItem(R.id.delete);
            deleteitem.setIcon(new IconicsDrawable(getContext()).icon(FontAwesome.Icon.faw_trash).color(Color.WHITE).sizeDp(25));

            if (adapter.getSelectedItemCount() > 1) {
                edititem.setVisible(false);
            } else {
                edititem.setVisible(true);
            }
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {

                case R.id.edit:
                    setupBuilder(2);// update mode
                    dialog = builder.create();
                    dialog.show();

                    break;

                case R.id.delete:
                    T.toastShort(getContext(), "deleting items");

                    for (Object o : adapter.getSelectedItems()) {
                        if (o instanceof Integer) {
                            monitor.deleteItem(adapter.itemList.get((Integer) o), day);
                        }
                    }

                    adapter.refreshList();
                    adapter.clearSelection();
                    actionMode.finish();
                    break;
            }

            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelection();
            actionMode = null;
        }
    }
}
