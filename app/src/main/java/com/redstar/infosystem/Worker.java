package com.redstar.infosystem;

/**
 * Worker's info
 */
public class Worker {
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private int age;
    private String date;
    private String post;
    private String group;
    private String company;


    public Worker()
    {
        id = 0;
        name = "";
        surname = "";
        patronymic = "";
        post = "";
        company = "";
        group = "";
        age = 0;
    }

    public Worker(int id, String name, String surname, String patronymic, int age, String post, String group, String company)
    {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.post = post;
        this.company = company;
        this.group = group;
        this.age = age;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public boolean isWorkerValid()
    {
        return (!name.isEmpty());
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
