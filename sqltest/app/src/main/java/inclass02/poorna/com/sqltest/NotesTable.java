package inclass02.poorna.com.sqltest;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class NotesTable {
    static final String TABLENAME="notes";
    static final String COLUMN_ID="id";
    static final String COLUMN_SUBJECT="subject";
    static final String COLUMN_TEXT="text";

    static public void onCreate(SQLiteDatabase db)
    {
        StringBuilder sb=new StringBuilder();
        sb.append("CREATE TABLE "+ TABLENAME+"(");
        sb.append(COLUMN_ID+" integer primary key autoincrement,");
        sb.append(COLUMN_SUBJECT+" text not null,");
        sb.append(COLUMN_TEXT+" text not null);");
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
        NotesTable.onCreate(db);
    }

}
