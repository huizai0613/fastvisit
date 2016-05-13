package cn.ahyxy.fastvisit.app.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.ahyxy.fastvisit.R;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by zack on 2016/5/13.
 */
public class MyConversationListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversation_list, container, false);

        ConversationListFragment fragment = new ConversationListFragment();
        Uri uri = Uri.parse("rong://" +getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "true")
                .build();
        fragment.setUri(uri);

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//xxx 为你要加载的 Id
        transaction.add(R.id.fragment_content, fragment);
        transaction.commit();
        return view;
    }
}
