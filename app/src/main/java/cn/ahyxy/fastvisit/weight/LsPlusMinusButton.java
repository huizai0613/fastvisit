package cn.ahyxy.fastvisit.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.ahyxy.fastvisit.R;


/**
 * Created by zhaofei on 15-9-22.
 */
public class LsPlusMinusButton extends LinearLayout implements View.OnClickListener
{
    public interface OnNumChangedListener
    {
        void onNumChanged(int num);
    }
    private int maxNum = Integer.MAX_VALUE;
    private int minNum = Integer.MIN_VALUE;
    private int originNum = 1;
    private int resultNum = 0;

    private Button mBtnPlus;
    private Button mBtnMinus;
    private TextView mTvNum;
    private OnNumChangedListener mListener;
    public LsPlusMinusButton(Context context)
    {
        super(context);
        initView(context);
    }

    public LsPlusMinusButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.PlusMinusAttr);
        maxNum = attr.getInt(R.styleable.PlusMinusAttr_maxNum, Integer.MAX_VALUE);
        minNum = attr.getInt(R.styleable.PlusMinusAttr_minNum, Integer.MIN_VALUE);
        originNum = attr.getInt(R.styleable.PlusMinusAttr_originNum, 1);
        attr.recycle();
        initView(context);
    }

    private void initView(Context context)
    {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.v_plus_minus, this);
        mBtnMinus = (Button) findViewById(R.id.btn_minus);
        mBtnPlus = (Button) findViewById(R.id.btn_plus);
        mBtnPlus.setOnClickListener(this);
        mBtnMinus.setOnClickListener(this);
        mTvNum = (TextView) findViewById(R.id.tv_num);
        mTvNum.setText(originNum + "");
        resultNum = originNum;
        updateState();
    }

    @Override
    public void onClick(View v)
    {
        int id = v.getId();
        if (id == mBtnMinus.getId()) {
            if (resultNum - 1 >= minNum) {
                mTvNum.setText((--resultNum) + "");
                if (this.mListener != null) {
                    mListener.onNumChanged(resultNum);
                }
            }
        } else if (id == mBtnPlus.getId()) {
            if (resultNum + 1 <= maxNum) {
                mTvNum.setText((++resultNum) + "");
                if (this.mListener != null) {
                    mListener.onNumChanged(resultNum);
                }
            }
        }
        updateState();
    }

    private void updateState()
    {
        if (resultNum <= minNum) {
            mBtnMinus.setEnabled(false);
        } else {
            mBtnMinus.setEnabled(true);
        }
        if (resultNum >= maxNum) {
            mBtnPlus.setEnabled(false);
        } else {
            mBtnPlus.setEnabled(true);
        }
    }

    public void setOnNumChangedListener(OnNumChangedListener listener)
    {
        this.mListener = listener;
    }

    public int getResultNum()
    {
        return resultNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }

    public void setMinNum(int minNum) {
        this.minNum = minNum;
    }

    public void setOriginNum(int originNum) {
        this.originNum = originNum;
        this.resultNum = originNum;
        mTvNum.setText(originNum + "");
        updateState();
    }
}
