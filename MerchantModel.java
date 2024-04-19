package Quickbites;

import java.io.Serializable;
import java.util.Date;

/**
 * Merchant information

 */
public class MerchantModel implements Serializable {

    /**
     * id
     */
    private Integer id;

    /**
     * Merchant code

     */
    private String merchantNo;

    /**
     * Business Name

     */
    private String merchantName;

    /**
     * Business address
     */
    private String merchantAdress;

    /**
     * phone
     */
    private String phone;

    /**
     * contact person
     */
    private String contactPerson;

    /**
     * password
     */
    private String password;

    private Date createTime;

    public MerchantModel(Integer id, String merchantNo, String merchantName, String merchantAdress, String phone, String contactPerson) {
        this.id = id;
        this.merchantNo = merchantNo;
        this.merchantName = merchantName;
        this.merchantAdress = merchantAdress;
        this.phone = phone;
        this.contactPerson = contactPerson;
    }

    public MerchantModel(Integer id, String merchantNo, String merchantName, String merchantAdress, String phone, String contactPerson, String password, Date createTime) {
        this.id = id;
        this.merchantNo = merchantNo;
        this.merchantName = merchantName;
        this.merchantAdress = merchantAdress;
        this.phone = phone;
        this.contactPerson = contactPerson;
        this.password = password;
        this.createTime = createTime;
    }

    public MerchantModel(String merchantNo, String merchantName, String merchantAdress, String phone, String contactPerson) {
        this.merchantNo = merchantNo;
        this.merchantName = merchantName;
        this.merchantAdress = merchantAdress;
        this.phone = phone;
        this.contactPerson = contactPerson;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAdress() {
        return merchantAdress;
    }

    public void setMerchantAdress(String merchantAdress) {
        this.merchantAdress = merchantAdress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
