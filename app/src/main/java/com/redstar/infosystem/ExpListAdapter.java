package com.redstar.infosystem;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ExpListAdapter extends BaseExpandableListAdapter {

    private ArrayList<Worker> workers;
    private ArrayList<User> users;
    private int group_layout;
    private int child_layout;
    private Context context;

    public ExpListAdapter(Context context, int group_layout, int child_layout, ArrayList<Worker> list)
    {
        this.context = context;
        this.group_layout = group_layout;
        this.child_layout = child_layout;
        this.workers = list;
    }

    public ExpListAdapter( ArrayList<User> users, Context context, int group_layout, int child_layout)
    {
        this.context = context;
        this.group_layout = group_layout;
        this.child_layout = child_layout;
        this.users = users;
    }

    @Override
    public int getGroupCount()
    {
        if (workers != null) return workers.size();
        else return users.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        if (workers != null) return workers.get(groupPosition);
        else return users.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        if (workers != null)
        {
            switch (childPosition)
            {
                case 0: return workers.get(groupPosition).getName();
                case 1: return workers.get(groupPosition).getSurname();
                case 2: return workers.get(groupPosition).getPatronymic();
                case 3: return workers.get(groupPosition).getAge();
                case 4: return workers.get(groupPosition).getPost();
                case 5: return workers.get(groupPosition).getGroup();
                case 6: return workers.get(groupPosition).getCompany();
                default: return workers.get(groupPosition).getName();
            }
        }
        else
        {
            switch (childPosition)
            {
                case 0: return users.get(groupPosition).getLogin();
                case 1: return users.get(groupPosition).getPassword();
                case 2: return users.get(groupPosition).getPermissionName();
                default: return users.get(groupPosition).getLogin();
            }
        }
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(group_layout, parent, false);
        }

        TextView textGroup = (TextView) convertView.findViewById(R.id.nameGroup);

        if (workers != null)
            textGroup.setText(workers.get(groupPosition).getName() + " " + workers.get(groupPosition).getSurname() + " " + workers.get(groupPosition).getPatronymic());
        else
            textGroup.setText(users.get(groupPosition).getLogin());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(child_layout, parent, false);
        }

        if (workers != null) {
            TextView textChild = (TextView) convertView.findViewById(R.id.nameChild);
            String buf = "Name: " + workers.get(groupPosition).getName();
            textChild.setText(buf);

            textChild = (TextView) convertView.findViewById(R.id.surnameChild);
            buf = "Surname: " + workers.get(groupPosition).getSurname();
            textChild.setText(buf);

            textChild = (TextView) convertView.findViewById(R.id.patronymicChild);
            buf = "Patronymic: " + workers.get(groupPosition).getPatronymic();
            textChild.setText(buf);

            textChild = (TextView) convertView.findViewById(R.id.ageChild);
            buf = "Age: " + workers.get(groupPosition).getAge();
            textChild.setText(buf);

            textChild = (TextView) convertView.findViewById(R.id.postChild);
            buf = "Post: " + workers.get(groupPosition).getPost();
            textChild.setText(buf);

            textChild = (TextView) convertView.findViewById(R.id.groupChild);
            buf = "Group: " + workers.get(groupPosition).getGroup();
            textChild.setText(buf);

            textChild = (TextView) convertView.findViewById(R.id.companyChild);
            buf = "Company: " + workers.get(groupPosition).getCompany();
            textChild.setText(buf);
        }
        else
        {
            TextView textChild = (TextView) convertView.findViewById(R.id.loginChild);
            String buf = "Login: " + users.get(groupPosition).getLogin();
            textChild.setText(buf);

            textChild = (TextView) convertView.findViewById(R.id.passwordChild);
            buf = "Password: " + users.get(groupPosition).getPassword();
            textChild.setText(buf);

            textChild = (TextView) convertView.findViewById(R.id.permissionChild);
            buf = "Permission: " + users.get(groupPosition).getPermissionName();
            textChild.setText(buf);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
