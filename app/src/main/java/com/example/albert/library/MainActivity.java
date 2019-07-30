package com.example.albert.library;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albert.library.model.Book;
import com.example.albert.library.model.Person;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mNameEditText;
    private RadioGroup mSexRadioGroup;
    private CheckBox mHistoryCheckBox,mSuspenseCheckBox, mLiteracyCheckBox;
    private SeekBar mSeekBar;
    private Button mNextButton;
    private Button mSearchButton;
    private EditText mBorrowTimeEditText;
    private TextView mReturnTime;
    private TextView mBookName;
    private TextView mCategory;
    private TextView mSuitableAge;

    private List<Book> mBooks;
    private Person mPerson;
    private List<Book> mBookResult;
    private boolean mIsHistory;
    private boolean mIsSuspense;
    private boolean mIsLiteracy;
    private int mAge;
    private TextView mShowAge;
    private ImageView mBookImageView;
    private TextView mShow;
    private int mCurrentIndex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

        initData();

        setListener();
    }

    private void setListener() {
        //设置姓名
        mNameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String name = mNameEditText.getText().toString();
                mPerson.setName(name);
            }
        });
        //设置性别
        mSexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkId) {
                switch (checkId) {
                    case R.id.male:
                        mPerson.setSex("男");
                        break;
                    case R.id.female:
                        mPerson.setSex("女");
                        break;
                }
            }
        });

        mHistoryCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mIsHistory = isChecked;
            }
        });

        mSuspenseCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mIsSuspense = isChecked;
            }
        });

        mLiteracyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mIsLiteracy = isChecked;
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mAge = mSeekBar.getProgress();
                mPerson.setAge(mAge);
                Integer age = mAge;
                mShowAge.setText(age.toString());
            }
        });

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy.MM.dd");

                String  tempDate = mBorrowTimeEditText.getText().toString();
                Date borrowDate = null;
                try {
                    borrowDate = formatter.parse(tempDate);
                    mPerson.setBorrowTime(borrowDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String tempReturnDate = mReturnTime.getText().toString();
                Date returnDate = null;
                try {
                    returnDate = formatter1.parse(tempReturnDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (getDistanceTime(borrowDate, returnDate) < 0) {
                    Toast.makeText(MainActivity.this, "借书时间晚于归还时间", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    search();
                    Toast.makeText(MainActivity.this,mPerson.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mCurrentIndex ++;
                if (mCurrentIndex < mBookResult.size()) {
                    mBookImageView.setImageResource(mBookResult.get(mCurrentIndex).getPic());
                    mBookName.setText(mBookResult.get(mCurrentIndex).getBookName());
                    if (mBookResult.get(mCurrentIndex).isSuspense()) {
                        mCategory.setText("悬疑");
                    } else if (mBookResult.get(mCurrentIndex).isLiteracy()) {
                        mCategory.setText("文艺");
                    } else {
                        mCategory.setText("历史");
                    }
                    mShow.setText("符合条件的书有" + (mBookResult.size() - mCurrentIndex) + "本");

                    mSuitableAge.setText(Integer.valueOf(mBookResult.get(mCurrentIndex).getSuitableAge()).toString());
                } else {
                    mBookImageView.setImageResource(R.drawable.aa);
                    mShow.setText("符合条件的书有"+0+"本");
                    mBookName.setText("人生感悟");
                    mCategory.setText("心灵鸡汤");
                    mSuitableAge.setText("15");
                }
            }
        });
    }

    //计算时差
    private int getDistanceTime(Date startTime, Date endTime) {
        int days = 0;
        long time1 = startTime.getTime();
        long time2 = endTime.getTime();

        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
            days = (int) (diff / (24 * 60 * 60 * 1000));
        } else {
            days = -1;
        }
        return days;

    }

    private void search() {

        if (mBookResult == null) {
            mBookResult = new ArrayList<>();
        }

        mBookResult.clear();

        mCurrentIndex = 0;

        for (int i = 0; i < mBooks.size(); i++) {
            Book book = mBooks.get(i);

            if (book != null) {
                if (book.getSuitableAge() > mAge &&
                        (book.isHistory() == mIsHistory)
                        || book.isLiteracy() == mIsLiteracy
                        || book.isSuspense() == mIsSuspense) {
                    mBookResult.add(book);
                }
            }
        }
        mShow.setText("符合条件的书有" + mBookResult.size() + "本");
        if (mCurrentIndex < mBookResult.size()) {
            mBookImageView.setImageResource(mBookResult.get(mCurrentIndex).getPic());
        } else {
            mBookImageView.setImageResource(R.drawable.aa);
        }
    }

    private void initData() {
        mBooks = new ArrayList<>();

        mBooks.add(new Book("人生感悟", 18, R.drawable.aa, false, false, true));
        mBooks.add(new Book("边城", 10, R.drawable.bb, false, false, true));
        mBooks.add(new Book("sapio", 18, R.drawable.cc, false, true, false));
        mBooks.add(new Book("光辉岁月", 18, R.drawable.dd, true, false, false));
        mBooks.add(new Book("宋词三百首", 5, R.drawable.ee, false, false, true));
        mBooks.add(new Book("中国古代文学", 18, R.drawable.ff, false, false, true));
        mBooks.add(new Book("无花果", 18, R.drawable.gg, false, false, true));
        mBooks.add(new Book("古镇记忆", 15, R.drawable.hh, false, false, true));

        mPerson = new Person();
        mBookResult = new ArrayList<>();  

    }

    private void findViews() {

        mNameEditText = findViewById(R.id.name);
        mSexRadioGroup = findViewById(R.id.sex);
        mHistoryCheckBox = findViewById(R.id.historyCheckBox);
        mSuspenseCheckBox = findViewById(R.id.suspenseCheckBox);
        mLiteracyCheckBox = findViewById(R.id.literacyCheckBox);
        mSeekBar = findViewById(R.id.seekBar);
        mNextButton = findViewById(R.id.next);
        mSearchButton = findViewById(R.id.search);
        mBorrowTimeEditText = findViewById(R.id.borrow_time);
        mReturnTime = findViewById(R.id.return_time);
        mShowAge = findViewById(R.id.showAge);
        mBookImageView = findViewById(R.id.bookImage);
        mBookName = findViewById(R.id.bookName);
        mCategory = findViewById(R.id.category);
        mSuitableAge = findViewById(R.id.suitableAge);
        mShow = findViewById(R.id.show);
    }

}
