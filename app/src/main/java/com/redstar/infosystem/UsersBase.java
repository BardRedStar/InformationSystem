package com.redstar.infosystem;


import java.util.ArrayList;

public class UsersBase {

    private ArrayList<User> listUsers = new ArrayList<>();

    public ArrayList<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(ArrayList<User> listUsers) {
        this.listUsers = listUsers;
    }

    public boolean addUser(User objUser)
    {
        return listUsers.add(objUser);
    }

    public boolean deleteUser(User objUser)
    {
        return listUsers.remove(objUser);
    }

    public int getUsersCount() {return listUsers.size();}

    public ArrayList<User> findUser(String log, String pass, int perm)
    {
        ArrayList<User> finded = new ArrayList<>();

        if (!log.isEmpty()) {
            for (User u : listUsers) {
                if (u.getLogin().equals(log)) {
                    finded.add(u);
                }
            }
        }
        else if (!pass.isEmpty())
        {
            for (User u : listUsers) {
                if (u.getPassword().equals(pass)) {
                    finded.add(u);
                }
            }
        }
        else if (perm > 0)
        {
            for (User u : listUsers) {
                if (u.getPermission() == perm) {
                    finded.add(u);
                }
            }
        }
        else
        {
            for (User u : listUsers) finded.add(u);
        }

        for (int i=0;i<finded.size();i++)
        {
            if ((!(log.isEmpty()) && !log.equals(finded.get(i).getLogin())) ||
                    ((!(pass.isEmpty())) && !pass.equals(finded.get(i).getPassword())) ||
                    ((perm > 0) && perm != finded.get(i).getPermission()))
            {
                finded.remove(i);
                i--;
            }
        }
        return finded;
    }

    public void clearBase()
    {
        listUsers.clear();
    }


}
