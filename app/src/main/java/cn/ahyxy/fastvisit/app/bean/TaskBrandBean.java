package cn.ahyxy.fastvisit.app.bean;

/**
 * Created by zack on 2016/5/22.
 */
public class TaskBrandBean {
    /**
     * id : 5
     * d_id : 1
     * s_id : 19,20,28,29
     * subject : 加强文化交流，促进企业发展，开拓企业市场
     * name : 小区送酒
     * people : null
     * item : 泸州老窖10箱
     * remark : null
     * feedback : null
     * status : 0
     * start_time : 1463040026
     * end_time : 1463639130
     * create_time : 1463040084
     * address : null
     */

    private int id;
    private int d_id;
    private String s_id;
    private String subject;
    private String name;
    private Object people;
    private String item;
    private Object remark;
    private Object feedback;
    private int status;
    private int start_time;
    private int end_time;
    private int create_time;
    private Object address;

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

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getPeople() {
        return people;
    }

    public void setPeople(Object people) {
        this.people = people;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    public Object getFeedback() {
        return feedback;
    }

    public void setFeedback(Object feedback) {
        this.feedback = feedback;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStart_time() {
        return start_time;
    }

    public void setStart_time(int start_time) {
        this.start_time = start_time;
    }

    public int getEnd_time() {
        return end_time;
    }

    public void setEnd_time(int end_time) {
        this.end_time = end_time;
    }

    public int getCreate_time() {
        return create_time;
    }

    public void setCreate_time(int create_time) {
        this.create_time = create_time;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }
}
