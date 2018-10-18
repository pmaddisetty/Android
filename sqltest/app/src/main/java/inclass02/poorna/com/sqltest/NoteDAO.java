package inclass02.poorna.com.sqltest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class NoteDAO {
    private SQLiteDatabase db;
     public NoteDAO(SQLiteDatabase db)
     {
         this.db=db;
     }
     public long save(Note note)
     {
         ContentValues values=new ContentValues();
         values.put(NotesTable.COLUMN_SUBJECT,note.getSubject());
         values.put(NotesTable.COLUMN_TEXT,note.getText());
         return  db.insert(NotesTable.TABLENAME,null,values);
     }
     public boolean update(Note note)
     {
         ContentValues values=new ContentValues();
         values.put(NotesTable.COLUMN_SUBJECT,note.getSubject());
         values.put(NotesTable.COLUMN_TEXT,note.getText());
         return db.update(NotesTable.TABLENAME,values,NotesTable.COLUMN_ID+"=?",new String[]{note.getId()+""}) >0;
     }

    public boolean delete(Note note)
    {
       return db.delete(NotesTable.TABLENAME,NotesTable.COLUMN_ID+"=?",new String[]{note.getId()+""})>0;
    }
    public Note get(long id)
    {
        Note note=null;
        Cursor c=db.query(true,NotesTable.TABLENAME ,new String[]{
                NotesTable.COLUMN_ID,NotesTable.COLUMN_SUBJECT,
                NotesTable.COLUMN_TEXT},NotesTable.COLUMN_ID+"=?",
                new String[]{id+""},null,null,null,null,null);
        if(c!=null && c.moveToFirst())
        {
            note=buildnotefromCursor(c);
            if(!c.isClosed())
            {
                c.close();
            }
        }
        return note ;
    }
    public List<Note> getAll()
    {
        List<Note> noteList=new ArrayList<>();
        Cursor c=db.query(true,NotesTable.TABLENAME ,new String[]{
                        NotesTable.COLUMN_ID,NotesTable.COLUMN_SUBJECT,
                        NotesTable.COLUMN_TEXT},null,
                null,null,null,null,null,null);


        String filter="smith";
       db.rawQuery("Select * from notes where text like ?",new String[]{"%"+filter+"%"});

       db.rawQuery("Select * from contacts order by ?",new String[]{noteList.get(1).getId()+" ASC"});

        if(c!=null&&c.moveToFirst())
        {
            do{
                Note note=buildnotefromCursor(c);
                if(note!=null)
                {
                    noteList.add(note);
                }
            }while (c.moveToNext());
            if(!c.isClosed())
            {
                c.close();
            }
        }
        return noteList;
    }

    private Note buildnotefromCursor(Cursor cr)
    {
        Note note=null;
        if(cr!=null)
        {
            note=new Note();
            note.setId(cr.getLong(0));
            note.setSubject(cr.getString(1));
            note.setText(cr.getString(2));
        }
        return note;
    }
}
