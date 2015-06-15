package ar.uba.fi.fiubappMobile.groups;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class GroupFileMultipartRequest<T> extends Request<T> {

    private static final String FILE_PART_NAME = "file";

    private MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();
    private final Response.Listener<T> mListener;
    private File mImageFile;
    private final Uri uri;
    protected Map<String, String> headers;
    private Context context;
    private String mimeType;

    public GroupFileMultipartRequest(String url,
                            Response.ErrorListener errorListener,
                            Response.Listener<T> listener,
                            Uri uri,
                            Context context,
                            Map<String, String> headers,
                            String mimeType)
    {
        super(Method.POST, url, errorListener);

        mListener = listener;
        this.uri = uri;
        this.context = context;
        this.headers = headers;
        this.mimeType = mimeType;

        buildMultipartEntity();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = super.getHeaders();

        if (this.headers == null
                || this.headers.equals(Collections.emptyMap())) {
            this.headers = new HashMap<String, String>();
        }

        return this.headers;
    }

    private void buildMultipartEntity()
    {

        String fileName = "";
        if (uri != null){
            if(uri.toString().contains("content://")) {
                fileName = getRealPathFromURI(this.context, this.uri);
            }
            else {
                fileName = uri.getPath();
            }
        }
        mImageFile = new File(fileName);
        mBuilder.addBinaryBody(FILE_PART_NAME, mImageFile, ContentType.create(this.mimeType), fileName);
        mBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        mBuilder.setLaxMode().setBoundary("xx").setCharset(Charset.forName("UTF-8"));
    }

    @Override
    public String getBodyContentType()
    {
        String contentTypeHeader = mBuilder.build().getContentType().getValue();
        return contentTypeHeader;
    }

    @Override
    public byte[] getBody() throws AuthFailureError
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {
            mBuilder.build().writeTo(bos);
        }
        catch (IOException e)
        {
            VolleyLog.e("IOException writing to ByteArrayOutputStream bos, building the multipart request.");
        }

        return bos.toByteArray();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response)
    {
        T result = null;
        return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(T response)
    {
        mListener.onResponse(response);
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}