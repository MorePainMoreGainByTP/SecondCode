package com.example.swjtu.printtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.print.PrintManager;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.swjtu.printtest.adapter.MyPrintDocumentAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<List<String>> contents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
    }

    private void initData() {
        contents = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            List<String> strings = new ArrayList<>();
            strings.add("书号" + i);
            strings.add("书名" + i);
            strings.add("作者" + i);
            strings.add("图书分类" + i);
            strings.add("开本" + i);
            strings.add("单价" + i);
            contents.add(strings);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.printImgMenu:
                doPrintImg();
                break;
            case R.id.printCustomMenu:
                doCustomPrint();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doPrintImg() {
        PrintHelper printHelper = new PrintHelper(this);
        printHelper.setScaleMode(PrintHelper.SCALE_MODE_FILL);  //设置打印模式
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.meijing);
        printHelper.printBitmap("print img test", bitmap);  //调用系统打印服务
    }

    private void doCustomPrint() {
        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        // Set job name, which will be displayed in the print queue
        String jobName = "customPrintDocument";
        // Start a print job, passing in a PrintDocumentAdapter implementation
        // to handle the generation of a print document
        printManager.print(jobName, new MyPrintDocumentAdapter(this, contents), null);
    }
}
