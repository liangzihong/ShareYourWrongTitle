package Adapters;

/**
 * Created by Liang Zihong on 2018/3/6.
 */

public class Group {

    private String GroupName;
    private int ProfilePhoto;
    private String LastRecord;

    public Group(String groupName,int profilePhoto,String lastRecord){
        GroupName=groupName;
        ProfilePhoto=profilePhoto;
        LastRecord=lastRecord;
    }

    public String getGroupName(){return GroupName;}
    public int getProfilePhoto(){return ProfilePhoto;}
    public String getLastRecord(){return LastRecord;}
}
