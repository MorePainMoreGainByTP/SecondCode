package com.example.swjtu.secondcode;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.swjtu.secondcode.contentResolver.ContactsPhoneActivity;
import com.example.swjtu.secondcode.entity.Book;
import com.example.swjtu.secondcode.entity.Category;
import com.example.swjtu.secondcode.immersiveMode.ImmersiveModeActivity;
import com.example.swjtu.secondcode.percentLayout.PercentFrameActivity;
import com.example.swjtu.secondcode.percentLayout.PercentRelativeActivity;
import com.example.swjtu.secondcode.phonePadCompatible.NewsActivity;
import com.example.swjtu.secondcode.singleInstance.AnotherTaskActivity;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

import static org.litepal.crud.DataSupport.findAll;

/**
 *
 */
public class MainActivity extends BaseActivity {

    public static final String KEY_NAME = "key_name";
    private static final String TAG = "MainActivity";
    private NetWorkChangeReceiver netWorkChangeReceiver;

    private LocalBroadcastManager localBroadcastManager;    //本地广播管理器
    private BroadcastReceiver localBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "接收到本地广播", Toast.LENGTH_SHORT).show();
        }
    };   //本地广播接收器，只能接收本地广播。而且只能动态注册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果activity已经创建且被销毁侯，再次创建时，savedInstanceState不为空
        if (savedInstanceState != null) {
            System.out.println(savedInstanceState.getString(KEY_NAME));
        }
        setContentView(R.layout.activity_main);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                Log.e(TAG, "uncaughtException: ", e);
            }
        });
        sendBroadcast(new Intent("hello.world"));
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        Log.i(TAG, "onCreate: MainActivity Start");
    }

    public void onFourActivity(View v) {
        startActivity(new Intent(this, FourActivity.class));
    }

    public void onCamera(View v) {
        startActivity(new Intent(this, CameraActivity.class));
    }
    public void onXML(View v) {
        startActivity(new Intent(this, ParserXMLActivity.class));
    }

    public void onContacts(View v) {
        startActivity(new Intent(this, ContactsPhoneActivity.class));
    }

    public void onCall(View v) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {//如果没有授权
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            makeCall();
        }
    }

    private void makeCall() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:10086"));
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makeCall();
                } else {
                    Toast.makeText(this, "You denied the permission!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //修改数据库的表结构，以及向数据库添加新表
    public void onUpdateDB(View v) {
        //只需要将Book中增加一个属性，并把xml中的version修改，即可修改表结构
        //在xml中list元素下，添加mapping元素即可自动创建表

        Category category = new Category();
        category.setCategoryCode(101);
        category.setCategoryName("计算机技术");
        category.save();
        List<Category> categories = findAll(Category.class);
        for (Category category1 : categories) {
            Log.i(TAG, "id:" + category1.getId() + ",CategoryName: " + category1.getCategoryName() + ",CategoryCode: " + category1.getCategoryCode());
        }
        //更新数据
        Book book2 = new Book();
        book2.setPress("swjtu");
        book2.setPrice(111);
        book2.updateAll("price < ? and pages > ?", "1000", "1");  //相当于where条件子句
        //将某列数据全部设为 该列数据类型的默认值（比如int 默认值为0）
        //book2.setToDefault("pages");
        //book2.updateAll();    //更新所有行数据

        DataSupport.deleteAll(Book.class, "price > ? ", "1000");
        Book book3 = DataSupport.findFirst(Book.class);
        Book book4 = DataSupport.findLast(Book.class);

        List<Book> books2 = DataSupport.select("name", "pages", "author")
                .where("pages > ?", "100")
                .order("price")
                .limit(10)  //限制10条数据
                .offset(10) //从第10条以后开始搜索
                .find(Book.class);
        Cursor cursor = DataSupport.findBySQL("select * from Book where pages > ? and price < ?", "200", "100");
        /*
        Book book1 = new Book();
        book1.setAuthor("lisi");
        book1.setName("mysql");
        book1.setPages(102);
        book1.setPrice(345.1);
        book1.setPress("swjtu--sichuan");
        book1.save();
        */
        List<Book> books = DataSupport.findAll(Book.class);
        for (Book book : books) {
            Log.i(TAG, "id:" + book.getId() + ",author: " + book.getAuthor() + ",name: " + book.getName() + ",pages: " + book.getPages() + ",price: " + book.getPrice() + ",press:" + book.getPress());
        }
//        book1.setPress("swjtu");
//        book1.save();
//        books = DataSupport.findAll(Book.class);
//        for (Book book : books) {
//            Log.i(TAG, "id:" + book.getId() + ",author: " + book.getAuthor() + ",name: " + book.getName() + ",pages: " + book.getPages() + ",price: " + book.getPrice() + ",press:" + book.getPress());
//        }

    }

    public void onLitePal(View v) {
        LitePal.getDatabase();  //任何一次数据库操作都会创建数据库，若不存在
        Book book = new Book();
        book.setAuthor("tp");
        book.setName("java");
        book.setPages(66);
        book.setPrice(89.3);
        book.save();
    }

    //动态启动广播接收器，不用在Manifest中注册，当activity销毁时，需要解绑
    public void startDynamicBroadcast(View v) {
        //设置广播过滤条件
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netWorkChangeReceiver = new NetWorkChangeReceiver();
        registerReceiver(netWorkChangeReceiver, intentFilter);
    }

    //发送本地广播，只有本地广播接收器才能接收到
    public void localBroadcast(View v) {
        //启动接收器
        IntentFilter intentFilter = new IntentFilter("com.example.swjtu.secondcode.LOCAL_BROADCAST");
        localBroadcastManager.registerReceiver(localBroadcastReceiver, intentFilter);  //只能动态注册
        Intent intent = new Intent("com.example.swjtu.secondcode.LOCAL_BROADCAST");
        localBroadcastManager.sendBroadcast(intent);
    }

    //有序广播
    public void orderBroadcast(View v) {
        Intent intent = new Intent("com.example.swjtu.secondcode.ORDER_BROADCAST");
        sendOrderedBroadcast(intent, null);  //发送全局广播，所有app都能收到
    }

    //一般的全局（整个手机）广播
    public void normalBraocast(View v) {
        Intent intent = new Intent("hello.world");
        sendBroadcast(intent);  //发送全局广播，所有app都能收到
    }


    //沉浸式体验
    public void immersive(View v) {
        startActivity(new Intent(this, ImmersiveModeActivity.class));
    }

    public void second(View v) {
        //显示启动需要参数的activity
        SecondActivity.actionStart(this, "lisi", 20);

        //隐式启动activity
//        Intent intent = new Intent("second");
//        intent.addCategory("hello");    //默认添加DEFAULT 的category，所有category必须匹配
//        startActivity(intent);
    }

    public void third(View v) {
        Intent intent = new Intent();
        intent.setAction("third");
        //所有category与data必须匹配
        intent.addCategory("zhnagsan");
        intent.setData(Uri.parse("http://www.tp.com"));   //只能接收Uri对象,表明Intent想要操作的数据，必须匹配标签data设置的多种属性（scheme、host、port、path、mimeType）
        startActivity(intent);
    }

    public void browser(View v) {
        //查看网址
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.baidu.com"));
        startActivity(intent);
    }

    public void dial(View v) {
        //拨打电话
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:10086"));
        startActivity(intent);
    }

    public void backPress(View v) {
        startActivityForResult(new Intent(this, BackPressActivity.class), 1);
    }

    //百分比布局，frame子类
    public void percentFrame(View v) {
        startActivity(new Intent(this, PercentFrameActivity.class));
    }

    //百分比布局relative子类
    public void percentRelative(View v) {
        startActivity(new Intent(this, PercentRelativeActivity.class));
    }

    public void goChat(View v) {
        startActivity(new Intent(this, ChatActivity.class));
    }

    public void phonePad(View v) {
        startActivity(new Intent(this, NewsActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "requestCode: " + requestCode);
        switch (requestCode) {
            case 1:
                Log.i(TAG, "resultCode: " + resultCode);
                if (resultCode == 0) {
                    Log.i(TAG, "data: " + data.getStringExtra("hello"));
                    Toast.makeText(this, data.getStringExtra("hello"), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void goAnother(View v) {
        startActivity(new Intent(this, AnotherTaskActivity.class));
    }

    //当activity不可见且因内存不足而被销毁时，调用此方法，可以将临时数据放到Bundle里面
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_NAME, "zhangsan");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy: MainActivity");
        //动态注册，需要解绑
        if (netWorkChangeReceiver != null) {
            unregisterReceiver(netWorkChangeReceiver);
        }
        localBroadcastManager.unregisterReceiver(localBroadcastReceiver);
    }


    /**
     * 监听网络状态改变的接收器
     */
    class NetWorkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "network is Available", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "network is unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
