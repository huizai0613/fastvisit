package cn.ahyxy.fastvisit.app.ui;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.baseui.BaseActivity;

/**
 * Created by yexiangyu on 16/4/14.
 */
@ContentView(R.layout.app_guide)
public class AppGuide extends BaseActivity
{
    @ViewInject(R.id.guide_viewpager)
    private RollPagerView guideVP;


    @Override
    public void initWidget()
    {
        super.initWidget();
        guideVP.setPlayDelay(3000);
        guideVP.setAnimationDurtion(500);
        guideVP.setAdapter(new TestLoopAdapter(guideVP));
        guideVP.setHintView(new ColorPointHintView(this, Color.parseColor("#F36300"), Color.parseColor("#C8C2C2")));

    }

    private class TestLoopAdapter extends LoopPagerAdapter
    {
        private String[] imgs = {
                "assets://banner_1.png",
                "assets://banner_1.png",
                "assets://banner_1.png",
                "assets://banner_1.png"
        };

        public TestLoopAdapter(RollPagerView viewPager)
        {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position)
        {
            LinearLayout linearLayout = new LinearLayout(container.getContext());
            ImageView view = new ImageView(container.getContext());
            x.image().bind(view, imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            LinearLayout.LayoutParams Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(220));
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.addView(view, Params);
            return linearLayout;
        }

        @Override
        public int getRealCount()
        {
            return imgs.length;
        }

    }

    @Event(value = R.id.guide_jump)
    private void jumpLogin(View view)
    {
        skipActivity(mActivity, LoginActivity.class);
    }
}
