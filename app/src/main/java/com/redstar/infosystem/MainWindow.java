package com.redstar.infosystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.redstar.infosystem.data.InfoSystemDbHelper;

import java.util.ArrayList;

public class MainWindow extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private InfoSystemDbHelper db;
    private WorkersBase workersBase;
    private UsersBase usersBase;
    private Worker selectedWorker = null;
    private User selectedUser = null;
    private int permission = 0;

    ///Request types
    private static final int REQUEST_WORKER = 1;
    private static final int REQUEST_USER = 2;

    //Tag for log messages
    private static final String LOG_TAG = "MainWindow";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);

        /// Get user permission
        Bundle extra = getIntent().getExtras();
        permission = extra.getInt("permission");

        /// Set up navigation menu and toolbar
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        toolbar.setTitle("Information System");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {

                /// Draw drawer (lol) by user's permission value
                TextView tw = (TextView) findViewById(R.id.nav_textview);

                if (permission == 1)
                {
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    Menu m = navigationView.getMenu();
                    m.getItem(0).setEnabled(false);
                    m.getItem(1).setEnabled(false);
                    tw.setText("You are logged as User");
                }
                else if (permission == 2)
                {
                    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                    Menu m = navigationView.getMenu();
                    m.getItem(1).setEnabled(false);
                    tw.setText("You are logged as Employee");
                }
                else if (permission == 3) tw.setText("You are logged as Admin");
                else
                {
                    Log.w(LOG_TAG, "Permission is invalid!");
                    finish();
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {}

            @Override
            public void onDrawerStateChanged(int newState) {}

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}
        });

        /// Initialize DB Helper
        db = new InfoSystemDbHelper(this);

        /// Initialize Tabs
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("workers");
        tabSpec.setContent(R.id.listWorkers);
        tabSpec.setIndicator("Workers");
        tabHost.addTab(tabSpec);

        /// Update data in lists and bind context menu for them
        workersBase = new WorkersBase();
        db.getWorkersFromDatabase(workersBase);
        updateDataInWorkersList(workersBase.getListWorkers());

        if (permission == 2) registerForContextMenu(findViewById(R.id.expWorkersListView));
        if (permission == 3)
        {
            usersBase = new UsersBase();
            db.getUsersFromDatabase(usersBase);
            updateDataInUsersList(usersBase.getListUsers());

            tabSpec = tabHost.newTabSpec("users");
            tabSpec.setContent(R.id.listUsers);
            tabSpec.setIndicator("Users");
            tabHost.addTab(tabSpec);
            registerForContextMenu(findViewById(R.id.expWorkersListView));
            registerForContextMenu(findViewById(R.id.expUsersListView));
        }
        tabHost.setCurrentTab(0);
    }

    /// Close database connection when app is stopped
    @Override
    protected void onStop()
    {
        db.closeDatabase();
        super.onStop();
    }

    /// Close database connection when app is destroyed
    @Override
    protected void onDestroy()
    {
        db.closeDatabase();
        super.onDestroy();
    }

    /**
     * Updates data in list with {@link Worker workers} info.
     *
     * @param workersList list with new information about workers
     */
    private void updateDataInWorkersList(ArrayList<Worker> workersList)
    {
        /// Set adapter with new list to ExpandableListView
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expWorkersListView);
        ExpListAdapter adapter = new ExpListAdapter(this, R.layout.explistview_group,
                R.layout.explistviewworkers_child, workersList);
        listView.setAdapter(adapter);

        /// If item has been longclicked, remember position for context menu
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedWorker = workersBase.getListWorkers().get(position);
                return false;
            }
        });
    }

    /**
     * Updates data in list with {@link User users} info.
     *
     * @param usersList list with new information about workers
     */
    private void updateDataInUsersList(ArrayList<User> usersList)
    {
        /// Set adapter with new list to ExpandableListView
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.expUsersListView);

        ExpListAdapter adapter = new ExpListAdapter(usersList, this,
                R.layout.explistview_group, R.layout.explistviewusers_child);
        listView.setAdapter(adapter);

        /// If item has been longclicked, remember position for context menu
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedUser = usersBase.getListUsers().get(position);
                return false;
            }
        });
    }

    /// On device back button has been pressed, close drawer if it was opened.
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /// Inflate options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_window_drawer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /// Inflate Context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    /**
     * Called when item has been selected.
     *
     * @param item Menu item which has been selected
     * @return true, if item has been selected and false otherwise.
     */
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.option_edit:
            {
                if (selectedWorker != null) {
                    Intent intent = new Intent(this, InfoWorkerWindow.class);
                    intent.putExtra("mode", 3);
                    intent.putExtra("name", selectedWorker.getName());
                    intent.putExtra("surname", selectedWorker.getSurname());
                    intent.putExtra("patronymic", selectedWorker.getPatronymic());
                    intent.putExtra("age", selectedWorker.getAge());
                    intent.putExtra("post", selectedWorker.getPost());
                    intent.putExtra("group", selectedWorker.getGroup());
                    intent.putExtra("company", selectedWorker.getCompany());
                    startActivityForResult(intent, REQUEST_WORKER);
                }
                else
                {
                    Intent intent = new Intent(this, InfoUserWindow.class);
                    intent.putExtra("mode", 3);
                    intent.putExtra("login", selectedUser.getLogin());
                    intent.putExtra("password", selectedUser.getPassword());
                    intent.putExtra("permission", selectedUser.getPermission());
                    startActivityForResult(intent, REQUEST_USER);
                }
                break;
            }
            case R.id.option_remove:
            {
                if (selectedWorker != null) {
                    if (db.deleteWorkerFromDb(selectedWorker)) {
                        workersBase.deleteWorker(selectedWorker);
                        updateDataInWorkersList(workersBase.getListWorkers());
                    }
                }
                else
                {
                    if (db.deleteUserFromDb(selectedUser))
                    {
                        usersBase.deleteUser(selectedUser);
                        updateDataInUsersList(usersBase.getListUsers());
                    }
                }
                break;
            }
        }
        return true;
    }

    /**
     * On any item from Navigation menu has been selected
     *
     * @param item Menu item which has been selected
     * @return true, if item has been selected and false otherwise.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addworker) {
            Intent intent = new Intent(this, InfoWorkerWindow.class);
            intent.putExtra("mode", 1);
            startActivityForResult(intent, REQUEST_WORKER);
        }
        else if (id == R.id.nav_adduser)
        {
            Intent intent = new Intent(this, InfoUserWindow.class);
            intent.putExtra("mode", 1);
            startActivityForResult(intent, REQUEST_USER);
        }
        else if (id == R.id.nav_logout) {
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * On called activity finished with result.
     *
     * @param requestCode Request identifier
     * @param resultCode Result identifier
     * @param data Transfered data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode==REQUEST_WORKER)
        {
            if (resultCode == RESULT_OK)
            {
                Bundle extras = data.getExtras();
                int mode = extras.getInt("mode");


                if (mode < 1)
                {
                    return;
                }

                String name = extras.getString("name");
                String surname = extras.getString("surname");
                String patronymic = extras.getString("patronymic");
                int age = extras.getInt("age");
                String post = extras.getString("post");
                String group = extras.getString("group");
                String company = extras.getString("company");


                if (mode == 1)
                {
                    Worker objWorker = db.addWorkerInDb(new Worker(0, name, surname, patronymic,
                            age, post, group, company));
                    if (objWorker != null)
                    {
                        workersBase.addWorker(objWorker);
                        updateDataInWorkersList(workersBase.getListWorkers());
                    }
                }
                else if (mode == 2)
                {
                    if (name.isEmpty() && surname.isEmpty() && patronymic.isEmpty() && age == 0
                            && post.isEmpty() && group.isEmpty() && company.isEmpty())
                    {
                        updateDataInWorkersList(workersBase.getListWorkers());
                        return;
                    }

                    updateDataInWorkersList(workersBase.findWorkers(name, surname, patronymic, age,
                            post, group, company));
                }
                else if (mode == 3)
                {
                    selectedWorker.setName(name);
                    selectedWorker.setSurname(surname);
                    selectedWorker.setPatronymic(patronymic);
                    selectedWorker.setAge(age);
                    selectedWorker.setPost(post);
                    selectedWorker.setGroup(group);
                    selectedWorker.setCompany(company);

                    db.updateWorkerInDb(selectedWorker);

                    updateDataInWorkersList(workersBase.getListWorkers());
                    selectedWorker = null;
                }
            }
        }
        else if (requestCode == REQUEST_USER)
        {
            if (resultCode == RESULT_OK)
            {
                Bundle extras = data.getExtras();
                int mode = extras.getInt("mode");

                if (mode < 1)
                {
                    return;
                }

                String login = extras.getString("login");
                String password = extras.getString("password");
                int permission = extras.getInt("permission");

                if (mode == 1)
                {
                    User objUser = db.addUserInDb(new User(0, login, password, permission));
                    if (objUser != null)
                    {
                        usersBase.addUser(objUser);
                        updateDataInUsersList(usersBase.getListUsers());
                    }
                }
                else if (mode == 2)
                {
                    if (login.equals("") && password.equals("") && permission == 0)
                    {
                        updateDataInUsersList(usersBase.getListUsers());
                        return;
                    }

                    updateDataInUsersList(usersBase.findUser(login, password, permission));
                }
                else if (mode == 3)
                {
                    selectedUser.setLogin(login);
                    selectedUser.setPassword(password);
                    selectedUser.setPermission(permission);
                    selectedUser.updatePermissionName();

                    db.updateUserInDb(selectedUser);

                    updateDataInUsersList(usersBase.getListUsers());
                    selectedUser = null;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * On search button has been clicked
     *
     * @param view context view
     */
    public void onFabClick(View view)
    {
        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        switch (tabHost.getCurrentTab())
        {
            case 0:
            {
                /// If we clicked on button on Workers tab, call workers search activity
                Intent intent = new Intent(this, InfoWorkerWindow.class);
                intent.putExtra("mode", 2);
                startActivityForResult(intent, REQUEST_WORKER);
                break;
            }
            case 1:
            {
                /// If we clicked on button on Users tab, call users search activity
                Intent intent = new Intent(this, InfoUserWindow.class);
                intent.putExtra("mode", 2);
                startActivityForResult(intent, REQUEST_USER);
                break;
            }
        }

    }
}
