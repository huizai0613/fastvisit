package cn.ahyxy.fastvisit.app.ui.friend.model;

import android.text.TextUtils;

import com.sea_monster.resource.Resource;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

import cn.ahyxy.fastvisit.utils.pinyin.PinyinHelper;

@Table(name = "Friend")
public class Friend implements Serializable, IFilterModel {

    @Column(name = "userId", isId = true, autoGen = false)
    private String userId;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "nicknamePinyin")
    private String nicknamePinyin;
    @Column(name = "portrait")
    private String portrait;
    @Column(name = "searchKey")
    private char searchKey;
    private Resource portraitResource;
    private boolean isSelected = false;
    private boolean isAdd = false;

    public Friend() {

    }

    public Friend(String userId, String nickname, String portrait) {
        this.userId = userId;
        this.nickname = nickname;
        this.portrait = portrait;


    }

    private final void createSeachKey(String nickname) {

        if (TextUtils.isEmpty(nickname)) {
            return;
        }

        nicknamePinyin = PinyinHelper.getInstance().getPinyins(nickname, "");

        if (nicknamePinyin != null && nicknamePinyin.length() > 0) {
            char key = nicknamePinyin.charAt(0);
            if (key >= 'A' && key <= 'Z') {

            } else if (key >= 'a' && key <= 'z') {
                key -= 32;
            } else if (key == '★') {
                key = '★';
            } else {
                key = '#';
            }
            searchKey = key;
        } else {
            searchKey = '#';
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
        createSeachKey(nickname);
    }

    public String getNicknamePinyin() {
        return nicknamePinyin;
    }

    public void setNicknamePinyin(String nicknamePinyin) {
        this.nicknamePinyin = nicknamePinyin;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public char getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(char searchKey) {
        this.searchKey = searchKey;
    }

    public Resource getPortraitResource() {
        return portraitResource;
    }

    public void setPortraitResource(Resource portraitResource) {
        this.portraitResource = portraitResource;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public String getFilterKey() {
        return getNickname() + getNicknamePinyin();
    }


    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        Friend friend = (Friend) obj;
        if ((this.getUserId() == friend.getUserId()) && (this.getNickname() == friend.getNickname()) && (this.getPortrait() == friend.getPortrait())) {
            return true;
        }
        return false;
    }
}
