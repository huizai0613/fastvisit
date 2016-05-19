package cn.ahyxy.fastvisit.app.DataManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.ahyxy.fastvisit.app.DataManager.parameter.AdvancedPOSParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.AllUserParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.CreateOutletParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.GetOutletCategoriesParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.OutletSearchHotParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.OutletSearchResultParam;
import cn.ahyxy.fastvisit.app.bean.POSBean;
import cn.ahyxy.fastvisit.app.bean.OutletCategoryBean;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonArray;
import cn.ahyxy.fastvisit.base.BaseCallBackJsonObject;

/**
 * Created by yexiangyu on 16/5/12.
 */
public class DataManager {
    public static void getAllUser(String dId, String id, BaseCallBackJsonArray baseCallBackJsonArray) {
        LogUtil.d("getAllUser d_id:" + dId + ", id:" + id);
        x.http().post(new AllUserParam(dId, id), baseCallBackJsonArray);
    }

    public static void getAdvancedPOS(String id, BaseCallBackJsonArray baseCallBackJsonArray) {
        LogUtil.d("getAdvancedPOS id:" + id);
        x.http().post(new AdvancedPOSParam(id), baseCallBackJsonArray);
    }

    public static void getOutletSearchHot(String id, BaseCallBackJsonArray baseCallBackJsonArray) {
        LogUtil.d("getOutletSearchHot id:" + id);
        x.http().post(new OutletSearchHotParam(id), baseCallBackJsonArray);
    }

    public static void getOutletSearchResult(String id, String keyword, BaseCallBackJsonArray baseCallBackJsonArray) {
        LogUtil.d("getOutletSearchResult id:" + id + ", keyword:" + keyword);
        x.http().post(new OutletSearchResultParam(id, keyword), baseCallBackJsonArray);
    }

    public static void getOutletCategories(String dId, BaseCallBackJsonArray baseCallBackJsonArray) {
        LogUtil.d("getOutletCategories dId:" + dId);
        x.http().post(new GetOutletCategoriesParam(dId), baseCallBackJsonArray);
    }

    public static void createOutlet(String dId, String cateOne, String cateTwo, String tX, String tY, String id,
                                    String name, String address, String contactName, String tel, String remark,
                                    BaseCallBackJsonObject baseCallBackJsonObject) {
        CreateOutletParam createOutletParam = new CreateOutletParam(dId, cateOne, cateTwo, tX, tY, id, name, address, contactName, tel, remark);
        LogUtil.d("createOutlet :" + createOutletParam.toString());
        x.http().post(createOutletParam, baseCallBackJsonObject);
    }

    public static List<POSBean> jsonArrayToPOSBeanList(JSONArray result) {
        List<POSBean> posBeanList = new ArrayList<>();
        int length = result.length();
        try {
            for (int i = 0; i < length; i++) {
                POSBean posBean = new POSBean();
                JSONObject jsonObject = result.getJSONObject(i);
                posBean.setT_name(jsonObject.getString("t_name"));
                posBean.setT_address(jsonObject.getString("t_address"));
                posBean.setCate_one(jsonObject.getInt("cate_one"));
                posBean.setT_level(jsonObject.getInt("t_level"));
                posBean.setContact_name(jsonObject.getString("contact_name"));
                posBean.setContact_tel(jsonObject.getString("contact_tel"));
                posBeanList.add(posBean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return posBeanList;
    }

    public static List<OutletCategoryBean> jsonArrayToPOSCategoryList(JSONArray result) {

        List<OutletCategoryBean> list = new ArrayList<>();
        int length = result.length();
        try {
            for (int i = 0; i < length; i++) {
                OutletCategoryBean bean = new OutletCategoryBean();
                JSONObject object = result.getJSONObject(i);
                bean.setId(object.getInt("id"));
                bean.setD_id(object.getInt("d_id"));
                bean.setP_id(object.getInt("p_id"));
                bean.setCate_name(object.getString("cate_name"));
                list.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
