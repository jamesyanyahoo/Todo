package com.yahoo.shopping.todo;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yahoo.shopping.todo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity
        implements View.OnClickListener, View.OnTouchListener, OnListItemFlingListener, AdapterView.OnItemClickListener {
    private List<Item> todoList;

    private ItemAdapter mItemAdapter;
    private GestureDetector mDetectorCompat;
    private SwipeGestureListener mSwipeDetector;

    private void loadTodoList() {
        if (todoList == null) {
            todoList = new ArrayList<>();
        }

        for (int i = 0; i < 5; i++) {
            todoList.add(new Item("this is todo item " + i));
        }
    }

    private void addTodoItem() {
        TextView tv = (TextView) findViewById(R.id.activity_main_txt_todoitem);

        String item = tv.getText().toString();
        if (item.isEmpty()) {
            Toast.makeText(this, R.string.toast_string_empty_text, Toast.LENGTH_SHORT).show();
            return;
        }

        todoList.add(new Item(item));
        mItemAdapter.notifyDataSetChanged();

        tv.setText("");
    }

    private void removeTodoItem() {
        if (mSwipeDetector.getCurrentPos() >= 0) {
            todoList.remove(mSwipeDetector.getCurrentPos());
        }
        mItemAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadTodoList();

        mItemAdapter = new ItemAdapter(this, R.layout.activity_list_item, todoList);

        ListView lv = (ListView) findViewById(R.id.activity_main_lv_listview);
        lv.setOnTouchListener(this);
        lv.setOnItemClickListener(this);
        lv.setAdapter(mItemAdapter);

        Button btnAdd = (Button) findViewById(R.id.activity_main_btn_add);
        btnAdd.setOnClickListener(this);

        mSwipeDetector = new SwipeGestureListener(lv);
        mDetectorCompat = new GestureDetector(this, mSwipeDetector);

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setLogo(R.mipmap.ic_launcher);
        menu.setDisplayUseLogoEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_btn_add:
                addTodoItem();
                break;
            case R.id.activity_main_list_item_btn_delete:
                removeTodoItem();
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mDetectorCompat.onTouchEvent(event);
        return false;
    }

    @Override
    public void onListItemFling(int pos) {
        for (Item item : this.todoList) {
            item.setDisplayDeleteButton(false);
        }

        Item item = this.todoList.get(pos);
        item.setDisplayDeleteButton(true);

        mItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        for (Item item : this.todoList) {
            item.setDisplayDeleteButton(false);
        }
        mItemAdapter.notifyDataSetChanged();
    }
}
