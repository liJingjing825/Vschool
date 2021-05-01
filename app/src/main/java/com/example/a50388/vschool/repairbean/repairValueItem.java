package com.example.a50388.vschool.repairbean;

/**
 * Created by 50388 on 2018/8/25.
 */

public
class repairValueItem {

    private long id;
    private String isRepaire;
    private String object;
    private String isDormotory;
    private String hostNO;
    private String position;
    private String numberStamp;
    private String pictureImage;



    private String tellphone;  //联系方式


    public repairValueItem(){}
    public
    repairValueItem(long id, String isRepaire, String object, String hostNO, String position, String numberStamp, String pictureImage,String tellphone) {
        this.id = id;
        this.isRepaire = isRepaire;
        this.object = object;
        this.hostNO = hostNO;
        this.position = position;
        this.numberStamp = numberStamp;
        this.pictureImage = pictureImage;
        this.tellphone=tellphone;
    }

    public
    long getId() {
        return id;
    }

    public
    void setId(long id) {
        this.id = id;
    }

    public
    String getIsRepaire() {
        return isRepaire;
    }

    public
    String getIsDormotory() {
        return isDormotory;
    }

    public
    void setIsDormotory(String isDormotory) {
        this.isDormotory = isDormotory;
    }

    public
    void setIsRepaire(String isRepaire) {
        this.isRepaire = isRepaire;
    }

    public
    String getObject() {
        return object;
    }

    public
    void setObject(String object) {
        this.object = object;
    }

    public
    String getHostNO() {
        return hostNO;
    }

    public
    void setHostNO(String hostNO) {
        this.hostNO = hostNO;
    }

    public
    String getPosition() {
        return position;
    }

    public
    void setPosition(String position) {
        this.position = position;
    }

    public
    String getNumberStamp() {
        return numberStamp;
    }

    public
    void setNumberStamp(String numberStamp) {
        this.numberStamp = numberStamp;
    }

    public
    String getPictureImage() {
        return pictureImage;
    }

    public
    void setPictureImage(String pictureImage) {
        this.pictureImage = pictureImage;
    }

    public String getTellphone() {
        return tellphone;
    }

    public void setTellphone(String tellphone) {
        this.tellphone = tellphone;
    }
    @Override
    public
    String toString() {
        return "repairValueItem{" +
                "id=" + id +
                ", isRepaire='" + isRepaire + '\'' +
                ", object='" + object + '\'' +
                ", isDormotory='" + isDormotory + '\'' +
                ", hostNO='" + hostNO + '\'' +
                ", position='" + position + '\'' +
                ", numberStamp='" + numberStamp + '\'' +
                ", pictureImage='" + pictureImage + '\'' +
                ", tellphone='" + tellphone + '\'' +
                '}';
    }
}
