package cn.ahyxy.fastvisit.bean.error;

public class NetRequestException extends Exception
{

    private SelfError selfError;

    public SelfError getSelfError()
    {
        return selfError;
    }

    public void setSelfError(SelfError selfError)
    {
        this.selfError = selfError;
    }

    public NetRequestException(SelfError selfError)
    {
        super();
// TODO Auto-generated constructor stub
        this.selfError = selfError;
    }

    public NetRequestException(String detailMessage, Throwable throwable,
                               SelfError selfError)
    {
        super(detailMessage, throwable);
// TODO Auto-generated constructor stub
        this.selfError = selfError;
    }

    public NetRequestException(String detailMessage, SelfError selfError)
    {
        super(detailMessage);
// TODO Auto-generated constructor stub
        this.selfError = selfError;
    }

    public NetRequestException(Throwable throwable, SelfError selfError)
    {
        super(throwable);
// TODO Auto-generated constructor stub
        this.selfError = selfError;
    }


}
