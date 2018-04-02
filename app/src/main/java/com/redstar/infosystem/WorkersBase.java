package com.redstar.infosystem;


import java.util.ArrayList;

/**
 * Workers info holder
 */
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

    /**
     * Adds worker in list
     *
     * @param id Worker's id
     * @param name worker's new name
     * @param surname worker's new surname
     * @param patronymic worker's new patronymic
     * @param age worker's new age
     * @param post worker's new post
     * @param group worker's new group
     * @param company worker's new company
     * @return true if success and false otherwise
     */
    public boolean addWorker(int id, String name, String surname, String patronymic, int age, String post, String group, String company)
    {
        return listWorkers.add(new Worker(id, name, surname, patronymic, age, post, group, company));
    }

    /**
     * Replaces worker's info with inputted data
     *
     * @param objWorker Worker's object to edit
     * @param name worker's new name
     * @param surname worker's new surname
     * @param patronymic worker's new patronymic
     * @param age worker's new age
     * @param post worker's new post
     * @param group worker's new group
     * @param company worker's new company
     * @return true if success
     */
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

    /**
     * Deletes worker from list by object
     *
     * @param objWorker worker's object to delete
     * @return true if success and false otherwise
     */
    public boolean deleteWorker(Worker objWorker)
    {
        return listWorkers.remove(objWorker);
    }

    /**
     * Find workers by info
     *
     * @param name worker's new name
     * @param surname worker's new surname
     * @param patronymic worker's new patronymic
     * @param age worker's new age
     * @param post worker's new post
     * @param group worker's new group
     * @param company worker's new company
     * @return list of workers which data matched with inputted data
     */
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

    /**
     * Clears workers list
     */
    public void clearBase()
    {
        listWorkers.clear();
    }
}
