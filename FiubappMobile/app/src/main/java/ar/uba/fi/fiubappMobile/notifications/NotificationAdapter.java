package ar.uba.fi.fiubappMobile.notifications;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.VolleyController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class NotificationAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Notification> notifications;
    ImageLoader imageLoader = VolleyController.getInstance().getImageLoader();

    public NotificationAdapter(Activity activity, List<Notification> notifications) {
        this.activity = activity;
        this.notifications = notifications;
    }

    @Override
    public int getCount() {
        return notifications.size();
    }

    @Override
    public Object getItem(int location) {
        return notifications.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            if (notifications.get(position).getClass().equals(ApplicationNotification.class)) {
                convertView = inflater.inflate(R.layout.application_notification, null);
                this.populateApplicationNotification((ApplicationNotification) notifications.get(position), convertView);
            }
        }

        return convertView;
    }

    private void populateApplicationNotification(ApplicationNotification notification, View convertView) {
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView description = (TextView) convertView.findViewById(R.id.description);
        TextView creationDate = (TextView) convertView.findViewById(R.id.creationDate);
        TextView markAsViewed = (TextView) convertView.findViewById(R.id.viewed);

        name.setText(notification.getApplicantName() + " " + notification.getApplicantLastName());
        description.setText(R.string.application_notification_message);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        markAsViewed.setText(R.string.application_notification_mark_as_viewed);
        creationDate.setText(df.format(notification.getCreationDate()));
    }

}
