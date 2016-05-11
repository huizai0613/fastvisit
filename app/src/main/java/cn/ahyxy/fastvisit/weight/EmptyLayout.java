package cn.ahyxy.fastvisit.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xutils.common.util.DensityUtil;
import org.xutils.x;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.utils.SystemTool;


public class EmptyLayout extends LinearLayout implements
        View.OnClickListener
{// , ISkinUIObserver {

    public static final int HIDE_LAYOUT = 4;
    public static final int NETWORK_ERROR = 1;
    public static final int NETWORK_LOADING = 2;
    public static final int NODATA = 3;
    public static final int NODATA_ENABLE_CLICK = 5;
    public static final int NO_LOGIN = 6;
    public static final int NONEIGHBORDATA = 7;

    public static final int NODATA_PADDING_TOP = 8;
    private final Context context;
    public ImageView errorImg;
    private int errorImgResoure_notNet = R.drawable.base_icon_null_8;
    private int errorImgResoure_loadError = R.drawable.base_icon_nothing;
    private int notDataImgResoure = R.drawable.base_page_icon_empty;
    private int notNeighborDataImgResoure = R.drawable.base_icon_nothing;
    private ProgressBar animProgress;
    private boolean clickEnable = true;
    private OnClickListener listener;
    private int mErrorState;
    private RelativeLayout mLayout;
    private String strNoDataContent = "";
    private TextView tv_TipInfo;
    private TextView tv_but;

    public EmptyLayout(Context context)
    {
        super(context);
        this.context = context;
        init();
    }

    public EmptyLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        init();
    }

    /**
     * 设置没有网络,图片
     *
     * @param errorImgResoure_notNet
     */
    public void setErrorImgResoure_notNet(int errorImgResoure_notNet)
    {
        this.errorImgResoure_notNet = errorImgResoure_notNet;
    }

    /**
     * 设置加载失败,图片
     *
     * @param errorImgResoure_loadError
     */
    public void setErrorImgResoure_loadError(int errorImgResoure_loadError)
    {
        this.errorImgResoure_loadError = errorImgResoure_loadError;
    }

    /**
     * 设置没有数据,图片
     *
     * @param notDataImgResoure
     */
    public void setNotDataImgResoure(int notDataImgResoure)
    {
        this.notDataImgResoure = notDataImgResoure;
    }

    private void init()
    {
        View view = View.inflate(context, R.layout.view_error_layout, null);
        errorImg = (ImageView) view.findViewById(R.id.img_error_layout);
        tv_TipInfo = (TextView) view.findViewById(R.id.tv_error_layout);
        tv_but = (TextView) view.findViewById(R.id.tv_error_but);
        mLayout = (RelativeLayout) view.findViewById(R.id.pageerrLayout);
        animProgress = (ProgressBar) view.findViewById(R.id.animProgress);
        setBackgroundColor(-1);
        setOnClickListener(this);
        errorImg.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (clickEnable) {
                    // setErrorType(NETWORK_LOADING);
                    if (listener != null)
                        listener.onClick(v);
                }
            }
        });
        addView(view);
        changeErrorLayoutBgMode(context);
    }

    public void changeErrorLayoutBgMode(Context context1)
    {
        // mLayout.setBackgroundColor(SkinsUtil.getColor(context1,
        // "bgcolor01"));
        // tv_TipInfo.setTextColor(SkinsUtil.getColor(context1, "textcolor05"));
    }

    public void dismiss()
    {
        mErrorState = HIDE_LAYOUT;
        setVisibility(View.GONE);
    }

    public int getErrorState()
    {
        return mErrorState;
    }

    public boolean isLoadError()
    {
        return mErrorState == NETWORK_ERROR;
    }

    public boolean isLoading()
    {
        return mErrorState == NETWORK_LOADING;
    }

    @Override
    public void onClick(View v)
    {
        if (clickEnable) {
            // setErrorType(NETWORK_LOADING);
            if (listener != null)
                listener.onClick(v);
        }
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        // MyApplication.getInstance().getAtSkinObserable().registered(this);
        onSkinChanged();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        // MyApplication.getInstance().getAtSkinObserable().unregistered(this);
    }

    public void setErrorButContent(String content)
    {
        tv_but.setText(content);
        tv_but.setVisibility(View.VISIBLE);
    }

    public void setErrorButClickListener(OnClickListener clickListener)
    {
        tv_but.setOnClickListener(clickListener);
    }

    public void onSkinChanged()
    {
        // mLayout.setBackgroundColor(SkinsUtil
        // .getColor(getContext(), "bgcolor01"));
        // tv_TipInfo.setTextColor(SkinsUtil.getColor(getContext(), "textcolor05"));
    }

    public void setDayNight(boolean flag)
    {
    }

    public void setErrorMessage(String msg)
    {
        tv_TipInfo.setText(msg);
    }

    /**
     * 新添设置背景
     *
     * @author 火蚁 2015-1-27 下午2:14:00
     */
    public void setErrorImag(int imgResource)
    {
        try {
            errorImg.setImageResource(imgResource);
        } catch (Exception e) {
        }
    }

    public void setErrorType(int i)
    {
        setVisibility(View.VISIBLE);
        switch (i) {
            case NETWORK_ERROR:
                mErrorState = NETWORK_ERROR;
                // img.setBackgroundDrawable(SkinsUtil.getDrawable(context,"pagefailed_bg"));
                if (SystemTool.checkNet(x.app())) {
                    tv_TipInfo.setText(R.string.error_view_load_error_click_to_refresh);
                    errorImg.setBackgroundResource(errorImgResoure_loadError);
                } else {
                    tv_TipInfo.setText(R.string.error_view_network_error_click_to_refresh);
                    errorImg.setBackgroundResource(errorImgResoure_notNet);
                }
                errorImg.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                clickEnable = true;
                break;
            case NETWORK_LOADING:
                mErrorState = NETWORK_LOADING;
                // animProgress.setBackgroundDrawable(SkinsUtil.getDrawable(context,"loadingpage_bg"));
                animProgress.setVisibility(View.VISIBLE);
                errorImg.setVisibility(View.GONE);
                tv_TipInfo.setText(R.string.error_view_loading);
                clickEnable = false;
                break;
            case NODATA:
                mErrorState = NODATA;
                // img.setBackgroundDrawable(SkinsUtil.getDrawable(context,"page_icon_empty"));
                errorImg.setBackgroundResource(notDataImgResoure);
                errorImg.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = true;
                break;
            case NONEIGHBORDATA:
                mErrorState = NONEIGHBORDATA;
                // img.setBackgroundDrawable(SkinsUtil.getDrawable(context,"page_icon_empty"));
                errorImg.setBackgroundResource(notNeighborDataImgResoure);
                errorImg.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                strNoDataContent = "这家伙很懒，什么也没有留下";
                mLayout.setPadding(0, DensityUtil.dip2px(200), 0, 0);
                setTvNoDataContent();
                clickEnable = false;
                break;
            case NODATA_PADDING_TOP:
                mErrorState = NODATA_PADDING_TOP;
                // img.setBackgroundDrawable(SkinsUtil.getDrawable(context,"page_icon_empty"));
                errorImg.setBackgroundResource(notDataImgResoure);
                errorImg.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                mLayout.setPadding(0, DensityUtil.dip2px(200), 0, 0);
                setTvNoDataContent();
                clickEnable = false;
                break;
            case HIDE_LAYOUT:
                setVisibility(View.GONE);
                break;
            case NODATA_ENABLE_CLICK:
                mErrorState = NODATA_ENABLE_CLICK;
                errorImg.setBackgroundResource(notDataImgResoure);
                // img.setBackgroundDrawable(SkinsUtil.getDrawable(context,"page_icon_empty"));
                errorImg.setVisibility(View.VISIBLE);
                animProgress.setVisibility(View.GONE);
                setTvNoDataContent();
                clickEnable = true;
                break;
            default:
                break;
        }
    }

    public void setNoDataContent(String noDataContent)
    {
        strNoDataContent = noDataContent;
    }

    public void setOnLayoutClickListener(OnClickListener listener)
    {
        this.listener = listener;
    }

    public void setTvNoDataContent()
    {
        if (!strNoDataContent.equals(""))
            tv_TipInfo.setText(strNoDataContent);
        else
            tv_TipInfo.setText(R.string.error_view_no_data);
    }

    @Override
    public void setVisibility(int visibility)
    {
        if (visibility == View.GONE)
            mErrorState = HIDE_LAYOUT;
        super.setVisibility(visibility);
    }
}
