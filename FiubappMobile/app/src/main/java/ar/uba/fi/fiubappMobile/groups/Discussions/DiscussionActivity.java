package ar.uba.fi.fiubappMobile.groups.Discussions;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.dominio.Message;

import java.util.ArrayList;
import java.util.List;

public class DiscussionActivity extends Activity {

    private String urlAPI;
    private MessagesAdapter messagesAdapter;
    private List<Message> messagesList = new ArrayList<Message>();
    private ListView messagesListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        messagesListView = (ListView) findViewById(R.id.lstVw_messages);

        fillMesaagesList();

        messagesAdapter = new MessagesAdapter(this, messagesList);
        messagesListView.setAdapter(messagesAdapter);

        ImageView btn_mssg_back = (ImageView) findViewById(R.id.btn_mssg_back);
        btn_mssg_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void fillMesaagesList() {
        Message message1 = new Message();
        Message message2 = new Message();
        message1.setCreationDate("03/05/2015");
        message1.setCreatorUserName("86500");
        message1.setText("Mesnaje de Prueba numero 1 para probar la vista");
        message2.setCreationDate("23/02/2015");
        message2.setCreatorUserName("86501");
        message2.setText("Mensaje numero 2");
        messagesList.add(message1);
        messagesList.add(message2);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
