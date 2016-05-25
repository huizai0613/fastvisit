package cn.ahyxy.fastvisit.app.bean;

/**
 * Created by zack on 2016/5/25.
 */
public class DiaryBean {
    /**
     * id : 2
     * s_id : 20
     * d_id : 1
     * problem : 你好吗
     * profited : 不好
     * plan : 一下
     * createtime : 1464142706
     * status : 1
     */

    private int id;
    private int s_id;
    private int d_id;
    private String problem;
    private String profited;
    private String plan;
    private int createtime;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public int getD_id() {
        return d_id;
    }

    public void setD_id(int d_id) {
        this.d_id = d_id;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getProfited() {
        return profited;
    }

    public void setProfited(String profited) {
        this.profited = profited;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public int getCreatetime() {
        return createtime;
    }

    public void setCreatetime(int createtime) {
        this.createtime = createtime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
