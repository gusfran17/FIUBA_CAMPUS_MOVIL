package com.fiubapp.fiubapp;

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

public class MessageMultipartRequest<T> extends Request<T> {


    private MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();
    private final Response.Listener<T> mListener;
    private final File mImageFile;
    private final Uri uri;
    protected Map<String, String> headers;
    private Context context;
    private String mimeType;
    private String userName;
    private final String text;

    public MessageMultipartRequest(String url,
                            Response.Listener<T> listener,
                            Response.ErrorListener errorListener,
                            Uri uri,
                            Context context,
                            Map<String, String> headers,
                            String mimeType,
                            String userName,
                            String text)
    {
        super(Method.POST, url, errorListener);

        mListener = listener;
        this.uri = uri;

        this.context = context;
        this.headers = headers;
        this.mimeType = mimeType;
        mImageFile = getFile(uri);
        this.userName = userName;
        this.text = text;

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
        String nombre = "";

        if(uri.toString().contains("content://")) {
            nombre = getRealPathFromURI(this.context, this.uri);
        }
        else {
            nombre = uri.getPath();
        }

        mBuilder.addBinaryBody("file", mImageFile, ContentType.create(this.mimeType), nombre);
        mBuilder.addTextBody("text", this.text, ContentType.create("text/plain"));
        mBuilder.addTextBody("userName", this.userName, ContentType.create("text/plain"));

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


    private File getFile(Uri uri){
        File file = null;

        if(uri.toString().contains("content://")) {
            file = new File(getRealPathFromURI(context,uri));
        }
        else{
            file = new File(uri.getPath());
        }

        return file;
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