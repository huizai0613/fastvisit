package cn.ahyxy.fastvisit.app.ui.friend.fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.DataManager.AllUserManager;
import cn.ahyxy.fastvisit.app.DataManager.UserManager;
import cn.ahyxy.fastvisit.app.ui.friend.adapter.FriendListAdapter;
import cn.ahyxy.fastvisit.app.ui.friend.model.Friend;
import cn.ahyxy.fastvisit.app.ui.friend.widget.PinnedHeaderListView;
import cn.ahyxy.fastvisit.app.ui.friend.widget.SwitchGroup;
import cn.ahyxy.fastvisit.app.ui.friend.widget.SwitchItemView;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonArray;
import cn.ahyxy.fastvisit.baseui.LsSimpleHomeFragment;
import cn.ahyxy.fastvisit.baseui.titlebar.TitleBar;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

public class FriendListFragment extends LsSimpleHomeFragment implements SwitchGroup.ItemHander, OnClickListener, TextWatcher, FriendListAdapter.OnFilterFinished, OnItemClickListener {

    private static final String TAG = FriendListFragment.class.getSimpleName();
    protected FriendListAdapter mAdapter;
    @ViewInject(R.id.de_ui_friend_list)
    private PinnedHeaderListView mListView;
    @ViewInject(R.id.de_ui_friend_message)
    private SwitchGroup mSwitchGroup;
    @ViewInject(R.id.de_ui_search)
    private EditText mEditText;

    protected List<Friend> mFriendsList;

    @Override
    protected int getLayoutId() {
        return R.layout.de_list_friend;
    }

    @Override
    protected void initWidget(View parentView) {
        super.initWidget(parentView);
        super.initWidget(parentView);
        TitleBar titleBar = TitleBar.getInstance(parentView);
        titleBar.setTitlebarMTv("有薪快仿", "#000000");
        titleBar.setTitlebarRightIv(R.mipmap.icon_r, null);
        titleBar.setTitlebarLeftTv("合肥", "#EA6800", R.mipmap.icon_l, null);
        titleBar.getTitlebarLeftTv().setPadding(20, 0, 20, 0);

        mListView.setPinnedHeaderView(LayoutInflater.from(this.getActivity()).inflate(R.layout.de_item_friend_index,
                mListView, false));

        mListView.setFastScrollEnabled(false);

        mListView.setOnItemClickListener(this);
        mSwitchGroup.setItemHander(this);
        mEditText.addTextChangedListener(this);

        mListView.setHeaderDividersEnabled(false);
        mListView.setFooterDividersEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected boolean needAutoRefresh() {
        return mFriendsList == null || mFriendsList.isEmpty();
    }

    @Override
    protected void getDataFronServer() {
        AllUserManager.getAllUser(String.valueOf(UserManager.getUserBean().getD_id()),
//                String.valueOf(UserManager.getUserBean().getId()),
                "19",
                UserManager.getUserBean().getToken(),
                new BaseCallBackJsonArray(getActivity()) {
                    @Override
                    public void onErrorJson(Throwable ex, boolean isOnCallback) {
                        LogUtil.e("ERROR:" + ex.toString());
                    }

                    @Override
                    public void onSuccessJsonArray(JSONArray result) {
                        int length = result.length();
                        mFriendsList = new ArrayList<>();

                        for (int i = 0; i < length; i++) {
                            try {
                                JSONObject jsonObject = result.getJSONObject(i);
                                Friend friend = new Friend();
                                friend.setNickname(jsonObject.getString("name"));
                                friend.setPortrait(jsonObject.getString("head_img"));
                                friend.setUserId(String.valueOf(jsonObject.getInt("id")));
                                mFriendsList.add(friend);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mFriendsList = sortFriends(mFriendsList);
                        mAdapter = new FriendListAdapter(getActivity(), mFriendsList);
                        mListView.setAdapter(mAdapter);
                        fillData();
                    }
                });
    }

    private final void fillData() {
        mAdapter.removeAll();
        mAdapter.setAdapterData(mFriendsList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFilterFinished() {
        if (mFriendsList != null && mFriendsList.size() == 0) {
            return;
        }

        if (mAdapter == null || mAdapter.isEmpty()) {
        } else {
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mAdapter != null) {
            mAdapter.getFilter().filter(s);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {

        if (v instanceof SwitchItemView) {
            CharSequence tag = ((SwitchItemView) v).getText();

            if (mAdapter != null && mAdapter.getSectionIndexer() != null) {
                Object[] sections = mAdapter.getSectionIndexer().getSections();
                int size = sections.length;

                for (int i = 0; i < size; i++) {
                    if (tag.equals(sections[i])) {
                        int index = mAdapter.getPositionForSection(i);
                        mListView.setSelection(index + mListView.getHeaderViewsCount());
                        break;
                    }
                }
            }
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object tagObj = view.getTag();

        if (tagObj != null && tagObj instanceof FriendListAdapter.ViewHolder) {
            FriendListAdapter.ViewHolder viewHolder = (FriendListAdapter.ViewHolder) tagObj;
//            mAdapter.onItemClick(viewHolder.friend.getUserId(), viewHolder.choice);
            if (RongIM.getInstance() != null) {
                RongIM.getInstance().startPrivateChat(getActivity(), viewHolder.friend.getUserId(), viewHolder.friend.getNickname());
            }
            return;
        }
    }

    @Override
    public void onDestroyView() {
        if (mAdapter != null) {
            mAdapter.destroy();
            mAdapter = null;
        }
        super.onDestroyView();
    }

    private ArrayList<Friend> sortFriends(List<Friend> friends) {

        String[] searchLetters = getResources().getStringArray(R.array.de_search_letters);

        HashMap<String, ArrayList<Friend>> userMap = new HashMap<String, ArrayList<Friend>>();

        ArrayList<Friend> friendsArrayList = new ArrayList<Friend>();

        for (Friend friend : friends) {
            String letter = new String(new char[]{friend.getSearchKey()});

            if (userMap.containsKey(letter)) {
                ArrayList<Friend> friendList = userMap.get(letter);
                friendList.add(friend);
            } else {
                ArrayList<Friend> friendList = new ArrayList<Friend>();
                friendList.add(friend);
                userMap.put(letter, friendList);
            }
        }

        for (int i = 0; i < searchLetters.length; i++) {
            String letter = searchLetters[i];
            ArrayList<Friend> fArrayList = userMap.get(letter);
            if (fArrayList != null) {
                friendsArrayList.addAll(fArrayList);
            }
        }

        return friendsArrayList;
    }

    @Override
    public void onTabSelected() {

    }
}
