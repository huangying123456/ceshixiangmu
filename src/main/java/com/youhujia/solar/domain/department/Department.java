package com.youhujia.solar.domain.department;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by huangYing on 2017/4/17.
 */
@Entity
public class Department {
    private long id;
    private boolean isGuest;
    private String name;
    private String number;
    private String authCode;
    private byte status;
    private String mayContact;
    private String imgUrl;
    private String wxSubQrCodeValue;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "is_guest", nullable = false)
    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 512)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "number", nullable = true, length = 512)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Basic
    @Column(name = "auth_code", nullable = true, length = 512)
    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Basic
    @Column(name = "status", nullable = false)
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "may_contact", nullable = true, length = 512)
    public String getMayContact() {
        return mayContact;
    }

    public void setMayContact(String mayContact) {
        this.mayContact = mayContact;
    }

    @Basic
    @Column(name = "img_url", nullable = true, length = 512)
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Basic
    @Column(name = "wx_sub_qr_code_value", nullable = true, length = 255)
    public String getWxSubQrCodeValue() {
        return wxSubQrCodeValue;
    }

    public void setWxSubQrCodeValue(String wxSubQrCodeValue) {
        this.wxSubQrCodeValue = wxSubQrCodeValue;
    }

    @Basic
    @Column(name = "created_at", nullable = false)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at", nullable = false)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (id != that.id) return false;
        if (isGuest != that.isGuest) return false;
        if (status != that.status) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (authCode != null ? !authCode.equals(that.authCode) : that.authCode != null) return false;
        if (mayContact != null ? !mayContact.equals(that.mayContact) : that.mayContact != null) return false;
        if (imgUrl != null ? !imgUrl.equals(that.imgUrl) : that.imgUrl != null) return false;
        if (wxSubQrCodeValue != null ? !wxSubQrCodeValue.equals(that.wxSubQrCodeValue) : that.wxSubQrCodeValue != null)
            return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (isGuest ? 1 : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (number != null ? number.hashCode() : 0);
        result = 31 * result + (authCode != null ? authCode.hashCode() : 0);
        result = 31 * result + (int) status;
        result = 31 * result + (mayContact != null ? mayContact.hashCode() : 0);
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        result = 31 * result + (wxSubQrCodeValue != null ? wxSubQrCodeValue.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }
}
