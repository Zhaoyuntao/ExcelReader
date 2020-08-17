package com.test.test2app.huaweimeeting;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.test2app.R;

public class ConferenceItemHolder extends RecyclerView.ViewHolder {
    private TextView textView1;
    private TextView textView2;
    private TextView title;
    private Button button;

    public ConferenceItemHolder(@NonNull View itemView) {
        super(itemView);
        textView1 = itemView.findViewById(R.id.text1);
        textView2 = itemView.findViewById(R.id.text2);
        button = itemView.findViewById(R.id.button);
        title = itemView.findViewById(R.id.title);
    }

    public void setTextView1(String text) {
        if (textView1 == null) {
            return;
        }
        textView1.setText(text);
    }

    public void setTextView2(String text) {
        if (textView2 == null) {
            return;
        }
        textView2.setText(text);
    }

    public void setOnButtonClickListener(View.OnClickListener onClickListener) {
        if (button == null) {
            return;
        }
        button.setOnClickListener(onClickListener);
    }

    public void hideButton() {
        if (button == null) {
            return;
        }
        button.setVisibility(View.GONE);
    }
    public void disableButton() {
        if (button == null) {
            return;
        }
        button.setEnabled(false);
    }

    public void setTitle(String title) {
        if (title == null) {
            return;
        }
        this.title.setText(title);
    }
}
