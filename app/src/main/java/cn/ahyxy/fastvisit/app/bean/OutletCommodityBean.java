package cn.ahyxy.fastvisit.app.bean;

import java.util.List;

/**
 * Created by zack on 2016/5/27.
 */
public class OutletCommodityBean {
    /**
     * id : 1
     * t_id : 1
     * c_id : 8
     * d_id : 1
     * s_id : null
     * c_stock : null
     * c_unit : null
     * c_price : null
     * c_policy : null
     * createtime : null
     * commodity : {"id":8,"d_id":1,"name":"国窖1573","barcode":"2111232587575JX","stock":"300","policy":{"start_time":0,"end_time":0,"tcids":"","content":""},"price":[{"tcid":"1","price":"123"},{"tcid":"2","price":"123"},{"tcid":"3","price":"123"},{"tcid":"4","price":"123"}],"cate":1,"spec":2,"unit":3,"commission":null,"status":1,"create_time":1463895175,"unit_two":3,"unit_num":12,"unit_name":"瓶","spec_name":"250ml"}
     */

    private int id;
    private int t_id;
    private int c_id;
    private int d_id;
    private Object s_id;
    private Object c_stock;
    private Object c_unit;
    private Object c_price;
    private Object c_policy;
    private Object createtime;
    /**
     * id : 8
     * d_id : 1
     * name : 国窖1573
     * barcode : 2111232587575JX
     * stock : 300
     * policy : {"start_time":0,"end_time":0,"tcids":"","content":""}
     * price : [{"tcid":"1","price":"123"},{"tcid":"2","price":"123"},{"tcid":"3","price":"123"},{"tcid":"4","price":"123"}]
     * cate : 1
     * spec : 2
     * unit : 3
     * commission : null
     * status : 1
     * create_time : 1463895175
     * unit_two : 3
     * unit_num : 12
     * unit_name : 瓶
     * spec_name : 250ml
     */

    private CommodityEntity commodity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getD_id() {
        return d_id;
    }

    public void setD_id(int d_id) {
        this.d_id = d_id;
    }

    public Object getS_id() {
        return s_id;
    }

    public void setS_id(Object s_id) {
        this.s_id = s_id;
    }

    public Object getC_stock() {
        return c_stock;
    }

    public void setC_stock(Object c_stock) {
        this.c_stock = c_stock;
    }

    public Object getC_unit() {
        return c_unit;
    }

    public void setC_unit(Object c_unit) {
        this.c_unit = c_unit;
    }

    public Object getC_price() {
        return c_price;
    }

    public void setC_price(Object c_price) {
        this.c_price = c_price;
    }

    public Object getC_policy() {
        return c_policy;
    }

    public void setC_policy(Object c_policy) {
        this.c_policy = c_policy;
    }

    public Object getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Object createtime) {
        this.createtime = createtime;
    }

    public CommodityEntity getCommodity() {
        return commodity;
    }

    public void setCommodity(CommodityEntity commodity) {
        this.commodity = commodity;
    }

    public static class CommodityEntity {
        private int id;
        private int d_id;
        private String name;
        private String barcode;
        private String stock;
        /**
         * start_time : 0
         * end_time : 0
         * tcids :
         * content :
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
        private String unit_name;
        private String spec_name;
        /**
         * tcid : 1
         * price : 123
         */

        private List<ProductBean.PriceEntity> price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getD_id() {
            return d_id;
        }

        public void setD_id(int d_id) {
            this.d_id = d_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public String getSpec_name() {
            return spec_name;
        }

        public void setSpec_name(String spec_name) {
            this.spec_name = spec_name;
        }

        public List<ProductBean.PriceEntity> getPrice() {
            return price;
        }

        public void setPrice(List<ProductBean.PriceEntity> price) {
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

    }
}
