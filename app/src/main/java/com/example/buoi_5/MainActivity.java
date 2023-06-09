package com.example.buoi_5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView lv_tin_tuc;
    String link = "https://vtc.vn/rss/thoi-su.rss";
    String link_2 = "https://vtv.vn/trong-nuoc/xa-hoi.rss";
    List<Tin_tuc> tin_tucList;
    String[] the_loai;
    Button btn_show;
    TextView tv_size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_size = findViewById(R.id.tv_size);
        btn_show = findViewById(R.id.btn_show);
        tin_tucList = new ArrayList<>();
        lv_tin_tuc = findViewById(R.id.lv_tin_tuc);
        the_loai = getResources().getStringArray(R.array.the_loai);

        //share bai bao
        lv_tin_tuc.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String link = tin_tucList.get(position).getLink();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Chia sẻ bài báo");
                shareIntent.putExtra(Intent.EXTRA_TEXT, link);
                startActivity(Intent.createChooser(shareIntent, "Chia sẻ bài báo"));
                return true;
            }
        });


        lv_tin_tuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {



            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Thông báo").setMessage("Bạn muốn xem tin tức bằng trình duyệt hay xem trên app luôn ?");

                builder.setNegativeButton("trên app", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this , Show_tin_tucActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("link" , tin_tucList.get(position).getLink());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });

                builder.setPositiveButton("trình duyệt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String link = tin_tucList.get(position).getLink();
                        startActivity(new Intent(Intent.ACTION_VIEW , Uri.parse(link)));
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }


        });
        show_tin_tuc(link , "Thời sự VTC");
    }

    public void test_data(String link){
        Log.e("---------------------" , "Kết nối đến link ");
        try {
//          kiểm tra link đúng hay sai
            URL url = new URL(link);
//          Mở kết nối
            HttpURLConnection urlConnection =
                    (HttpURLConnection) url.openConnection();
//          lấy data vào qua inputStream ( một đối tượng cho phép đọc )
            InputStream inputStream = urlConnection.getInputStream();
//          Dùng các đối tượng sau để xử lí file hoặc dữ liệu kiểu xml
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(false);

            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(inputStream, "utf-8");
//            lay su kien
            int event = xmlPullParser.getEventType();

            Tin_tuc tin_tuc = null;
            String text = null;

            Log.e("\n\tbắt đầu lấy data " , "step 1");

            while (event != XmlPullParser.END_DOCUMENT) {

                String tag = xmlPullParser.getName();
                switch (event) {
                    case XmlPullParser.START_TAG:
                        if ( tag.equalsIgnoreCase("item") ){
                            tin_tuc = new Tin_tuc();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = xmlPullParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tin_tuc != null){
                            if (tag.equalsIgnoreCase("title")){
                                tin_tuc.title = text;
                            }

                            if (tag.equalsIgnoreCase("description")){
                                tin_tuc.des = text;
                            }

                            if (tag.equalsIgnoreCase("link")){
                                tin_tuc.link = text;
                            }

                            if (tag.equalsIgnoreCase("pubDate")){
                                tin_tuc.pubDate = text;
                            }

                            if (tag.equalsIgnoreCase("item")){
                                tin_tucList.add(tin_tuc);
                            }
                        }
                        break;
                }

                event = xmlPullParser.next();
            }
            Log.e("----------------------" , " " + tin_tucList.size() + "\t\n\t\t Finish");
            tv_size.setText("Tổng :  " + tin_tucList.size() + " Tin và bài viết");
        } catch (MalformedURLException e) {
            Log.e("Error 1 :  " , String.valueOf(e.getMessage()));
//            e.printStackTrace();
        } catch (IOException e) {
            Log.e("Error 2 :  " , String.valueOf(e.getMessage()));
//            e.printStackTrace();
        } catch (XmlPullParserException e) {
            Log.e("Error 3 :  " , String.valueOf(e.getMessage()));
//            e.printStackTrace();
        }

    }
    public void load_data(View view){
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi =
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        NetworkInfo mobile_3g =
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mobile_3g.isConnected() || wifi.isConnected()){

            Toast.makeText(getApplicationContext() , "wifi or 3G/4G  Connected",
                    Toast.LENGTH_SHORT).show();
//            show(link_2);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Đọc báo theo chủ đề !!!");
            builder.setCancelable(false);

            builder.setItems(the_loai, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (the_loai[which].equalsIgnoreCase("Thời sự VTC")){
                        Toast.makeText(getApplicationContext() , the_loai[which] ,Toast.LENGTH_SHORT).show();
                        show_tin_tuc(link , "Thời sự VTC");
                    } else if (the_loai[which].equalsIgnoreCase("Xã hội VTV")){
                        Toast.makeText(getApplicationContext() , the_loai[which] ,Toast.LENGTH_SHORT).show();
                        show_tin_tuc(link_2 , "Xã hội VTV");
                    } else if (the_loai[which].equalsIgnoreCase("Tin công nghệ VTV")){
                        Toast.makeText(getApplicationContext() , the_loai[which] ,Toast.LENGTH_SHORT).show();
                        show_tin_tuc("https://vtv.vn/cong-nghe.rss" , "Tin công nghệ VTV");
                    } else if (the_loai[which].equalsIgnoreCase("Tin công nghệ VTC")){
                        Toast.makeText(getApplicationContext() , the_loai[which] ,Toast.LENGTH_SHORT).show();
                        show_tin_tuc("https://vtc.vn/rss/khoa-hoc-cong-nghe.rss" , "Tin công nghệ VTC");
                    } else if (the_loai[which].equalsIgnoreCase("Vnexpress khoa học")){
                        Toast.makeText(getApplicationContext() , the_loai[which] ,Toast.LENGTH_SHORT).show();
                        show_tin_tuc("https://vnexpress.net/rss/khoa-hoc.rss" , "Vnexpress khoa học");
                    } else if (the_loai[which].equalsIgnoreCase("Tư Vấn công nghệ VTV")){
                        Toast.makeText(getApplicationContext() , the_loai[which] ,Toast.LENGTH_SHORT).show();
                        show_tin_tuc("https://vtv.vn/cong-nghe/tu-van.rss" , "Tư Vấn công nghệ VTV");
                    } else if (the_loai[which].equalsIgnoreCase("VietNamNet Công nghệ")){
                        Toast.makeText(getApplicationContext() , the_loai[which] ,Toast.LENGTH_SHORT).show();
                        show_tin_tuc("https://vietnamnet.vn/rss/cong-nghe.rss" , "VietNamNet Công nghệ");
                    }
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Notifycation").setMessage("Vui lòng bật 3G/4G hoặc Wifi để xem được tin tức");
            builder.setCancelable(false);
            builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void show_tin_tuc(String link , String rss_name){
        tin_tucList.clear();
        Log.e("-----------------------" , "vao luong phu 1111111111111");
        //      tạo luồng phụ xử lí
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Log.e("-----------------------" , "Bắt đầu xử lí xml");
                test_data(link);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
//                duoc goi khi luong ket thuc
//                khai bao adapter
//                Log.e("getId :  " + tin_tucList.get(0).getId() ,"getTitle :  " + tin_tucList.get(0).getTitle());
//                Log.e("\n getDes :  " + tin_tucList.get(0).getDes() , "\n getPubDate :  " + tin_tucList.get(0).getPubDate());
                TinTuc_Adapter adapter = new TinTuc_Adapter(MainActivity.this , tin_tucList , rss_name);
//                bo array vao adapter
//                set adapter cho listview
                adapter.notifyDataSetChanged();
                lv_tin_tuc.setAdapter(adapter);

            }
        };
        asyncTask.execute();
    }
}