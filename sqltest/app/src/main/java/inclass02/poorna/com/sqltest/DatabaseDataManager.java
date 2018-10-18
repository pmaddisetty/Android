package inclass02.poorna.com.sqltest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class DatabaseDataManager {
    private Context mcontext;
    private DatabaseOpenHelper dbopenhelper;
    private SQLiteDatabase db;
    private NoteDAO notedao;

    public DatabaseDataManager(Context mcontext)
    {
        this.mcontext=mcontext;
        dbopenhelper=new DatabaseOpenHelper(this.mcontext);
        db=dbopenhelper.getWritableDatabase();
        notedao=new NoteDAO(db);
    }

    public void close()
    {
        if(db!=null)
        {
            db.close();
        }
    }
    public NoteDAO getNotedao()
    {
        return this.notedao;
    }
    public long save(Note note)
    {
        return this.notedao.save(note);
    }
    public boolean update(Note note)
    {
        return this.notedao.update(note);
    }
    public boolean delete(Note note)
    {
        return this.delete(note);
    }
    public Note get(long id)
    {
        return this.notedao.get(id);
    }
    public List<Note> getAll()
    {
        return this.notedao.getAll();
    }
}
