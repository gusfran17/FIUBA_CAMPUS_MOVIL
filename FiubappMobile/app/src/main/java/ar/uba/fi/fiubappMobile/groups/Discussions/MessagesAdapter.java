package ar.uba.fi.fiubappMobile.groups.Discussions;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.dominio.Message;

import java.util.List;

/**
 * Created by Gustavo.Franco on 22/05/2015.
 */
public class MessagesAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<Message> messageItems;


    public MessagesAdapter(Activity activity, List<Message> messageItems) {
        this.activity = activity;
        this.messageItems = messageItems;
    }

    @Override
    public int getCount() {
        return messageItems.size();
    }

    @Override
    public Object getItem(int i) {
        return messageItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.message_list_row, null);

        TextView lbl_mssg_comment = (TextView) convertView.findViewById(R.id.lbl_mssg_comment);
        TextView lbl_mssg_creator_name = (TextView) convertView.findViewById(R.id.lbl_mssg_creator_name);
        TextView lbl_mssg_creation_date = (TextView) convertView.findViewById(R.id.lbl_mssg_creation_date);
        ImageView img_uploaded_file = (ImageView) convertView.findViewById(R.id.img_uploaded_file);
        TextView txtvw_uploaded = (TextView) convertView.findViewById(R.id.txtvw_uploaded);


        Message message = this.messageItems.get(i);
        lbl_mssg_comment.setText(message.getText());
        lbl_mssg_creator_name.setText(message.getCreatorUserName());
        lbl_mssg_creation_date.setText(message.getCreationDate());

        if (!message.isHasAttachedFile()){
            img_uploaded_file.setVisibility(View.GONE);
            txtvw_uploaded.setVisibility(View.GONE);
        } else {
            img_uploaded_file.setVisibility(View.VISIBLE);
            txtvw_uploaded.setVisibility(View.VISIBLE);
        }

        return convertView;    }
}
