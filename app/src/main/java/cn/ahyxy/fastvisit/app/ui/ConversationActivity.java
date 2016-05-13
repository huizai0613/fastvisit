package cn.ahyxy.fastvisit.app.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.baseui.BaseActivity;
import cn.ahyxy.fastvisit.baseui.titlebar.TitleBar;
import io.rong.imlib.model.Conversation;

@ContentView(R.layout.activity_conversation)
public class ConversationActivity extends BaseActivity {

    /**
     * 目标 Id
     */
    private String mTargetId;

    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String mTargetIds;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    @Override
    public void initWidget() {
        super.initWidget();
        Intent intent = getIntent();
        Uri uri = intent.getData();
        LogUtil.d("DATA:" + getIntent().getData());
        LogUtil.d("TITLE:" + uri.getQueryParameter("title"));
        TitleBar instance = TitleBar.getInstance(mBaseActivity);
        instance.initDefaultBackTitle(mBaseActivity, uri.getQueryParameter("title"));
    }
}
