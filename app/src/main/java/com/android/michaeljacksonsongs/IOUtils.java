package com.android.michaeljacksonsongs;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class IOUtils {

    public IOUtils(Context context, ProgressHandlerCallback callback){
        this.callback = callback;
        this.context = context;
    }

    Context context;
    File file;
    File jsonFile;
    String urlString = "https://itunes.apple.com/search?term=Michael+jackson";

    ProgressHandlerCallback callback;

    String Response;

    void getResultList(){
        ItunesAsyncTask task = new ItunesAsyncTask();
        task.execute();
    }

    public void saveResponseFromHttpUrl(URL url) {

        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = urlConnection.getInputStream();
            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                file = new File(android.os.Environment.getExternalStorageDirectory(), "MichaelSongs");
            else
                file = context.getCacheDir();
            if (!file.exists())
                file.mkdirs();
            jsonFile = new File(Environment.getExternalStorageDirectory()+"/MichaelSongs/file.txt");
            OutputStream outStream = new FileOutputStream(jsonFile);

            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            in.close();
            outStream.close();
            urlConnection.disconnect();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String readJSONFile(){

        Scanner sc = null;
        try {
            sc = new Scanner(jsonFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StringBuffer sb = new StringBuffer();

        while(sc.hasNext()) {
            sb.append("\n\r"+sc.nextLine());
        }

        return sb.toString();
    }


    class ItunesAsyncTask extends AsyncTask<String, Void, Void> {

        URL url;

        @Override
        protected Void doInBackground(String... strings) {
            try {
                url = new URL(urlString);
                saveResponseFromHttpUrl(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void weatherData) {
            Response = readJSONFile();
            callback.onDataDownloadFinish();
        }
    }

}
