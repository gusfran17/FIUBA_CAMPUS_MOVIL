package ar.uba.fi.fiubappMobile.groups.Discussions;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.fiubapp.fiubapp.R;
import com.fiubapp.fiubapp.VolleyController;
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
        convertView = inflater.inflate(R.layout.message_list_row, null);

        TextView lbl_mssg_comment = (TextView) convertView.findViewById(R.id.lbl_mssg_comment);
        TextView lbl_mssg_creator_name = (TextView) convertView.findViewById(R.id.lbl_mssg_creator_name);
        TextView lbl_mssg_creation_date = (TextView) convertView.findViewById(R.id.lbl_mssg_creation_date);
        final ImageView img_uploaded_file = (ImageView) convertView.findViewById(R.id.img_uploaded_file);
        TextView txtvw_uploaded = (TextView) convertView.findViewById(R.id.txtvw_uploaded);


        final Message message = this.messageItems.get(i);
        lbl_mssg_comment.setText(message.getText());
        lbl_mssg_creator_name.setText(message.getCreatorUserName());
        lbl_mssg_creation_date.setText(message.getCreationDate());

        if (!message.isHasAttachedFile()){
            img_uploaded_file.setVisibility(View.GONE);
            txtvw_uploaded.setVisibility(View.GONE);
        } else {
            img_uploaded_file.setVisibility(View.VISIBLE);
            txtvw_uploaded.setVisibility(View.VISIBLE);
            txtvw_uploaded.setText(message.getFileName());

            //request para la imagen de perfil
            ImageRequest ir = new ImageRequest(message.getAttachedFile(),
                    new Response.Listener<Bitmap>() {

                        @Override
                        public void onResponse(Bitmap response) {
                            message.getAttachedFile();
                            img_uploaded_file.setImageBitmap(response);

                        }
                    },
                    0,
                    0,
                    null,
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //error.printStackTrace();
                            message.getAttachedFile();
                        }
                    });
            VolleyController.getInstance().addToRequestQueue(ir);

            img_uploaded_file.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.HONEYCOMB)
                @Override
                public void onClick(View view) {

                    String urlAPI = activity.getResources().getString(R.string.urlAPI);
                    String url = urlAPI + "/groups/" + message.getIdGrupo() + "/discussions/" + message.getIdDiscusion() + "/messages/" + message.getId() + "/file";

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    activity.startActivity(i);

                    //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(message.getAttachedFile()));
                    //activity.startActivityForResult(intent, 200);

                    //Intent pickIntent = new Intent(Intent.ACTION_PICK);
                    //pickIntent.setType("*/*");
                    //int requestCode = 200;
                    //pickIntent.setData(Uri.parse(message.getAttachedFile()));
                    //activity.startActivityForResult(pickIntent, requestCode);

                    /*
                    DownloadManager.Request r = new DownloadManager.Request(Uri.parse(message.getAttachedFile()));

                    // This put the download in the same Download dir the browser uses
                    r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, message.getFileName());

                    // When downloading music and videos they will be listed in the player
                    // (Seems to be available since Honeycomb only)
                    r.allowScanningByMediaScanner();

                    // Notify user when download is completed
                    // (Seems to be available since Honeycomb only)
                    r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

                    // Start download
                    DownloadManager dm = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
                    dm.enqueue(r);
                    */
                }
            });
        }

        return convertView;    }
}
