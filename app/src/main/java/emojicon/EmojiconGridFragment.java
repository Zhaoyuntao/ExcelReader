/*
 * Copyright 2014 Hieu Rocker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package emojicon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.test.test2app.R;

import java.util.LinkedList;
import java.util.List;

import emojicon.emoji.Emojicon;

/**
 * @author Hieu Rocker (rockerhieu@gmail.com)
 */
public class EmojiconGridFragment extends Fragment implements AdapterView.OnItemClickListener {
    private OnEmojiconClickedListener mOnEmojiconClickedListener;
    private OnEmojiconBackspaceClickedListener mOnEmojiconBackspaceClickedListener;
    private Emojicon[] mData;
    private EmojiAdapter mEmojiAdapter;
    private ViewPager mViewPager;
    private PointView mEmojiIndicator;

    protected static EmojiconGridFragment newInstance(Emojicon[] emojicons, PointView emojiIndicator) {
        EmojiconGridFragment emojiGridFragment = new EmojiconGridFragment();
        emojiGridFragment.mData = emojicons;
        emojiGridFragment.mEmojiIndicator = emojiIndicator;
        return emojiGridFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parent = inflater.inflate(R.layout.emojicon_grid_tab, null);
        mEmojiIndicator = (PointView) parent.findViewById(R.id.emoji_indicator);
        mViewPager = (ViewPager) parent.findViewById(R.id.emoji_grid_pager);
        mViewPager.setOffscreenPageLimit(1);
        ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                mEmojiIndicator.setCurrentPosition(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        };
        mViewPager.setOnPageChangeListener(onPageChangeListener);
        return parent;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EmojiLibrary.init(view.getContext().getApplicationContext());
        mData = SelectEmojis.getData(getActivity().getApplicationContext());
        mEmojiAdapter = new EmojiAdapter(mData);
        mViewPager.setAdapter(mEmojiAdapter);
        mEmojiIndicator.setCount(mEmojiAdapter.getCount(), mViewPager.getCurrentItem());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(getClass().getSimpleName(), "request code: " + requestCode + ", result code: " + resultCode
                + "============================================================");
    }

    public void onMyPageSelected() {
        if (mViewPager != null && mEmojiIndicator != null && mEmojiAdapter != null) {
            mEmojiIndicator.setCount(mEmojiAdapter.getCount(), mViewPager.getCurrentItem());
        } else {
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnEmojiconClickedListener) {
            mOnEmojiconClickedListener = (OnEmojiconClickedListener) activity;
        } else {
            throw new IllegalArgumentException(activity + " must implement interface "
                    + OnEmojiconClickedListener.class.getSimpleName());
        }

        if (activity instanceof OnEmojiconBackspaceClickedListener) {
            mOnEmojiconBackspaceClickedListener = (OnEmojiconBackspaceClickedListener) activity;
        } else {
            throw new IllegalArgumentException(activity + " must implement interface "
                    + OnEmojiconBackspaceClickedListener.class.getSimpleName());
        }
    }

    @Override
    public void onDetach() {
        mOnEmojiconClickedListener = null;
        mOnEmojiconBackspaceClickedListener = null;
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mOnEmojiconClickedListener != null) {
            mOnEmojiconClickedListener.onEmojiconClicked((Emojicon) parent.getItemAtPosition(position));
        }
    }

    public interface OnEmojiconClickedListener {
        void onEmojiconClicked(Emojicon emojicon);
    }

    public interface OnEmojiconBackspaceClickedListener {
        void onEmojiconBackspaceClicked(View v);
    }

    public static void input(EditText editText, Emojicon emojicon) {
        if (editText == null || emojicon == null) {
            return;
        }

        int start = editText.getSelectionStart();
        int end = editText.getSelectionEnd();
        if (start < 0) {
            editText.append(emojicon.getEmoji());
        } else {
            editText.getText().replace(Math.min(start, end), Math.max(start, end), emojicon.getEmoji(), 0,
                    emojicon.getEmoji().length());
        }
    }

    public static void backspace(EditText editText) {
        KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
        editText.dispatchKeyEvent(event);
    }

    private class EmojiAdapter extends PagerAdapter {

        private List<EmojiGridAdapter> mEmojiGridAdapters = new LinkedList<EmojiGridAdapter>();

        public EmojiAdapter(Emojicon[] emojicons) {
            int raw = getResources().getInteger(R.integer.emoji_grid_raw_count);
            int column = getResources().getInteger(R.integer.emoji_grid_column_count);

            int count = emojicons != null ? emojicons.length : 0;
            int pageSize = raw * column - 1;
            int pages = (count - 1) / pageSize + 1;
            for (int i = 0; i < pages; i++) {
                mEmojiGridAdapters.add(new EmojiGridAdapter(emojicons, i * pageSize, pageSize));
            }
        }

        @Override
        public int getCount() {
            return mEmojiGridAdapters.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View rootView = LayoutInflater.from(getActivity()).inflate(R.layout.emojicon_grid, null);
            GridView gridView = (GridView) rootView.findViewById(R.id.Emoji_GridView);
            gridView.setAdapter(mEmojiGridAdapters.get(position));
            gridView.setOnItemClickListener(EmojiconGridFragment.this);
            rootView.findViewById(R.id.Emoji_Backspace).setOnTouchListener(
                    new EmojiRepeatListener(1000, 50, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.playSoundEffect(SoundEffectConstants.CLICK);
                            if (mOnEmojiconBackspaceClickedListener != null) {
                                mOnEmojiconBackspaceClickedListener.onEmojiconBackspaceClicked(v);
                            }
                        }
                    }));
            container.addView(rootView);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (object instanceof View) {
                container.removeView((View) object);
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }
    }

    private class EmojiGridAdapter extends BaseAdapter {

        private List<Emojicon> mEmojiIcons = new LinkedList<Emojicon>();

        public EmojiGridAdapter(Emojicon[] emojicons, int start, int count) {
            int lastPos = Math.min(emojicons.length - 1, start + count - 1);
            for (int i = start; i <= lastPos; i++) {
                mEmojiIcons.add(emojicons[i]);
            }
        }

        @Override
        public int getCount() {
            return mEmojiIcons.size();
        }

        @Override
        public Object getItem(int position) {
            return mEmojiIcons.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.emojicon_item, null);
            }
            Emojicon emoji = mEmojiIcons.get(position);
            ((TextView) convertView).setText(emoji.getEmoji());
            return convertView;
        }
    }
}
