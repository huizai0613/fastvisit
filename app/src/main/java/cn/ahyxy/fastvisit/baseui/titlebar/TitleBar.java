package cn.ahyxy.fastvisit.baseui.titlebar;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.AppContext;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.utils.StringUtils;


/**
 * Created by yexiangyu on 16/3/8.
 */
public class TitleBar
{
    private static TitleBar titleBar;
    private View mRoomView;

    private TextView titlebarLeftTv;
    private TextView titlebarRightTv;
    private TextView titlebarMTv;

    private ImageView titlebarLeftIv;
    private ImageView titlebarRightIv;
    private ImageView titlebarMIv;

    private TitleBar(BaseActivity activity)
    {
        this.mRoomView = activity.findViewById(R.id.titlebar);
        initView();
    }

    private TitleBar(View rootView)
    {
        this.mRoomView = rootView;
        initView();
    }

    public TextView getTitlebarLeftTv()
    {
        return titlebarLeftTv;
    }

    public TextView getTitlebarRightTv()
    {
        return titlebarRightTv;
    }

    public TextView getTitlebarMTv()
    {
        return titlebarMTv;
    }

    public ImageView getTitlebarLeftIv()
    {
        return titlebarLeftIv;
    }

    public ImageView getTitlebarRightIv()
    {
        return titlebarRightIv;
    }

    public static TitleBar getInstance(BaseActivity activity)
    {
        return new TitleBar(activity);
    }

    public static TitleBar getInstance(View rootVIew)
    {
        return new TitleBar(rootVIew);


    }


    private void initView()
    {

        if (mRoomView != null) {
            titlebarLeftTv = (TextView) mRoomView.findViewById(R.id.titlebar_left_tv);
            titlebarRightTv = (TextView) mRoomView.findViewById(R.id.titlebar_right_tv);
            titlebarMTv = (TextView) mRoomView.findViewById(R.id.titlebar_m_tv);

            titlebarMIv = (ImageView) mRoomView.findViewById(R.id.titlebar_left_iv);
            titlebarRightIv = (ImageView) mRoomView.findViewById(R.id.titlebar_right_iv);
            titlebarLeftIv = (ImageView) mRoomView.findViewById(R.id.titlebar_left_iv);
        }

    }

    public void initBackTitle(final BaseActivity activity, String backStr, int backRes)
    {
        View.OnClickListener onClickListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                activity.animaFinish();
            }
        };
        if (!StringUtils.isEmpty(backStr)) {
            setTitlebarLeftTv(backStr, onClickListener);
        } else {
            if (backRes != 0) {
                setTitlebarLeftIv(backRes, onClickListener);
            }
        }
    }

    public void setTitlebarLeftTv(String butStr, View.OnClickListener listener)
    {
        titlebarLeftTv.setVisibility(View.VISIBLE);
        titlebarLeftTv.setText(butStr);
        titlebarLeftTv.setOnClickListener(listener);
    }

    public void setTitlebarLeftTv(String butStr, String color, int icon, View.OnClickListener listener)
    {
        titlebarLeftTv.setVisibility(View.VISIBLE);
        titlebarLeftTv.setTextColor(Color.parseColor(color));
        Drawable drawable = AppContext.getInstance().getResources().getDrawable(icon);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());//必须设置图片大小，否则不显示
        titlebarLeftTv.setCompoundDrawables(null, null, drawable, null);
        titlebarLeftTv.setText(butStr);
        titlebarLeftTv.setOnClickListener(listener);
    }

    public void setTitlebarLeftIv(int reSoure, View.OnClickListener listener)
    {
        titlebarLeftIv.setVisibility(View.VISIBLE);
        titlebarLeftIv.setImageResource(reSoure);
        titlebarLeftIv.setOnClickListener(listener);
    }

    public void setTitlebarRightTv(String butStr, View.OnClickListener listener)
    {
        titlebarRightTv.setVisibility(View.VISIBLE);
        titlebarRightTv.setText(butStr);
        titlebarRightTv.setOnClickListener(listener);
    }

    public void setTitlebarRightIv(int reSoure, View.OnClickListener listener)
    {
        titlebarRightIv.setVisibility(View.VISIBLE);
        titlebarRightIv.setImageResource(reSoure);
        titlebarRightIv.setOnClickListener(listener);
    }


    public void setTitlebarMTv(String butStr)
    {
        titlebarMTv.setVisibility(View.VISIBLE);
        titlebarMTv.setText(butStr);
    }

    public void setTitlebarMTv(String butStr, String color)
    {
        titlebarMTv.setVisibility(View.VISIBLE);
        titlebarMTv.setText(butStr);
        titlebarMTv.setTextColor(Color.parseColor(color));

    }

    public void setTitlebarMIv(int reSoure)
    {
        titlebarMIv.setVisibility(View.VISIBLE);
        titlebarMIv.setImageResource(reSoure);
    }

}
