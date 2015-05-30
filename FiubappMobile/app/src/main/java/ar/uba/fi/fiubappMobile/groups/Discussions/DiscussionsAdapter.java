package ar.uba.fi.fiubappMobile.groups.Discussions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.dominio.Discussion;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Gustavo.Franco on 22/05/2015.
 */
public class DiscussionsAdapter extends BaseAdapter {
    private int groupId;
    private Activity activity;
    private LayoutInflater inflater;
    private List<Discussion> discussionItems;

    public DiscussionsAdapter(Activity activity, List<Discussion> discussionItems, int groupId) {
        this.activity = activity;
        this.discussionItems = discussionItems;
        this.groupId = groupId;
    }


    @Override
    public int getCount() {
        return this.discussionItems.size();
    }

    @Override
    public Object getItem(int i) {
        return this.discussionItems.get(i);
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
            convertView = inflater.inflate(R.layout.discussion_list_row, null);

        TextView lbl_discussion_name = (TextView) convertView.findViewById(R.id.lbl_discussion_name);
        TextView lbl_creator_name = (TextView) convertView.findViewById(R.id.lbl_creator_name);
        TextView lbl_creation_date_value = (TextView) convertView.findViewById(R.id.lbl_creation_date_value);
        TextView lbl_messages_amount = (TextView) convertView.findViewById(R.id.lbl_messages_amount);

        final Discussion discussion = this.discussionItems.get(i);
        lbl_discussion_name.setText(discussion.getDiscussionName());
        lbl_creator_name.setText(discussion.getCreatorUserName());
        lbl_creation_date_value.setText(discussion.getCreationDate());
        lbl_messages_amount.setText(discussion.getMessagesAmount() + " comentarios");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, DiscussionActivity.class);
                i.putExtra("discussionId",discussion.getId());
                i.putExtra("groupId", groupId);
                i.putExtra("discussionName",discussion.getDiscussionName());
                activity.startActivity(i);

            }
        });

        return convertView;
    }
}
