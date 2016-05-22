package cn.ahyxy.fastvisit.app.bean;

import android.os.Parcel;

import java.util.List;

/**
 * Created by zack on 2016/5/22.
 */
public class ProductBean extends PostProductBean {
    /**
     * id : 5
     * d_id : 1
     * name : 国窖1573
     * barcode : 2111232587575JX
     * stock : 500
     * policy : {"start_time":1247186700000,"end_time":1462513658000,"tcids":"1,2","content":"123213"}
     * price : [{"tcid":"1","price":"158"},{"tcid":"2","price":"158"},{"tcid":"3","price":"158"},{"tcid":"4","price":"158"}]
     * cate : 1
     * spec : 2
     * unit : 2
     * commission : null
     * status : 1
     * create_time : 1462084985
     * unit_two : 3
     * unit_num : 12
     * unit_name : 箱
     * spec_name : 250ml
     */

    private int d_id;
    private String barcode;
    private String stock;
    /**
     * start_time : 1247186700000
     * end_time : 1462513658000
     * tcids : 1,2
     * content : 123213
     */

    private PolicyEntity policy;
    private int cate;
    private int spec;
    private int unit;
    private Object commission;
    private int status;
    private int create_time;
    private int unit_two;
    private int unit_num;
    /**
     * tcid : 1
     * price : 158
     */

    private List<PriceEntity> price;

    protected ProductBean(Parcel in) {
        super(in);
    }

    public int getD_id() {
        return d_id;
    }

    public void setD_id(int d_id) {
        this.d_id = d_id;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public PolicyEntity getPolicy() {
        return policy;
    }

    public void setPolicy(PolicyEntity policy) {
        this.policy = policy;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }

    public int getSpec() {
        return spec;
    }

    public void setSpec(int spec) {
        this.spec = spec;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public Object getCommission() {
        return commission;
    }

    public void setCommission(Object commission) {
        this.commission = commission;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public int getUnit_two() {
        return unit_two;
    }

    public void setUnit_two(int unit_two) {
        this.unit_two = unit_two;
    }

    public int getUnit_num() {
        return unit_num;
    }

    public void setUnit_num(int unit_num) {
        this.unit_num = unit_num;
    }

    public List<PriceEntity> getPrice() {
        return price;
    }

    public void setPrice(List<PriceEntity> price) {
        this.price = price;
    }

    public static class PolicyEntity {
        private long start_time;
        private long end_time;
        private String tcids;
        private String content;

        public long getStart_time() {
            return start_time;
        }

        public void setStart_time(long start_time) {
            this.start_time = start_time;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        public String getTcids() {
            return tcids;
        }

        public void setTcids(String tcids) {
            this.tcids = tcids;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class PriceEntity {
        private String tcid;
        private String price;

        public String getTcid() {
            return tcid;
        }

        public void setTcid(String tcid) {
            this.tcid = tcid;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
