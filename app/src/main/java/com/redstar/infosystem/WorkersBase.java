package com.redstar.infosystem;


import java.util.ArrayList;

public class WorkersBase {

    private ArrayList<Worker> listWorkers = new ArrayList<>();

    public ArrayList<Worker> getListWorkers()
    {
        return listWorkers;
    }

    public void setListWorkers(ArrayList<Worker> listWorkers)
    {
        this.listWorkers = listWorkers;
    }

    public int getWorkersCount()
    {
        return listWorkers.size();
    }

    public boolean addWorker(Worker objWorker)
    {
        return listWorkers.add(objWorker);
    }

    public boolean addWorker(int id, String name, String surname, String patronymic, int age, String post, String group, String company)
    {
        return listWorkers.add(new Worker(id, name, surname, patronymic, age, post, group, company));
    }

    public boolean editWorker(Worker objWorker, String name, String surname, String patronymic, int age, String post, String group, String company)
    {
        if (!name.isEmpty()) objWorker.setName(name);
        if (!surname.isEmpty()) objWorker.setSurname(surname);
        if (!post.isEmpty()) objWorker.setPost(post);
        if (age > 0) objWorker.setAge(age);
        if (!group.isEmpty()) objWorker.setGroup(group);
        if (!company.isEmpty()) objWorker.setCompany(company);
        return true;
    }

    public boolean deleteWorker(Worker objWorker)
    {
        return listWorkers.remove(objWorker);
    }

    public ArrayList<Worker> findWorkers(String name, String surname, String patronymic, int age, String post, String group, String company)
    {
        ArrayList<Worker> finded = new ArrayList<>();

        if (!(name.isEmpty()))
        {
            for (Worker w : listWorkers)
                if (w.getName().equals(name)) finded.add(w);
        }
        else if (!(surname.isEmpty()))
        {
            for (Worker w : listWorkers)
                if (w.getSurname().equals(surname)) finded.add(w);
        }
        else if (!(patronymic.isEmpty()))
        {
            for (Worker w : listWorkers)
                if (w.getPatronymic().equals(patronymic)) finded.add(w);
        }
        else if (!(post.isEmpty()))
        {
            for (Worker w : listWorkers)
                if (w.getPost().equals(post)) finded.add(w);
        }
        else if (age > 0)
        {
            for (Worker w : listWorkers)
                if (w.getAge() == age) finded.add(w);
        }
        else if (!(group.isEmpty()))
        {
            for (Worker w : listWorkers)
                if (w.getGroup().equals(group)) finded.add(w);
        }
        else if (!(company.isEmpty()))
        {
            for (Worker w : listWorkers)
                if (w.getCompany().equals(company)) finded.add(w);
        }
        else
        {
            for (Worker w : listWorkers) finded.add(w);
        }

        for (int i=0;i<finded.size();i++)
        {
            if ((!(name.isEmpty()) && !name.equals(finded.get(i).getName())) ||
                    ((!(surname.isEmpty())) && !surname.equals(finded.get(i).getSurname())) ||
                    ((!(patronymic.isEmpty())) && !patronymic.equals(finded.get(i).getPatronymic())) ||
                    ((!(post.isEmpty())) && !post.equals(finded.get(i).getPost())) ||
                    ((age > 0) && age != finded.get(i).getAge()) ||
                    ((!(group.isEmpty())) && !group.equals(finded.get(i).getGroup())) ||
                    ((!(company.isEmpty())) && !company.equals(finded.get(i).getCompany())))
            {
                finded.remove(i);
                i--;
            }
        }

        return finded;
    }

    public void clearBase()
    {
        listWorkers.clear();
    }
}
