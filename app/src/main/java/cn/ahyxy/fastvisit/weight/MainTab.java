package cn.ahyxy.fastvisit.weight;


import cn.ahyxy.fastvisit.R;
import cn.ahyxy.fastvisit.app.ui.fragment.MainFragment;
import cn.ahyxy.fastvisit.app.ui.fragment.MyConversationListFragment;
import cn.ahyxy.fastvisit.app.ui.fragment.MyFragment;
import cn.ahyxy.fastvisit.app.ui.friend.fragment.FriendListFragment;

public enum MainTab
{

    MSG(0, "消息", R.drawable.tab_icon_nav1,
            MyConversationListFragment.class),
    ADDRESSBOOK(1, "通讯录", R.drawable.tab_icon_nav2,
            FriendListFragment.class),
    WORK(2, "工作", R.drawable.tab_icon_nav3,
            MainFragment.class),
    CODE(2, "我的", R.drawable.tab_icon_nav4,
            MyFragment.class);


    private int idx;
    private String resName;
    private int resIcon;
    private Class<?> clz;

    private MainTab(int idx, String resName, int resIcon, Class<?> clz)
    {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx()
    {
        return idx;
    }

    public void setIdx(int idx)
    {
        this.idx = idx;
    }

    public String getResName()
    {
        return resName;
    }

    public void setResName(String resName)
    {
        this.resName = resName;
    }

    public int getResIcon()
    {
        return resIcon;
    }

    public void setResIcon(int resIcon)
    {
        this.resIcon = resIcon;
    }

    public Class<?> getClz()
    {
        return clz;
    }

    public void setClz(Class<?> clz)
    {
        this.clz = clz;
    }
}
