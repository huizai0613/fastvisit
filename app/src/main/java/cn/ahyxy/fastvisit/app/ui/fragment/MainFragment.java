package cn.ahyxy.fastvisit.app.ui.fragment;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.baseui.LsSimpleHomeFragment;
import cn.ahyxy.fastvisit.baseui.titlebar.TitleBar;
import cn.ahyxy.fastvisit.utils.ToastUtils;

/**
 * Created by yexiangyu on 16/4/23.
 */
public class MainFragment extends LsSimpleHomeFragment
{
    @Override
    protected int getLayoutId()
    {
        return R.layout.fragment_main;
    }

    @ViewInject(R.id.top_viewpager)
    private RollPagerView topVP;

    @ViewInject(R.id.main_bottom)
    private ImageView mainBottom;

    @Override
    protected void initWidget(View parentView)
    {
        super.initWidget(parentView);
        TitleBar titleBar = TitleBar.getInstance(parentView);
        titleBar.setTitlebarMTv("有薪快仿", "#000000");
        titleBar.setTitlebarRightIv(R.mipmap.icon_r, null);
        titleBar.setTitlebarLeftTv("合肥", "#EA6800", R.mipmap.icon_l, null);
        titleBar.getTitlebarLeftTv().setPadding(20, 0, 20, 0);

        topVP.setPlayDelay(3000);
        topVP.setAnimationDurtion(500);
        int H = (int) (DensityUtil.getScreenWidth() / 2.3f);
        topVP.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, H));
        topVP.setAdapter(new TestLoopAdapter(topVP));
        topVP.setHintView(new ColorPointHintView(mActivity, Color.parseColor("#000000"), Color.parseColor("#f3f3f3")));

        mainBottom.getLayoutParams().height = (int) (DensityUtil.getScreenWidth() / 5.8f);
    }

    @Override
    protected void getDataFronServer()
    {

    }

    @Override
    public void onTabSelected()
    {

    }

    private class TestLoopAdapter extends LoopPagerAdapter
    {
        private String[] imgs = {
                "assets://main_banner01.png",
                "assets://main_banner01.png",
                "assets://main_banner01.png"
        };

        public TestLoopAdapter(RollPagerView viewPager)
        {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position)
        {
            ImageView view = new ImageView(container.getContext());
            x.image().bind(view, imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return view;
        }

        @Override
        public int getRealCount()
        {
            return imgs.length;
        }

    }

    @Event(value = {R.id.ll_playcard, R.id.ll_newpoint, R.id.ll_pointsearch, R.id.ll_pointmanage, R.id.ll_push, R.id.ll_public, R.id.ll_worknotes, R.id.ll_more})
    private void iconClick(View view)
    {
        switch (view.getId()) {
            case R.id.ll_playcard://打卡
                break;
            case R.id.ll_newpoint://新开网点
                break;
            case R.id.ll_pointsearch://网点搜索
                break;
            case R.id.ll_pointmanage://网点管理
                break;
            case R.id.ll_push://推广
                break;
            case R.id.ll_public://公告
                break;
            case R.id.ll_worknotes://工作日记
                break;
            case R.id.ll_more://更多
                ToastUtils.Infotoast(mActivity,"开发升级中......");
                break;
        }
    }
}
