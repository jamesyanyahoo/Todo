package com.yahoo.shopping.todo;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yahoo.shopping.todo.model.Item;

import java.util.List;

/**
 * Created by jamesyan on 7/21/15.
 */
public class ItemAdapter extends ArrayAdapter<Item> {
    private MainActivity context;
    private List<Item> objects;

    private final static int SWIPE_DELAY = 25;
    private final static int SWIPE_MARGIN = 55;

    public ItemAdapter(Context context, int resource, List<Item> objects) {
        super(context, resource, objects);

        this.context = (MainActivity) context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Item item = this.objects.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.activity_list_item, null);

        TextView tv = (TextView) view.findViewById(R.id.activity_main_list_item_text);
        final RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tv.getLayoutParams();
        tv.setText(item.getItem());

        final Button btnDelete = (Button) view.findViewById(R.id.activity_main_list_item_btn_delete);
        if (item.isDisplayDeleteButton()) {
            ValueAnimator animator = ValueAnimator.ofInt(0, -SWIPE_MARGIN);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int currentValue = (int) animation.getAnimatedValue();
                    if (currentValue == -SWIPE_MARGIN) {
                        btnDelete.setVisibility(View.VISIBLE);
                    }

                    layoutParams.leftMargin = currentValue;
                    view.requestLayout();
                }
            });
            animator.setDuration(SWIPE_DELAY);
            animator.start();
        } else {
            btnDelete.setVisibility(View.INVISIBLE);
        }
        btnDelete.setOnClickListener(context);

        return view;
    }
}
