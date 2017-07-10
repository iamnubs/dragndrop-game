package com.aulia.belajariqra;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

public class Game1Activity extends BaseActivity {
    @BindView(R.id.a)
    ImageView mA;
    @BindView(R.id.b)
    ImageView mB;
    @BindView(R.id.c)
    ImageView mC;
    @BindView(R.id.d)
    ImageView mD;
    @BindView(R.id.question)
    TextView mQuestion;
    @BindView(R.id.root)
    View mRoot;
    @BindView(R.id.target)
    View mTarget;

    @BindView(R.id.stage)
    RelativeLayout stage;
    @BindView(R.id.score)
    TextView mScore;
    @BindView(R.id.status)
    ImageView mStatus;

    private RelativeLayout.LayoutParams mCurrentLayoutParams;
    private RelativeLayout.LayoutParams mInitialLayoutParams;

    private int mCurrentPosition;
    private int mCurrentScore;
    private int mX;
    private int mY;

    private List<Integer> mQuestions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_1);

        ButterKnife.bind(this);

        Tutorial.show(this, 0);

        BirdMotion.init(stage, R.drawable.ic_bird_down, R.drawable.ic_bird_up, 4);

        CloudMotion.init(stage, R.drawable.ic_cloud_very_small, 8);

        mQuestions = new ArrayList<>();

        for (int i = 0; i < 29; i++) {
            mQuestions.add(i);
        }

        Collections.shuffle(mQuestions);

        load();
    }

    @OnTouch(value = {R.id.a, R.id.b, R.id.c, R.id.d})
    boolean drag(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = (int) event.getRawX();
                mY = (int) event.getRawY();

                mCurrentLayoutParams = new RelativeLayout.LayoutParams((RelativeLayout.LayoutParams) v.getLayoutParams());
                mInitialLayoutParams = new RelativeLayout.LayoutParams((RelativeLayout.LayoutParams) v.getLayoutParams());

                return true;

            case MotionEvent.ACTION_UP:
                v.setLayoutParams(mInitialLayoutParams);

                if (isTargetContains(mCurrentLayoutParams.leftMargin + v.getWidth() / 2, mCurrentLayoutParams.topMargin + v.getHeight() / 2)) {
                    boolean r = (boolean) v.getTag();

                    if (r) {
                        mCurrentScore += 20;

                        mScore.setText(String.valueOf(mCurrentScore));
                        mStatus.setImageResource(R.drawable.hebat);

                        mCurrentPosition++;

                        if (mCurrentPosition == 29) {
                            Toast.makeText(this, "Selamat Kamu Keren", Toast.LENGTH_SHORT).show();

                            finish();
                        } else {
                            load();
                        }
                    } else {
                        mStatus.setImageResource(R.drawable.yah_salah);
                    }

                    mStatus.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mStatus.setImageDrawable(null);
                        }
                    }, 1000);
                }

                return true;

            case MotionEvent.ACTION_MOVE:
                mCurrentLayoutParams.leftMargin = (int) event.getRawX() - mX + mInitialLayoutParams.leftMargin;
                mCurrentLayoutParams.topMargin = (int) event.getRawY() - mY + mInitialLayoutParams.topMargin;

                v.setLayoutParams(mCurrentLayoutParams);

                return true;

            default:
                return false;
        }
    }

    private boolean isTargetContains(int oX, int oY) {
        int[] l = new int[2];

        mTarget.getLocationInWindow(l);

        int x = l[0];
        int y = l[1];

        mRoot.getLocationInWindow(l);

        x -= l[0];
        y -= l[1];

        int h = mTarget.getHeight();
        int w = mTarget.getWidth();

        return !(oX < x || oX > x + w || oY < y || oY > y + h);
    }

    private void load() {
        Huruf huruf = Huruf.get(this, mQuestions.get(mCurrentPosition));

        mQuestion.setText(huruf.text);

        List<Integer> options = new ArrayList<>();

        for (int i = 0; i < mQuestions.size(); i++) {
            if (i != mQuestions.get(mCurrentPosition)) {
                options.add(i);
            }
        }

        Collections.shuffle(options);

        options = options.subList(0, 3);
        options.add(mQuestions.get(mCurrentPosition));

        Collections.shuffle(options);

        mA.setImageResource(Huruf.get(this, options.get(0)).image);
        mA.setTag(mQuestions.get(mCurrentPosition).equals(options.get(0)));
        mB.setImageResource(Huruf.get(this, options.get(1)).image);
        mB.setTag(mQuestions.get(mCurrentPosition).equals(options.get(1)));
        mC.setImageResource(Huruf.get(this, options.get(2)).image);
        mC.setTag(mQuestions.get(mCurrentPosition).equals(options.get(2)));
        mD.setImageResource(Huruf.get(this, options.get(3)).image);
        mD.setTag(mQuestions.get(mCurrentPosition).equals(options.get(3)));

        Log.e("Cheat Q" + mQuestions.get(mCurrentPosition), Arrays.deepToString(options.toArray()));
    }
}
