package cn.ahyxy.fastvisit.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.utils.Utils;

/**
 * Created by YeXiangYu on 15-9-22.
 */
public class StarSeekBar extends SeekBar
{

    private int score = 0;

    private OnStarClickListener onStarClickListener;
    private boolean clickAble = true;

    private OnStarChangeListener onStarChangeListener;

    public StarSeekBar(Context context)
    {
        super(context);
        init();
    }

    public StarSeekBar(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public StarSeekBar(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    public int getScore()
    {
        return score;
    }

//    public StarSeekBar setScore(int score)
//    {
//        this.score = score;
//        switch (score) {
//            case 1:
//                this.setBackgroundResource(R.drawable.star5);
//                break;
//            case 2:
//                this.setBackgroundResource(R.drawable.star4);
//                break;
//            case 3:
//                this.setBackgroundResource(R.drawable.star3);
//                break;
//            case 4:
//                this.setBackgroundResource(R.drawable.star2);
//                break;
//            case 5:
//                this.setBackgroundResource(R.drawable.star1);
//                break;
//            default:
//                this.setBackgroundResource(R.drawable.star6);
//                break;
//        }
//        return this;
//
//    }


    public StarSeekBar setScore(double score)
    {
        this.score = (int)score;
        double value = Utils.getRetainDecimal(score, 1);
        if (0 < value && value <= 0.5) {
            this.setBackgroundResource(R.drawable.star0_5);
        } else if (0.5 < value && value <= 1) {
            this.setBackgroundResource(R.drawable.star1);
        } else if (1 < value && value <= 1.5) {
            this.setBackgroundResource(R.drawable.star1_5);
        } else if (1.5 < value && value <= 2) {
            this.setBackgroundResource(R.drawable.star2);
        } else if (2 < value && value <= 2.5) {
            this.setBackgroundResource(R.drawable.star2_5);
        } else if (2.5 < value && value <= 3) {
            this.setBackgroundResource(R.drawable.star3);
        } else if (3 < value && value <= 3.5) {
            this.setBackgroundResource(R.drawable.star3_5);
        } else if (3.5 < value && value <= 4) {
            this.setBackgroundResource(R.drawable.star4);
        } else if (4 < value && value <= 4.5) {
            this.setBackgroundResource(R.drawable.star4_5);
        } else if (4.5 < value && value <= 5) {
            this.setBackgroundResource(R.drawable.star5);
        } else if (value == 0) {
            this.setBackgroundResource(R.drawable.star6);
        }
        return this;
    }

    public StarSeekBar setStarClick(boolean click)
    {
        this.clickAble = click;
        return this;
    }

    private void init()
    {

        setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return false;
            }
        });


        setOnSeekBarChangeListener(new OnSeekBarChangeListener()
        {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                // TODO Auto-generated method stub
                if (!clickAble) {
                    if (onStarClickListener != null)
                        onStarClickListener.Onclick();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser)
            {
                if (!clickAble) {
                    return;
                }
                if (progress >= 0 && progress <= 20) {
                    score = 1;
                    seekBar.setBackgroundResource(R.drawable.star1);
                } else if (progress > 20 && progress <= 40) {
                    score = 2;
                    seekBar.setBackgroundResource(R.drawable.star2);
                } else if (progress > 40 && progress <= 60) {
                    score = 3;
                    seekBar.setBackgroundResource(R.drawable.star3);
                } else if (progress > 60 && progress <= 80) {
                    score = 4;
                    seekBar.setBackgroundResource(R.drawable.star4);
                } else if (progress > 80 && progress <= 100) {
                    score = 5;
                    seekBar.setBackgroundResource(R.drawable.star5);
                }
                if (onStarChangeListener != null)
                    onStarChangeListener.OnChange(score);
            }
        });
    }

    public OnStarClickListener getOnStarClickListener()
    {
        return onStarClickListener;
    }

    public void setOnStarClickListener(OnStarClickListener onStarClickListener)
    {
        this.onStarClickListener = onStarClickListener;
    }

    public OnStarChangeListener getOnStarChangeListener()
    {
        return onStarChangeListener;
    }

    public void setOnStarChangeListener(OnStarChangeListener onStarChangeListener)
    {
        this.onStarChangeListener = onStarChangeListener;
    }

    @Override
    public synchronized void setProgress(int progress)
    {
        setStarClick(true);
        super.setProgress(progress);
    }


    public interface OnStarClickListener
    {
        void Onclick();
    }

    public interface OnStarChangeListener
    {
        void OnChange(int score);
    }
}
