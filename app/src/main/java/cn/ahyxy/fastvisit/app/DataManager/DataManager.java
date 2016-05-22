package cn.ahyxy.fastvisit.app.DataManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.LogUtil;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.ahyxy.fastvisit.app.DataManager.parameter.AdvancedPOSParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.AllUserParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.CommitOrderParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.CreateOutletParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.GetOrderListParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.GetOutletCategoriesParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.GetOutletListParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.GetProductListParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.HotProductsParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.OutletSearchHotParam;
import cn.ahyxy.fastvisit.app.DataManager.parameter.OutletSearchResultParam;
import cn.ahyxy.fastvisit.app.bean.HotProductBean;
import cn.ahyxy.fastvisit.app.bean.POSBean;
import cn.ahyxy.fastvisit.app.bean.OutletCategoryBean;
import cn.ahyxy.fastvisit.app.bean.ProductBean;
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

    public static void getOutletSearchResult(String id, String dId, String keyword, BaseCallBackJsonArray baseCallBackJsonArray) {
        LogUtil.d("getOutletSearchResult id:" + id + ", dId:" + dId + ", keyword:" + keyword);
        x.http().post(new OutletSearchResultParam(id, dId, keyword), baseCallBackJsonArray);
    }

    public static void getOutletList(String id, BaseCallBackJsonArray baseCallBackJsonArray) {
        LogUtil.d("getOutletList id:" + id);
        x.http().post(new GetOutletListParam(id), baseCallBackJsonArray);
    }

    public static void getOutletCategories(String dId, BaseCallBackJsonArray baseCallBackJsonArray) {
        LogUtil.d("getOutletCategories dId:" + dId);
        x.http().post(new GetOutletCategoriesParam(dId), baseCallBackJsonArray);
    }

    public static void createOutlet(String dId, String cateOne, String cateTwo, String tX, String tY, String id,
                                    String name, String address, String contactName, String tel, String remark,
                                    BaseCallBackJsonObject baseCallBackJsonObject) {
        CreateOutletParam createOutletParam = new CreateOutletParam(dId, cateOne, cateTwo, tX, tY, id, name, address, contactName, tel, remark);
        LogUtil.d("createOutlet dId:" + dId);
        x.http().post(createOutletParam, baseCallBackJsonObject);
    }

    public static void getProductList(String dId, BaseCallBackJsonArray baseCallBackJsonArray) {
        GetProductListParam param = new GetProductListParam(dId);
        LogUtil.d("getProductList dId:" + dId);
        x.http().post(param, baseCallBackJsonArray);
    }

    public static void commitOrder(HashMap<String, String> map, BaseCallBackJsonObject baseCallBackJsonObject) {
        x.http().post(new CommitOrderParam(map), baseCallBackJsonObject);
    }

    public static void getOrderList(String id, BaseCallBackJsonArray baseCallBackJsonArray) {
        LogUtil.d("getOrderList id:" + id);
        x.http().post(new GetOrderListParam(id), baseCallBackJsonArray);
    }

    public static void getHotProducts(String dId, BaseCallBackJsonArray baseCallBackJsonArray) {
        HotProductsParam hotProductsParam = new HotProductsParam(dId);
        LogUtil.d("getHotProducts dId:" + dId);
        x.http().post(hotProductsParam, baseCallBackJsonArray);
    }

    public static List<POSBean> jsonArrayToPOSBeanList(JSONArray result) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<POSBean>>(){}.getType();
        return gson.fromJson(result.toString(), type);
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

    public static List<HotProductBean> jsonArrayToHotProductList(JSONArray result) {
        List<HotProductBean> list = new ArrayList<>();
        int length = result.length();
        try {
            for (int i = 0; i < length; i++) {
                HotProductBean bean = new HotProductBean();
                JSONObject object = result.getJSONObject(i);
                bean.setPercent((float) object.getDouble("percent"));
                bean.setColor(object.getString("color"));
                bean.setName(object.getString("name"));
                list.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<ProductBean> jsonArrayToProductList(JSONArray result) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<ProductBean>>(){}.getType();
        return gson.fromJson(result.toString(), type);
    }

}
