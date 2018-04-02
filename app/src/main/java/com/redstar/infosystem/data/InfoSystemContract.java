package com.redstar.infosystem.data;

import android.provider.BaseColumns;

public final class InfoSystemContract {
    private InfoSystemContract() {
    };

    public static final class WorkersEntry implements BaseColumns {
        public final static String TABLE_NAME = "workers";

        public final static String _ID = "_id";
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_SURNAME = "surname";
        public final static String COLUMN_PATRONYMIC = "patronymic";
        public final static String COLUMN_POST = "post";
        public final static String COLUMN_AGE = "age";
        public final static String COLUMN_GROUP = "groupname";
        public final static String COLUMN_COMPANY = "companyname";

    }

    public static final class UsersEntry implements BaseColumns
    {
        public final static String TABLE_NAME = "users";

        public final static String _ID = "_id";
        public final static String COLUMN_LOGIN = "login";
        public final static String COLUMN_PASSWORD = "password";
        public final static String COLUMN_PERMISSION = "permission";
    }
}