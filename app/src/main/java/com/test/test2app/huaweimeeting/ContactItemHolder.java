package com.test.test2app.huaweimeeting;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.test2app.R;

public class ContactItemHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView textView;
    private ImageButton imageButton;
    private ImageButton imageButton2;

    public ContactItemHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.avatar);
        textView = itemView.findViewById(R.id.title);
        imageButton = itemView.findViewById(R.id.button1);
        imageButton2 = itemView.findViewById(R.id.button2);
    }

    public void setImage(Bitmap bitmap) {
        if (imageView == null) {
            return;
        }
        imageView.setImageDrawable(new BitmapDrawable(bitmap));
    }

    public void setText(String text) {
        if (textView == null) {
            return;
        }
        textView.setText(text);
    }

    public void setOnButton1ClickListener(View.OnClickListener onClickListener) {
        if (imageButton == null) {
            return;
        }
        imageButton.setOnClickListener(onClickListener);
    }

    public void setOnButton2ClickListener(View.OnClickListener onClickListener) {
        if (imageButton2 == null) {
            return;
        }
        imageButton2.setOnClickListener(onClickListener);
    }
}
