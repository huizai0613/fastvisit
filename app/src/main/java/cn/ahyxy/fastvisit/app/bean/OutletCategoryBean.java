package cn.ahyxy.fastvisit.app.bean;

/**
 * Created by zack on 2016/5/19.
 */
public class OutletCategoryBean {
    /**
     * id : 1
     * cate_name : KA
     * p_id : 0
     * d_id : 1
     */
    public static int MAIN_CATEGORY_P_ID = 0;
    private int id;
    private String cate_name;
    private int p_id;
    private int d_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getD_id() {
        return d_id;
    }

    public void setD_id(int d_id) {
        this.d_id = d_id;
    }
}
