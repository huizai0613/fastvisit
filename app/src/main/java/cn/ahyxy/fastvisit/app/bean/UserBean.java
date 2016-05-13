package cn.ahyxy.fastvisit.app.bean;

import org.json.JSONObject;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import cn.ahyxy.fastvisit.bean.base.BaseBean;

/**
 * Created by yexiangyu on 16/5/12.
 */
@Table(name = "User")
public class UserBean extends BaseBean<UserBean>
{
    @Column(name = "id", isId = true,autoGen = false)
    private int id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "s_no")
    private String s_no;
    @Column(name = "name")
    private String name;
    @Column(name = "tel")
    private String tel;
    @Column(name = "age")
    private String age;
    @Column(name = "email")
    private String email;
    @Column(name = "token")
    private String token;


    @Column(name = "d_id")
    private int d_id;
    @Column(name = "gender")
    private int gender;
    @Column(name = "status")
    private int status;
    @Column(name = "createtime")
    private long createtime;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getS_no()
    {
        return s_no;
    }

    public void setS_no(String s_no)
    {
        this.s_no = s_no;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getAge()
    {
        return age;
    }

    public void setAge(String age)
    {
        this.age = age;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public int getD_id()
    {
        return d_id;
    }

    public void setD_id(int d_id)
    {
        this.d_id = d_id;
    }

    public int getGender()
    {
        return gender;
    }

    public void setGender(int gender)
    {
        this.gender = gender;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public long getCreatetime()
    {
        return createtime;
    }

    public void setCreatetime(long createtime)
    {
        this.createtime = createtime;
    }

    @Override
    public UserBean parserBean(JSONObject jsonObject)
    {
        id = jsonObject.optInt("id");
        d_id = jsonObject.optInt("d_id");
        gender = jsonObject.optInt("gender");
        status = jsonObject.optInt("status");
        createtime = jsonObject.optLong("createtime");


        username = jsonObject.optString("username");
        password = jsonObject.optString("password");
        s_no = jsonObject.optString("s_no");
        name = jsonObject.optString("name");
        tel = jsonObject.optString("tel");
        age = jsonObject.optString("age");
        email = jsonObject.optString("email");
        token = jsonObject.optString("token");
        return null;
    }
}
