package inclass02.poorna.com.group4_inclass14;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ContactsTable {

    static final String TABLENAME="contacts";
    static final String COLUMN_ID="id";
    static final String COLUMN_NAME="name";
    static final String COLUMN_EMAIL="email";
    static final String COLUMN_DEPT="department";

    static public void onCreate(SQLiteDatabase db)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("CREATE TABLE "+ TABLENAME+"(");
        sb.append(COLUMN_ID+" integer primary key autoincrement,");
        sb.append(COLUMN_NAME+" text not null,");
        sb.append(COLUMN_EMAIL+" text not null,");
        sb.append(COLUMN_DEPT+" text not null);");
        try{
            db.execSQL(sb.toString());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }


    }

    static public void onUpgrade(SQLiteDatabase db,int oldversion,int newversion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+TABLENAME);
        ContactsTable.onCreate(db);
    }
}
