package c.sakshi.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    TextView welcome;

    public static ArrayList<Note> notes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        welcome = (TextView) findViewById(R.id.textViewWelcome);
        SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("username", "");
        welcome.setText("Welcome " + str + "!");
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        notes = new DBHelper(sqLiteDatabase).readNotes(str);

        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note : notes) {
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));
        }


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), Main3Activity.class);
                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.lab5menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.itemLogout){
            Intent intent = new Intent(this, MainActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences("c.sakshi.lab5", Context.MODE_PRIVATE);
            sharedPreferences.edit().remove("username").apply();
            startActivity(intent);
            return true;
        }
        if(item.getItemId() == R.id.itemAddNote){
            Intent intent = new Intent(this, Main3Activity.class);
            startActivity(intent);
            return true;
        }
        return true;
    }
}
